package net.textilemc.textile.api.recipe.v1;

import java.util.Collections;
import java.util.List;

import net.textilemc.textile.impl.recipe.DispatcherHolder;

import net.minecraft.item.ItemStack;

/**
 * Stores a bunch of recipes
 */
public interface Dispatcher<T extends CraftingRecipeType> {
	void accept(T recipe);

	ItemStack getOutput(List<ItemStack> stacks);

	static <T extends CraftingRecipeType> void register(Dispatcher<T> dispatcher) {
		DispatcherHolder.DISPATCHERS.add(dispatcher);
	}

	static List<Dispatcher<?>> getAll() {
		return Collections.unmodifiableList(DispatcherHolder.DISPATCHERS);
	}

}
