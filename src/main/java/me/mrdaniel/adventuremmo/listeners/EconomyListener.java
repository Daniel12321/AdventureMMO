package me.mrdaniel.adventuremmo.listeners;

import java.math.BigDecimal;

import javax.annotation.Nonnull;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.service.ChangeServiceProviderEvent;
import org.spongepowered.api.service.economy.EconomyService;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.event.LevelUpEvent;
import me.mrdaniel.adventuremmo.exception.ServiceException;
import me.mrdaniel.adventuremmo.io.Config;

public class EconomyListener {

	private EconomyService economy;

	private final double initial;
	private final double increment;

	public EconomyListener(@Nonnull final AdventureMMO mmo, @Nonnull final Config config) throws ServiceException {
		this.economy = mmo.getGame().getServiceManager().provide(EconomyService.class).orElseThrow(() -> new ServiceException("Failed to find Economy Service!"));
		this.initial = config.getNode("economy", "base_money").getDouble(100.0);
		this.increment = config.getNode("economy", "increment_money").getDouble(10.0);
	}

	@Listener
	public void onLevelUp(final LevelUpEvent e) {
		this.economy.getOrCreateAccount(e.getPlayer().getUniqueId()).ifPresent(account -> {
			account.deposit(this.economy.getDefaultCurrency(), new BigDecimal(this.initial + (e.getNewLevel() * this.increment)), e.getCause());
		});
	}

	@Listener
	public void onEconomyChange(final ChangeServiceProviderEvent e) {
		if (e.getNewProvider() instanceof EconomyService) {
			this.economy = (EconomyService) e.getNewProvider();
		}
	}
}