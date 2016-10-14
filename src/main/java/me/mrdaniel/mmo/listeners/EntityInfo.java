package me.mrdaniel.mmo.listeners;

public class EntityInfo {
	
	public String uuid;
	public String world;
	public int bleeding;
	
	public EntityInfo(String uuid, String world, int bleeding) {
		this.uuid = uuid;
		this.world = world;
		this.bleeding = bleeding;
	}
}