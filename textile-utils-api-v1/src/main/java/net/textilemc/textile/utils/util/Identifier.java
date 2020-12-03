package net.textilemc.textile.utils.util;

import net.minecraft.nbt.CompoundTag;

public class Identifier {
	private final String namespace;
	private final String path;


	public Identifier (String namespace, String path) {
		this.namespace = namespace;
		this.path = path;
	}

	public String getNamespace() {
		return namespace;
	}

	public String getPath() {
		return path;
	}

	public CompoundTag toTag(CompoundTag tag) {
		CompoundTag subtag = new CompoundTag();
		subtag.putString("namespace", this.getNamespace());
		subtag.putString("path", this.getPath());
		tag.put("nameId", subtag);
		return tag;
	}

	public static Identifier fromTag(CompoundTag tag) {
		return new Identifier(tag.getString("namespace"), tag.getString("path"));
	}
}
