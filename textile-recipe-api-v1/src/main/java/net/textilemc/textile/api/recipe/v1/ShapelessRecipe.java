package net.textilemc.textile.api.recipe.v1;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Recipe type for recipes that do not have any specific shape.
 */
public class ShapelessRecipe implements CraftingRecipeType {
	public static final Dispatcher<ShapelessRecipe> DISPATCHER = new SimpleDispatcher<>();
	private final ItemStack output;
	private final ArrayList<ItemStack> ingredients;

	/**
	 * @param output The output stack
	 * @param ingredients The ingredients
	 * @throws IllegalArgumentException If the size of ingredients is 0
	 */
	public ShapelessRecipe(ItemStack output, ArrayList<ItemStack> ingredients) {
		Preconditions.checkArgument(ingredients.size() > 0);
		this.output = output;
		this.ingredients = ingredients;
	}

	@Override
	public ItemStack getOutput(List<ItemStack> stacks) {
		return this.output;
	}
	@Override
	public boolean matches(List<ItemStack> items, World world) {
		//noinspection unchecked
		ArrayList<ItemStack> stacks = (ArrayList<ItemStack>) this.ingredients.clone();

		for (ItemStack stack : items) {
			for (ItemStack itemStack : stacks) {
				if (stack != null && stack.id == itemStack.id && (itemStack.meta == 32767 || stack.meta == itemStack.meta)) {
					stacks.remove(itemStack);
					break;
				}
			}
		}

		return stacks.isEmpty();
	}

	public static Builder builder(ItemStack output) {
		return new ShapelessRecipeBuilder(output);
	}

	static {
		Dispatcher.register(DISPATCHER);
	}

	/**
	 * Helper class for creating shapeless recipes
	 */
	public interface Builder {
		/**
		 * @param stack The item stack to be added to the shapeless recipe
		 * @return This builder
		 */
		Builder add(ItemStack stack);

		/**
		 * @param item The item to be added to the shapeless recipe
		 * @return This builder
		 */
		Builder add(Item item);

		/**
		 * Converts this builder to a recipe
		 *
		 * @return a shapeless recipe
		 */
		ShapelessRecipe build();
	}
}
