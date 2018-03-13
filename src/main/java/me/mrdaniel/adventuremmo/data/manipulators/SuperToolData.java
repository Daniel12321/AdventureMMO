package me.mrdaniel.adventuremmo.data.manipulators;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.inventory.ItemStack;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import me.mrdaniel.adventuremmo.data.MMOKeys;
import me.mrdaniel.adventuremmo.utils.TextUtils;

public class SuperToolData extends AbstractData<SuperToolData, ImmutableSuperToolData> {

	private List<Enchantment> enchants;
	private String name;
	private int durability;

	public SuperToolData() {
		this(Lists.newArrayList(), "", 0);
	}

	public SuperToolData(@Nonnull final List<Enchantment> enchants, @Nonnull final String name, final int durability) {
		this.enchants = enchants;
		this.name = name;
		this.durability = durability;

		registerGettersAndSetters();
	}

	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(MMOKeys.ENCHANTS, this::getEnchants);
		registerFieldSetter(MMOKeys.ENCHANTS, this::setEnchants);
		registerKeyValue(MMOKeys.ENCHANTS, this::getEnchantsValue);

		registerFieldGetter(MMOKeys.NAME, this::getName);
		registerFieldSetter(MMOKeys.NAME, this::setName);
		registerKeyValue(MMOKeys.NAME, this::getNameValue);

		registerFieldGetter(MMOKeys.DURABILITY, this::getDurability);
		registerFieldSetter(MMOKeys.DURABILITY, this::setDurability);
		registerKeyValue(MMOKeys.DURABILITY, this::getDurabilityValue);
	}

	public ListValue<Enchantment> getEnchantsValue() {
		return MMOKeys.FACTORY.createListValue(MMOKeys.ENCHANTS, this.enchants);
	}

	public List<Enchantment> getEnchants() {
		return this.enchants;
	}

	public void setEnchants(final List<Enchantment> enchants) {
		this.enchants = enchants;
	}

	public Value<String> getNameValue() {
		return MMOKeys.FACTORY.createValue(MMOKeys.NAME, this.name);
	}

	public String getName() {
		return this.name;
	}

	public void setName(@Nonnull final String name) {
		this.name = name;
	}

	public Value<Integer> getDurabilityValue() {
		return MMOKeys.FACTORY.createValue(MMOKeys.DURABILITY, this.durability);
	}

	public int getDurability() {
		return this.durability;
	}

	public void setDurability(final int durability) {
		this.durability = durability;
	}

	@Nonnull
	public ItemStack restore(@Nonnull final ItemStack tool) {
		if (this.name.equals("")) {
			tool.remove(Keys.DISPLAY_NAME);
		} else {
			tool.offer(Keys.DISPLAY_NAME, TextUtils.toText(this.name));
		}

		tool.offer(Keys.ITEM_ENCHANTMENTS, this.enchants);
		tool.offer(Keys.UNBREAKABLE, false);
		if (this.durability != 0) {
			tool.offer(Keys.ITEM_DURABILITY, this.durability);
		}

		tool.remove(SuperToolData.class);
		return tool;
	}

	@SuppressWarnings("unchecked")
	public Optional<SuperToolData> from(@Nonnull final DataView view) {
		return Optional
				.of(new SuperToolData(
						view.getList(MMOKeys.ENCHANTS.getQuery()).map(l -> (List<Enchantment>) l)
								.orElse(Lists.newArrayList()),
						view.getString(MMOKeys.NAME.getQuery()).orElse(""),
						view.getInt(MMOKeys.DURABILITY.getQuery()).orElse(0)));
	}

	@Override
	public DataContainer toContainer() {
		return super.toContainer().set(MMOKeys.ENCHANTS.getQuery(), this.enchants)
				.set(MMOKeys.NAME.getQuery(), this.name).set(MMOKeys.DURABILITY.getQuery(), this.durability);
	}

	@Override
	public Optional<SuperToolData> fill(DataHolder holder, MergeFunction overlap) {
		return Optional.of(Preconditions.checkNotNull(overlap).merge(copy(), from(holder.toContainer()).orElse(null)));
	}

	@Override
	public Optional<SuperToolData> from(DataContainer container) {
		return from((DataView) container);
	}

	@Override
	public SuperToolData copy() {
		return new SuperToolData(this.enchants, this.name, this.durability);
	}

	@Override
	public ImmutableSuperToolData asImmutable() {
		return new ImmutableSuperToolData(this.enchants, this.name, this.durability);
	}

	@Override
	public int getContentVersion() {
		return 1;
	}
}