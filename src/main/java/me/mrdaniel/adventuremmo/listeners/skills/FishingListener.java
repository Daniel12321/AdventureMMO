package me.mrdaniel.adventuremmo.listeners.skills;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.action.FishingEvent;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.event.filter.cause.Root;
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
import me.mrdaniel.adventuremmo.io.Config;
import me.mrdaniel.adventuremmo.io.playerdata.PlayerData;
import me.mrdaniel.adventuremmo.utils.ItemInfo;
import me.mrdaniel.adventuremmo.utils.ItemUtils;

public class FishingListener extends ActiveAbilityListener {

	private final int fish_exp;
	private final boolean replace_default_loot;

	private final List<Integer> levels;
	private final Map<Integer, Tuple<Double, ItemInfo>> drops;

	public FishingListener(@Nonnull final AdventureMMO mmo, @Nonnull final Config config) {
		super(mmo, Abilities.FISH_FRENZY, SkillTypes.FISHING, ToolTypes.ROD, Tristate.UNDEFINED);

		this.fish_exp = config.getNode("skills", "fishing", "fish_exp").getInt(250);
		this.replace_default_loot = config.getNode("abilities", "watertreasure", "replace_default_loot")
				.getBoolean(true);

		this.levels = Lists.newArrayList();
		this.drops = Maps.newHashMap();

		config.getNode("abilities", "watertreasure", "loot").getChildrenMap().forEach((typeO, node) -> {
			Optional<ItemType> type = mmo.getGame().getRegistry().getType(ItemType.class, (String) typeO);
			if (type.isPresent()) {
				this.drops.put(node.getNode("lvl").getInt(0), new Tuple<Double, ItemInfo>(
						node.getNode("chance").getDouble(1),
						new ItemInfo(type.get(), node.getNode("min_amount").getInt(1),
								node.getNode("max_amount").getInt(1), node.getNode("min_damage").getInt(0),
								node.getNode("max_damage").getInt(0), node.getNode("enchanted").getBoolean(false))));
			} else {
				mmo.getLogger().error("Failed to find itemtype for: {}", (String) typeO);
			}
		});

		this.levels.addAll(this.drops.keySet());
		this.levels.sort(null);
	}

	@Listener(order = Order.LATE)
	@IsCancelled(value = Tristate.FALSE)
	public void onFish(final FishingEvent.Stop e, @Root Player player) {
		e.getTransactions().forEach(trans -> {
			PlayerData pdata = super.getMMO().getPlayerDatabase().addExp(super.getMMO(), player, super.skill,
					this.fish_exp);
			final int level = pdata.getLevel(super.skill);

			ItemStack item = trans.getFinal().createStack();
			if (Abilities.WATER_TREASURE.getChance(level)) {
				item = this.getDrop(level);
			} else {
				if (this.replace_default_loot && item.getType() != ItemTypes.FISH) {
					item = ItemStack.builder().itemType(ItemTypes.FISH).build();
				}
				if (Abilities.DOUBLE_DROP.getChance(level)) {
					item.setQuantity(item.getQuantity() * 2);
				}
			}
			trans.setCustom(item.createSnapshot());
		});
	}

	@Nonnull
	private ItemStack getDrop(final int level) {
		for (int i = this.levels.size() - 1; i >= 0; i--) {
			int l = this.levels.get(i);
			if (level >= l && this.drops.get(l).getFirst() > Math.random() * 100) {
				return this.drops.get(l).getSecond().create(super.getMMO());
			}
		}
		return ItemUtils.build(ItemTypes.DYE, (int) Math.random() * 11 + 10, 4);
	}
}