package net.textilemc.textile.mixin.recipe;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.item.ItemStack;
import net.minecraft.storage.CraftingInventory;

@Mixin(CraftingInventory.class)
public interface CraftingInventoryAccessor {
	@Accessor
	ItemStack[] getField_871();
}
