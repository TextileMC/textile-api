package net.textilemc.textile.api.recipe.event.v1.test;

import net.textilemc.textile.api.recipe.event.v1.ShapelessRecipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.fabricmc.api.ModInitializer;

public class RecipeTest implements ModInitializer {
	@Override
	public void onInitialize() {
		ShapelessRecipe.DISPATCHER.accept(ShapelessRecipe.builder(new ItemStack(Item.COAL))
				.add(new ItemStack(Block.LOG))
				.add(new ItemStack(Block.DIRT))
				.add(new ItemStack(Block.DIRT))
				.add(new ItemStack(Block.DIRT))
				.add(new ItemStack(Block.DIRT))
				.build());
	}
}
