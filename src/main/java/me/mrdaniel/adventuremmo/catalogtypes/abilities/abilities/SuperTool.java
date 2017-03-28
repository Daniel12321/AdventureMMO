package me.mrdaniel.adventuremmo.catalogtypes.abilities.abilities;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.ActiveAbilityActions;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;
import me.mrdaniel.adventuremmo.utils.ItemUtils;

public class SuperTool implements ActiveAbilityActions {

	private final ToolType tool;

	public SuperTool(@Nonnull final ToolType tool) {
		this.tool = tool;
	}

	@Override
	public void activate(@Nonnull final AdventureMMO mmo, final Player p) {
		ItemUtils.giveSuperTool(p, tool);
	}

	@Override
	public void deactivate(@Nonnull final AdventureMMO mmo, final Player p) {
		ItemUtils.restoreSuperTool(p, mmo.getContainer());
	}
}