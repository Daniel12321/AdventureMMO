package me.mrdaniel.mmo.listeners;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.data.type.TreeType;
import org.spongepowered.api.data.type.TreeTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.data.MMOData;
import me.mrdaniel.mmo.enums.Ability;
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.skills.SkillAction;
import me.mrdaniel.mmo.skills.SkillSet;

public class BlockListener {

	@Listener(order = Order.LAST)
	public void onBlockBreak(final ChangeBlockEvent.Break e, @Root final Player p) {
		if (e.isCancelled()) { return; }
		BlockSnapshot bss = e.getTransactions().get(0).getOriginal();
		BlockType type = bss.getState().getType();

		MMOPlayer mmop = Main.getInstance().getMMOPlayerDatabase().getOrCreatePlayer(p.getUniqueId());
		Location<World> loc = bss.getLocation().get();

		if (Main.getInstance().getChunkManager().shouldWatch(type) && bss.getLocation().isPresent()) { 
			if (Main.getInstance().getChunkManager().isBlocked(loc)) {
				Main.getInstance().getChunkManager().remove(loc); 
				return;
			}
		}
		if (Main.getInstance().getBlackList().contains(bss.getState().getId())) { return; }

		Optional<ItemStack> hand = p.getItemInHand(HandTypes.MAIN_HAND);
		if (hand.isPresent()
				&& hand.get().get(MMOData.class).orElse(new MMOData(false)).getEnabled()
				&& !Abilities.getInstance().active.containsKey(p.getUniqueId())) {
			p.setItemInHand(HandTypes.MAIN_HAND, null);
			return;
		}

		Main.getInstance().getChunkManager().remove(loc); 

		if (p.get(Keys.GAME_MODE).get() != GameModes.SURVIVAL) { return; }

		SkillSet skills = mmop.getSkills();
		if (type.equals(BlockTypes.POTATOES)) { mmop.process(new SkillAction(SkillType.FARMING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDrop(ItemTypes.POTATO, 2, 0, Ability.FARMING_DOUBLEDROP, skills.getSkill(SkillType.FARMING), loc); Drops.getInstance().GreenTerraDrops(p, ItemTypes.POTATO, loc, 3); }
		else if (type.equals(BlockTypes.WHEAT)) { mmop.process(new SkillAction(SkillType.FARMING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDrop(ItemTypes.WHEAT, 1, 0, Ability.FARMING_DOUBLEDROP, skills.getSkill(SkillType.FARMING), loc); Drops.getInstance().GreenTerraDrops(p, ItemTypes.WHEAT, loc, 2); }
		else if (type.equals(BlockTypes.CARROTS)) { mmop.process(new SkillAction(SkillType.FARMING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDrop(ItemTypes.CARROT, 2, 0, Ability.FARMING_DOUBLEDROP, skills.getSkill(SkillType.FARMING), loc); Drops.getInstance().GreenTerraDrops(p, ItemTypes.CARROT, loc, 3); }
		else if (type.equals(BlockTypes.NETHER_WART)) { mmop.process(new SkillAction(SkillType.FARMING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDrop(ItemTypes.NETHER_WART, 2, 0, Ability.FARMING_DOUBLEDROP, skills.getSkill(SkillType.FARMING), loc); Drops.getInstance().GreenTerraDrops(p, ItemTypes.NETHER_WART, loc, 2); }
		else if (type.equals(BlockTypes.CACTUS)) { mmop.process(new SkillAction(SkillType.FARMING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDrop(ItemTypes.CACTUS, 1, 0, Ability.FARMING_DOUBLEDROP, skills.getSkill(SkillType.FARMING), loc); Drops.getInstance().GreenTerraDrops(p, ItemTypes.CACTUS, loc, 2); }
		else if (type.equals(BlockTypes.REEDS)) { mmop.process(new SkillAction(SkillType.FARMING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDrop(ItemTypes.REEDS, 1, 0, Ability.FARMING_DOUBLEDROP, skills.getSkill(SkillType.FARMING), loc); Drops.getInstance().GreenTerraDrops(p, ItemTypes.REEDS, loc, 2); }
		else if (type.equals(BlockTypes.MELON_BLOCK)) { mmop.process(new SkillAction(SkillType.FARMING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDropOre(p, ItemTypes.MELON_BLOCK, ItemTypes.MELON, 5, 0, Ability.FARMING_DOUBLEDROP, skills.getSkill(SkillType.FARMING), loc, false); Drops.getInstance().GreenTerraDrops(p, ItemTypes.MELON, loc, 5); }
		else if (type.equals(BlockTypes.PUMPKIN)) { mmop.process(new SkillAction(SkillType.FARMING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDrop(ItemTypes.PUMPKIN, 1, 0, Ability.FARMING_DOUBLEDROP, skills.getSkill(SkillType.FARMING), loc); Drops.getInstance().GreenTerraDrops(p, ItemTypes.PUMPKIN, loc, 2); }
		else if (type.equals(BlockTypes.WATERLILY)) { mmop.process(new SkillAction(SkillType.FARMING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDrop(ItemTypes.WATERLILY, 1, 0, Ability.FARMING_DOUBLEDROP, skills.getSkill(SkillType.FARMING), loc); Drops.getInstance().GreenTerraDrops(p, ItemTypes.WATERLILY, loc, 2); }
		else if (type.equals(BlockTypes.RED_MUSHROOM)) { mmop.process(new SkillAction(SkillType.FARMING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDrop(ItemTypes.RED_MUSHROOM, 1, 0, Ability.FARMING_DOUBLEDROP, skills.getSkill(SkillType.FARMING), loc); Drops.getInstance().GreenTerraDrops(p, ItemTypes.RED_MUSHROOM, loc, 2); }
		else if (type.equals(BlockTypes.BROWN_MUSHROOM)) { mmop.process(new SkillAction(SkillType.FARMING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDrop(ItemTypes.BROWN_MUSHROOM, 1, 0, Ability.FARMING_DOUBLEDROP, skills.getSkill(SkillType.FARMING), loc); Drops.getInstance().GreenTerraDrops(p, ItemTypes.BROWN_MUSHROOM, loc, 2); }
		else if (type.equals(BlockTypes.RED_MUSHROOM_BLOCK)) { mmop.process(new SkillAction(SkillType.FARMING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDropOre(p, ItemTypes.RED_MUSHROOM_BLOCK, ItemTypes.RED_MUSHROOM, 1, 0, Ability.FARMING_DOUBLEDROP, skills.getSkill(SkillType.FARMING), loc, false); Drops.getInstance().GreenTerraDrops(p, ItemTypes.RED_MUSHROOM, loc, 2); }
		else if (type.equals(BlockTypes.BROWN_MUSHROOM_BLOCK)) { mmop.process(new SkillAction(SkillType.FARMING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDropOre(p, ItemTypes.BROWN_MUSHROOM_BLOCK, ItemTypes.BROWN_MUSHROOM, 1, 0, Ability.FARMING_DOUBLEDROP, skills.getSkill(SkillType.FARMING), loc, false); Drops.getInstance().GreenTerraDrops(p, ItemTypes.BROWN_MUSHROOM, loc, 2); }

		else if (type.equals(BlockTypes.LEAVES)) { mmop.process(new SkillAction(SkillType.FARMING, Main.getInstance().getValueStore().getBlockValue(type).get())); mmop.process(new SkillAction(SkillType.WOODCUTTING, 15)); }
		else if (type.equals(BlockTypes.LEAVES2)) { mmop.process(new SkillAction(SkillType.FARMING, Main.getInstance().getValueStore().getBlockValue(type).get())); mmop.process(new SkillAction(SkillType.WOODCUTTING, 15)); }

		else if (type.equals(BlockTypes.OBSIDIAN)) { mmop.process(new SkillAction(SkillType.MINING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDrop(ItemTypes.OBSIDIAN, 1, 0, Ability.MINING_DOUBLEDROP, skills.getSkill(SkillType.MINING), loc); }
		else if (type.equals(BlockTypes.DIAMOND_ORE)) { mmop.process(new SkillAction(SkillType.MINING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDropOre(p, ItemTypes.DIAMOND_ORE, ItemTypes.DIAMOND, 1, 0, Ability.MINING_DOUBLEDROP, skills.getSkill(SkillType.MINING), loc, false); }
		else if (type.equals(BlockTypes.EMERALD_ORE)) { mmop.process(new SkillAction(SkillType.MINING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDropOre(p, ItemTypes.EMERALD_ORE, ItemTypes.EMERALD, 1, 0, Ability.MINING_DOUBLEDROP, skills.getSkill(SkillType.MINING), loc, false); }
		else if (type.equals(BlockTypes.GOLD_ORE)) { mmop.process(new SkillAction(SkillType.MINING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDrop(ItemTypes.GOLD_ORE, 1, 0, Ability.MINING_DOUBLEDROP, skills.getSkill(SkillType.MINING), loc); }
		else if (type.equals(BlockTypes.IRON_ORE)) { mmop.process(new SkillAction(SkillType.MINING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDrop(ItemTypes.IRON_ORE, 1, 0, Ability.MINING_DOUBLEDROP, skills.getSkill(SkillType.MINING), loc); }
		else if (type.equals(BlockTypes.COAL_ORE)) { mmop.process(new SkillAction(SkillType.MINING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDropOre(p, ItemTypes.COAL_ORE, ItemTypes.COAL, 1, 0, Ability.MINING_DOUBLEDROP, skills.getSkill(SkillType.MINING), loc, false); }
		else if (type.equals(BlockTypes.REDSTONE_ORE)) { mmop.process(new SkillAction(SkillType.MINING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDropOre(p, ItemTypes.REDSTONE_ORE, ItemTypes.REDSTONE, 3, 0, Ability.MINING_DOUBLEDROP, skills.getSkill(SkillType.MINING), loc, false); }
		else if (type.equals(BlockTypes.LIT_REDSTONE_ORE)) { mmop.process(new SkillAction(SkillType.MINING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDropOre(p, ItemTypes.REDSTONE_ORE, ItemTypes.REDSTONE, 3, 0, Ability.MINING_DOUBLEDROP, skills.getSkill(SkillType.MINING), loc, false); }
		else if (type.equals(BlockTypes.LAPIS_ORE)) { mmop.process(new SkillAction(SkillType.MINING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDropOre(p, ItemTypes.LAPIS_ORE, ItemTypes.DYE, 4, 4, Ability.MINING_DOUBLEDROP, skills.getSkill(SkillType.MINING), loc, false); }
		else if (type.equals(BlockTypes.QUARTZ_ORE)) { mmop.process(new SkillAction(SkillType.MINING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDropOre(p, ItemTypes.QUARTZ_ORE, ItemTypes.QUARTZ, 3, 0, Ability.MINING_DOUBLEDROP, skills.getSkill(SkillType.MINING), loc, false); }
		else if (type.equals(BlockTypes.MOSSY_COBBLESTONE)) { mmop.process(new SkillAction(SkillType.MINING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDrop(ItemTypes.MOSSY_COBBLESTONE, 1, 0, Ability.MINING_DOUBLEDROP, skills.getSkill(SkillType.MINING), loc); }

		else if (type.equals(BlockTypes.LOG)) { mmop.process(new SkillAction(SkillType.WOODCUTTING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDrop(ItemTypes.LOG, 1, matchTree(bss.get(Keys.TREE_TYPE).get()), Ability.WOODCUTTING_DOUBLEDROP, skills.getSkill(SkillType.WOODCUTTING), loc); }
		else if (type.equals(BlockTypes.LOG2)) { mmop.process(new SkillAction(SkillType.WOODCUTTING, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDrop(ItemTypes.LOG2, 1, matchTree(bss.get(Keys.TREE_TYPE).get()), Ability.WOODCUTTING_DOUBLEDROP, skills.getSkill(SkillType.WOODCUTTING), loc); }

		else if (type.equals(BlockTypes.SAND)) { mmop.process(new SkillAction(SkillType.EXCAVATION, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDrop(ItemTypes.SAND, 1, 0, Ability.EXCAVATION_DOUBLEDROP, skills.getSkill(SkillType.EXCAVATION), loc); Drops.getInstance().DiggingTreasure(mmop, loc); }
		else if (type.equals(BlockTypes.DIRT)) { mmop.process(new SkillAction(SkillType.EXCAVATION, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDrop(ItemTypes.DIRT, 1, 0, Ability.EXCAVATION_DOUBLEDROP, skills.getSkill(SkillType.EXCAVATION), loc); Drops.getInstance().DiggingTreasure(mmop, loc); }
		else if (type.equals(BlockTypes.GRAVEL)) { mmop.process(new SkillAction(SkillType.EXCAVATION, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDrop(ItemTypes.GRAVEL, 1, 0, Ability.EXCAVATION_DOUBLEDROP, skills.getSkill(SkillType.EXCAVATION), loc); Drops.getInstance().DiggingTreasure(mmop, loc); }
		else if (type.equals(BlockTypes.GRASS)) { mmop.process(new SkillAction(SkillType.EXCAVATION, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDropOre(p, ItemTypes.GRASS, ItemTypes.DIRT, 1, 0, Ability.EXCAVATION_DOUBLEDROP, skills.getSkill(SkillType.EXCAVATION), loc, true); Drops.getInstance().DiggingTreasure(mmop, loc); }
		else if (type.equals(BlockTypes.SOUL_SAND)) { mmop.process(new SkillAction(SkillType.EXCAVATION, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDrop(ItemTypes.SOUL_SAND, 1, 0, Ability.EXCAVATION_DOUBLEDROP, skills.getSkill(SkillType.EXCAVATION), loc); Drops.getInstance().DiggingTreasure(mmop, loc); }
		else if (type.equals(BlockTypes.MYCELIUM)) { mmop.process(new SkillAction(SkillType.EXCAVATION, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDropOre(p, ItemTypes.MYCELIUM, ItemTypes.DIRT, 1, 0, Ability.EXCAVATION_DOUBLEDROP, skills.getSkill(SkillType.EXCAVATION), loc, true); Drops.getInstance().DiggingTreasure(mmop, loc); }
		else if (type.equals(BlockTypes.CLAY)) { mmop.process(new SkillAction(SkillType.EXCAVATION, Main.getInstance().getValueStore().getBlockValue(type).get())); Drops.getInstance().DoubleDropOre(p, ItemTypes.CLAY, ItemTypes.CLAY_BALL, 4, 0, Ability.EXCAVATION_DOUBLEDROP, skills.getSkill(SkillType.EXCAVATION), loc, true); Drops.getInstance().DiggingTreasure(mmop, loc); }
		else { Main.getInstance().getModdedBlocks().get(bss.getState().getId()).ifPresent(mBlock -> mmop.process(new SkillAction(mBlock.type, mBlock.exp))); }
	}

	@Listener(order = Order.LAST)
	public void onBlockPlace(final ChangeBlockEvent.Place e, @Root final Player p) {
		if (e.isCancelled()) { return; }
		BlockState state = e.getTransactions().get(0).getFinal().getState();
		if (Main.getInstance().getChunkManager().shouldWatch(state.getType())) {
			Optional<Location<World>> loc = e.getTransactions().get(0).getFinal().getLocation();
			if (loc.isPresent()) {
				Main.getInstance().getChunkManager().add(loc.get());
			}
		}
	}

	private int matchTree(@Nonnull final TreeType treeType) {
		if (treeType.equals(TreeTypes.SPRUCE)) { return 1; }
		else if (treeType.equals(TreeTypes.BIRCH)) { return 2; }
		else if (treeType.equals(TreeTypes.JUNGLE)) { return 3; }
		else if (treeType.equals(TreeTypes.DARK_OAK)) { return 1; }
		return 0;
	}
}