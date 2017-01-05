package me.mrdaniel.mmo.listeners;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.BrickTypes;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.data.type.TreeType;
import org.spongepowered.api.data.type.TreeTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.complex.EnderDragon;
import org.spongepowered.api.entity.living.monster.Creeper;
import org.spongepowered.api.entity.living.monster.Skeleton;
import org.spongepowered.api.entity.living.monster.WitherSkeleton;
import org.spongepowered.api.entity.living.monster.Zombie;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.entity.projectile.arrow.Arrow;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.entity.projectile.LaunchProjectileEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.transaction.SlotTransaction;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.data.DelayData;
import me.mrdaniel.mmo.data.MMOData;
import me.mrdaniel.mmo.enums.Ability;
import me.mrdaniel.mmo.enums.DelayType;
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.enums.ToolType;
import me.mrdaniel.mmo.event.ClickEvent;
import me.mrdaniel.mmo.event.ClickType;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.skills.Skill;
import me.mrdaniel.mmo.skills.SkillAction;
import me.mrdaniel.mmo.utils.EffectUtils;
import me.mrdaniel.mmo.utils.ItemUtils;
import me.mrdaniel.mmo.utils.ServerUtils;

public class AbilityListener {

	@Nonnull private final HashMap<UUID, ToolType> raised;
	@Nonnull private final List<EntityInfo> bleeding;

	public AbilityListener() {

		this.raised = Maps.newHashMap();
		this.bleeding = Lists.newArrayList();

		Task.builder().delayTicks(30).intervalTicks(30).name("Bleeding")
		.execute(() -> {
			for (int i = 0; i < this.bleeding.size(); i++) {
				EntityInfo eInfo = this.bleeding.get(i);
				if (Main.getInstance().getGame().getServer().getWorld(eInfo.world).get().getEntity(UUID.fromString(eInfo.uuid)).isPresent()) {
					Entity ent = Main.getInstance().getGame().getServer().getWorld(eInfo.world).get().getEntity(UUID.fromString(eInfo.uuid)).get();
					ent.damage(1, DamageSource.builder().type(DamageTypes.MAGIC).bypassesArmor().build());
					EffectUtils.BLEEDING.send(ent.getLocation());
					eInfo.bleeding--;
					if (eInfo.bleeding <= 0) this.bleeding.remove(i);
				}
				else { this.bleeding.remove(i); }
			}
		}).submit(Main.getInstance());
	}

	//Handles readying and lowering of tools
	@Listener
	public void onRightClick(final ClickEvent e) {

		if (e.getType() != ClickType.RIGHT) { return; }
		Player p = e.getPlayer();

		if (p.get(DelayData.class).isPresent()) {
			DelayData data = p.get(DelayData.class).get();
			if (data.isActive(DelayType.CLICK)) { return; }
			else { data.add(DelayType.CLICK, System.currentTimeMillis() + 2000L); p.offer(data); }
		}

		if (!(p.gameMode().get() == GameModes.SURVIVAL)) { return; }

		ItemType hand = p.getItemInHand(HandTypes.MAIN_HAND).isPresent() ? p.getItemInHand(HandTypes.MAIN_HAND).get().getItem() : ItemTypes.NONE;
		Optional<ToolType> toolType = ToolType.of(hand);
		if (!toolType.isPresent()) { return; }
		if (!toolType.get().isActivaAbility()) { return; }

		if (Main.getInstance().getBlackList().contains(hand.getId().toLowerCase())) { return; }
		if (toolType.get().requiresSneaking() && !p.get(Keys.IS_SNEAKING).orElse(false)) {  return; }

		if (this.raised.containsKey(p.getUniqueId())) {
			if (toolType.get() == this.raised.get(p.getUniqueId())) {
				p.sendMessage(Main.getInstance().getConfig().PREFIX.concat(Text.of(TextColors.RED, "*You lower your " + toolType.get().getName() + "*")));
				this.raised.remove(p.getUniqueId());
				return;
			}
		}

		p.sendMessage(Main.getInstance().getConfig().PREFIX.concat(Text.of(TextColors.GREEN, "*You ready your " + toolType.get().getName() + "*")));
		this.raised.put(p.getUniqueId(), toolType.get());

		Task.builder().delayTicks(80)
		.execute(()-> {
			if (this.raised.containsKey(p.getUniqueId())) {
				p.sendMessage(Main.getInstance().getConfig().PREFIX.concat(Text.of(TextColors.RED, "*You lower your " + toolType.get().getName() + "*"))); raised.remove(p.getUniqueId());
			}
		}).submit(Main.getInstance());
	}

	//Handles activating abilities
	@Listener
	public void onLeftClick(final ClickEvent e) {
		if (e.getType() != ClickType.LEFT) { return; }

		Player p = e.getPlayer();

		ItemType hand = p.getItemInHand(HandTypes.MAIN_HAND).isPresent() ? p.getItemInHand(HandTypes.MAIN_HAND).get().getItem() : ItemTypes.NONE;

		if (this.raised.containsKey(p.getUniqueId())) {
			if (!(p.gameMode().get() == GameModes.SURVIVAL)) { return; }

			Optional<ToolType> toolType = ToolType.of(hand.getType());
			if (!toolType.isPresent()) { return; }
			if (!toolType.get().isActivaAbility()) { return; }

			Optional<Ability> ability = toolType.get().getAbility(e.getBlock().orElse(BlockState.builder().blockType(BlockTypes.AIR).build()).getType());
			if (!ability.isPresent()) { return; }
			if (Main.getInstance().getConfig().BLOCKEDABILITYIES.contains(ability)) { return; }

			if (toolType.get().requiresSneaking() && !p.get(Keys.IS_SNEAKING).orElse(false) ) { return; }

			DelayData data = p.get(DelayData.class).orElse(new DelayData(Maps.newHashMap()));
			if (ability.get().getDelay() != null && data.isActive(ability.get().getDelay())) {
				if (!data.isActive(DelayType.RECHARGE_MESSAGE)) {
					p.sendMessage(Main.getInstance().getConfig().PREFIX.concat(Text.of(TextColors.RED, "*Ability is recharging (" + ability.get().getDelay() + "s)*")));
					data.add(DelayType.RECHARGE_MESSAGE, System.currentTimeMillis() + 5000L);
					p.offer(data);
				}
				return;
			}

			if (toolType.get() == this.raised.get(p.getUniqueId())) {
				this.raised.remove(p.getUniqueId());
				Abilities.getInstance().activate(p, ability.get());
			}
		}
	}

	@Listener(order = Order.LAST)
	public void onTreeVeller(final ChangeBlockEvent.Break e, @Root final Player p) {
		if (e.isCancelled()) { return; }
		BlockSnapshot bss = e.getTransactions().get(0).getOriginal();
		BlockType type = bss.getState().getType();
		if (type == BlockTypes.LOG || type == BlockTypes.LOG2) {
			if (Main.getInstance().getChunkManager().isBlocked(bss.getLocation().get())) { return; }
			if (Abilities.getInstance().active.containsKey(p.getName())) {
				if (Abilities.getInstance().active.get(p.getName()) == Ability.TREE_VELLER) {
					MMOPlayer mmop = Main.getInstance().getMMOPlayerDatabase().getOrCreatePlayer(p.getUniqueId());
					breakNext(e.getTransactions().get(0).getOriginal().getLocation().get(), mmop);
				}
			}
		}
	}

	@Listener(order = Order.LAST)
	public void onGreenTerra(final InteractBlockEvent.Secondary e, @Root final Player p) {
		if (e.isCancelled()) { return; }
		if (Abilities.getInstance().active.containsKey(p.getUniqueId())) {
			if (Abilities.getInstance().active.get(p.getUniqueId()) == Ability.GREEN_TERRA) {
				e.setCancelled(true);
				BlockType type = e.getTargetBlock().getState().getType();
				Optional<Location<World>> locOpt = e.getTargetBlock().getLocation();
				if (locOpt.isPresent()) {
					Location<World> loc = locOpt.get();
					if (type.equals(BlockTypes.DIRT)) { loc.setBlockType(BlockTypes.GRASS, ServerUtils.getCause()); }
					else if (type == BlockTypes.STONEBRICK) { loc.setBlock(loc.getBlock().with(Keys.BRICK_TYPE, BrickTypes.MOSSY).get(), ServerUtils.getCause()); }
					else if (type == BlockTypes.COBBLESTONE) { loc.setBlockType(BlockTypes.MOSSY_COBBLESTONE, ServerUtils.getCause()); }
					else if (type == BlockTypes.TRIPWIRE) { loc.setBlockType(BlockTypes.WEB, ServerUtils.getCause()); }
				}
			}
		}
	}

	@Listener
	public void onItemClick(final ClickInventoryEvent e) {
		for (SlotTransaction trans : e.getTransactions()) {
			ItemStack stack = trans.getOriginal().createStack();
			if (stack.get(MMOData.class).isPresent()) {
				if (stack.get(MMOData.class).get().getEnabled()) {
					e.setCancelled(true); return;
				}
			}
		}
	}

	@Listener(order = Order.LAST)
	public void onFallDamage(final DamageEntityEvent e) {
		if (e.isCancelled()) { return; }
		if (!(e.getTargetEntity() instanceof Player)) { return; }
		Player hurt = (Player) e.getTargetEntity();

		MMOPlayer mmohurt = Main.getInstance().getMMOPlayerDatabase().getOrCreatePlayer(hurt.getUniqueId());
		int level = mmohurt.getSkills().getSkill(SkillType.ACROBATICS).level;

		if (Main.getInstance().getValueStore().getAbility(Ability.DODGE).getSecond().getValue(level) > Math.random()*100.0) {
			e.setCancelled(true);
			hurt.sendMessage(Main.getInstance().getConfig().PREFIX.concat(Text.of(TextColors.GREEN, "*You dodged to avoid taking damage*")));
		}
		Optional<DamageSource> source = e.getCause().first(DamageSource.class);
		if (source.isPresent()) {
			if (source.get().getType() == DamageTypes.FALL) {
				mmohurt.process(new SkillAction(SkillType.ACROBATICS, (int) (Main.getInstance().getValueStore().getAcrobatics()*e.getOriginalDamage())));
				if (Main.getInstance().getValueStore().getAbility(Ability.ROLL).getSecond().getValue(level) > Math.random()*100.0) {
					e.setCancelled(true);
					hurt.sendMessage(Main.getInstance().getConfig().PREFIX.concat(Text.of(TextColors.GREEN, "*You rolled to avoid taking damage*")));
				}
			}
		}
	}

	@Listener(order = Order.LAST)
	public void onDamage(final DamageEntityEvent e, @First final EntityDamageSource source) {
		if (e.isCancelled()) { return; }
		Entity hurtE = e.getTargetEntity();
		if (!(hurtE instanceof Living)) { return; }
		Living hurtL = (Living) hurtE;
		if (source.getSource() instanceof Player) {
			Player damager = (Player) source.getSource();
			MMOPlayer mmodamager = Main.getInstance().getMMOPlayerDatabase().getOrCreatePlayer(damager.getUniqueId());
			ToolType tool = (damager.getItemInHand(HandTypes.MAIN_HAND).isPresent()) ? ToolType.of(damager.getItemInHand(HandTypes.MAIN_HAND).get().getItem()).orElse(null) : ToolType.HAND;

			if (tool == null) { return; }
			else if (tool == ToolType.SWORD) { mmodamager.process(new SkillAction(SkillType.SWORDS, Main.getInstance().getValueStore().getSwordsHurt())); }
			else if (tool == ToolType.AXE) { mmodamager.process(new SkillAction(SkillType.AXES, Main.getInstance().getValueStore().getAxesHurt())); }
			else if (tool == ToolType.HAND) { mmodamager.process(new SkillAction(SkillType.UNARMED, Main.getInstance().getValueStore().getUnarmedHurt())); }

			if (Abilities.getInstance().active.containsKey(damager.getUniqueId())) {
				Ability ability = Abilities.getInstance().active.get(damager.getUniqueId());
				if (ability == Ability.SAITAMA_PUNCH)  {
					if (tool == ToolType.HAND) Main.getInstance().getGame().getScheduler().createTaskBuilder()
					.delayTicks(1)
					.execute(() -> { Vector3d old = hurtL.getVelocity(); hurtL.setVelocity(new Vector3d(old.getX()*5, 0.75, old.getZ()*5)); })
					.submit(Main.getInstance());
				}
				else if (ability == Ability.SLAUGHTER) {
					if (tool == ToolType.AXE) hurtL.getWorld().getEntities().forEach(ent -> { if (ent.getLocation().getPosition().distance(hurtL.getLocation().getPosition()) < 2) { ent.damage(e.getFinalDamage(), DamageSource.builder().type(DamageTypes.ATTACK).build()); }});
				}
				else if (ability == Ability.BLOODSHED && damager.getItemInHand(HandTypes.MAIN_HAND).isPresent()) {
					if (tool == ToolType.SWORD) bleeding.add(new EntityInfo(hurtL.getUniqueId().toString(), hurtL.getWorld().getName(), 5));
				}
			}
			if (hurtL instanceof Player) {
				Player hurt = (Player) hurtL;
				if (damager.getItemInHand(HandTypes.MAIN_HAND).isPresent()) { return; }
				if (Main.getInstance().getValueStore().getAbility(Ability.DISARM).getSecond().getValue(mmodamager.getSkills().getSkill(SkillType.UNARMED).level) > Math.random()*100.0) {
					if (hurt.getItemInHand(HandTypes.MAIN_HAND).isPresent()) {
						final ItemStack stack = hurt.getItemInHand(HandTypes.MAIN_HAND).get().copy();
						final Location<World> loc = hurt.getLocation().copy();
						hurt.setItemInHand(HandTypes.MAIN_HAND, null);
						Task.builder().delayTicks(30).execute(() -> ItemUtils.drop(stack, loc)).submit(Main.getInstance());
					}
				}
			}
		}
		else if (source.getSource() instanceof Arrow) {
			Arrow a = (Arrow) source.getSource();
			if (!(a.getShooter() instanceof Player)) { return; }
			Player damager = (Player) a.getShooter();
			MMOPlayer mmop = Main.getInstance().getMMOPlayerDatabase().getOrCreatePlayer(damager.getUniqueId());
			mmop.process(new SkillAction(SkillType.ARCHERY, Main.getInstance().getValueStore().getArcheryHurt()));
		}
	}

	@Listener(order = Order.LAST)
	public void onBowShoot(final LaunchProjectileEvent e) {
		if (e.getTargetEntity() instanceof Arrow) {
			Arrow a = (Arrow) e.getTargetEntity();
			if (a.getShooter() instanceof Player) {
				Player p = (Player) a.getShooter();
				MMOPlayer mmop = Main.getInstance().getMMOPlayerDatabase().getOrCreatePlayer(p.getUniqueId());
				if (Main.getInstance().getValueStore().getAbility(Ability.ARROW_RAIN).getSecond().getValue(mmop.getSkills().getSkill(SkillType.ARCHERY).level) > Math.random()*100.0) {
					a.offer(Keys.FIRE_TICKS, 1000);
					for (int i = 0; i < 12; i++) {
						Entity ent = ServerUtils.spawn(a.getLocation(), a.getType());
						a.getValues().forEach(value -> ent.offer(value));
						ent.offer(Keys.FIRE_TICKS, 1000);

						Vector3d old = a.getVelocity();
						ent.setVelocity(new Vector3d(old.getX()+(Math.random()/2)-0.25, old.getY()+(Math.random()/4)-0.125, old.getZ()+(Math.random()/2)-0.25));
					}
				}
			}
		}
	}

	@Listener
	public void onDeathBleeding(final DestructEntityEvent e) {
		this.bleeding.forEach(eInfo -> {
			if (eInfo.uuid == e.getTargetEntity().getUniqueId().toString()) {
				this.bleeding.remove(eInfo);
				return;
			}
		});
	}

	@Listener(order = Order.LAST)
	public void onDeath(final DestructEntityEvent.Death e, @First final EntityDamageSource source) {
		Living died = e.getTargetEntity();
		if (source.getSource() instanceof Living) {
			Living killerL = (Living) source.getSource();
			if (killerL instanceof Player) {
				Player killer = (Player) killerL;
				
				MMOPlayer mmokiller = Main.getInstance().getMMOPlayerDatabase().getOrCreatePlayer(killer.getUniqueId());
				ToolType tool = (killer.getItemInHand(HandTypes.MAIN_HAND).isPresent()) ? ToolType.of(killer.getItemInHand(HandTypes.MAIN_HAND).get().getItem()).orElse(null) : ToolType.HAND;
				
				if (tool == null) { return; }
				else if (tool == ToolType.SWORD) { mmokiller.process(new SkillAction(SkillType.SWORDS, Main.getInstance().getValueStore().getSwordsKill())); }
				else if (tool == ToolType.AXE) { mmokiller.process(new SkillAction(SkillType.AXES, Main.getInstance().getValueStore().getAxesKill())); }
				else if (tool == ToolType.HAND) { mmokiller.process(new SkillAction(SkillType.UNARMED, Main.getInstance().getValueStore().getUnarmedKill())); }
				
				if (tool != ToolType.SWORD) { return; }
				
				if (Main.getInstance().getValueStore().getAbility(Ability.DECAPITATION).getSecond().getValue(mmokiller.getSkills().getSkill(SkillType.SWORDS).level) > Math.random()*100.0) {
					int dura = 3;

					if (died instanceof Skeleton) { dura = 0; }
					else if (died instanceof WitherSkeleton) { dura = 1; }
					else if (died instanceof Zombie) dura = 2;
					else if (died instanceof Creeper) dura = 4;
					else if (died instanceof EnderDragon) dura = 5;

					ItemStack stack = ItemUtils.build(ItemTypes.SKULL, 1, dura);
					if (died instanceof Player) { 
						Player diedP = (Player) died;
						stack.offer(Keys.REPRESENTED_PLAYER, diedP.getProfile());
					}
					ItemUtils.drop(stack, died.getLocation());
				}
			}
		}
		else if (source.getSource() instanceof Arrow) {
			Arrow a = (Arrow) source.getSource();
			if (!(a.getShooter() instanceof Player)) { return; }
			Player damager = (Player) a.getShooter();
			MMOPlayer mmop = Main.getInstance().getMMOPlayerDatabase().getOrCreatePlayer(damager.getUniqueId());
			mmop.process(new SkillAction(SkillType.ARCHERY, Main.getInstance().getValueStore().getArcheryKill()));
		}
	}

	private void breakNext(@Nonnull final Location<World> loc, @Nonnull final MMOPlayer jp) {
		for (Direction dir : new Direction[]{Direction.UP, Direction.DOWN, Direction.NORTH,Direction.EAST, Direction.SOUTH, Direction.WEST}) {
			Location<World> l = loc.getRelative(dir);
			if (l.hasBlock()) {
				if (l.getBlock().getType().equals(BlockTypes.LOG) || l.getBlock().getType().equals(BlockTypes.LOG2)) {
					if (Main.getInstance().getChunkManager().isBlocked(l)) { return; }
					ItemType type = l.getBlock().getType().getItem().get();
					int amount = 1;
					int dura = matchTree(l.getBlock().get(Keys.TREE_TYPE).get());
					Skill skill = jp.getSkills().getSkill(SkillType.WOODCUTTING);
					if (Main.getInstance().getValueStore().getAbility(Ability.WOODCUTTING_DOUBLEDROP).getSecond().getValue(skill.level) > Math.random()*100.0) { amount = 2; }
					Main.getInstance().getValueStore().getBlockValue(l.getBlock().getType()).ifPresent(exp -> jp.process(new SkillAction(SkillType.WOODCUTTING, exp)));
					ItemStack stack = ItemUtils.build(type, amount, dura);
					ItemUtils.drop(stack, l);
					l.removeBlock(ServerUtils.getCause());
					breakNext(l, jp);
				}
			}
		}
	}

	private int matchTree(@Nonnull final TreeType treeType) {
		if (treeType == TreeTypes.SPRUCE) { return 1; }
		else if (treeType == TreeTypes.BIRCH) { return 2; }
		else if (treeType == TreeTypes.JUNGLE) { return 3; }
		else if (treeType == TreeTypes.DARK_OAK) { return 1; }
		else return 0;
	}
}