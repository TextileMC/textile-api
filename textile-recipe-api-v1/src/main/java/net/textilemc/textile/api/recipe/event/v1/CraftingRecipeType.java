package net.textilemc.textile.api.recipe.event.v1;

import java.util.Arrays;
import java.util.List;

import net.textilemc.textile.mixin.recipe.CraftingInventoryAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.storage.CraftingInventory;
import net.minecraft.world.World;

/**
 * Base class for custom crafting recipe types.
 */
public interface CraftingRecipeType {
	/**
	 * @param inventory The inventory. May be null
	 * @return Output stack
	 */
	ItemStack getOutput(List<ItemStack> inventory);

	/**
	 * @param inventory The inventory
	 * @return Whether the inventory's stacks match
	 */
	default boolean matches(CraftingInventory inventory, World world) {
		//noinspection ConstantConditions
		return this.matches(Arrays.asList(((CraftingInventoryAccessor) (Object) inventory).getField_871().clone()), world);
	}

	/**
	 * @param stacks The item stacks
	 * @return Whether the inventory's stacks match
	 */
	boolean matches(List<ItemStack> stacks, World world);

	/**
	 * Called after this recipe has been matched and consumed.
	 *
	 * <p>This can be used to add recipe remainders, damage items, etc.</p>
	 */
	default void afterEvaluate(@NotNull CraftingInventory inventory, World world) {
	}
}
