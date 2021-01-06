package net.textilemc.textile.api.recipe.v1;

import java.util.ArrayList;
import java.util.Objects;

import com.google.common.base.Preconditions;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ShapelessRecipeBuilder implements ShapelessRecipe.Builder {
	private final ItemStack output;
	private final ArrayList<ItemStack> stacks = new ArrayList<>();

	ShapelessRecipeBuilder(ItemStack output) {
		this.output = output;
	}

	public ShapelessRecipeBuilder add(ItemStack stack) {
		this.stacks.add(Objects.requireNonNull(stack));
		return this;
	}

	public ShapelessRecipeBuilder add(Item item) {
		return this.add(new ItemStack(Objects.requireNonNull(item)));
	}

	@SuppressWarnings("unchecked")
	public ShapelessRecipe build() {
		Preconditions.checkArgument(this.stacks.size() > 0, "Empty Shapeless Recipe");
		return new ShapelessRecipe(this.output, (ArrayList<ItemStack>) this.stacks.clone());
	}
}
