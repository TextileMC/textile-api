package net.textilemc.textile.api.recipe.event.v1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
	private ShapelessRecipe(ItemStack output, ArrayList<ItemStack> ingredients) {
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
		return new Builder(output);
	}

	/**
	 * Helper class for creating shapeless recipes
	 */
	public static class Builder {
		private final ItemStack output;
		private final ArrayList<ItemStack> stacks = new ArrayList<>();

		private Builder(ItemStack output) {
			this.output = output;
		}

		public Builder add(ItemStack stack) {
			this.stacks.add(Objects.requireNonNull(stack));
			return this;
		}

		public Builder add(Item item) {
			return this.add(new ItemStack(Objects.requireNonNull(item)));
		}

		/**
		 * Converts this builder to a recipe
		 * @return a shapeless recipe
		 */
		@SuppressWarnings("unchecked")
		public ShapelessRecipe build() {
			Preconditions.checkArgument(this.stacks.size() > 0, "Empty Shapeless Recipe");
			return new ShapelessRecipe(this.output, (ArrayList<ItemStack>) this.stacks.clone());
		}
	}
}
