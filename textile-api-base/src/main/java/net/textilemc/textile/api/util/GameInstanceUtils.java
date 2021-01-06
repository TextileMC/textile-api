package net.textilemc.textile.api.util;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.MinecraftApplet;
import net.minecraft.client.MinecraftClient;

/**
 * Utilities to get game instances.
 */
@SuppressWarnings("FieldMayBeFinal")
public class GameInstanceUtils {
	private static MinecraftClient CLIENT = null;
	private static MinecraftApplet APPLET = null;

	@NotNull
	public static MinecraftClient getMinecraftClient() {
		return Objects.requireNonNull(CLIENT, "Requested Minecraft client too early!");
	}

	@NotNull
	public static MinecraftApplet getMinecraftApplet() {
		return Objects.requireNonNull(APPLET, "Requested Minecraft applet too early!");
	}
}
