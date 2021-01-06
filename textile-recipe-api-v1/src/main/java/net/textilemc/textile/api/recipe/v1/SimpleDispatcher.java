package net.textilemc.textile.api.recipe.v1;

import java.util.ArrayList;
import java.util.List;

import net.textilemc.textile.api.util.GameInstanceUtils;
import org.jetbrains.annotations.Nullable;

import net.minecraft.item.ItemStack;

/**
 * A simple dispatcher implementation.
 * @param <T> The recipe type
 */
public class SimpleDispatcher<T extends CraftingRecipeType> implements Dispatcher<T> {
	private final List<T> recipes = new ArrayList<>();

	@Override
	public void accept(T recipe) {
		this.recipes.add(recipe);
	}

	@Nullable
	@Override
	public ItemStack getOutput(List<ItemStack> stacks) {
		for (T recipe : this.recipes) {
			if (recipe.matches(stacks, GameInstanceUtils.getMinecraftClient().world)) {
				return recipe.getOutput(stacks);
			}
		}

		return null;
	}
}
