package me.mrdaniel.mmo.listeners;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.data.type.BrickTypes;
import org.spongepowered.api.data.type.TreeType;
import org.spongepowered.api.data.type.TreeTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.ConstructEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.AffectSlotEvent;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.enums.AbilityEnum;
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.enums.ToolType;
import me.mrdaniel.mmo.io.Config;
import me.mrdaniel.mmo.io.blocktracking.WatchList;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.io.players.MMOPlayerDatabase;
import me.mrdaniel.mmo.skills.Abilities;
import me.mrdaniel.mmo.skills.Skill;
import me.mrdaniel.mmo.skills.SkillAction;
import me.mrdaniel.mmo.utils.DelayWrapper;
import me.mrdaniel.mmo.utils.ItemUtils;

public class AbilityListener {
	
	private HashMap<String, AbilityEnum> players;
	
	public AbilityListener() { this.players = new HashMap<String, AbilityEnum>(); }
	
	@Listener
	public void onRightClick(InteractBlockEvent.Secondary e, @First final Player p) {
		
		if (!(p.getItemInHand().isPresent())) { return; }
		
		ToolType toolType = ToolType.matchID(p.getItemInHand().get().getItem().getId());
		if (toolType == null) { return; }
		AbilityEnum ae = AbilityEnum.match(toolType);
		if (ae == null) { return; }
		
		if (players.containsKey(p.getName())) {
			if (ae == players.get(p.getName())) {
				p.sendMessage(Config.PREFIX().concat(Text.of(TextColors.RED, "*You lower your " + ae.tool.name() + "*")));
				players.remove(p.getName());
				return;
			}
		}
		
		if (Abilities.getInstance().delays.containsKey(p.getName())) {
			for (DelayWrapper wrapper : Abilities.getInstance().delays.get(p.getName())) {
				if (wrapper.ae.equals(ae)) {
					if (System.currentTimeMillis() > wrapper.expires) {
						Abilities.getInstance().delays.get(p.getName()).remove(wrapper);
						break;
					}
					else {
						int seconds = (int) ((wrapper.expires - System.currentTimeMillis()) / 1000);
						p.sendMessage(Config.PREFIX().concat(Text.of(TextColors.RED, "*Ability is recharging (" + seconds + "s)*")));
						return;
					}
				}
			}
		}
		
		p.sendMessage(Config.PREFIX().concat(Text.of(TextColors.GREEN, "*You ready your " + ae.tool.name() + "*")));
		players.put(p.getName(), ae);
		
		Main.getInstance().getGame().getScheduler().createTaskBuilder()
		.delay(4, TimeUnit.SECONDS)
		.execute(()-> {
			if (players.containsKey(p.getName())) {
				p.sendMessage(Config.PREFIX().concat(Text.of(TextColors.RED, "*You lower your " + ae.tool.name() + "*"))); players.remove(p.getName());
			}
		}).submit(Main.getInstance());
	}
	@Listener
	public void onLeftClick(InteractBlockEvent.Primary e, @First Player p) {
		
		if (!(p.getItemInHand().isPresent())) { return; }
		
		if (players.containsKey(p.getName())) {
			ToolType toolType = ToolType.matchID(p.getItemInHand().get().getItem().getId());
			if (toolType == null) { return; }
			AbilityEnum ae = AbilityEnum.match(toolType);
			if (ae == null) { return; }
			
			if (ae.equals(players.get(p.getName()))) {
				players.remove(p.getName());
				
				Abilities.getInstance().activate(p, ae);
			}
		}
	}
	@Listener(order = Order.LAST)
	public void onTreeVeller(ChangeBlockEvent.Break e, @First Player p) {
		if (e.isCancelled()) { return; }
		BlockSnapshot bss = e.getTransactions().get(0).getOriginal();
		BlockType type = bss.getState().getType();
		if (type.equals(BlockTypes.LOG) || type.equals(BlockTypes.LOG2)) {
			if (WatchList.isBlocked(bss.getLocation().get())) { return; }
			if (Abilities.getInstance().active.containsKey(p.getName())) {
				if (Abilities.getInstance().active.get(p.getName()) == AbilityEnum.TREE_VELLER) {
					MMOPlayer mmop = MMOPlayerDatabase.getInstance().getOrCreate(p.getUniqueId().toString());
					breakNext(e.getTransactions().get(0).getOriginal().getLocation().get(), mmop);
				}
			}
		}
	}
	@Listener(order = Order.LAST)
	public void onGreenTerra(InteractBlockEvent.Secondary e, @First Player p) {
		if (e.isCancelled()) { return; }
		if (Abilities.getInstance().active.containsKey(p.getName())) {
			if (Abilities.getInstance().active.get(p.getName()) == AbilityEnum.GREEN_TERRA) {
				e.setCancelled(true);
				BlockType type = e.getTargetBlock().getState().getType();
				Location<World> loc = e.getTargetBlock().getLocation().get();
				if (type.equals(BlockTypes.DIRT)) { loc.setBlockType(BlockTypes.GRASS); }
				else if (type.equals(BlockTypes.STONEBRICK)) { loc.offer(Keys.BRICK_TYPE, BrickTypes.MOSSY); }
				else if (type.equals(BlockTypes.COBBLESTONE)) { loc.setBlockType(BlockTypes.MOSSY_COBBLESTONE); }
				else if (type.equals(BlockTypes.TRIPWIRE)) { loc.setBlockType(BlockTypes.WEB); }
			}
		}
	}
	@Listener(order = Order.LAST)
	public void onItemClick(ClickInventoryEvent e, @First Player p) {
		if (Abilities.getInstance().active.containsKey(p.getName())) { 
			if (Abilities.getInstance().active.get(p.getName()).equals(AbilityEnum.SUPER_BREAKER)
			|| Abilities.getInstance().active.get(p.getName()).equals(AbilityEnum.GIGA_DRILL_BREAKER)) {
				e.setCancelled(true); 
			}
		}
	}
	@Listener(order = Order.LAST)
	public void onItemSwitch(AffectSlotEvent e, @First Player p) {
		if (Abilities.getInstance().active.containsKey(p.getName())) { 
			if (Abilities.getInstance().active.get(p.getName()).equals(AbilityEnum.SUPER_BREAKER)
			|| Abilities.getInstance().active.get(p.getName()).equals(AbilityEnum.GIGA_DRILL_BREAKER)) {
				e.setCancelled(true); 
			}
		}
	}
	@Listener(order = Order.LAST)
	public void onEntitySpawn(ConstructEntityEvent.Post e) {
		//TODO find a better way to do this
		if (e.getTargetEntity() instanceof Item) {
			final Item itemEntity = (Item) e.getTargetEntity();
			
			Main.getInstance().getGame().getScheduler().createTaskBuilder().delay(50, TimeUnit.MILLISECONDS).execute(()-> {
				ItemStack item = itemEntity.getItemData().item().get().createStack();
				Optional<LoreData> loreOpt = item.get(LoreData.class);
				if (!(loreOpt.isPresent())) { return; }
				for (Text txt : loreOpt.get().asList()) if (txt.toPlain().contains("MMO")) itemEntity.remove();
			}).submit(Main.getInstance());
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
					if (Math.random() * 100.0 < ((double)skill.level) * 0.3) { amount = 2; }
					jp.process(new SkillAction(SkillType.WOODCUTTING, 50));
					ItemStack stack = ItemUtils.build(type, amount, dura);
					ItemUtils.drop(stack, l);
					l.removeBlock();
					breakNext(l, jp);
				}
			}
		}
	}
	private int matchTree(TreeType treeType) {
		if (treeType.equals(TreeTypes.SPRUCE)) { return 1; }
		else if (treeType.equals(TreeTypes.BIRCH)) { return 2; }
		else if (treeType.equals(TreeTypes.JUNGLE)) { return 3; }
		else if (treeType.equals(TreeTypes.DARK_OAK)) { return 1; }
		else return 0;
	}
}