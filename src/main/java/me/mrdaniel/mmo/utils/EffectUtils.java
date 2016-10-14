package me.mrdaniel.mmo.utils;

import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.entity.Entity;

import com.flowpowered.math.vector.Vector3d;

import me.mrdaniel.mmo.Main;

public class EffectUtils {
	public static void sendEffects(Entity e, ParticleType particleType, int count) {
		ParticleEffect effect = Main.getInstance().getGame().getRegistry().createBuilder(ParticleEffect.Builder.class).type(particleType).count(count).offset(new Vector3d(0.6,0.6,0.6)).build();
		e.getWorld().spawnParticles(effect, e.getLocation().getPosition().add(0, 1, 0));
	}
	public static void sendSound(Entity e, SoundType type) {
		e.getWorld().playSound(type, e.getLocation().getPosition(), 1.0);
	}
}