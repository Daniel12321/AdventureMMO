package me.mrdaniel.mmo.skills;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

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
import me.mrdaniel.mmo.io.Config;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.utils.EffectUtils;
import me.mrdaniel.mmo.utils.ServerUtils;

public class SkillManager {
	
	public static void level(MMOPlayer mmop, SkillType type) {
		Optional<Player> pOpt = Main.getInstance().getGame().getServer().getPlayer(UUID.fromString(mmop.getUUID()));
		if (!pOpt.isPresent()) { return; }
		Player p = pOpt.get();
		p.sendMessage(Config.getInstance().PREFIX.concat(Text.of(TextColors.AQUA, "Your " + type.name + " Skill went up to " + String.valueOf(mmop.getSkills().getSkill(type).level) + "!")));
		
		if (mmop.getSettings().getSetting(Setting.EFFECTS)) { EffectUtils.LEVELUP.send(p.getLocation()); }
		
		if (Config.getInstance().ECONENABLED) {
			BigDecimal money = new BigDecimal(Config.getInstance().INCREMENT * ((double)mmop.getSkills().getSkill(type).level) + Config.getInstance().START);
			EconomyService econ = Main.getInstance().getEconomyService();
			UniqueAccount acc = econ.getOrCreateAccount(p.getUniqueId()).get();
			TransactionResult result = acc.deposit(econ.getDefaultCurrency(), money, ServerUtils.getCause());
			if (result.getResult() == ResultType.SUCCESS) { p.sendMessage(Config.getInstance().PREFIX.concat(Text.of(TextColors.AQUA, "You earned ", econ.getDefaultCurrency().getSymbol(), money.toPlainString()))); }
		}
		mmop.updateTop(p.getName(), type);
	}
}