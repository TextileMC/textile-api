package net.textilemc.textile.api.lifecycle.event.v1;

import net.textilemc.textile.api.event.Event;
import net.textilemc.textile.api.event.EventFactory;

import net.minecraft.client.MinecraftClient;

public class ClientLifecycleEvents {
	/**
	 * Fired at the beginning {@link MinecraftClient#shutdown()}
	 */
	public static final Event<ClientStopping> CLIENT_STOPPING = EventFactory.createArrayBacked(ClientStopping.class, listeners -> client -> {
		for (ClientStopping listener : listeners) {
			listener.onClientStopping(client);
		}
	});

	/**
	 * Fired after the window is destroyed.
	 *
	 * <p>This may not be fired if an exception is caught while destroying the mouse and keyboard contexts.</p>
	 */
	public static final Event<ClientStopped> CLIENT_STOPPED = EventFactory.createArrayBacked(ClientStopped.class, listeners -> client -> {
		for (ClientStopped listener : listeners) {
			listener.onClientStopped(client);
		}
	});

	public interface ClientStopping {
		void onClientStopping(MinecraftClient client);
	}

	public interface ClientStopped {
		void onClientStopped(MinecraftClient client);
	}
}
