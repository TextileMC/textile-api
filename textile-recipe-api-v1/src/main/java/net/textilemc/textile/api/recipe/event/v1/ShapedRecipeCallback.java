package net.textilemc.textile.api.recipe.event.v1;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface ShapedRecipeCallback {
	void addRecipes(ShapedRecipeConsumer consumer);

	@FunctionalInterface
	interface ShapedRecipeConsumer {
		void addShapedRecipe(ItemStack var1, Object... args);
	}
}
