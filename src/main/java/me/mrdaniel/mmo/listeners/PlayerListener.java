package me.mrdaniel.mmo.listeners;

import java.util.Optional;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.action.FishingEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.TameEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.mmo.enums.Ability;
import me.mrdaniel.mmo.enums.RepairStore;
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.io.Config;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.io.players.MMOPlayerDatabase;
import me.mrdaniel.mmo.skills.Skill;
import me.mrdaniel.mmo.skills.SkillAction;
import me.mrdaniel.mmo.utils.ItemUtils;
import me.mrdaniel.mmo.utils.ItemWrapper;

public class PlayerListener {
	
	@Listener(order = Order.LAST)
	public void onInteractRight(InteractBlockEvent.Secondary e, @First Player p) {
		if (e.isCancelled()) { return; }
		BlockSnapshot bss = e.getTargetBlock();
		BlockState bs = bss.getState();
		
		Optional<Location<World>> locOpt = bss.getLocation();
		if (locOpt.isPresent()) {
			Location<World> loc = locOpt.get();
			
			if (bs.getType().equals(BlockTypes.GOLD_BLOCK)) {
				if (p.getItemInHand(HandTypes.MAIN_HAND).isPresent() && p.getItemInHand(HandTypes.MAIN_HAND).get() != ItemTypes.NONE) {
					ItemStack hand = p.getItemInHand(HandTypes.MAIN_HAND).get();
					if (!RepairStore.getInstance().items.containsKey(hand.getItem().getType())) { return; }
					
					ItemWrapper ir = RepairStore.getInstance().items.get(hand.getItem().getType());
					
					e.setCancelled(true);
					
					MMOPlayer mmop = MMOPlayerDatabase.getInstance().getOrCreate(p.getUniqueId().toString());
					p.setItemInHand(HandTypes.MAIN_HAND, null);
					Location<World> dropLoc = new Location<World>(loc.getExtent(), loc.getX(), loc.getY()+1, loc.getZ());
					
					double maxItems = ir.amount;
					int level = mmop.getSkills().getSkill(SkillType.SALVAGE).level;
					double percent = Ability.SALVAGE.getValue(level);
					if (percent > 100) { percent = 100; }
					int amount = (int) (maxItems * (percent/100.0));
					if (amount < 1) { amount = 1; }
					
					ItemStack item = ItemUtils.build(ir.type, amount, 0);
					ItemUtils.drop(item, dropLoc);
					mmop.process(new SkillAction(SkillType.SALVAGE, ir.exp));
				}
				else { p.sendMessage(Config.PREFIX().concat(Text.of(TextColors.GREEN, "Click the Gold Block with an item to salvage it"))); }
			}
		}
	}
	@Listener(order = Order.EARLY)
	public void onFallDamage(DamageEntityEvent e) {
		if (!(e.getTargetEntity() instanceof Player)) { return; }
		Player p = (Player) e.getTargetEntity();
		MMOPlayer mmop = MMOPlayerDatabase.getInstance().getOrCreate(p.getUniqueId().toString());
		Skill skill = mmop.getSkills().getSkill(SkillType.ACROBATICS);
		Ability ability = Ability.DODGE;
		if (ability.getValue(skill.level) > Math.random()*100.0) {
			e.setCancelled(true);
			p.sendMessage(Config.PREFIX().concat(Text.of(TextColors.GREEN, "*You dodged to avoid taking damage*")));
		}
		Optional<DamageSource> sourceOpt = e.getCause().first(DamageSource.class);
		if (sourceOpt.isPresent()) {
			DamageSource source = sourceOpt.get();
			if (source.getType().equals(DamageTypes.FALL)) {
				mmop.process(new SkillAction(SkillType.ACROBATICS, (int) (4.0*e.getOriginalDamage())));
				ability = Ability.ROLL;
				if (ability.getValue(skill.level) > Math.random()*100.0) {
					e.setCancelled(true);
					p.sendMessage(Config.PREFIX().concat(Text.of(TextColors.GREEN, "*You rolled to avoid taking damage*")));
				}
			}
		}
	}
	@Listener(order = Order.LAST)
	public void onFishing(FishingEvent.Stop e, @First Player p) {
		if (e.isCancelled()) { return; }
		if (e.getItemStackTransaction() != null) if (e.getItemStackTransaction() != null 
				&& e.getItemStackTransaction().get(0) != null 
				&& e.getItemStackTransaction().get(0).getFinal() != null 
				&& e.getItemStackTransaction().get(0).getFinal() != ItemTypes.NONE) { 
			MMOPlayer mmop = MMOPlayerDatabase.getInstance().getOrCreate(p.getUniqueId().toString());
			mmop.process(new SkillAction(SkillType.FISHING, 450));
			Drops.getInstance().FishingTreasure(p, mmop);
		}
	}
	@Listener(order = Order.LAST)
	public void onTaming(TameEntityEvent e, @First Player p) {
		if (e.isCancelled()) { return; }
		MMOPlayer mmop = MMOPlayerDatabase.getInstance().getOrCreate(p.getUniqueId().toString());
		mmop.process(new SkillAction(SkillType.TAMING, 750));
	}
	@Listener
	public void onPlayerJoin(ClientConnectionEvent.Join e) {
		MMOPlayer mmop = MMOPlayerDatabase.getInstance().getOrCreate(e.getTargetEntity().getUniqueId().toString());
		mmop.save();
		mmop.updateTop(e.getTargetEntity().getName());
	}
	@Listener
	public void onPlayerQuit(ClientConnectionEvent.Disconnect e) {
		MMOPlayer mmop = MMOPlayerDatabase.getInstance().getOrCreate(e.getTargetEntity().getUniqueId().toString());
		mmop.save();
		mmop.updateTop(e.getTargetEntity().getName());
		MMOPlayerDatabase.getInstance().unload(mmop);
	}
}