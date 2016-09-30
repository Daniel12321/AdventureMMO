package me.mrdaniel.mmo.skills;

import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.io.Config;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.utils.EffectUtils;
import me.mrdaniel.mmo.utils.ServerUtils;

public class SkillManager {
	
	public static void level(MMOPlayer mmop, SkillType type) {
		Player p = ServerUtils.getPlayerFromUUID(mmop.getUUID());
		if (p == null) { return; }
		p.sendMessage(Config.PREFIX().concat(Text.of(TextColors.AQUA, "Your " + type.name + " Skill went up to " + String.valueOf(mmop.getSkills().getSkill(type).level) + "!")));
		
		EffectUtils.sendEffects(p, ParticleTypes.FIREWORKS_SPARK, 50);
		EffectUtils.sendSound(p, SoundTypes.FIREWORK_TWINKLE);
		
		mmop.updateTop(p.getName(), type);
	}
}