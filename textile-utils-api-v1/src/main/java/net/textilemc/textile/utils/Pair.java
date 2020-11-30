package net.textilemc.textile.utils;

public class Pair<T, M> {
	private final T left;
	private final M right;
	public Pair(T left, M right) {
		this.left = left;
		this.right = right;
	}
	public T getLeft() {
		return left;
	}
	public M getRight() {
		return right;
	}
}
