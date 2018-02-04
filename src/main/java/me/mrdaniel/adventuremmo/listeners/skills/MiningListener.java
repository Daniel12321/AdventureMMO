package me.mrdaniel.adventuremmo.listeners.skills;

import javax.annotation.Nonnull;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.util.Tristate;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Abilities;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolTypes;
import me.mrdaniel.adventuremmo.event.BreakBlockEvent;
import me.mrdaniel.adventuremmo.io.playerdata.PlayerData;

public class MiningListener extends ActiveAbilityListener {

	public MiningListener(@Nonnull final AdventureMMO mmo) {
		super(mmo, Abilities.MAD_MINER, SkillTypes.MINING, ToolTypes.PICKAXE, Tristate.UNDEFINED);
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
		}
	}
}