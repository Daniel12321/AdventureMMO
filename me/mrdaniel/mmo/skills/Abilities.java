package me.mrdaniel.mmo.skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.Enchantments;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.enums.AbilityEnum;
import me.mrdaniel.mmo.io.Config;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.io.players.MMOPlayerDatabase;
import me.mrdaniel.mmo.utils.DelayWrapper;
import me.mrdaniel.mmo.utils.EffectUtils;

public class Abilities {
	
	public HashMap<String, AbilityEnum> active;
	public HashMap<String, ArrayList<DelayWrapper>> delays;
	
	private static Abilities instance = null;
	public static Abilities getInstance() { if (instance == null) { instance = new Abilities(); } return instance; }
	private Abilities() { this.active = new HashMap<String, AbilityEnum>(); this.delays = new HashMap<String, ArrayList<DelayWrapper>>(); }
	
	public void activate(final Player p, final AbilityEnum ae) {
		active.put(p.getName(), ae);
		
		EffectUtils.sendEffects(p, ParticleTypes.VILLAGER_HAPPY, 100);
		EffectUtils.sendSound(p, SoundTypes.FIREWORK_LAUNCH);
		
		p.sendMessage(Config.PREFIX().concat(Text.of(TextColors.GREEN, "Activated " + ae.name + "!")));
		if (delays.containsKey(p.getName())) {
			delays.get(p.getName()).add(new DelayWrapper(System.currentTimeMillis() + Config.RECHARGE_MILLIS(), ae));
		}
		else {
			delays.put(p.getName(), new ArrayList<DelayWrapper>());
			delays.get(p.getName()).add(new DelayWrapper(System.currentTimeMillis() + Config.RECHARGE_MILLIS(), ae));
		}
		
		int delay = 5000;
		MMOPlayer mmop = MMOPlayerDatabase.getInstance().getOrCreate(p.getUniqueId().toString());
		delay += (int) (((double)mmop.getSkills().getSkill(ae.skillType).level)/0.02);
		
		final ItemStack item = p.getItemInHand().get().copy();
		final boolean wepUp = (ae == AbilityEnum.SUPER_BREAKER || ae == AbilityEnum.GIGA_DRILL_BREAKER);
		if (wepUp) { addEffi5(p); }
			
		Main.getInstance().getGame().getScheduler().createTaskBuilder().delay(delay, TimeUnit.MILLISECONDS).execute(()-> {
			if (wepUp) { p.setItemInHand(item); }
			active.remove(p.getName());
			p.sendMessage(Config.PREFIX().concat(Text.of(TextColors.RED, ae.name + " expired!")));
			EffectUtils.sendEffects(p, ParticleTypes.LAVA, 25);
			EffectUtils.sendSound(p, SoundTypes.FIREWORK_LAUNCH);
		}).submit(Main.getInstance());
	}
	private void addEffi5(Player p) {
		ItemStack superItem = p.getItemInHand().get();
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
		p.setItemInHand(superItem);
	}
}