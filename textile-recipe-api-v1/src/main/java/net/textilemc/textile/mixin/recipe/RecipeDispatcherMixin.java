/*
 * Copyright (c) 2020 TextileMC
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
