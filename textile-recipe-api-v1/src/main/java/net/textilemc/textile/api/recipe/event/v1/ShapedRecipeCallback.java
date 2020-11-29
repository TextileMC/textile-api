package net.textilemc.textile.api.recipe.event.v1;

import net.textilemc.textile.api.event.Event;
import net.textilemc.textile.api.event.EventFactory;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeDispatcher;

/**
 * Allows adding shaped recipes.
 */
@FunctionalInterface
public interface ShapedRecipeCallback {
	/**
	 * Fires at the end of the {@link RecipeDispatcher} constructor
	 */
	Event<ShapedRecipeCallback> EVENT = EventFactory.createArrayBacked(ShapedRecipeCallback.class, listeners -> consumer -> {
		for (ShapedRecipeCallback callback : listeners) {
			callback.accept(consumer);
		}
	});

	void accept(ShapedRecipeConsumer consumer);

	@FunctionalInterface
	interface ShapedRecipeConsumer {
		void addShapedRecipe(ItemStack var1, Object... args);
	}
}
