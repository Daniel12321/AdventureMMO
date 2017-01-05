package me.mrdaniel.mmo.listeners;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.data.type.OcelotType;
import org.spongepowered.api.data.type.OcelotTypes;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.animal.Horse;
import org.spongepowered.api.entity.living.animal.Ocelot;
import org.spongepowered.api.entity.living.animal.Wolf;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.Enchantments;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.Hotbar;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.data.DelayData;
import me.mrdaniel.mmo.data.MMOData;
import me.mrdaniel.mmo.enums.Ability;
import me.mrdaniel.mmo.enums.Setting;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.skills.Skill;
import me.mrdaniel.mmo.utils.EffectUtils;
import me.mrdaniel.mmo.utils.ServerUtils;

public class Abilities {

	@Nonnull public final HashMap<UUID, Ability> active;

	private static Abilities instance = null;
	public static Abilities getInstance() {
		if (instance == null) { instance = new Abilities(); }
		return instance;
	}

	private Abilities() {
		this.active = Maps.newHashMap();
	}

	public void activate(@Nonnull final Player p, @Nonnull final Ability ability) {

		p.sendMessage(Main.getInstance().getConfig().PREFIX.concat(Text.of(TextColors.GREEN, "Activated " + ability.getName() + "!")));

		MMOPlayer mmop = Main.getInstance().getMMOPlayerDatabase().getOrCreatePlayer(p.getUniqueId());
		Skill skill = mmop.getSkills().getSkill(ability.getSkillType());
		double time = Main.getInstance().getValueStore().getAbility(ability).getSecond().getValue(mmop.getSkills().getSkill(ability.getSkillType()).level);

		if (ability == Ability.SUMMON_HORSE || ability == Ability.SUMMON_WOLF || ability == Ability.SUMMON_OCELOT) {

			if (skill.level < 30 && ability == Ability.SUMMON_WOLF) { p.sendMessage(Main.getInstance().getConfig().PREFIX.concat(Text.of(TextColors.RED, "You need to be level 30 or higher to summon wolfes"))); return; }
			if (skill.level < 50 && ability == Ability.SUMMON_OCELOT) { p.sendMessage(Main.getInstance().getConfig().PREFIX.concat(Text.of(TextColors.RED, "You need to be level 50 or higher to summon ocelot"))); return; }
			if (skill.level < 100 && ability == Ability.SUMMON_HORSE) { p.sendMessage(Main.getInstance().getConfig().PREFIX.concat(Text.of(TextColors.RED, "You need to be level 100 or higher to summon horses"))); return; }

			ItemStack hand = p.getItemInHand(HandTypes.MAIN_HAND).get();

			if (hand.getQuantity() < 10 && ability == Ability.SUMMON_WOLF) { p.sendMessage(Main.getInstance().getConfig().PREFIX.concat(Text.of(TextColors.RED, "You need 10 bones to summon a wolf"))); return; }
			if (hand.getQuantity() < 10 && ability == Ability.SUMMON_OCELOT) { p.sendMessage(Main.getInstance().getConfig().PREFIX.concat(Text.of(TextColors.RED, "You need 10 fish to summon a wolf"))); return; }
			if (hand.getQuantity() < 10 && ability == Ability.SUMMON_HORSE) { p.sendMessage(Main.getInstance().getConfig().PREFIX.concat(Text.of(TextColors.RED, "You need 10 apples to summon a horse"))); return; }

			if (hand.getQuantity() == 10) { p.setItemInHand(HandTypes.MAIN_HAND, null); }
			else { hand.setQuantity(hand.getQuantity()-10); p.setItemInHand(HandTypes.MAIN_HAND, hand); }
		}

		if (mmop.getSettings().getSetting(Setting.EFFECTS)) { EffectUtils.ACTIVATEABILITY.send(p.getLocation()); }

		DelayData data = p.get(DelayData.class).orElse(new DelayData(Maps.newHashMap()));
		if (ability.getDelay() != null) { data.add(ability.getDelay(), System.currentTimeMillis() + (long) (Main.getInstance().getValueStore().getAbility(ability).getSecond().getValue(skill.level)*1000)); }
		p.offer(data);

		final boolean wepup = (ability == Ability.GIGA_DRILL_BREAKER || ability == Ability.SUPER_BREAKER);
		final int slot = (wepup) ? ((Hotbar) p.getInventory().query(Hotbar.class)).getSelectedSlotIndex() : 0;
		final ItemStack item = (wepup) ? p.getItemInHand(HandTypes.MAIN_HAND).get().copy() : null;

		if (wepup) { addEffi5(p); }
		if (ability == Ability.SUMMON_HORSE || ability == Ability.SUMMON_WOLF || ability == Ability.SUMMON_OCELOT) {

			p.offer(Keys.IS_SNEAKING, false);

			if (ability == Ability.SUMMON_WOLF) {
				Wolf wolf = (Wolf) ServerUtils.spawn(p.getLocation(), EntityTypes.WOLF);
				wolf.offer(Keys.TAMED_OWNER, Optional.of(p.getUniqueId()));
				wolf.setCreator(p.getUniqueId());
				wolf.setNotifier(p.getUniqueId());
				wolf.addPassenger(p);
			}
			else if (ability == Ability.SUMMON_OCELOT) {
				Ocelot ocelot = (Ocelot) ServerUtils.spawn(p.getLocation(), EntityTypes.OCELOT);
				ocelot.offer(Keys.OCELOT_TYPE, getRandomOcelotType());
				ocelot.offer(Keys.TAMED_OWNER, Optional.of(p.getUniqueId()));
				ocelot.setCreator(p.getUniqueId());
				ocelot.setNotifier(p.getUniqueId());
				ocelot.addPassenger(p);
			}
			else if (ability == Ability.SUMMON_HORSE) {
				Horse horse = (Horse) ServerUtils.spawn(p.getLocation(), EntityTypes.HORSE);
				horse.offer(Keys.TAMED_OWNER, Optional.of(p.getUniqueId()));
				horse.setCreator(p.getUniqueId());
				horse.setNotifier(p.getUniqueId());
				horse.addPassenger(p);
			}
			return;
		}
		this.active.put(p.getUniqueId(), ability);

		Task.builder().delay((long) time*1000, TimeUnit.MILLISECONDS).execute(() -> {
			this.active.remove(p.getUniqueId());
			if (wepup) { p.getInventory().query(Hotbar.class).query(new SlotIndex(slot)).set(item); }
			p.sendMessage(Main.getInstance().getConfig().PREFIX.concat(Text.of(TextColors.RED, ability.getName() + " expired!")));
			if (mmop.getSettings().getSetting(Setting.EFFECTS)) { EffectUtils.ENDABILITY.send(p.getLocation()); }
		}).submit(Main.getInstance());
	}

	private void addEffi5(@Nonnull final Player p) {
		ItemStack superItem = p.getItemInHand(HandTypes.MAIN_HAND).get();
		EnchantmentData ench = superItem.getOrCreate(EnchantmentData.class).get();
		int lvl = 5;
		for (ItemEnchantment itemEnch : ench.getListValue()) {
			if (itemEnch.getEnchantment() == Enchantments.EFFICIENCY) { lvl += itemEnch.getLevel(); break; }
		}
		ench.set(ench.enchantments().add(new ItemEnchantment(Enchantments.EFFICIENCY, lvl)));
		superItem.offer(ench);
		superItem.offer(Keys.UNBREAKABLE, true);
		superItem.offer(Keys.ITEM_LORE, Lists.newArrayList(Text.of(TextColors.RED, "MMO Item")));
		superItem.offer(Keys.DISPLAY_NAME, Text.of(TextColors.DARK_RED, TextStyles.BOLD, "Super Tool"));
		superItem.offer(new MMOData(true));
		p.setItemInHand(HandTypes.MAIN_HAND, superItem);
	}

	private OcelotType getRandomOcelotType() {
		int r = (int) (Math.random()*3);
		if (r == 0) { return OcelotTypes.BLACK_CAT; }
		if (r == 1) { return OcelotTypes.SIAMESE_CAT; }
		return OcelotTypes.RED_CAT;
	}
}