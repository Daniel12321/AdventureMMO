package me.mrdaniel.adventuremmo.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import com.google.common.collect.Maps;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolTypes;
import me.mrdaniel.adventuremmo.data.BlockData;
import me.mrdaniel.adventuremmo.data.ToolData;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ItemDatabase extends MMOObject {

	private final Map<BlockType, BlockData> blocks;
	private final Map<ItemType, ToolData> tools;

	private final ConfigurationLoader<CommentedConfigurationNode> loader;
	private final CommentedConfigurationNode node;

	public ItemDatabase(@Nonnull final AdventureMMO mmo, @Nonnull final Path path) {
		super(mmo);

		this.blocks = Maps.newHashMap();
		this.tools = Maps.newHashMap();

		this.loader = HoconConfigurationLoader.builder().setPath(path).build();

		if (!Files.exists(path)) {
			try { mmo.getContainer().getAsset("itemdata.conf").get().copyToFile(path); }
			catch (final IOException exc) { mmo.getLogger().error("Failed to create itemdata file: {}", exc); }
		}
		this.node = this.load();

		this.node.getNode("blocks").getChildrenMap().forEach((ido, value) -> {
			String id = (String)ido;
			Optional<BlockType> type = super.getGame().getRegistry().getType(BlockType.class, id);
			Optional<BlockData> data = BlockData.deserialize(value.getString());
			if (type.isPresent()) {
				if (data.isPresent()) { if (super.getMMO().getConfig().isSkillEnabled(data.get().getSkill())) { this.blocks.put(type.get(), data.get()); } }
				else { super.getLogger().warn("Invalid exp format for block id {}, skipping!", id); }
			}
			else { super.getLogger().warn("Failed to find block id {}, skipping!", id); }
		});

		this.node.getNode("tools").getChildrenMap().forEach((ido, value) -> {
			String id = (String)ido;
			Optional<ItemType> type = super.getGame().getRegistry().getType(ItemType.class, id);
			Optional<ToolData> data = ToolData.deserialize(value.getString());
			if (type.isPresent()) {
				if (data.isPresent()) { this.tools.put(type.get(), data.get()); }
				else { super.getLogger().warn("Invalid tooltype format for tool id {}, skipping!", id); }
			}
			else { super.getLogger().warn("Failed to find item id {}, skipping!", id); }
		});
	}

	private CommentedConfigurationNode load() {
		try { return this.loader.load(); }
		catch (final IOException exc) { super.getMMO().getLogger().error("Failed to load itemdata file: {}", exc); return this.loader.createEmptyNode(); }
	}

//	public void set(@Nonnull final BlockType type, @Nonnull final BlockData data) {
//		this.blocks.put(type, data);
//		this.node.getNode("blocks", type.getId()).setValue(data.serialize());
//		this.save();
//	}
//
//	public void set(@Nonnull final ItemType type, @Nonnull final ToolData data) {
//		this.tools.put(type, data);
//		this.node.getNode("tools", type.getId()).setValue(data.serialize());
//		this.save();
//	}
//
//	private void save() {
//		try { this.loader.save(this.node); }
//		catch (final IOException exc) { super.getMMO().getLogger().error("Failed to save itemdata file: {}", exc); }
//	}

	@Nonnull
	public Optional<BlockData> getData(@Nonnull final BlockType type) {
		return Optional.ofNullable(this.blocks.get(type));
	}

	@Nonnull
	public Optional<ToolData> getData(@Nonnull final ItemType type) {
		return Optional.ofNullable(this.tools.get(type));
	}

	@Nonnull
	public Optional<ToolData> getData(@Nullable final ItemStack item) {
		if (item == null) { return Optional.of(new ToolData(ToolTypes.HAND)); }
		return Optional.ofNullable(this.tools.get(item.getItem()));
	}
}