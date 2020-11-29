package net.textilemc.textile.mixin.recipe;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.textilemc.textile.api.recipe.event.v1.ShapedRecipeCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeDispatcher;

@Mixin(RecipeDispatcher.class)
abstract class RecipeDispatcherMixin {
	@Shadow
	abstract void addShapedRecipe(ItemStack output, Object... args);

	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/util/Collections;sort(Ljava/util/List;Ljava/util/Comparator;)V"))
	public <T> void interceptSort(List<T> list, Comparator<? super T> c) {
		ShapedRecipeCallback.EVENT.invoker().accept(this::addShapedRecipe);
		//noinspection Java8ListSort
		Collections.sort(list, c);
	}
}
