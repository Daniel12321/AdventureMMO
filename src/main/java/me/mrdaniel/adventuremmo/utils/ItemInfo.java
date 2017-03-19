package me.mrdaniel.adventuremmo.utils;

import javax.annotation.Nonnull;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import me.mrdaniel.adventuremmo.AdventureMMO;

public class ItemInfo {

	private final ItemType type;
	private final int min_amount;
	private final int max_amount;
	private final int min_damage;
	private final int max_damage;
	private final boolean enchanted;

	public ItemInfo(@Nonnull final ItemType type, final int min_amount, final int max_amount, final int min_damage, final int max_damage, final boolean enchanted) {
		this.type = type;
		this.min_amount = min_amount;
		this.max_amount = max_amount + 1;
		this.min_damage = min_damage;
		this.max_damage = max_damage + 1;
		this.enchanted = enchanted;
	}

	@Nonnull
	public ItemStack create(@Nonnull final AdventureMMO mmo) {
		ItemStack item = ItemUtils.build(this.type, (int) (Math.random() * (this.max_amount - this.min_amount) + this.min_amount), (int) (Math.random() * (this.max_damage - this.min_damage) + this.min_damage));
		return this.enchanted ? ItemUtils.enchant(mmo, item) : item;
	}

//	@Override
//	public String toString() {
//		return "ItemInfo{ItemType=" + this.type.getId() + ", amount=" + this.min_amount + "-" + this.max_amount + ", damage=" + this.min_damage + "-" + this.max_damage + ", enchanted=" + this.enchanted + "}";
//	}
}