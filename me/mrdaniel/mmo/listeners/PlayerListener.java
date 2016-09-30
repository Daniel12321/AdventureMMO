package me.mrdaniel.mmo.listeners;

import java.util.Optional;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
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

import me.mrdaniel.mmo.enums.PassiveEnum;
import me.mrdaniel.mmo.enums.RepairEnum;
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.io.Config;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.io.players.MMOPlayerDatabase;
import me.mrdaniel.mmo.skills.Skill;
import me.mrdaniel.mmo.skills.SkillAction;
import me.mrdaniel.mmo.utils.ItemUtils;

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
				if (p.getItemInHand().isPresent() && p.getItemInHand().get() != ItemTypes.NONE) {
					
					RepairEnum re = RepairEnum.match(p.getItemInHand().get().getItem().getId());
					if (re == null) { return; }
					
					e.setCancelled(true);
					
					MMOPlayer mmop = MMOPlayerDatabase.getInstance().getOrCreate(p.getUniqueId().toString());
					p.setItemInHand(null);
					Location<World> dropLoc = new Location<World>(loc.getExtent(), loc.getX(), loc.getY()+1, loc.getZ());
					
					double maxItems = re.getMaxItems();
					int level = mmop.getSkills().getSkill(SkillType.SALVAGE).level;
					double percent = 25 + level;
					if (percent > 100) { percent = 100; }
					int amount = (int) (maxItems * (percent/100.0));
					if (amount == 0) { amount = 1; }
					
					ItemStack item = ItemUtils.build(re.getType(), amount, 0);
					ItemUtils.drop(item, dropLoc);
					mmop.process(new SkillAction(SkillType.SALVAGE, 400));
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
		PassiveEnum pe = PassiveEnum.DODGE;
		if (pe.start + (pe.increase * ((double)skill.level)) > (Math.random())*100) {
			e.setCancelled(true);
			p.sendMessage(Config.PREFIX().concat(Text.of(TextColors.GREEN, "*You dodged to avoid taking damage*")));
		}
		Optional<DamageSource> sourceOpt = e.getCause().first(DamageSource.class);
		if (sourceOpt.isPresent()) {
			DamageSource source = sourceOpt.get();
			if (source.getType().equals(DamageTypes.FALL)) {
				mmop.process(new SkillAction(SkillType.ACROBATICS, (int) (4.0*e.getOriginalDamage())));
				pe = PassiveEnum.ROLL;
				if (pe.start + (pe.increase * ((double)skill.level)) > (Math.random())*100) {
					e.setCancelled(true);
					p.sendMessage(Config.PREFIX().concat(Text.of(TextColors.GREEN, "*You rolled to avoid taking damage*")));
				}
			}
		}
	}
	@Listener(order = Order.LAST)
	public void onFishing(FishingEvent.Stop e, @First Player p) {
		if (e.isCancelled()) { return; }
		if (e.getItemStackTransaction() != null) if (e.getItemStackTransaction().getFinal() != null && e.getItemStackTransaction().getFinal().getType() != ItemTypes.NONE) { 
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