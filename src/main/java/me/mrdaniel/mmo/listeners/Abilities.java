package me.mrdaniel.mmo.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.animal.Horse;
import org.spongepowered.api.entity.living.animal.Ocelot;
import org.spongepowered.api.entity.living.animal.Wolf;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.Enchantments;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.Hotbar;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.enums.Ability;
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.io.Config;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.io.players.MMOPlayerDatabase;
import me.mrdaniel.mmo.skills.Skill;
import me.mrdaniel.mmo.utils.DelayWrapper;
import me.mrdaniel.mmo.utils.EffectUtils;
import me.mrdaniel.mmo.utils.ServerUtils;

public class Abilities {
	
	public HashMap<String, Ability> active;
	public HashMap<String, ArrayList<DelayWrapper>> delays;
	
	private static Abilities instance = null;
	public static Abilities getInstance() { if (instance == null) { instance = new Abilities(); } return instance; }
	private Abilities() { this.active = new HashMap<String, Ability>(); this.delays = new HashMap<String, ArrayList<DelayWrapper>>(); }
	
	public void activate(Player p, Ability ability, SkillType type) {
		
		p.sendMessage(Config.PREFIX().concat(Text.of(TextColors.GREEN, "Activated " + ability.name + "!")));
		
		MMOPlayer mmop = MMOPlayerDatabase.getInstance().getOrCreate(p.getUniqueId().toString());
		double time = ability.getValue(mmop.getSkills().getSkill(type).level);
		if (ability.millis) { time *= 1000; }
		
		if (ability == Ability.GIGA_DRILL_BREAKER || ability == Ability.SUPER_BREAKER || ability == Ability.TREE_VELLER || ability == Ability.GREEN_TERRA) {
			active.put(p.getName(), ability);
			
			EffectUtils.sendEffects(p, ParticleTypes.VILLAGER_HAPPY, 100);
			EffectUtils.sendSound(p, SoundTypes.ENTITY_FIREWORK_LAUNCH);
			
			if (delays.containsKey(p.getName())) {
				delays.get(p.getName()).add(new DelayWrapper(System.currentTimeMillis() + Config.RECHARGE_MILLIS(), ability));
			}
			else {
				delays.put(p.getName(), new ArrayList<DelayWrapper>());
				delays.get(p.getName()).add(new DelayWrapper(System.currentTimeMillis() + Config.RECHARGE_MILLIS(), ability));
			}
			
			final ItemStack item = p.getItemInHand(HandTypes.MAIN_HAND).get().copy();
			int slotPre = 0;
			
			for (int i = 0; i <= 8; i++) {
				if (p.getInventory().query(Hotbar.class).query(new SlotIndex(i)) != null) {
					if (p.getInventory().query(Hotbar.class).query(new SlotIndex(i)).peek().isPresent()) {
						if (p.getInventory().query(Hotbar.class).query(new SlotIndex(i)).peek().get().equalTo(p.getItemInHand(HandTypes.MAIN_HAND).get())) {
							slotPre = i;
							break;
						}
					}
				}
			}
			final int slot = slotPre;
			
			final boolean wepUp = (ability == Ability.SUPER_BREAKER || ability == Ability.GIGA_DRILL_BREAKER);
			if (wepUp) { addEffi5(p); }
			
			Main.getInstance().getGame().getScheduler().createTaskBuilder().delay((long) time, TimeUnit.MILLISECONDS).execute(()-> {
				if (wepUp) { p.getInventory().query(Hotbar.class).query(new SlotIndex(slot)).set(item); }
				active.remove(p.getName());
				p.sendMessage(Config.PREFIX().concat(Text.of(TextColors.RED, ability.name + " expired!")));
				EffectUtils.sendEffects(p, ParticleTypes.LAVA, 25);
				EffectUtils.sendSound(p, SoundTypes.ENTITY_FIREWORK_LAUNCH);
			}).submit(Main.getInstance());
		}
		else if (ability == Ability.SUMMON_HORSE || ability == Ability.SUMMON_WOLF || ability == Ability.SUMMON_OCELOT) {
			
			Skill skill = mmop.getSkills().getSkill(type);
			
			if (skill.level < 30 && ability == Ability.SUMMON_WOLF) { p.sendMessage(Config.PREFIX().concat(Text.of(TextColors.RED, "You need to be level 30 or higher to summon wolfes"))); return; }
			if (skill.level < 50 && ability == Ability.SUMMON_OCELOT) { p.sendMessage(Config.PREFIX().concat(Text.of(TextColors.RED, "You need to be level 50 or higher to summon ocelot"))); return; }
			if (skill.level < 100 && ability == Ability.SUMMON_HORSE) { p.sendMessage(Config.PREFIX().concat(Text.of(TextColors.RED, "You need to be level 100 or higher to summon horses"))); return; }
			
			ItemStack hand = p.getItemInHand(HandTypes.MAIN_HAND).get().copy();
			
			if (hand.getQuantity() < 10 && ability == Ability.SUMMON_WOLF) { p.sendMessage(Config.PREFIX().concat(Text.of(TextColors.RED, "You need 10 bones to summon a wolf"))); return; }
			if (hand.getQuantity() < 10 && ability == Ability.SUMMON_OCELOT) { p.sendMessage(Config.PREFIX().concat(Text.of(TextColors.RED, "You need 10 fish to summon a wolf"))); return; }
			if (hand.getQuantity() < 10 && ability == Ability.SUMMON_HORSE) { p.sendMessage(Config.PREFIX().concat(Text.of(TextColors.RED, "You need 10 apples to summon a horse"))); return; }
			
			if (hand.getQuantity() == 10) { p.setItemInHand(HandTypes.MAIN_HAND, null); }
			else { hand.setQuantity(hand.getQuantity()-10); p.setItemInHand(HandTypes.MAIN_HAND, hand); }
			
			EffectUtils.sendEffects(p, ParticleTypes.VILLAGER_HAPPY, 100);
			EffectUtils.sendSound(p, SoundTypes.ENTITY_FIREWORK_LAUNCH);
			
			if (delays.containsKey(p.getName())) {
				delays.get(p.getName()).add(new DelayWrapper(System.currentTimeMillis() + ((int) (ability.getValue(skill.level)*1000)), ability));
			}
			else {
				delays.put(p.getName(), new ArrayList<DelayWrapper>());
				delays.get(p.getName()).add(new DelayWrapper(System.currentTimeMillis() + ((int) (ability.getValue(skill.level)*1000)), ability));
			}
			p.offer(Keys.IS_SNEAKING, false);
			
			if (ability == Ability.SUMMON_WOLF) {
				Entity e = ServerUtils.spawn(p.getLocation(), EntityTypes.WOLF);
				Wolf wolf = (Wolf) e;
				Optional<UUID> uuid = Optional.of(p.getUniqueId());
				wolf.offer(Keys.TAMED_OWNER, uuid);
				wolf.setCreator(p.getUniqueId());
				wolf.setNotifier(p.getUniqueId());
				wolf.addPassenger(p);
			}
			else if (ability == Ability.SUMMON_OCELOT) {
				Entity e = ServerUtils.spawn(p.getLocation(), EntityTypes.OCELOT);
				Ocelot ocelot = (Ocelot) e;
				Optional<UUID> uuid = Optional.of(p.getUniqueId());
				ocelot.offer(Keys.TAMED_OWNER, uuid);
				ocelot.setCreator(p.getUniqueId());
				ocelot.setNotifier(p.getUniqueId());
				ocelot.addPassenger(p);
			}
			else if (ability == Ability.SUMMON_HORSE) {
				Entity e = ServerUtils.spawn(p.getLocation(), EntityTypes.HORSE);
				Horse horse = (Horse) e;
				Optional<UUID> uuid = Optional.of(p.getUniqueId());
				horse.offer(Keys.TAMED_OWNER, uuid);
				horse.setCreator(p.getUniqueId());
				horse.setNotifier(p.getUniqueId());
				horse.addPassenger(p);
			}
		}
	}
	
	private void addEffi5(Player p) {
		ItemStack superItem = p.getItemInHand(HandTypes.MAIN_HAND).get();
		EnchantmentData ench = superItem.getOrCreate(EnchantmentData.class).get();
		int lvl = 5;
		for (ItemEnchantment itemEnch : ench.getListValue()) {
			if (itemEnch.getEnchantment() == Enchantments.EFFICIENCY) {
				lvl += itemEnch.getLevel();
				break;
			}
		}
		ench.set(ench.enchantments().add(new ItemEnchantment(Enchantments.EFFICIENCY, lvl)));
		superItem.offer(ench);
		LoreData lore = superItem.getOrCreate(LoreData.class).get();
		lore.removeAll(lore.asList());
		lore.addElement(Text.of(TextColors.RED, "MMO Item"));
		superItem.offer(lore);
		superItem.offer(Keys.DISPLAY_NAME, Text.of(TextColors.DARK_RED, TextStyles.BOLD, "Super Tool"));
		p.setItemInHand(HandTypes.MAIN_HAND, superItem);
	}
}