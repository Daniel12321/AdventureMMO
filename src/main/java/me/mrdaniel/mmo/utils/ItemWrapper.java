package me.mrdaniel.mmo.utils;

import org.spongepowered.api.item.ItemType;

public class ItemWrapper {
	
	public ItemType type;
	public int amount;
	public int exp;
	
	public ItemWrapper(ItemType type, int amount, int exp) { this.type = type; this.amount = amount; this.exp = exp; }
}