package me.mrdaniel.adventuremmo.catalogtypes.tools;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

public final class ToolTypes {

	private ToolTypes(){}

	public static final ToolType PICKAXE = new ToolType("Pickaxe", "pickaxe");
	public static final ToolType AXE = new ToolType("Axe", "axe");
	public static final ToolType SHOVEL = new ToolType("Shovel", "shovel");
	public static final ToolType HOE = new ToolType("Hoe", "hoe");
	public static final ToolType ROD = new ToolType("Fishing Rod", "rod");
	public static final ToolType SWORD = new ToolType("Sword", "sword");
	public static final ToolType HAND = new ToolType("Hand", "hand");
	public static final ToolType BOW = new ToolType("Bow", "bow");

	public static final List<ToolType> VALUES = Lists.newArrayList(PICKAXE, AXE, SHOVEL, HOE, ROD, SWORD, HAND, BOW);

	@Nonnull
	public static Optional<ToolType> of(@Nonnull final String id) {
		for (ToolType type : VALUES) { if (type.getId().equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}
}