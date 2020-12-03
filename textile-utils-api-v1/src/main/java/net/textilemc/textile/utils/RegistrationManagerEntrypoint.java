package net.textilemc.textile.utils;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrationManagerEntrypoint implements ModInitializer {

	public static Logger LOGGER = LogManager.getLogger("Textile API");

	@Override
	public void onInitialize () {
		LOGGER.info("Block registrar - Loading up!");
	}

	// Cannot return 0 or else MC will hang on the TitleScreen
	public static int getNextBlockId() {
		for (int i = 1; i < Block.BLOCKS.length; i++) {
			if (Block.BLOCKS[i] == null)
				return i;
		}
		return -1;
	}
}
