package me.mrdaniel.adventuremmo.utils;

import javax.annotation.Nonnull;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

public class ItemInfo {

	private final ItemType type;
	private final int min_amount;
	private final int max_amount;
	private final int dura;

	public ItemInfo(@Nonnull final ItemType type, final int min_amount, final int max_amount, final int dura) {
		this.type = type;
		this.min_amount = min_amount;
		this.max_amount = max_amount + 1;
		this.dura = dura;
	}

	@Nonnull
	public ItemStack create() {
		return ItemUtils.build(this.type, this.min_amount + (int) (Math.random() * (this.max_amount - this.min_amount)), this.dura);
	}

	@Override
	public String toString() {
		return "ItemInfo{ItemType=" + this.type.getId() + "}";
	}
}