package net.textilemc.textile.impl.recipe;

import java.util.LinkedList;

import net.textilemc.textile.api.recipe.v1.Dispatcher;
import net.textilemc.textile.api.recipe.v1.ShapelessRecipe;
import net.textilemc.textile.api.recipe.v1.SimpleDispatcher;

public class DispatcherHolder {
	public static final LinkedList<Dispatcher<?>> DISPATCHERS = new LinkedList<>();
}
