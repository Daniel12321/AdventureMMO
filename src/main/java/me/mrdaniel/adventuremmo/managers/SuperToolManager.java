package me.mrdaniel.adventuremmo.managers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.Hotbar;
import org.spongepowered.api.item.inventory.entity.PlayerInventory;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.util.Tuple;

import com.google.common.collect.Maps;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;
import me.mrdaniel.adventuremmo.utils.ItemUtils;
import me.mrdaniel.adventuremmo.utils.ServerUtils;

public class SuperToolManager extends MMOObject {

	private final Map<UUID, Tuple<SlotIndex, ItemStack>> items;

	public SuperToolManager(@Nonnull final AdventureMMO mmo) {
		super(mmo);

		this.items = Maps.newHashMap();
	}

	public void give(@Nonnull final Player p, @Nonnull final ToolType tool) {
		if (this.items.containsKey(p.getUniqueId())) { this.undo(p); }

		ItemStack hand = p.getItemInHand(HandTypes.MAIN_HAND).get();
		this.items.put(p.getUniqueId(), new Tuple<SlotIndex, ItemStack>(new SlotIndex(((PlayerInventory)p.getInventory()).getHotbar().getSelectedSlotIndex()), hand));

		p.setItemInHand(HandTypes.MAIN_HAND, ItemUtils.createSuperTool(hand, tool));
	}

	public void undo(@Nonnull final Player p) {
		Optional.ofNullable(this.items.get(p.getUniqueId())).ifPresent(tool -> {
			p.closeInventory(ServerUtils.getCause(super.getContainer(), NamedCause.of("player", p)));
			p.getInventory().query(Hotbar.class).query(tool.getFirst()).set(tool.getSecond());
			this.items.remove(p.getUniqueId());
		});
	}
}