package me.mrdaniel.adventuremmo.listeners.skills;

import javax.annotation.Nonnull;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.TreeType;
import org.spongepowered.api.data.type.TreeTypes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.util.Tristate;
import org.spongepowered.api.world.BlockChangeFlag;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.common.collect.Lists;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Abilities;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolTypes;
import me.mrdaniel.adventuremmo.data.BlockData;
import me.mrdaniel.adventuremmo.data.manipulators.MMOData;
import me.mrdaniel.adventuremmo.event.BreakBlockEvent;
import me.mrdaniel.adventuremmo.utils.ItemUtils;

public class WoodcuttingListener extends ActiveAbilityListener {

	public WoodcuttingListener(@Nonnull final AdventureMMO mmo) {
		super(mmo, Abilities.TREE_VELLER, SkillTypes.WOODCUTTING, ToolTypes.AXE, Tristate.TRUE);
	}

	@Listener
	public void onBlockBreak(final BreakBlockEvent e, @First final BlockData block, @First final ToolType tool) {
		if (block.getSkill() == super.skill && tool == super.tool) {
			super.getMMO().getPlayerDatabase().get(e.getPlayer().getUniqueId()).addExp(e.getPlayer(), super.skill, block.getExp());

			e.getPlayer().get(MMOData.class).ifPresent(data -> {
				if (data.isAbilityActive(super.ability.getId())) {
					Task.builder().delayTicks(2).execute(() -> {
						Lists.newArrayList(Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST).forEach(direction -> {
							Location<World> newloc = e.getBlock().getRelative(direction);
							super.getMMO().getItemDatabase().getData(newloc.getBlockType()).ifPresent(blockdata -> {
								if (blockdata.getSkill() == this.skill) {
									ItemStackSnapshot item = ItemUtils.build(newloc.getBlockType().getItem().get(), 1, this.matchTree(newloc.getBlock().get(Keys.TREE_TYPE).orElse(TreeTypes.OAK))).createSnapshot();
									newloc.setBlockType(BlockTypes.AIR, BlockChangeFlag.ALL, Cause.source(super.getMMO().getContainer()).named(NamedCause.simulated(e.getPlayer())).build());
									ItemUtils.drop(newloc, item);
									super.getGame().getEventManager().post(new BreakBlockEvent(super.getMMO(), e.getPlayer(), newloc, blockdata, this.tool));
								}
							});
						}); 
					}).submit(super.getMMO());
				}
			});
		}
	}

	private int matchTree(@Nonnull final TreeType type) {
		return type == TreeTypes.SPRUCE || type == TreeTypes.DARK_OAK ? 1 : type == TreeTypes.BIRCH ? 2 : type == TreeTypes.JUNGLE ? 3 : 0;
	}
}