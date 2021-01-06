package net.textilemc.textile.api.recipe.event.v1;

import java.util.List;

import net.minecraft.item.ItemStack;

/**
 * Stores a bunch of recipes
 */
public interface Dispatcher<T extends CraftingRecipeType> {
	void accept(T recipe);

	ItemStack getOutput(List<ItemStack> stacks);
}
