package me.mrdaniel.adventuremmo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.adventuremmo.commands.CommandSetting;
import me.mrdaniel.adventuremmo.commands.CommandSettings;
import me.mrdaniel.adventuremmo.commands.CommandSkill;
import me.mrdaniel.adventuremmo.data.HoconPlayerDatabase;
import me.mrdaniel.adventuremmo.data.ItemDatabase;
import me.mrdaniel.adventuremmo.data.PlayerDatabase;
import me.mrdaniel.adventuremmo.data.manipulators.ActivateData;
import me.mrdaniel.adventuremmo.data.manipulators.ActivateDataBuilder;
import me.mrdaniel.adventuremmo.data.manipulators.ImmutableActivateData;
import me.mrdaniel.adventuremmo.data.manipulators.ImmutableSettingsData;
import me.mrdaniel.adventuremmo.data.manipulators.SettingsData;
import me.mrdaniel.adventuremmo.data.manipulators.SettingsDataBuilder;
import me.mrdaniel.adventuremmo.listeners.ClientListener;
import me.mrdaniel.adventuremmo.listeners.PlayerListener;
import me.mrdaniel.adventuremmo.utils.ChoiceMaps;

@Plugin(id = "adventuremmo",
	name = "AdventureMMO",
	version = "1.7.0",
	description = "A light-weight plugin that adds skills with all sorts of fun game mechanics to your server.",
	authors = {"Daniel12321"})
public class AdventureMMO {

	private final Game game;
	private final Logger logger;
	private final Path configdir;
	private final PluginContainer container;

	private ItemDatabase itemdata;
	private PlayerDatabase playerdata;
	private ChoiceMaps choices;

	private UserStorageService users;

	@Inject
	public AdventureMMO(final Game game, @ConfigDir(sharedRoot = false) final Path path, final PluginContainer container) {
		this.game = game;
		this.logger = LoggerFactory.getLogger("AdventureMMO");
		this.configdir = path;
		this.container = container;

		this.logger.info("Initializing...");

		if (!Files.exists(path)) {
			try { Files.createDirectory(path); }
			catch (final IOException exc) { this.logger.error("Failed to create main config directory: {}", exc); }
		}

		this.logger.info("Initialized successfully.");
	}

	@Listener
	public void onPreInit(@Nullable final GamePreInitializationEvent e) {
		this.logger.info("Registering custom data...");

		this.game.getDataManager().register(ActivateData.class, ImmutableActivateData.class, new ActivateDataBuilder());
		this.game.getDataManager().register(SettingsData.class, ImmutableSettingsData.class, new SettingsDataBuilder());

		this.logger.info("Registered custom data successfully.");
	}

	@Listener
	public void onInit(@Nullable final GameInitializationEvent e) {
		this.logger.info("Loading...");

		this.playerdata = new HoconPlayerDatabase(this, this.configdir.resolve("playerdata"));
		this.itemdata = new ItemDatabase(this, this.configdir.resolve("itemdata.conf"));
		this.choices = new ChoiceMaps();

		CommandSpec skill = CommandSpec.builder().description(Text.of(TextColors.BLUE, "AdventureMMO | Skills Command"))
				.arguments(GenericArguments.optionalWeak(GenericArguments.choices(Text.of("skill"), this.choices.getSkills())))
				.executor(new CommandSkill(this))
				.permission("mmo.skills")
				.build();
		this.game.getCommandManager().register(this, skill, "skill", "skills", "mmoskill", "mmoskills");

		CommandSpec setting = CommandSpec.builder().description(Text.of(TextColors.BLUE, "AdventureMMO | Setting Command"))
				.arguments(GenericArguments.choices(Text.of("setting"), this.choices.getSettings()), GenericArguments.bool(Text.of("value")))
				.executor(new CommandSetting(this))
				.permission("mmo.setting")
				.build();
		this.game.getCommandManager().register(this, setting, "setting", "mmosetting");

		CommandSpec settings = CommandSpec.builder().description(Text.of(TextColors.BLUE, "AdventureMMO | Settings Command"))
				.executor(new CommandSettings())
				.permission("mmo.settings")
				.build();
		this.game.getCommandManager().register(this, settings, "settings", "mmosettings");

		this.game.getEventManager().registerListeners(this, new ClientListener(this));
		this.game.getEventManager().registerListeners(this, new PlayerListener(this));

		this.logger.info("Loaded successfully.");
	}

	@Listener
	public void onPostInit(@Nullable final GamePostInitializationEvent e) {
		this.logger.info("Loading sercives...");

		this.users = this.game.getServiceManager().provide(UserStorageService.class).get();

		this.logger.info("Loaded successfully services.");
	}

	@Listener
	public void onReload(@Nullable final GameReloadEvent e) {
		this.logger.info("Reloading...");

		this.game.getEventManager().unregisterPluginListeners(this);
		this.game.getScheduler().getScheduledTasks(this).forEach(task -> task.cancel());
		this.game.getCommandManager().getOwnedBy(this).forEach(this.game.getCommandManager()::removeMapping);

		this.onInit(null);
		this.onPostInit(null);

		this.logger.info("Reloaded successfully.");
	}

	@Nonnull public Game getGame() { return this.game; }
	@Nonnull public Logger getLogger() { return this.logger; }
	@Nonnull public PluginContainer getContainer() { return this.container; }

	@Nonnull public PlayerDatabase getPlayerDatabase() { return this.playerdata; }
	@Nonnull public ItemDatabase getItemDatabase() { return this.itemdata; }
	@Nonnull public ChoiceMaps getChoices() { return this.choices; }

	@Nonnull public UserStorageService getUsers() { return this.users; }
}