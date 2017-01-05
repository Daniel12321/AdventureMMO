package me.mrdaniel.mmo.io.players;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.spongepowered.api.service.economy.transaction.TransactionResult;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.enums.Setting;
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.skills.SkillAction;
import me.mrdaniel.mmo.skills.SkillSet;
import me.mrdaniel.mmo.utils.EffectUtils;
import me.mrdaniel.mmo.utils.ServerUtils;

public class MMOPlayer {

	@Nonnull private final String uuid;
	@Nonnull private final SkillSet skills;
	@Nonnull private final Settings settings;

	MMOPlayer(@Nonnull final String uuid, @Nonnull final SkillSet skills, @Nonnull final Settings settings) {
		this.uuid = uuid;
		this.skills = skills;
		this.settings = settings;
	}

	/**
	 * Get the players UUID in String format.
	 * 
	 * @return UUID String
	 */
	public String getUUID() { 
		return uuid; 
	}

	/**
	 * Get the player's skills.
	 * 
	 * @return SkillSet
	 */
	public SkillSet getSkills() { 
		return skills; 
	}

	/**
	 * Get the player's settings.
	 * 
	 * @return Settings
	 */
	public Settings getSettings() { 
		return settings; 
	}

	/**
	 * Process a SkillAction to add exp to a certain skill.
	 * 
	 * @param SkillAction
	 * To create a SkillAction, create a new instance of SkillAction with the exp and SkillType as parameters.
	 */
	public void process(@Nonnull final SkillAction action) {
		if (this.skills.addExp(action.getType(), action.getExp())) {

			Optional<Player> pOpt = Main.getInstance().getGame().getServer().getPlayer(UUID.fromString(this.uuid));
			if (!pOpt.isPresent()) { return; }
			Player p = pOpt.get();
			p.sendMessage(Main.getInstance().getConfig().PREFIX.concat(Text.of(TextColors.AQUA, "Your " + action.getType().getName() + " Skill went up to " + String.valueOf(this.getSkills().getSkill(action.getType()).level) + "!")));

			if (this.getSettings().getSetting(Setting.EFFECTS)) { EffectUtils.LEVELUP.send(p.getLocation()); }

			if (Main.getInstance().getConfig().ECONENABLED) {
				BigDecimal money = new BigDecimal(Main.getInstance().getConfig().INCREMENT * ((double)this.getSkills().getSkill(action.getType()).level) + Main.getInstance().getConfig().START);
				EconomyService econ = Main.getInstance().getEconomyService();
				UniqueAccount acc = econ.getOrCreateAccount(p.getUniqueId()).get();
				TransactionResult result = acc.deposit(econ.getDefaultCurrency(), money, ServerUtils.getCause());
				if (result.getResult() == ResultType.SUCCESS) { p.sendMessage(Main.getInstance().getConfig().PREFIX.concat(Text.of(TextColors.AQUA, "You earned ", econ.getDefaultCurrency().getSymbol(), money.toPlainString()))); }
			}
			this.updateTop(p.getName(), action.getType());
		}
	}

	/**
	 * Get the player total level.
	 * 
	 * @return int
	 * Total levels.
	 */
	public int totalLevels() { 
		int total = 0;
		for (SkillType type : SkillType.values()) { total += this.skills.getSkill(type).level; }
		return total;
	}

	/**
	 * Save the player.
	 */
	public void save() { 
		Main.getInstance().getMMOPlayerDatabase().save(this);
	}

	/**
	 * Update the SkillTop for this player.
	 * 
	 * @param String name
	 * The name of the player.
	 */
	public void updateTop(@Nonnull final String name) {
		Main.getInstance().getSkillTop().update(name, this);
	}

	/**
	 * Update the SkillTop for this player.
	 * 
	 * @param String name
	 * The name of the player.
	 */
	public void updateTop(@Nonnull final String name, @Nonnull final SkillType type) {
		Main.getInstance().getSkillTop().getTop(type).update(name, this.skills.getSkill(type).level);
	}
}