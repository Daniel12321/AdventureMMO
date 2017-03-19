package me.mrdaniel.adventuremmo.listeners.skills;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.util.Tristate;
import org.spongepowered.api.util.Tuple;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Abilities;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolTypes;
import me.mrdaniel.adventuremmo.event.BreakBlockEvent;
import me.mrdaniel.adventuremmo.io.Config;
import me.mrdaniel.adventuremmo.io.PlayerData;
import me.mrdaniel.adventuremmo.utils.ItemInfo;
import me.mrdaniel.adventuremmo.utils.ItemUtils;

public class ExcavationListener extends ActiveAbilityListener {

	private final List<Integer> levels;
	private final Map<Integer, Tuple<Double, ItemInfo>> drops;

	public ExcavationListener(@Nonnull final AdventureMMO mmo, @Nonnull final Config config) {
		super(mmo, Abilities.GIGA_DRILL, SkillTypes.EXCAVATION, ToolTypes.SHOVEL, Tristate.UNDEFINED);

		this.levels = Lists.newArrayList();
		this.drops = Maps.newHashMap();

		config.getNode("abilities", "treasurehunt", "loot").getChildrenMap().forEach((typeO, node) -> {
			Optional<ItemType> type = mmo.getGame().getRegistry().getType(ItemType.class, (String)typeO);
			if (type.isPresent()) { this.drops.put(node.getNode("lvl").getInt(0), new Tuple<Double, ItemInfo>(node.getNode("chance").getDouble(1), new ItemInfo(type.get(), node.getNode("min_amount").getInt(1), node.getNode("max_amount").getInt(1), node.getNode("min_damage").getInt(0), node.getNode("max_damage").getInt(0), node.getNode("enchanted").getBoolean(false)))); }
			else { mmo.getLogger().error("Failed to find itemtype for: {}", (String)typeO); }
		});

		this.levels.addAll(this.drops.keySet());
		this.levels.sort(null);
	}

	@Listener
	public void onBlockBreak(final BreakBlockEvent e) {
		if (e.getBlock().getSkill() == super.skill && e.getTool() != null && e.getTool() == super.tool) {
			PlayerData pdata = super.getMMO().getPlayerDatabase().addExp(super.getMMO(), e.getPlayer(), super.skill, e.getBlock().getExp());

			final int level = pdata.getLevel(super.skill);
			if (Abilities.DOUBLE_DROP.getChance(level)) {
				super.getMMO().getDoubleDrops().add(e.getLocation().getExtent(), e.getLocation().getBlockPosition());
			}

			if (Abilities.TREASURE_HUNT.getChance(level)) {
				ItemUtils.drop(e.getLocation(), this.getDrop(level).createSnapshot());
			}
		}
	}

	@Nonnull
	private ItemStack getDrop(final int level) {
		for (int i = this.levels.size() - 1; i >= 0; i--) {
			int l = this.levels.get(i);
			if (level >= l && this.drops.get(l).getFirst() > Math.random()*100) { return this.drops.get(l).getSecond().create(super.getMMO()); }
		}
		return ItemStack.of(ItemTypes.GLOWSTONE, 1);
	}
}