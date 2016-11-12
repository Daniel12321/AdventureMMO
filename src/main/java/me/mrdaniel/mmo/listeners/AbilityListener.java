package me.mrdaniel.mmo.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.BrickTypes;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.data.type.SkeletonTypes;
import org.spongepowered.api.data.type.TreeType;
import org.spongepowered.api.data.type.TreeTypes;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.complex.EnderDragon;
import org.spongepowered.api.entity.living.monster.Creeper;
import org.spongepowered.api.entity.living.monster.Skeleton;
import org.spongepowered.api.entity.living.monster.Zombie;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.entity.projectile.arrow.Arrow;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause.Builder;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.event.entity.ConstructEntityEvent;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.transaction.SlotTransaction;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.data.MMOData;
import me.mrdaniel.mmo.enums.Ability;
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.enums.ToolType;
import me.mrdaniel.mmo.io.AbilitiesConfig;
import me.mrdaniel.mmo.io.BlackList;
import me.mrdaniel.mmo.io.Config;
import me.mrdaniel.mmo.io.ValuesConfig;
import me.mrdaniel.mmo.io.blocktracking.WatchList;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.io.players.MMOPlayerDatabase;
import me.mrdaniel.mmo.skills.Skill;
import me.mrdaniel.mmo.skills.SkillAction;
import me.mrdaniel.mmo.utils.DelayInfo;
import me.mrdaniel.mmo.utils.EffectUtils;
import me.mrdaniel.mmo.utils.ItemUtils;
import me.mrdaniel.mmo.utils.ServerUtils;

public class AbilityListener {
	
	private HashMap<String, ToolType> raised;
	private HashMap<String, Long> clickDelays;
	private List<EntityInfo> bleeding;
	
	public AbilityListener() {

		raised = new HashMap<String, ToolType>();
		clickDelays = new HashMap<String, Long>();
		bleeding = new ArrayList<EntityInfo>();

		Main.getInstance().getGame().getScheduler().createTaskBuilder()
		.delayTicks(30)
		.intervalTicks(30)
		.name("Bleeding")
		.execute(() -> {
			for (int i = 0; i < bleeding.size(); i++) {
				EntityInfo eInfo = bleeding.get(i);
				if (Main.getInstance().getGame().getServer().getWorld(eInfo.world).get().getEntity(UUID.fromString(eInfo.uuid)).isPresent()) {
					Entity ent = Main.getInstance().getGame().getServer().getWorld(eInfo.world).get().getEntity(UUID.fromString(eInfo.uuid)).get();
					ent.damage(1, DamageSource.builder().type(DamageTypes.MAGIC).bypassesArmor().build());
					EffectUtils.BLEEDING.send(ent.getLocation());
					eInfo.bleeding--;
					if (eInfo.bleeding <= 0) bleeding.remove(i);
				}
				else {
					bleeding.remove(i);
				}
			}
		}).submit(Main.getInstance());
	}

	//Handles readying and lowering of tools
	@Listener
	public void onRightClick(InteractBlockEvent.Secondary e, @Root Player p) {
		
		ItemType hand = p.getItemInHand(HandTypes.MAIN_HAND).isPresent() ? p.getItemInHand(HandTypes.MAIN_HAND).get().getItem() : ItemTypes.NONE;
		if (clickDelays.containsKey(p.getName())) {
			if (System.currentTimeMillis() > clickDelays.get(p.getName())) { clickDelays.remove(p.getName()); }
			else { return; }
		}
		if (!(p.gameMode().get() == GameModes.SURVIVAL)) { return; }
		
		ToolType toolType = ToolType.matchID(hand);
		if (toolType == null) { return; }
		if (!toolType.activeAbility) { return; }
		
		if (BlackList.getInstance().blacklist.contains(hand.getId().toLowerCase())) { return; }
		
		if (toolType.requiresSneaking) {
			if (!p.get(Keys.IS_SNEAKING).isPresent()) { return; }	
			if (p.get(Keys.IS_SNEAKING).get().booleanValue() == false) { return; }
		}
		
		clickDelays.put(p.getName(), System.currentTimeMillis() + 1500L);
		
		if (raised.containsKey(p.getName())) {
			if (toolType == raised.get(p.getName())) {
				p.sendMessage(Config.getInstance().PREFIX.concat(Text.of(TextColors.RED, "*You lower your " + toolType.name + "*")));
				raised.remove(p.getName());
				return;
			}
		}
		
		p.sendMessage(Config.getInstance().PREFIX.concat(Text.of(TextColors.GREEN, "*You ready your " + toolType.name + "*")));
		raised.put(p.getName(), toolType);
		
		Main.getInstance().getGame().getScheduler().createTaskBuilder()
		.delay(4, TimeUnit.SECONDS)
		.execute(()-> {
			if (raised.containsKey(p.getName())) {
				p.sendMessage(Config.getInstance().PREFIX.concat(Text.of(TextColors.RED, "*You lower your " + toolType.name + "*"))); raised.remove(p.getName());
			}
		}).submit(Main.getInstance());
	}
	
	//Handles activating abilities
	@Listener
	public void onLeftClick(InteractBlockEvent.Primary e, @Root Player p) {

		ItemType hand = p.getItemInHand(HandTypes.MAIN_HAND).isPresent() ? p.getItemInHand(HandTypes.MAIN_HAND).get().getItem() : ItemTypes.NONE;

		if (raised.containsKey(p.getName())) {
			if (!(p.gameMode().get() == GameModes.SURVIVAL)) { return; }

			ToolType toolType = ToolType.matchID(hand.getType());
			if (toolType == null) { return; }
			if (!toolType.activeAbility) { return; }

			Ability ability = toolType.getAbility(e.getTargetBlock().getState().getType());
			if (ability == null) { return; }
			if (Config.getInstance().BLOCKEDABILITYIES.contains(ability)) { return; }

			if (toolType.requiresSneaking) {
				if (!p.get(Keys.IS_SNEAKING).isPresent()) { return; }	
				if (p.get(Keys.IS_SNEAKING).get().booleanValue() == false) { return; }
			}

			if (Abilities.getInstance().delays.containsKey(p.getName())) {
				for (DelayInfo delayInfo : Abilities.getInstance().delays.get(p.getName())) {
					if (delayInfo.ability == ability) {
						if (System.currentTimeMillis() > delayInfo.expires) {
							Abilities.getInstance().delays.get(p.getName()).remove(delayInfo);
							if (Abilities.getInstance().delays.get(p.getName()).isEmpty()) { Abilities.getInstance().delays.remove(p.getName()); }
							break;
						}
						else {
							int seconds = (int) ((delayInfo.expires - System.currentTimeMillis()) / 1000);
							p.sendMessage(Config.getInstance().PREFIX.concat(Text.of(TextColors.RED, "*Ability is recharging (" + seconds + "s)*")));
							return;
						}
					}
				}
			}
			
			if (toolType == raised.get(p.getName())) {
				raised.remove(p.getName());
				Abilities.getInstance().activate(p, ability);
			}
		}
	}
	@Listener(order = Order.LAST)
	public void onTreeVeller(ChangeBlockEvent.Break e, @Root Player p) {
		if (e.isCancelled()) { return; }
		BlockSnapshot bss = e.getTransactions().get(0).getOriginal();
		BlockType type = bss.getState().getType();
		if (type == BlockTypes.LOG || type == BlockTypes.LOG2) {
			if (WatchList.isBlocked(bss.getLocation().get())) { return; }
			if (Abilities.getInstance().active.containsKey(p.getName())) {
				if (Abilities.getInstance().active.get(p.getName()) == Ability.TREE_VELLER) {
					MMOPlayer mmop = MMOPlayerDatabase.getInstance().getOrCreatePlayer(p.getUniqueId().toString());
					breakNext(e.getTransactions().get(0).getOriginal().getLocation().get(), mmop);
				}
			}
		}
	}

	@Listener(order = Order.LAST)
	public void onGreenTerra(InteractBlockEvent.Secondary e, @Root Player p) {
		if (e.isCancelled()) { return; }
		if (Abilities.getInstance().active.containsKey(p.getName())) {
			if (Abilities.getInstance().active.get(p.getName()) == Ability.GREEN_TERRA) {
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

	@Listener(order = Order.LAST)
	public void onItemClick(ClickInventoryEvent e, @Root Player p) {
		if (e.isCancelled()) { return; }
		for (SlotTransaction trans : e.getTransactions()) {
			if (trans.getOriginal().createStack().get(MMOData.class).isPresent()) {
				trans.setCustom(ItemStackSnapshot.NONE);
			}
		}
	}

	@Listener(order = Order.LAST)
	public void onFallDamage(DamageEntityEvent e) {
		if (e.isCancelled()) { return; }
		if (!(e.getTargetEntity() instanceof Player)) { return; }
		Player hurt = (Player) e.getTargetEntity();
		
		MMOPlayer mmohurt = MMOPlayerDatabase.getInstance().getOrCreatePlayer(hurt.getUniqueId().toString());
		int level = mmohurt.getSkills().getSkill(SkillType.ACROBATICS).level;
		
		if (Ability.DODGE.getValue(level) > Math.random()*100.0) {
			e.setCancelled(true);
			hurt.sendMessage(Config.getInstance().PREFIX.concat(Text.of(TextColors.GREEN, "*You dodged to avoid taking damage*")));
		}
		Optional<DamageSource> source = e.getCause().first(DamageSource.class);
		if (source.isPresent()) {
			if (source.get().getType() == DamageTypes.FALL) {
				mmohurt.process(new SkillAction(SkillType.ACROBATICS, (int) (AbilitiesConfig.getInstance().skillExps.get(SkillType.ACROBATICS)[0]*e.getOriginalDamage())));
				if (Ability.ROLL.getValue(level) > Math.random()*100.0) {
					e.setCancelled(true);
					hurt.sendMessage(Config.getInstance().PREFIX.concat(Text.of(TextColors.GREEN, "*You rolled to avoid taking damage*")));
				}
			}
		}
	}
	
	@Listener(order = Order.LAST)
	public void onDamage(DamageEntityEvent e, @First EntityDamageSource source) {
		if (e.isCancelled()) { return; }
		Entity hurtE = e.getTargetEntity();
		if (!(hurtE instanceof Living)) { return; }
		Living hurtL = (Living) hurtE;
		if (source.getSource() instanceof Player) {
			Player damager = (Player) source.getSource();
			MMOPlayer mmodamager = MMOPlayerDatabase.getInstance().getOrCreatePlayer(damager.getUniqueId());
			ToolType tool = (damager.getItemInHand(HandTypes.MAIN_HAND).isPresent()) ? ToolType.matchID(damager.getItemInHand(HandTypes.MAIN_HAND).get().getItem()) : ToolType.HAND;
			
			if (tool == ToolType.SWORD) { mmodamager.process(new SkillAction(SkillType.SWORDS, AbilitiesConfig.getInstance().skillExps.get(SkillType.SWORDS)[1])); }
			else if (tool == ToolType.AXE) { mmodamager.process(new SkillAction(SkillType.AXES, AbilitiesConfig.getInstance().skillExps.get(SkillType.AXES)[1])); }
			else if (tool == ToolType.HAND) { mmodamager.process(new SkillAction(SkillType.UNARMED, AbilitiesConfig.getInstance().skillExps.get(SkillType.UNARMED)[1])); }
			
			if (Abilities.getInstance().active.containsKey(damager.getName())) {
				Ability ability = Abilities.getInstance().active.get(damager.getName());
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
				if (Ability.DISARM.getValue(mmodamager.getSkills().getSkill(SkillType.UNARMED).level) > Math.random()*100.0) {
					if (hurt.getItemInHand(HandTypes.MAIN_HAND).isPresent()) {
						final ItemStack stack = hurt.getItemInHand(HandTypes.MAIN_HAND).get().copy();
						final Location<World> loc = hurt.getLocation().copy();
						hurt.setItemInHand(HandTypes.MAIN_HAND, null);
						Main.getInstance().getGame().getScheduler()
						.createTaskBuilder()
						.delayTicks(30)
						.execute(() -> { ItemUtils.drop(stack, loc); } )
						.submit(Main.getInstance());
					}
				}
			}
		}
		else if (source.getSource() instanceof Arrow) {
			Arrow a = (Arrow) source.getSource();
			if (!(a.getShooter() instanceof Player)) { return; }
			Player damager = (Player) a.getShooter();
			MMOPlayer mmop = MMOPlayerDatabase.getInstance().getOrCreatePlayer(damager.getUniqueId());
			mmop.process(new SkillAction(SkillType.ARCHERY, AbilitiesConfig.getInstance().skillExps.get(SkillType.ARCHERY)[1]));
		}
	}

	@Listener(order = Order.LAST)
	public void onBowShoot(ConstructEntityEvent.Post e) {
		if (e.getTargetEntity() instanceof Arrow) {
			Arrow a = (Arrow) e.getTargetEntity();
			Main.getInstance().getGame().getScheduler().createTaskBuilder().delayTicks(1).execute(() -> {
				if (a.getShooter() instanceof Player) {
					Player p = (Player) a.getShooter();
					MMOPlayer mmop = MMOPlayerDatabase.getInstance().getOrCreatePlayer(p.getUniqueId());
					if (Ability.ARROW_RAIN.getValue(mmop.getSkills().getSkill(SkillType.ARCHERY).level) > Math.random()*100.0) {
						a.offer(Keys.FIRE_TICKS, 1000);
						for (int i = 0; i < 10; i++) {
							Entity ent = a.getWorld().createEntity(a.getType(), a.getLocation().getPosition());
							for (ImmutableValue<?> x : a.getValues()) { ent.offer(x); }
							ent.offer(Keys.FIRE_TICKS, 1000);
							Vector3d old = a.getVelocity();
							ent.setVelocity(new Vector3d(old.getX()+(Math.random()/2)-0.25, old.getY()+(Math.random()/4)-0.125, old.getZ()+(Math.random()/2)-0.25));
							a.getWorld().spawnEntity(ent, Cause.source(Main.getInstance().getGame().getRegistry().createBuilder(Builder.class).entity(ent).type(SpawnTypes.PLUGIN).build()).build());
						}
					}
				}
			}).submit(Main.getInstance());
		}
	}
	@Listener
	public void onDeathBleeding(DestructEntityEvent e) {
		bleeding.forEach(eInfo -> {
			if (eInfo.uuid == e.getTargetEntity().getUniqueId().toString()) {
				bleeding.remove(eInfo);
				return;
			}
		});
	}

	@Listener(order = Order.LAST)
	public void onDeath(DestructEntityEvent.Death e, @First EntityDamageSource source) {
		Living died = e.getTargetEntity();
		if (source.getSource() instanceof Living) {
			Living killerL = (Living) source.getSource();
			if (killerL instanceof Player) {
				Player killer = (Player) killerL;
				
				MMOPlayer mmokiller = MMOPlayerDatabase.getInstance().getOrCreatePlayer(killer.getUniqueId());
				ToolType tool = (killer.getItemInHand(HandTypes.MAIN_HAND).isPresent()) ? ToolType.matchID(killer.getItemInHand(HandTypes.MAIN_HAND).get().getItem()) : ToolType.HAND;
				
				if (tool == ToolType.SWORD) { mmokiller.process(new SkillAction(SkillType.SWORDS, AbilitiesConfig.getInstance().skillExps.get(SkillType.SWORDS)[0])); }
				else if (tool == ToolType.AXE) { mmokiller.process(new SkillAction(SkillType.AXES, AbilitiesConfig.getInstance().skillExps.get(SkillType.AXES)[0])); }
				else if (tool == ToolType.HAND) { mmokiller.process(new SkillAction(SkillType.UNARMED, AbilitiesConfig.getInstance().skillExps.get(SkillType.UNARMED)[0])); }
				
				if (tool != ToolType.SWORD) { return; }
				
				if (Ability.DECAPITATION.getValue(mmokiller.getSkills().getSkill(SkillType.SWORDS).level) > Math.random()*100.0) {
					int dura = 3;
					if (died instanceof Skeleton) {
						if (died.get(Keys.SKELETON_TYPE).isPresent() && died.get(Keys.SKELETON_TYPE).get() == SkeletonTypes.WITHER) dura = 1;
						else dura = 0;
					}
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
			MMOPlayer mmop = MMOPlayerDatabase.getInstance().getOrCreatePlayer(damager.getUniqueId());
			mmop.process(new SkillAction(SkillType.ARCHERY, AbilitiesConfig.getInstance().skillExps.get(SkillType.ARCHERY)[0]));
		}
	}

	private void breakNext(Location<World> loc, MMOPlayer jp) {
		for (Direction dir : new Direction[]{Direction.UP, Direction.DOWN, Direction.NORTH,Direction.EAST, Direction.SOUTH, Direction.WEST}) {
			Location<World> l = loc.getRelative(dir);
			if (l.hasBlock()) {
				if (l.getBlock().getType().equals(BlockTypes.LOG) || l.getBlock().getType().equals(BlockTypes.LOG2)) {
					if (WatchList.isBlocked(l)) { return; }
					ItemType type = l.getBlock().getType().getItem().get();
					int amount = 1;
					int dura = matchTree(l.getBlock().get(Keys.TREE_TYPE).get());
					Skill skill = jp.getSkills().getSkill(SkillType.WOODCUTTING);
					if (Ability.WOODCUTTING_DOUBLEDROP.getValue(skill.level) > Math.random()*100.0) { amount = 2; }
					jp.process(new SkillAction(SkillType.WOODCUTTING, ValuesConfig.getInstance().blockExps.get(l.getBlock().getType())));
					ItemStack stack = ItemUtils.build(type, amount, dura);
					ItemUtils.drop(stack, l);
					l.removeBlock(ServerUtils.getCause());
					breakNext(l, jp);
				}
			}
		}
	}

	private int matchTree(TreeType treeType) {
		if (treeType == TreeTypes.SPRUCE) { return 1; }
		else if (treeType == TreeTypes.BIRCH) { return 2; }
		else if (treeType == TreeTypes.JUNGLE) { return 3; }
		else if (treeType == TreeTypes.DARK_OAK) { return 1; }
		else return 0;
	}
}