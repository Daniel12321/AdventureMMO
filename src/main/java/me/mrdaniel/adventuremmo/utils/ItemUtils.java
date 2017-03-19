package me.mrdaniel.adventuremmo.utils;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.data.type.SkullType;
import org.spongepowered.api.data.type.SkullTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.Enchantments;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.common.collect.Lists;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolTypes;
import me.mrdaniel.adventuremmo.data.manipulators.SuperToolData;

public class ItemUtils {

	@Nonnull
	public static ItemStack build(@Nonnull final ItemType type, final int amount, final int unsafe) {
		return ItemStack.builder().fromContainer(new MemoryDataContainer().set(DataQuery.of("ItemType"), type).set(DataQuery.of("Count"), amount).set(DataQuery.of("UnsafeDamage"), unsafe)).build();
	}

	@Nonnull
	public static Entity drop(@Nonnull final Location<World> loc, @Nonnull final ItemStackSnapshot item) {
		Entity e = loc.createEntity(EntityTypes.ITEM);
		e.offer(Keys.REPRESENTED_ITEM, item);
		e.offer(Keys.PICKUP_DELAY, 10);
		loc.getExtent().spawnEntity(e, ServerUtils.getSpawnCause(e));
		return e;
	}

	@Nonnull
	public static Optional<ItemStack> getHead(@Nonnull final EntityType type) {
		ItemStack item = ItemStack.builder().itemType(ItemTypes.SKULL).quantity(1).build();

		SkullType skull;
		if (type == EntityTypes.ZOMBIE) { skull = SkullTypes.ZOMBIE; }
		else if (type == EntityTypes.SKELETON) { skull = SkullTypes.SKELETON; }
		else if (type == EntityTypes.CREEPER) { skull = SkullTypes.CREEPER; }
		else if (type == EntityTypes.WITHER_SKELETON) { skull = SkullTypes.WITHER_SKELETON; }
		else if (type == EntityTypes.ENDER_DRAGON) { skull = SkullTypes.ENDER_DRAGON; }
		else return Optional.empty();

		item.offer(Keys.SKULL_TYPE, skull);
		return Optional.of(item);
	}

	@Nonnull
	public static ItemStack getPlayerHead(@Nonnull final Player p) {
		return ItemStack.builder().itemType(ItemTypes.SKULL).keyValue(Keys.SKULL_TYPE, SkullTypes.PLAYER).keyValue(Keys.REPRESENTED_PLAYER, p.getProfile()).build();
	}

	@Nonnull
	public static ItemStack createSuperTool(@Nonnull final ItemStack item, @Nonnull final ToolType tool) {
		ItemStack supertool = item.copy();
		final boolean rod = tool == ToolTypes.ROD;
		int lvl = rod ? 3 : 5;
		int max_lvl = rod ? 5 : 10;
		Enchantment type = rod ? Enchantments.LURE : Enchantments.EFFICIENCY;

		List<ItemEnchantment> ench = supertool.get(Keys.ITEM_ENCHANTMENTS).orElse(Lists.newArrayList());
		for (ItemEnchantment enchant : ench) {
			if (enchant.getEnchantment() == type) {
				lvl += enchant.getLevel();
				ench.remove(enchant);
				break;
			}
		}
		ench.add(new ItemEnchantment(type, MathUtils.between(lvl, 1, max_lvl)));
		supertool.offer(Keys.ITEM_ENCHANTMENTS, ench);
		supertool.offer(Keys.DISPLAY_NAME, Text.of(TextColors.RED, TextStyles.BOLD, "Super ", tool.getName()));
		supertool.offer(new SuperToolData(true));
		supertool.offer(Keys.UNBREAKABLE, true);

		return supertool;
	}

	@Nonnull
	public static ItemStack enchant(@Nonnull final AdventureMMO mmo, @Nonnull final ItemStack item) {
		List<ItemEnchantment> enchants = Lists.newArrayList();
		mmo.getGame().getRegistry().getAllOf(Enchantment.class).forEach(ench -> {
			if (Math.random() > 0.9 && ench.canBeAppliedByTable(item)) { enchants.add(new ItemEnchantment(ench, (int) Math.random() * ench.getMaximumLevel() + 1)); }
		});
		item.offer(Keys.ITEM_ENCHANTMENTS, enchants);
		return item;
	}
}