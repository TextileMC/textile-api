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
