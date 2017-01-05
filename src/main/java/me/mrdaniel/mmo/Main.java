package me.mrdaniel.mmo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingEvent;
import org.spongepowered.api.event.service.ChangeServiceProviderEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.google.inject.Inject;

import me.mrdaniel.mmo.commands.CommandAdmin;
import me.mrdaniel.mmo.commands.CommandAdminReload;
import me.mrdaniel.mmo.commands.CommandAdminSet;
import me.mrdaniel.mmo.commands.CommandAdminView;
import me.mrdaniel.mmo.commands.CommandSettings;
import me.mrdaniel.mmo.commands.CommandShell;
import me.mrdaniel.mmo.commands.CommandSkills;
import me.mrdaniel.mmo.commands.CommandTop;
import me.mrdaniel.mmo.data.DelayData;
import me.mrdaniel.mmo.data.DelayDataBuilder;
import me.mrdaniel.mmo.data.ImmutableDelayData;
import me.mrdaniel.mmo.data.ImmutableMMOData;
import me.mrdaniel.mmo.data.MMOData;
import me.mrdaniel.mmo.data.MMODataBuilder;
import me.mrdaniel.mmo.enums.Setting;
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.event.ClickListener;
import me.mrdaniel.mmo.io.BlackList;
import me.mrdaniel.mmo.io.Config;
import me.mrdaniel.mmo.io.ModdedBlocks;
import me.mrdaniel.mmo.io.ModdedTools;
import me.mrdaniel.mmo.io.ValueStore;
import me.mrdaniel.mmo.io.blocktracking.ChunkManager;
import me.mrdaniel.mmo.io.players.MMOPlayerDatabase;
import me.mrdaniel.mmo.io.top.SkillTop;
import me.mrdaniel.mmo.listeners.AbilityListener;
import me.mrdaniel.mmo.listeners.BlockListener;
import me.mrdaniel.mmo.listeners.PlayerListener;
import me.mrdaniel.mmo.listeners.WorldListener;
import me.mrdaniel.mmo.scoreboard.ScoreboardManager;
import me.mrdaniel.mmo.utils.Permissions;

@Singleton
@Plugin(id = "adventuremmo", name = "AdventureMMO", version = "1.7.0")
public class Main {

	@Inject
	@Nonnull private Logger logger;
	@Inject
	@Nonnull private Game game;

    @Inject
    @ConfigDir(sharedRoot = false)
    @Nonnull private Path configDir;

	@Nonnull private static Main instance;
	@Nonnull public static Main getInstance() { return instance; }

	@Nonnull public Game getGame() { return game; }
	@Nonnull public Logger getLogger() { return logger; }

	@Nonnull private EconomyService economyService;
	@Nonnull private MMOPlayerDatabase database;
	@Nonnull private ScoreboardManager scoreboard;
	@Nonnull private SkillTop skilltop;
	@Nonnull private Config config;
	@Nonnull private ValueStore valuestore;
	@Nonnull private BlackList blacklist;
	@Nonnull private ModdedTools moddedtools;
	@Nonnull private ModdedBlocks moddedblocks;
	@Nonnull private ChunkManager chunkmanager;

	@Listener
	public void onPreInit(@Nullable final GamePreInitializationEvent e) {
		this.game.getDataManager().register(DelayData.class, ImmutableDelayData.class, new DelayDataBuilder());
		this.game.getDataManager().register(MMOData.class, ImmutableMMOData.class, new MMODataBuilder());
	}

	@Listener
	public void onInit(@Nullable final GameInitializationEvent e) {
		this.logger.info("Loading Plugin...");
		Main.instance = this;
		if (!Files.exists(this.configDir)) {
			try { Files.createDirectory(this.configDir); }
			catch (IOException exc) { this.logger.error("Failed to create main config directory: {}", exc); }
		}

		this.database = new MMOPlayerDatabase(this.configDir.resolve("players"));
		this.scoreboard = new ScoreboardManager();
		this.skilltop = new SkillTop(this.configDir.resolve("tops"));
		this.config = new Config(this.configDir.resolve("config.conf"));
		this.valuestore = new ValueStore(this.configDir.resolve("values.conf"));
		this.blacklist = new BlackList(this.configDir.resolve("blacklist.conf"));
		this.moddedtools = new ModdedTools(this.configDir.resolve("moddedtools.conf"));
		this.moddedblocks = new ModdedBlocks(this.configDir.resolve("moddedblocks.conf"));
		this.chunkmanager = new ChunkManager(this.configDir.resolve("store"));

		CommandSpec mining = CommandSpec.builder().description(Text.of(TextColors.BLUE, "MMO | Mining Command")).executor(new CommandShell(SkillType.MINING)).build();
		CommandSpec woodcutting = CommandSpec.builder().description(Text.of(TextColors.BLUE, "MMO | Woodcutting Command")).executor(new CommandShell(SkillType.WOODCUTTING)).build();
		CommandSpec excavation = CommandSpec.builder().description(Text.of(TextColors.BLUE, "MMO | Excavation Command")).executor(new CommandShell(SkillType.EXCAVATION)).build();
		CommandSpec fishing = CommandSpec.builder().description(Text.of(TextColors.BLUE, "MMO | Fishing Command")).executor(new CommandShell(SkillType.FISHING)).build();
		CommandSpec farming = CommandSpec.builder().description(Text.of(TextColors.BLUE, "MMO | Farming Command")).executor(new CommandShell(SkillType.FARMING)).build();
		CommandSpec acrobatics = CommandSpec.builder().description(Text.of(TextColors.BLUE, "MMO | Acrobatics Command")).executor(new CommandShell(SkillType.ACROBATICS)).build();
		CommandSpec taming = CommandSpec.builder().description(Text.of(TextColors.BLUE, "MMO | Taming Command")).executor(new CommandShell(SkillType.TAMING)).build();
		CommandSpec salvage = CommandSpec.builder().description(Text.of(TextColors.BLUE, "MMO | Salvage Command")).executor(new CommandShell(SkillType.SALVAGE)).build();
		CommandSpec repair = CommandSpec.builder().description(Text.of(TextColors.BLUE, "MMO | Repair Command")).executor(new CommandShell(SkillType.REPAIR)).build();
		CommandSpec swords = CommandSpec.builder().description(Text.of(TextColors.BLUE, "MMO | Swords Command")).executor(new CommandShell(SkillType.SWORDS)).build();
		CommandSpec axes = CommandSpec.builder().description(Text.of(TextColors.BLUE, "MMO | Axes Command")).executor(new CommandShell(SkillType.AXES)).build();
		CommandSpec unarmed = CommandSpec.builder().description(Text.of(TextColors.BLUE, "MMO | Unarmed Command")).executor(new CommandShell(SkillType.UNARMED)).build();
		CommandSpec archery = CommandSpec.builder().description(Text.of(TextColors.BLUE, "MMO | Archery Command")).executor(new CommandShell(SkillType.ARCHERY)).build();

		CommandSpec skill = CommandSpec.builder().description(Text.of(TextColors.BLUE, "MMO | Skills Command"))
				.arguments(GenericArguments.optional(GenericArguments.enumValue(Text.of("type"), SkillType.class)))
				.executor(new CommandSkills())
//				.child(mining, "mining")
//				.child(woodcutting, "woodcutting")
//				.child(excavation, "excavation")
//				.child(fishing, "fishing")
//				.child(farming, "farming")
//				.child(acrobatics, "acrobatics")
//				.child(taming, "taming")
//				.child(salvage, "salvage")
//				.child(repair, "repair")
//				.child(swords, "swords")
//				.child(axes, "axes")
//				.child(unarmed, "unarmed")
//				.child(archery, "bows", "archery")
				.build();

		this.game.getCommandManager().register(this, skill, "skill", "skills", "mmoskill", "mmoskills");
		if (this.config.config.getNode("commands", "/mining").getBoolean()) { this.game.getCommandManager().register(this, mining, "mining"); }
		if (this.config.config.getNode("commands", "/woodcutting").getBoolean()) { this.game.getCommandManager().register(this, woodcutting, "woodcutting"); }
		if (this.config.config.getNode("commands", "/excavation").getBoolean()) { this.game.getCommandManager().register(this, excavation, "excavation"); }
		if (this.config.config.getNode("commands", "/fishing").getBoolean()) { this.game.getCommandManager().register(this, fishing, "fishing"); }
		if (this.config.config.getNode("commands", "/farming").getBoolean()) { this.game.getCommandManager().register(this, farming, "farming"); }
		if (this.config.config.getNode("commands", "/acrobatics").getBoolean()) { this.game.getCommandManager().register(this, acrobatics, "acrobatics"); }
		if (this.config.config.getNode("commands", "/taming").getBoolean()) { this.game.getCommandManager().register(this, taming, "taming"); }
		if (this.config.config.getNode("commands", "/salvage").getBoolean()) { this.game.getCommandManager().register(this, salvage, "salvage"); }
		if (this.config.config.getNode("commands", "/repair").getBoolean()) { this.game.getCommandManager().register(this, repair, "repair"); }
		if (this.config.config.getNode("commands", "/swords").getBoolean()) { this.game.getCommandManager().register(this, swords, "swords"); }
		if (this.config.config.getNode("commands", "/axes").getBoolean()) { this.game.getCommandManager().register(this, axes, "axes"); }
		if (this.config.config.getNode("commands", "/unarmed").getBoolean()) { this.game.getCommandManager().register(this, unarmed, "unarmed"); }
		if (this.config.config.getNode("commands", "/archery").getBoolean()) { this.game.getCommandManager().register(this, archery, "bows", "archery"); }

		CommandSpec top = CommandSpec.builder().description(Text.of(TextColors.BLUE, "MMO | Skills Top"))
				.arguments(GenericArguments.optional(GenericArguments.string(Text.of("type"))))
				.executor(new CommandTop())
				.build();

		this.game.getCommandManager().register(this, top, "skilltop", "skillstop", "mmoskilltop", "mmoskillstop");

		CommandSpec settings = CommandSpec.builder().description(Text.of(TextColors.BLUE, "MMO | Settings Command"))
				.arguments(GenericArguments.optionalWeak(GenericArguments.enumValue(Text.of("setting"), Setting.class)), GenericArguments.optionalWeak(GenericArguments.bool(Text.of("value"))))
				.executor(new CommandSettings())
				.build();

		this.game.getCommandManager().register(this, settings, "setting", "mmosetting", "settings", "mmosettings");

		CommandSpec view = CommandSpec.builder().description(Text.of("MMO | Admin View Command"))
				.permission(Permissions.MMO_ADMIN_VIEW_OTHERS)
				.arguments(GenericArguments.player(Text.of("other")), GenericArguments.optionalWeak(GenericArguments.enumValue(Text.of("type"), SkillType.class)))
				.executor(new CommandAdminView())
				.build();

		CommandSpec set = CommandSpec.builder().description(Text.of(TextColors.BLUE, "MMO | Admin Set Command"))
				.permission(Permissions.MMO_ADMIN_SET)
				.arguments(GenericArguments.player(Text.of("other")), GenericArguments.enumValue(Text.of("type"), SkillType.class), GenericArguments.integer(Text.of("value")))
				.executor(new CommandAdminSet())
				.build();

		CommandSpec reload = CommandSpec.builder().description(Text.of(TextColors.BLUE, "MMO | Reload Command"))
				.permission(Permissions.MMO_ADMIN_RELOAD)
				.executor(new CommandAdminReload())
				.build();

		CommandSpec admin = CommandSpec.builder().description(Text.of(TextColors.BLUE, "MMO | Admin Command"))
				.permission(Permissions.MMO_ADMIN)
				.executor(new CommandAdmin())
				.child(view, "view")
				.child(set, "set")
				.child(reload, "reload")
				.build();

		this.game.getCommandManager().register(this, admin, "mmoadmin");

		this.game.getEventManager().registerListeners(this, new WorldListener());
		this.game.getEventManager().registerListeners(this, new PlayerListener());
		this.game.getEventManager().registerListeners(this, new AbilityListener());
		this.game.getEventManager().registerListeners(this, new BlockListener());
		this.game.getEventManager().registerListeners(this, new ClickListener());

		this.logger.info("Plugin Enabled!");
    }
	
	@Listener
	public void onPostInit(@Nullable final GamePostInitializationEvent event) {
		Optional<EconomyService> optionalEconomyService = this.game.getServiceManager().provide(EconomyService.class);
		if (optionalEconomyService.isPresent()) { this.economyService = optionalEconomyService.get(); }
		else if (this.config.ECONENABLED) {
			this.logger.error("No economy plugin was found!");
			this.logger.error("Disabling economy in config");
			this.config.disableEcon();
		}
	}

	@Listener
	public void onStopping(@Nullable final GameStoppingEvent e) {
		this.logger.info("Saving All Data");
		this.database.saveAll();
		this.chunkmanager.writeAll();
		this.logger.info("All Data Was Saved");
	}

	@Listener
	public void onReload(@Nullable final GameReloadEvent e) {
		this.onStopping(null);

		this.game.getEventManager().unregisterPluginListeners(this);
		this.game.getCommandManager().getOwnedBy(this).forEach(this.game.getCommandManager()::removeMapping);
		this.game.getScheduler().getScheduledTasks(this).forEach(task -> task.cancel());

		this.onInit(null);
	}

	@Listener
	public void onServiceChange(@Nonnull final ChangeServiceProviderEvent e) {
		if (e.getNewProvider() instanceof EconomyService) {
			this.economyService = (EconomyService) e.getNewProvider();
		}
	}

	@Nonnull public EconomyService getEconomyService() { return this.economyService; }
	@Nonnull public MMOPlayerDatabase getMMOPlayerDatabase() { return this.database; }
	@Nonnull public BlackList getBlackList() { return this.blacklist; }
	@Nonnull public ScoreboardManager getScoreboardManager() { return this.scoreboard; }
	@Nonnull public SkillTop getSkillTop() { return this.skilltop; }
	@Nonnull public ValueStore getValueStore() { return this.valuestore; }
	@Nonnull public Config getConfig() { return this.config; }
	@Nonnull public ModdedTools getModdedTools() { return this.moddedtools; }
	@Nonnull public ModdedBlocks getModdedBlocks() { return this.moddedblocks; }
	@Nonnull public ChunkManager getChunkManager() { return this.chunkmanager; }
}