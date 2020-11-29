/*
 * Copyright (c) 2020 TextileMC
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
