package net.textilemc.textile.utils;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class SidedRegistry {

	public static Pair<Block, Item> registerBlockWithItem(int id, Material material) {
		Block BLOCK = new Block(id, material);
		if (Block.BLOCKS[id] != null) {
			Item.ITEMS[id] = new BlockItem(id - 256);
		}
		return new Pair<>(BLOCK, Item.ITEMS[id]);
	}

}
