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

import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;
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
		ItemStack item = ItemStack.builder().itemType(ItemTypes.SKULL).quantity(1).build();
		item.offer(Keys.SKULL_TYPE, SkullTypes.PLAYER);
		item.offer(Keys.REPRESENTED_PLAYER, p.getProfile());
		return item;
	}

	@Nonnull
	public static ItemStack createSuperTool(@Nonnull final ItemStack tool, @Nonnull final ToolType type) {
		ItemStack item = tool.copy();
		List<ItemEnchantment> ench = item.get(Keys.ITEM_ENCHANTMENTS).orElse(Lists.newArrayList());
		int lvl = 5;
		for (ItemEnchantment enchant : ench) {
			if (enchant.getEnchantment() == Enchantments.EFFICIENCY) {
				lvl += enchant.getLevel();
				ench.remove(enchant);
				break;
			}
		}
		ench.add(new ItemEnchantment(Enchantments.EFFICIENCY, lvl));
		item.offer(Keys.ITEM_ENCHANTMENTS, ench);
		item.offer(Keys.DISPLAY_NAME, Text.of(TextColors.BLUE, TextStyles.BOLD, "MMO Super ", type.getName()));
		item.offer(new SuperToolData(true));
		item.offer(Keys.UNBREAKABLE, true);

		return item;
	}
}