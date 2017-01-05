package me.mrdaniel.mmo.utils;

import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import me.mrdaniel.mmo.Main;

public enum EffectUtils {

	BLEEDING(ParticleTypes.REDSTONE_DUST, SoundTypes.ENTITY_MAGMACUBE_HURT, 100),
	LEVELUP(ParticleTypes.FIREWORKS_SPARK,SoundTypes.ENTITY_FIREWORK_TWINKLE, 50),
	ACTIVATEABILITY(ParticleTypes.HAPPY_VILLAGER, SoundTypes.ENTITY_FIREWORK_TWINKLE ,100),
	ENDABILITY(ParticleTypes.LAVA, SoundTypes.ENTITY_FIREWORK_TWINKLE ,25);

	private ParticleType effect;
	private SoundType sound;
	private int amount;

	EffectUtils(ParticleType effect, SoundType sound, int amount) {
		this.effect = effect;
		this.sound = sound;
		this.amount = amount;
	}

	public void send(Location<World> loc) {
		ParticleEffect effect = Main.getInstance().getGame().getRegistry().createBuilder(ParticleEffect.Builder.class).type(this.effect).quantity(this.amount).offset(new Vector3d(0.6,0.6,0.6)).build();
		loc.getExtent().spawnParticles(effect, loc.getPosition().add(0, 1, 0));
		loc.getExtent().playSound(this.sound, loc.getPosition(), 1.0);
	}
}