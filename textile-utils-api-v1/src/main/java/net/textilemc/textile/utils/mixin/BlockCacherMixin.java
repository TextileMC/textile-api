package net.textilemc.textile.utils.mixin;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Util;
import net.textilemc.textile.utils.Block.CustomBlock;
import net.textilemc.textile.utils.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Mixin(MinecraftClient.class)
public abstract class BlockCacherMixin {

	@Shadow
	private static File mcDir;

	@Unique
	private String name;

	//Before you ask, yes, caching is necessary in this case. It fails otherwise complaining about "World cannot be cast to String"
	@Inject(at=@At("HEAD"), method = "joinWorld(Ljava/lang/String;)V")
	public void cacheName(String name, CallbackInfo info) {
		this.name = name;
	}

	@Inject(at=@At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;joinWorld(Lnet/minecraft/world/World;Ljava/lang/String;)V", ordinal = 1), method = "joinWorld(Ljava/lang/String;)V")
	public void removeConflictOlderVersion(CallbackInfo info) {
		mapBlockToDat(new File(new File(mcDir, "saves"), name));

	}
	@Inject(at=@At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;joinWorld(Lnet/minecraft/world/World;Ljava/lang/String;)V", ordinal = 2), method = "joinWorld(Ljava/lang/String;)V")
	public void removeConflictOlderVersion2(CallbackInfo info) {
		mapBlockToDat(new File(new File(mcDir, "saves"), name));
	}

	@Unique
	private void mapBlockToDat(File saveDir) {
		if (!(new File(saveDir, "block_registry.dat")).exists()) {
			System.out.println("Saving Block Registry to .minecraft/saves/" + name + "/block_registry.dat");
			CompoundTag tag = new CompoundTag();
			for (int i = 0; i < Block.BLOCKS.length; i++) {
                /*if (Block.BLOCKS[i] == null) {
                } else */if (!(Block.BLOCKS[i] instanceof CustomBlock)) {
					CompoundTag subTag = new CompoundTag();
					subTag.putString("name", "$vanilla$");
					subTag.putByte("id", (byte) i);
					tag.put("block" + i, subTag);
				} else if (Block.BLOCKS[i] instanceof CustomBlock) {
					CompoundTag subtag = new CompoundTag();
					subtag.putString("name", "");
					Identifier stringId = ((CustomBlock) Block.BLOCKS[i]).getStringId();
					stringId.toTag(subtag);
					subtag.putByte("id", (byte) i);
					tag.put("block" + i, subtag);
				}
			}
			try {
				Util.writeCompressed(tag, new FileOutputStream(new File(saveDir, "block_registry.dat")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new NullPointerException();
			}
		} else {
			System.out.println("Matching old Block Registry read from .minecraft/saves/"+name+"/block_registry.dat");
			CompoundTag tagFromFile;
			try {
				tagFromFile = Util.readCompressed(new FileInputStream(new File(saveDir, "block_registry.dat")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new NullPointerException();
			}
			Block[] temp = new Block[256];
			Block[] finalArray = new Block[256];
			Item[] tempItem = new Item[1024];
			Item[] finalItem = new Item[1024];
			System.arraycopy(Item.ITEMS, 0, tempItem, 0, tempItem.length);
			System.arraycopy(Block.BLOCKS, 0, temp, 0, temp.length);
			for (int i = 0; i < 256; i++) {
				CompoundTag blockEntry = tagFromFile.getCompound("block"+i);
				Identifier identifier;
				if (!blockEntry.getString("name").equals("")) {
					//name = blockEntry.getString("name");
					finalArray[i] = temp[i];
					finalItem[i] = tempItem[i];
				} else {
					identifier = Identifier.fromTag(blockEntry);
					int id = blockEntry.getByte("id");
					int currentId = temp[getCurrentIdFromBlock(identifier)].id;
					finalArray[i] = temp[currentId];
					finalArray[i].id = id;
					finalItem[i] = tempItem[currentId];
					finalItem[i].id = id;
				}

			}
			Block.BLOCKS = finalArray;
			Item.ITEMS = finalItem;
		}
	}

	@Unique
	private static int getCurrentIdFromBlock(Identifier id) {
		for (int i = 0; i < 256; i++) {
			if (Block.BLOCKS[i] instanceof CustomBlock) {
				if ((((CustomBlock) Block.BLOCKS[i]).getStringId()).equals(id)) {
					return i;
				}
			}
		}
		throw new NullPointerException("Could not find block! You should only see this if you removed the mod which it was in, in which case your world will have corrupted!! Please make a new World!!");
	}
}
