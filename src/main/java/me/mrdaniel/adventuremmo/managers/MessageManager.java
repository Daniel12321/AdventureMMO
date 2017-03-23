package me.mrdaniel.adventuremmo.managers;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Ability;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import me.mrdaniel.adventuremmo.data.manipulators.MMOData;
import me.mrdaniel.adventuremmo.utils.TextUtils;
import ninja.leaping.configurate.ConfigurationNode;

public class MessageManager extends MMOObject {

	private final int delay_seconds;

	private final Text dodge;
	private final Text roll;
	private final Text disarm;

	private final Text reload;

	private final String set;

	private final String levelup;
	private final String ability_recharge;
	private final String ability_activate;
	private final String ability_end;

	public MessageManager(@Nonnull final AdventureMMO mmo, @Nonnull final ConfigurationNode node) {
		super(mmo);

		this.delay_seconds = node.getNode("seconds_between_messages").getInt(5);

		String prefix = node.getNode("prefix").getString("&8[&9MMO&8]");
		if (!prefix.equals("")) { prefix += " "; }

		this.dodge = TextUtils.toText(prefix + node.getNode("dodge").getString(""));
		this.roll = TextUtils.toText(prefix + node.getNode("roll").getString(""));
		this.disarm = TextUtils.toText(prefix + node.getNode("disarm").getString(""));

		this.reload = TextUtils.toText(prefix + "&cReloaded successfully.");

		this.set = prefix + "&aYou set %player%'s %skill% level to %level%";

		this.levelup = prefix + node.getNode("levelup").getString("");
		this.ability_recharge = prefix + node.getNode("ability_recharge").getString("");
		this.ability_activate = prefix + node.getNode("ability_activate").getString("");
		this.ability_end = prefix + node.getNode("ability_end").getString("");
	}

	public void sendDodge(@Nonnull final Player p) { this.send(p, this.dodge, p.get(MMOData.class).orElse(new MMOData())); }
	public void sendRoll(@Nonnull final Player p) { this.send(p, this.roll, p.get(MMOData.class).orElse(new MMOData())); }
	public void sendDisarm(@Nonnull final Player p) { this.send(p, this.disarm, p.get(MMOData.class).orElse(new MMOData())); }
	public void sendLevelUp(@Nonnull final Player p, @Nonnull final SkillType skill, final int level) { this.send(p, TextUtils.toText(this.levelup.replace("%skill%", skill.getName()).replace("%level%", String.valueOf(level))), p.get(MMOData.class).orElse(new MMOData())); }
	public void sendAbilityRecharge(@Nonnull final Player p, final int seconds) { this.sendDelayed(p, TextUtils.toText(this.ability_recharge.replace("%seconds%", String.valueOf(seconds))), p.get(MMOData.class).orElse(new MMOData())); }
	public void sendAbilityActivate(@Nonnull final Player p, @Nonnull final Ability ability) { this.send(p, TextUtils.toText(this.ability_activate.replace("%ability%", ability.getName())), p.get(MMOData.class).orElse(new MMOData())); }
	public void sendAbilityEnd(@Nonnull final Player p, @Nonnull final Ability ability) { this.send(p, TextUtils.toText(this.ability_end.replace("%ability%", ability.getName())), p.get(MMOData.class).orElse(new MMOData())); }
	public void sendReload(@Nonnull final CommandSource src) { src.sendMessage(this.reload); }
	public void sendSet(@Nonnull final CommandSource src, @Nonnull final String player, @Nonnull final SkillType skill, final int level) { src.sendMessage(TextUtils.toText(this.set.replace("%player%", player).replace("%skill%", skill.getName()).replace("%level%", String.valueOf(level)))); }

	private void sendDelayed(@Nonnull final Player p, @Nonnull final Text txt, @Nonnull final MMOData data) {
		if (!data.isDelayActive("message_delay")) { this.send(p, txt, data); }
	}

	private void send(@Nonnull final Player p, @Nonnull final Text txt, @Nonnull final MMOData data) {
		p.sendMessage(data.getActionBar() ? ChatTypes.ACTION_BAR : ChatTypes.CHAT, txt);

		data.setDelay("message_delay", System.currentTimeMillis() + (this.delay_seconds * 1000));
		p.offer(data);
	}
}