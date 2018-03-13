package me.mrdaniel.adventuremmo.listeners.skills;

import javax.annotation.Nonnull;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.TreeType;
import org.spongepowered.api.data.type.TreeTypes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.util.Tristate;
import org.spongepowered.api.world.BlockChangeFlags;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.common.collect.Lists;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Abilities;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolTypes;
import me.mrdaniel.adventuremmo.data.manipulators.MMOData;
import me.mrdaniel.adventuremmo.event.BreakBlockEvent;
import me.mrdaniel.adventuremmo.io.playerdata.PlayerData;
import me.mrdaniel.adventuremmo.utils.ItemUtils;

public class WoodcuttingListener extends ActiveAbilityListener {

	public WoodcuttingListener(@Nonnull final AdventureMMO mmo) {
		super(mmo, Abilities.TREE_FELLER, SkillTypes.WOODCUTTING, ToolTypes.AXE, Tristate.TRUE);
	}

	@Listener
	public void onBlockBreak(final BreakBlockEvent e) {
		if (e.getBlock().getSkill() == super.skill && e.getTool() != null && e.getTool() == super.tool) {
			PlayerData pdata = super.getMMO().getPlayerDatabase().addExp(super.getMMO(), e.getPlayer(), super.skill,
					e.getBlock().getExp());

			if (Abilities.DOUBLE_DROP.getChance(pdata.getLevel(super.skill))) {
				super.getMMO().getDoubleDrops().addDouble(e.getLocation().getExtent(),
						e.getLocation().getBlockPosition());
			}

			if (e.getPlayer().get(MMOData.class).orElse(new MMOData()).isAbilityActive(super.ability.getId())) {
				Task.builder().delayTicks(2).execute(() -> {
					Lists.newArrayList(Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST)
							.forEach(direction -> {
								Location<World> newloc = e.getLocation().getRelative(direction);
								super.getMMO().getItemDatabase().getData(newloc.getBlockType()).ifPresent(blockdata -> {
									if (blockdata.getSkill() == this.skill) {
										ItemStackSnapshot item = ItemUtils.build(newloc.getBlockType().getItem().get(),
												Abilities.DOUBLE_DROP.getChance(pdata.getLevel(super.skill)) ? 1 : 2,
												this.matchTree(
														newloc.getBlock().get(Keys.TREE_TYPE).orElse(TreeTypes.OAK)))
												.createSnapshot();
										newloc.setBlockType(BlockTypes.AIR, BlockChangeFlags.ALL);
										ItemUtils.drop(newloc, item);
										super.getGame().getEventManager().post(new BreakBlockEvent(super.getMMO(),
												e.getPlayer(), newloc, blockdata, this.tool));
									}
								});
							});
				}).submit(super.getMMO());
			}
		}
	}

	private int matchTree(@Nonnull final TreeType type) {
		return type == TreeTypes.SPRUCE || type == TreeTypes.DARK_OAK ? 1
				: type == TreeTypes.BIRCH ? 2 : type == TreeTypes.JUNGLE ? 3 : 0;
	}
}