package net.textilemc.textile.gui.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.Button;
import net.minecraft.client.gui.DrawableHelper;

@Mixin(Button.class)
public abstract class ButtonMixin extends DrawableHelper {
    @Shadow private int height;

    @Inject(method = "<init>(IIIIILjava/lang/String;)V", at = @At("RETURN"))
    private void onInit(int i, int i2, int i3, int width, int height, String text, CallbackInfo ci) {
        this.height = height;
    }
}
