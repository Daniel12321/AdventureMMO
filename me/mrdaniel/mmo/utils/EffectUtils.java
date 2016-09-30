package me.mrdaniel.mmo.utils;

import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.entity.living.player.Player;

import com.flowpowered.math.vector.Vector3d;

import me.mrdaniel.mmo.Main;

public class EffectUtils {
	public static void sendEffects(Player p, ParticleType particleType, int count) {
		ParticleEffect effect = Main.getInstance().getGame().getRegistry().createBuilder(ParticleEffect.Builder.class).type(particleType).count(count).offset(new Vector3d(0.6,0.6,0.6)).build();
		p.spawnParticles(effect, p.getLocation().getPosition().add(0, 1, 0));
	}
	public static void sendSound(Player p, SoundType type) {
		p.getLocation().getExtent().playSound(type, p.getLocation().getPosition(), 1.1);
	}
}