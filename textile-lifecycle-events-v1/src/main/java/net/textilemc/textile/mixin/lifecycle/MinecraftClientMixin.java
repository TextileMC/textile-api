package net.textilemc.textile.mixin.lifecycle;

import net.textilemc.textile.api.lifecycle.event.v1.ClientLifecycleEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	@Inject(method = "shutdown", at = @At(value = "INVOKE", target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V", remap = false, shift = At.Shift.AFTER))
	public void shutdownEvent(CallbackInfo ci) {
		ClientLifecycleEvents.CLIENT_STOPPING.invoker().onClientStopping((MinecraftClient) (Object) this);
	}

	@Inject(method = "shutdown", at = @At("TAIL"))
	public void afterShutdown(CallbackInfo ci) {
		ClientLifecycleEvents.CLIENT_STOPPED.invoker().onClientStopped((MinecraftClient) (Object) this);
	}
}
