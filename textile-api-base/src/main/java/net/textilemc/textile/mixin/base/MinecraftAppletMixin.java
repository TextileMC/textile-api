package net.textilemc.textile.mixin.base;

import java.lang.reflect.Field;

import net.textilemc.textile.api.util.GameInstanceUtils;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftApplet;
import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftApplet.class)
public class MinecraftAppletMixin {
	@Shadow
	private MinecraftClient client;

	@Inject(method = "init", at = @At("HEAD"))
	public void preInit(CallbackInfo ci) {
		try {
			//noinspection JavaReflectionMemberAccess
			Field f = GameInstanceUtils.class.getField("APPLET");
			f.setAccessible(true);
			f.set(null, this);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new AssertionError();
		}
	}

	@Inject(method = "init", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/client/MinecraftClient;field_969:Z"))
	public void afterInit(CallbackInfo ci) {
		try {
			//noinspection JavaReflectionMemberAccess
			Field f = GameInstanceUtils.class.getField("CLIENT");
			f.setAccessible(true);
			f.set(null, this.client);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new AssertionError();
		}
	}
}
