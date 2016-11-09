package me.mrdaniel.mmo;

import java.nio.file.Path;
import java.util.Optional;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingEvent;
import org.spongepowered.api.event.service.ChangeServiceProviderEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.economy.EconomyService;

import com.google.inject.Inject;

import me.mrdaniel.mmo.commands.CommandMMOAdmin;
import me.mrdaniel.mmo.commands.CommandMMOReload;
import me.mrdaniel.mmo.commands.CommandSettings;
import me.mrdaniel.mmo.commands.CommandShell;
import me.mrdaniel.mmo.commands.CommandSkills;
import me.mrdaniel.mmo.commands.CommandTop;
import me.mrdaniel.mmo.data.ImmutableMMOData;
import me.mrdaniel.mmo.data.MMOData;
import me.mrdaniel.mmo.data.MMODataBuilder;
import me.mrdaniel.mmo.io.AbilitiesConfig;
import me.mrdaniel.mmo.io.BlackList;
import me.mrdaniel.mmo.io.Config;
import me.mrdaniel.mmo.io.ModdedBlocks;
import me.mrdaniel.mmo.io.ModdedTools;
import me.mrdaniel.mmo.io.ValuesConfig;
import me.mrdaniel.mmo.io.blocktracking.ChunkManager;
import me.mrdaniel.mmo.io.players.MMOPlayerDatabase;
import me.mrdaniel.mmo.io.top.SkillTop;
import me.mrdaniel.mmo.listeners.AbilityListener;
import me.mrdaniel.mmo.listeners.BlockListener;
import me.mrdaniel.mmo.listeners.PlayerListener;
import me.mrdaniel.mmo.listeners.WorldListener;

@Plugin(id = "adventuremmo", name = "AdventureMMO", version = "1.6.1")
public class Main {

	@Inject
	private Logger logger;
	@Inject
	private Game game;
    @Inject
    @ConfigDir(sharedRoot = false)
    private Path path;
	private static Main instance;
	private EconomyService economyService;

	public static Main getInstance() { return instance; }
	public Game getGame() { return game; }
	public Logger getLogger() { return logger; }
	public Path getPath() { return path; }
	public EconomyService getEconomyService() { return economyService; }

	@Listener
	public void onPreInit() {
		this.game.getDataManager().register(MMOData.class, ImmutableMMOData.class, new MMODataBuilder());
	}

	@Listener
	public void onInit(GameInitializationEvent e) {
		this.logger.info("Preparing plugin");
		Main.instance = this;
		if (!path.toFile().exists()) { path.toFile().mkdir(); }

        MMOPlayerDatabase.getInstance().setPlayersPath(path.resolve("players"));
		AbilitiesConfig.getInstance().setup();
		ValuesConfig.getInstance().setup();
		Config.getInstance().setup();
		SkillTop.getInstance().setup();
		ModdedBlocks.getInstance().setup();
		ModdedTools.getInstance().setup();
		BlackList.getInstance().setup();

		ChunkManager.getInstance().setup();

		this.game.getCommandManager().register(this, new CommandSkills(), "skill", "skills", "mmoskills", "mmoskill");
		this.game.getCommandManager().register(this, new CommandTop(), "skilltop", "skillstop", "mmoskillstop", "mmoskillstop");

		this.game.getCommandManager().register(this, new CommandMMOAdmin(), "mmoadmin");
		this.game.getCommandManager().register(this, new CommandMMOReload(), "mmoreload");
		this.game.getCommandManager().register(this, new CommandSettings(), "settings", "setting");

		if (Config.getInstance().config.getNode("commands", "/acrobatics").getBoolean()) { game.getCommandManager().register(this, new CommandShell("acrobatics"), "acrobatics"); }
		if (Config.getInstance().config.getNode("commands", "/excavation").getBoolean()) { game.getCommandManager().register(this, new CommandShell("excavation"), "excavation"); }
		if (Config.getInstance().config.getNode("commands", "/farming").getBoolean()) { game.getCommandManager().register(this, new CommandShell("farming"), "farming"); }
		if (Config.getInstance().config.getNode("commands", "/fishing").getBoolean()) { game.getCommandManager().register(this, new CommandShell("fishing"), "fishing"); }
		if (Config.getInstance().config.getNode("commands", "/mining").getBoolean()) { game.getCommandManager().register(this, new CommandShell("mining"), "mining"); }
		if (Config.getInstance().config.getNode("commands", "/salvage").getBoolean()) { game.getCommandManager().register(this, new CommandShell("salvage"), "salvage"); }
		if (Config.getInstance().config.getNode("commands", "/taming").getBoolean()) { game.getCommandManager().register(this, new CommandShell("taming"), "taming"); }
		if (Config.getInstance().config.getNode("commands", "/woodcutting").getBoolean()) { game.getCommandManager().register(this, new CommandShell("woodcutting"), "woodcutting"); }
		if (Config.getInstance().config.getNode("commands", "/repair").getBoolean()) { game.getCommandManager().register(this, new CommandShell("repair"), "repair"); }
		if (Config.getInstance().config.getNode("commands", "/swords").getBoolean()) { game.getCommandManager().register(this, new CommandShell("swords"), "swords"); }
		if (Config.getInstance().config.getNode("commands", "/axes").getBoolean()) { game.getCommandManager().register(this, new CommandShell("axes"), "axes"); }
		if (Config.getInstance().config.getNode("commands", "/unarmed").getBoolean()) { game.getCommandManager().register(this, new CommandShell("unarmed"), "unarmed"); }
		if (Config.getInstance().config.getNode("commands", "/archery").getBoolean()) { game.getCommandManager().register(this, new CommandShell("archery"), "archery"); }

		this.game.getEventManager().registerListeners(this, new WorldListener());
		this.game.getEventManager().registerListeners(this, new PlayerListener());
		this.game.getEventManager().registerListeners(this, new AbilityListener());
		this.game.getEventManager().registerListeners(this, new BlockListener());

		this.logger.info("Plugin Enabled");
    }
	
	@Listener
	public void onPostInit(GamePostInitializationEvent event) {
		Optional<EconomyService> optionalEconomyService = this.game.getServiceManager().provide(EconomyService.class);
		if (optionalEconomyService.isPresent()) { this.economyService = optionalEconomyService.get(); }
		else {
			if (Config.getInstance().ECONENABLED) {
				this.logger.error("No economy plugin was found!");
				this.logger.error("Disabling economy in config");
				Config.getInstance().disableEcon();
			}
		}
	}
	
	@Listener
	public void onStopping(GameStoppingEvent e) {
		this.logger.info("Saving All Data");
		MMOPlayerDatabase.getInstance().saveAll();
		ChunkManager.getInstance().writeAll();
		this.logger.info("All Data Was Saved");
	}

	@Listener
	public void onReload(GameReloadEvent e) {
		this.onStopping(null);

		game.getEventManager().unregisterPluginListeners(this);
		game.getCommandManager().getOwnedBy(this).forEach(game.getCommandManager()::removeMapping);

		this.onInit(null);
	}

	@Listener
	public void onServiceChange(ChangeServiceProviderEvent e) {
		if (e.getNewProvider() instanceof EconomyService) {
			this.economyService = (EconomyService) e.getNewProvider();
		}
	}
}