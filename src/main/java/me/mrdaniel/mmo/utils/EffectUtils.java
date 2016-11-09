package me.mrdaniel.mmo.utils;

import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import me.mrdaniel.mmo.Main;

public enum EffectUtils {

	LEVELUP,
	ACTIVATEABILITY,
	ENDABILITY,
	BLEEDING;

	public void send(Location<World> loc) {
		if (this == BLEEDING) {
			ParticleEffect effect = Main.getInstance().getGame().getRegistry().createBuilder(ParticleEffect.Builder.class).type(ParticleTypes.REDSTONE_DUST).quantity(100).offset(new Vector3d(0.6,0.6,0.6)).build();
			loc.getExtent().spawnParticles(effect, loc.getPosition().add(0, 1, 0));
			loc.getExtent().playSound(SoundTypes.ENTITY_MAGMACUBE_HURT, loc.getPosition(), 1.0);
		}
		else if (this == LEVELUP) { 
			ParticleEffect effect = Main.getInstance().getGame().getRegistry().createBuilder(ParticleEffect.Builder.class).type(ParticleTypes.FIREWORKS_SPARK).quantity(50).offset(new Vector3d(0.6,0.6,0.6)).build();
			loc.getExtent().spawnParticles(effect, loc.getPosition().add(0, 1, 0));
			loc.getExtent().playSound(SoundTypes.ENTITY_FIREWORK_TWINKLE, loc.getPosition(), 1.0);
		}
		else if (this == ACTIVATEABILITY) { 
			ParticleEffect effect = Main.getInstance().getGame().getRegistry().createBuilder(ParticleEffect.Builder.class).type(ParticleTypes.HAPPY_VILLAGER).quantity(100).offset(new Vector3d(0.6,0.6,0.6)).build();
			loc.getExtent().spawnParticles(effect, loc.getPosition().add(0, 1, 0));
			loc.getExtent().playSound(SoundTypes.ENTITY_FIREWORK_TWINKLE, loc.getPosition(), 1.0);
		}
		else if (this == ENDABILITY) { 
			ParticleEffect effect = Main.getInstance().getGame().getRegistry().createBuilder(ParticleEffect.Builder.class).type(ParticleTypes.LAVA).quantity(25).offset(new Vector3d(0.6,0.6,0.6)).build();
			loc.getExtent().spawnParticles(effect, loc.getPosition().add(0, 1, 0));
			loc.getExtent().playSound(SoundTypes.ENTITY_FIREWORK_TWINKLE, loc.getPosition(), 1.0);
		}
	}
}