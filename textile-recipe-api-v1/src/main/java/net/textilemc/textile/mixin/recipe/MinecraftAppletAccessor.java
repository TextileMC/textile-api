package net.textilemc.textile.mixin.recipe;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.MinecraftApplet;
import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftApplet.class)
public interface MinecraftAppletAccessor {
	@Accessor
	MinecraftClient getClient();
}
