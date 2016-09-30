package me.mrdaniel.mmo.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.Enchantments;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.mmo.enums.AbilityEnum;
import me.mrdaniel.mmo.enums.PassiveEnum;
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.enums.ToolType;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.skills.Abilities;
import me.mrdaniel.mmo.skills.Skill;
import me.mrdaniel.mmo.utils.ItemUtils;

public class Drops {
	
	private Random r;
	
	private static Drops instance = null;
	public static Drops getInstance() { if (instance == null) { instance = new Drops(); } return instance; }
			
	private Drops() { r = new Random(); }
	
	public void DoubleDropOre(Player p, ItemType ore, ItemType drop, int amountDrop, int duraDrop, PassiveEnum pe, Skill skill, Location<World> loc, boolean ignoreFortune) {
		if (pe.start + (pe.increase * ((double)skill.level)) > r.nextInt(101)) {
			if (p.getItemInHand().isPresent()) {
				ItemStack hand = p.getItemInHand().get();
				if (hand.get(EnchantmentData.class).isPresent()) {
					EnchantmentData data = hand.get(EnchantmentData.class).get();
					boolean silkTouch = false;
					double fortune = 0;
					for (ItemEnchantment ench : data.asList()) {
						if (ench.getEnchantment().equals(Enchantments.SILK_TOUCH)) { silkTouch = true; break; }
						else if (ench.getEnchantment().equals(Enchantments.FORTUNE)) { fortune = ench.getLevel(); }
					}
					if (silkTouch) {
						ItemStack stack = ItemUtils.build(ore, 1, 0);
						ItemUtils.drop(stack, loc);
						return;
					}
					else if (fortune > 0 && !ignoreFortune) {
						amountDrop += (int) (((double)amountDrop)*fortune*Math.random());
					}
				}
			}
			ItemStack stack = ItemUtils.build(drop, amountDrop, duraDrop);
			ItemUtils.drop(stack, loc);
		}
	}
	public void DoubleDrop(ItemType drop, int amount, int dura, PassiveEnum pe, Skill skill, Location<World> loc) {
		if (pe.start + (pe.increase * ((double)skill.level)) > r.nextInt(101)) {
			ItemStack stack = ItemUtils.build(drop, amount, dura);
			ItemUtils.drop(stack, loc);
		}
	}
	public void GreenTerraDrops(Player p, ItemType type, Location<World> loc, int amount) {
		if (Abilities.getInstance().active.containsKey(p.getName())) {
			if (Abilities.getInstance().active.get(p.getName()).equals(AbilityEnum.GREEN_TERRA)) {
				ItemStack stack = ItemUtils.build(type, amount, 0);
				ItemUtils.drop(stack, loc);
			}
		}
	}
	public void FishingTreasure(Player p, MMOPlayer mmop) {
		PassiveEnum pe = PassiveEnum.WATER_TREASURE;
		Skill skill = mmop.getSkills().getSkill(SkillType.FISHING);
		if (pe.start + (pe.increase * ((double)skill.level)) < r.nextInt(101)) { return; }
		ItemStack item = getFishingTreasure(skill.level);
		ItemUtils.drop(item, p.getLocation());
	}
	public void DiggingTreasure(MMOPlayer mmop, Location<World> loc) {
		PassiveEnum pe = PassiveEnum.TREASURE_HUNT;
		Skill skill = mmop.getSkills().getSkill(SkillType.EXCAVATION);
		if (pe.start + (pe.increase * ((double)skill.level)) < r.nextInt(101)) { return; }
		ItemStack item = getDiggingTreasure(skill.level);
		ItemUtils.drop(item, loc);
	}
	
	private ItemStack getDiggingTreasure(int level) {
		
		if (level >= 400 && r.nextInt(10001) > 99_50) { return ItemUtils.build(ItemTypes.DRAGON_EGG, 1, 0); }
		else if (level >= 300 && r.nextInt(10001) > 99_50) { return ItemUtils.build(ItemTypes.NETHER_STAR, 1, 0); }
		else if (level >= 200 && r.nextInt(10001) > 98_00) { return ItemUtils.build(ItemTypes.CAKE, 1, 0); }
		else if (level >= 150 && r.nextInt(10001) > 96_00) { return ItemUtils.build(ItemTypes.EMERALD, 1, 0); }
		else if (level >= 100 && r.nextInt(10001) > 95_00) { return ItemUtils.build(ItemTypes.DIAMOND, 1, 0); }
		else if (level >= 80 && r.nextInt(10001) > 92_50) { return ItemUtils.build(ItemTypes.DYE, 1, 3); }
		else if (level >= 60 && r.nextInt(10001) > 91_25) { return ItemUtils.build(ItemTypes.SLIME_BALL, 1, 0); }
		else if (level >= 55 && r.nextInt(10001) > 90_00) { return ItemUtils.build(ItemTypes.SPIDER_EYE, 1, 0); }
		else if (level >= 50 && r.nextInt(10001) > 80_00) { return ItemUtils.build(ItemTypes.GOLD_NUGGET, 1, 0); }
		else if (level >= 35 && r.nextInt(10001) > 75_00) { return ItemUtils.build(ItemTypes.BONE, 1, 0); }
		else if (level >= 25 && r.nextInt(10001) > 70_00) { return ItemUtils.build(ItemTypes.FLINT, 1, 0); }
		else if (level >= 20 && r.nextInt(10001) > 65_00) { return ItemUtils.build(ItemTypes.GUNPOWDER, 1, 0); }
		return ItemUtils.build(ItemTypes.GLOWSTONE_DUST, 1, 0);
	}
	
	private ItemStack getFishingTreasure(int level) {
		
		if (level >= 400 && r.nextInt(10001) > 99_50) { return addRandomEnchantment(ItemUtils.build(ItemTypes.DIAMOND_HELMET, 1, r.nextInt(364))); }
		else if (level >= 400 && r.nextInt(10001) > 99_50) { return addRandomEnchantment(ItemUtils.build(ItemTypes.DIAMOND_CHESTPLATE, 1, r.nextInt(529))); }
		else if (level >= 400 && r.nextInt(10001) > 99_50) { return addRandomEnchantment(ItemUtils.build(ItemTypes.DIAMOND_LEGGINGS, 1, r.nextInt(496))); }
		else if (level >= 400 && r.nextInt(10001) > 99_50) { return addRandomEnchantment(ItemUtils.build(ItemTypes.DIAMOND_BOOTS, 1, r.nextInt(430))); }
		else if (level >= 400 && r.nextInt(10001) > 99_50) { return addRandomEnchantment(ItemUtils.build(ItemTypes.DIAMOND_SWORD, 1, r.nextInt(1562))); }
		else if (level >= 400 && r.nextInt(10001) > 99_50) { return addRandomEnchantment(ItemUtils.build(ItemTypes.DIAMOND_AXE, 1, r.nextInt(1562))); }
		else if (level >= 400 && r.nextInt(10001) > 99_50) { return addRandomEnchantment(ItemUtils.build(ItemTypes.DIAMOND_PICKAXE, 1, r.nextInt(1562))); }
		else if (level >= 400 && r.nextInt(10001) > 99_50) { return addRandomEnchantment(ItemUtils.build(ItemTypes.DIAMOND_SHOVEL, 1, r.nextInt(1562))); }
		else if (level >= 400 && r.nextInt(10001) > 99_50) { return addRandomEnchantment(ItemUtils.build(ItemTypes.DIAMOND_HOE, 1, r.nextInt(1562))); }
		
		else if (level >= 300 && r.nextInt(10001) > 99_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.IRON_HELMET, 1, r.nextInt(166))); }
		else if (level >= 300 && r.nextInt(10001) > 99_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.IRON_CHESTPLATE, 1, r.nextInt(241))); }
		else if (level >= 300 && r.nextInt(10001) > 99_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.IRON_LEGGINGS, 1, r.nextInt(226))); }
		else if (level >= 300 && r.nextInt(10001) > 99_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.IRON_BOOTS, 1, r.nextInt(196))); }
		else if (level >= 300 && r.nextInt(10001) > 99_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.IRON_SWORD, 1, r.nextInt(251))); }
		else if (level >= 300 && r.nextInt(10001) > 99_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.IRON_AXE, 1, r.nextInt(251))); }
		else if (level >= 300 && r.nextInt(10001) > 99_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.IRON_PICKAXE, 1, r.nextInt(251))); }
		else if (level >= 300 && r.nextInt(10001) > 99_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.IRON_SHOVEL, 1, r.nextInt(251))); }
		else if (level >= 300 && r.nextInt(10001) > 99_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.IRON_HOE, 1, r.nextInt(251))); }
		
		else if (level >= 200 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.GOLDEN_HELMET, 1, r.nextInt(78))); }
		else if (level >= 200 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.GOLDEN_CHESTPLATE, 1, r.nextInt(113))); }
		else if (level >= 200 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.GOLDEN_LEGGINGS, 1, r.nextInt(106))); }
		else if (level >= 200 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.GOLDEN_BOOTS, 1, r.nextInt(92))); }
		else if (level >= 200 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.GOLDEN_SWORD, 1, r.nextInt(33))); }
		else if (level >= 200 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.GOLDEN_AXE, 1, r.nextInt(33))); }
		else if (level >= 200 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.GOLDEN_PICKAXE, 1, r.nextInt(33))); }
		else if (level >= 200 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.GOLDEN_SHOVEL, 1, r.nextInt(33))); }
		else if (level >= 200 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.GOLDEN_HOE, 1, r.nextInt(33))); }
		
		else if (level >= 150 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.BOW, 1, r.nextInt(385))); }
		
		else if (level >= 100 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.CHAINMAIL_HELMET, 1, r.nextInt(166))); }
		else if (level >= 100 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.CHAINMAIL_CHESTPLATE, 1, r.nextInt(241))); }
		else if (level >= 100 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.CHAINMAIL_LEGGINGS, 1, r.nextInt(226))); }
		else if (level >= 100 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.CHAINMAIL_BOOTS, 1, r.nextInt(196))); }
		else if (level >= 100 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.STONE_SWORD, 1, r.nextInt(132))); }
		else if (level >= 100 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.STONE_AXE, 1, r.nextInt(132))); }
		else if (level >= 100 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.STONE_PICKAXE, 1, r.nextInt(132))); }
		else if (level >= 100 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.STONE_SHOVEL, 1, r.nextInt(132))); }
		else if (level >= 100 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.STONE_HOE, 1, r.nextInt(132))); }
		
		else if (level >= 75 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.FISHING_ROD, 1, r.nextInt(65))); }
		
		else if (level >= 10 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.LEATHER_HELMET, 1, r.nextInt(56))); }
		else if (level >= 10 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.LEATHER_CHESTPLATE, 1, r.nextInt(81))); }
		else if (level >= 10 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.LEATHER_LEGGINGS, 1, r.nextInt(76))); }
		else if (level >= 10 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.LEATHER_BOOTS, 1, r.nextInt(66))); }
		else if (level >= 10 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.WOODEN_SWORD, 1, r.nextInt(60))); }
		else if (level >= 10 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.WOODEN_AXE, 1, r.nextInt(60))); }
		else if (level >= 10 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.WOODEN_PICKAXE, 1, r.nextInt(60))); }
		else if (level >= 10 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.WOODEN_SHOVEL, 1, r.nextInt(60))); }
		else if (level >= 10 && r.nextInt(10001) > 98_00) { return addRandomEnchantment(ItemUtils.build(ItemTypes.WOODEN_HOE, 1, r.nextInt(60))); }
		
		else if (level >= 300 && r.nextInt(10001) > 99_25) { return ItemUtils.build(ItemTypes.DRAGON_EGG, 1, 0); }
		else if (level >= 200 && r.nextInt(10001) > 99_00) { return ItemUtils.build(ItemTypes.NETHER_STAR, 1, 0); }
		else if (level >= 120 && r.nextInt(10001) > 98_00) { return ItemUtils.build(ItemTypes.GHAST_TEAR, r.nextInt(3)+1, 0); }
		else if (level >= 100 && r.nextInt(10001) > 95_00) { return ItemUtils.build(ItemTypes.EMERALD, r.nextInt(8)+1, 0); }
		else if (level >= 90 && r.nextInt(10001) > 95_00) { return ItemUtils.build(ItemTypes.DIAMOND, r.nextInt(8)+1, 0); }
		else if (level >= 80 && r.nextInt(10001) > 95_00) { return ItemUtils.build(ItemTypes.SKULL, 1, r.nextInt(5)); }
		else if (level >= 70 && r.nextInt(10001) > 90_00) { return ItemUtils.build(ItemTypes.ENDER_PEARL, r.nextInt(4)+1, 0); }
		else if (level >= 60 && r.nextInt(10001) > 90_00) { return ItemUtils.build(ItemTypes.BLAZE_ROD, r.nextInt(8)+1, 0); }
		else if (level >= 50 && r.nextInt(10001) > 85_00) { return ItemUtils.build(ItemTypes.GOLD_INGOT, r.nextInt(10)+1, 0); }
		else if (level >= 40 && r.nextInt(10001) > 85_00) { return ItemUtils.build(ItemTypes.IRON_INGOT, r.nextInt(10)+1, 0); }
		else if (level >= 30 && r.nextInt(10001) > 75_00) { return ItemUtils.build(ItemTypes.SPONGE, r.nextInt(5)+2, r.nextInt(2)); }
		else if (level >= 20 && r.nextInt(10001) > 70_00) { return ItemUtils.build(ItemTypes.NAME_TAG, 1, 0); }
		return ItemUtils.build(ItemTypes.DYE, r.nextInt(11)+10, 4);
	}
	
	private ItemStack addRandomEnchantment(ItemStack stack) {
		EnchantmentData data = stack.getOrCreate(EnchantmentData.class).get();
		List<ItemEnchantment> ench = new ArrayList<ItemEnchantment>();
		ToolType type = ToolType.matchID(stack.getItem().getId());
		
		if (type == ToolType.SWORD) {
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.SHARPNESS, r.nextInt(5)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.BANE_OF_ARTHROPODS, r.nextInt(5)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.SMITE, r.nextInt(5)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.LOOTING, r.nextInt(3)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.FIRE_ASPECT, r.nextInt(2)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.KNOCKBACK, r.nextInt(2)+1)); }
			if (r.nextInt(101) > 80) { ench.add(new ItemEnchantment(Enchantments.UNBREAKING, r.nextInt(3)+1)); }
		}
		else if (type == ToolType.AXE) {
			if (r.nextInt(101) > 80) { ench.add(new ItemEnchantment(Enchantments.SHARPNESS, r.nextInt(5)+1)); }
			if (r.nextInt(101) > 80) { ench.add(new ItemEnchantment(Enchantments.BANE_OF_ARTHROPODS, r.nextInt(5)+1)); }
			if (r.nextInt(101) > 80) { ench.add(new ItemEnchantment(Enchantments.SMITE, r.nextInt(5)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.EFFICIENCY, r.nextInt(5)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.UNBREAKING, r.nextInt(3)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.SILK_TOUCH, 1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.FORTUNE, r.nextInt(3)+1)); }
		}
		else if (type == ToolType.SHOVEL || type == ToolType.PICKAXE) {
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.EFFICIENCY, r.nextInt(5)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.UNBREAKING, r.nextInt(3)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.SILK_TOUCH, 1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.FORTUNE, r.nextInt(3)+1)); }
		}
		else if (type == ToolType.ROD) {
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.LURE, r.nextInt(3)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.LUCK_OF_THE_SEA, r.nextInt(3)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.UNBREAKING, r.nextInt(3)+1)); }
		}
		else if (type == ToolType.BOW) {
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.POWER, r.nextInt(5)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.FLAME, r.nextInt(2)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.PUNCH, r.nextInt(2)+1)); }
			if (r.nextInt(101) > 75) { ench.add(new ItemEnchantment(Enchantments.INFINITY, 1)); }
			if (r.nextInt(101) > 80) { ench.add(new ItemEnchantment(Enchantments.UNBREAKING, r.nextInt(3)+1)); }
		}
		else if (type == ToolType.CHESTPLATE || type == ToolType.LEGGINGS) {
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.PROTECTION, r.nextInt(4)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.PROJECTILE_PROTECTION, r.nextInt(4)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.FIRE_PROTECTION, r.nextInt(4)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.BLAST_PROTECTION, r.nextInt(4)+1)); }
			if (r.nextInt(101) > 75) { ench.add(new ItemEnchantment(Enchantments.THORNS, r.nextInt(3)+1)); }
			if (r.nextInt(101) > 80) { ench.add(new ItemEnchantment(Enchantments.UNBREAKING, r.nextInt(3)+1)); }
		}
		else if (type == ToolType.BOOTS) {
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.PROTECTION, r.nextInt(4)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.PROJECTILE_PROTECTION, r.nextInt(4)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.FIRE_PROTECTION, r.nextInt(4)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.BLAST_PROTECTION, r.nextInt(4)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.FEATHER_FALLING, r.nextInt(4)+1)); }
			if (r.nextInt(101) > 75) { ench.add(new ItemEnchantment(Enchantments.THORNS, r.nextInt(3)+1)); }
			if (r.nextInt(101) > 80) { ench.add(new ItemEnchantment(Enchantments.UNBREAKING, r.nextInt(3)+1)); }
		}
		else if (type == ToolType.HELMET) {
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.PROTECTION, r.nextInt(4)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.PROJECTILE_PROTECTION, r.nextInt(4)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.FIRE_PROTECTION, r.nextInt(4)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.BLAST_PROTECTION, r.nextInt(4)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.RESPIRATION, r.nextInt(3)+1)); }
			if (r.nextInt(101) > 70) { ench.add(new ItemEnchantment(Enchantments.AQUA_AFFINITY, 1)); }
			if (r.nextInt(101) > 75) { ench.add(new ItemEnchantment(Enchantments.THORNS, r.nextInt(3)+1)); }
			if (r.nextInt(101) > 80) { ench.add(new ItemEnchantment(Enchantments.UNBREAKING, r.nextInt(3)+1)); }
		}
		ItemStack item = stack.copy();
		data.setElements(ench);
		if (!ench.isEmpty()) item.offer(data);
		return item;
	}
}