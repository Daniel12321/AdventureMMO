package me.mrdaniel.adventuremmo.managers;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.data.manipulators.MMOData;
import me.mrdaniel.adventuremmo.utils.TextUtils;
import ninja.leaping.configurate.ConfigurationNode;

public class MessageManager extends MMOObject {

	private final boolean action_bar;
	private final int delay_seconds;

	private final Text dodge;
	private final Text roll;

	private final String levelup;
	private final String ability_recharge;
	private final String ability_activate;
	private final String ability_end;

	public MessageManager(@Nonnull final AdventureMMO mmo, @Nonnull final ConfigurationNode node) {
		super(mmo);

		this.action_bar = node.getNode("action_bar").getBoolean();
		this.delay_seconds = node.getNode("seconds_between_messages").getInt();

		String prefix = node.getNode("prefix").getString();
		if (!prefix.equals("")) { prefix += " "; }

		this.dodge = TextUtils.toText(prefix + node.getNode("dodge").getString());
		this.roll = TextUtils.toText(prefix + node.getNode("roll").getString());

		this.levelup = prefix + node.getNode("levelup").getString();
		this.ability_recharge = prefix + node.getNode("ability_recharge").getString();
		this.ability_activate = prefix + node.getNode("ability_activate").getString();
		this.ability_end = prefix + node.getNode("ability_end").getString();
	}

	public void sendDodge(@Nonnull final Player p) { this.send(p, this.dodge, p.get(MMOData.class).orElse(new MMOData())); }
	public void sendRoll(@Nonnull final Player p) { this.send(p, this.roll, p.get(MMOData.class).orElse(new MMOData())); }
	public void sendLevelUp(@Nonnull final Player p, @Nonnull final String skill, final int level) { this.send(p, TextUtils.toText(this.levelup.replace("%skill%", skill).replace("%level%", String.valueOf(level))), p.get(MMOData.class).orElse(new MMOData())); }
	public void sendAbilityRecharge(@Nonnull final Player p, final int seconds) { this.sendDelayed(p, TextUtils.toText(this.ability_recharge.replace("%seconds%", String.valueOf(seconds))), p.get(MMOData.class).orElse(new MMOData())); }
	public void sendAbilityActivate(@Nonnull final Player p, @Nonnull final String ability) { this.send(p, TextUtils.toText(this.ability_activate.replace("%ability%", ability)), p.get(MMOData.class).orElse(new MMOData())); }
	public void sendAbilityEnd(@Nonnull final Player p, @Nonnull final String ability) { this.send(p, TextUtils.toText(this.ability_end.replace("%ability%", ability)), p.get(MMOData.class).orElse(new MMOData())); }

	private void sendDelayed(@Nonnull final Player p, @Nonnull final Text txt, @Nonnull final MMOData data) {
		if (!data.isDelayActive("message_delay")) {
			data.setDelay("message_delay", System.currentTimeMillis() + (this.delay_seconds * 1000));
			this.send(p, txt, data);
		}
	}

	private void send(@Nonnull final Player p, @Nonnull final Text txt, @Nonnull final MMOData data) {
		if (this.action_bar) { p.sendMessage(ChatTypes.ACTION_BAR, txt); }
		else { p.sendMessage(txt); }

		data.setDelay("message_delay", System.currentTimeMillis() + (this.delay_seconds * 1000));
		p.offer(data);
	}
}