package me.mrdaniel.mmo.io.top;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import me.mrdaniel.mmo.utils.TopWrapper;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class Top {

	private File file;
	private ConfigurationLoader<?> manager;
	private ConfigurationNode storage;
	
	public Top(File f) {
		file = f;
		manager = HoconConfigurationLoader.builder().setFile(file).build();
		storage = manager.createEmptyNode(ConfigurationOptions.defaults());
	}
		
	public void setup() {
		try {
			if (!file.exists()) {
				file.createNewFile();
				
				storage.getNode("1", "name").setValue("x");
				storage.getNode("1", "level").setValue(0);
				
				storage.getNode("2", "name").setValue("x");
				storage.getNode("2", "level").setValue(0);
				
				storage.getNode("3", "name").setValue("x");
				storage.getNode("3", "level").setValue(0);
				
				storage.getNode("4", "name").setValue("x");
				storage.getNode("4", "level").setValue(0);
				
				storage.getNode("5", "name").setValue("x");
				storage.getNode("5", "level").setValue(0);
				
				storage.getNode("6", "name").setValue("x");
				storage.getNode("6", "level").setValue(0);
				
				storage.getNode("7", "name").setValue("x");
				storage.getNode("7", "level").setValue(0);
				
				storage.getNode("8", "name").setValue("x");
				storage.getNode("8", "level").setValue(0);
				
				storage.getNode("9", "name").setValue("x");
				storage.getNode("9", "level").setValue(0);
				
				storage.getNode("10", "name").setValue("x");
				storage.getNode("10", "level").setValue(0);
				
		        manager.save(storage);
			}
			storage = manager.load();
		}
		catch (IOException e) { e.printStackTrace(); }
	}
	public void update(String name, int level) {
		
		for (int i = 1; i <= 10; i++) {
			if (storage.getNode(String.valueOf(i), "name").getString().equalsIgnoreCase(name)) {
				if (level < storage.getNode(String.valueOf(i), "level").getInt()) {
					storage.getNode(String.valueOf(i), "level").setValue(level-1);
					for (int j = i; j <= 9; j++) {
						switchPlaces(j, j+1);
					}
					update(name, level);
					return;
				}
				storage.getNode(String.valueOf(i), "level").setValue(level);
				jLoop: for (int j = i; j >= 2; j--) {
					if (j == 1) { break jLoop; }
					if (level >= storage.getNode(String.valueOf(j-1), "level").getInt()) {
						switchPlaces(j, j-1);
					}
				}
				try { manager.save(storage); }
				catch (IOException exc) { exc.printStackTrace(); }
				return;
			}
		}
		if (level < storage.getNode("10", "level").getInt()) { return; }
		storage.getNode("10", "name").setValue(name);
		storage.getNode("10", "level").setValue(level);
		for (int i = 10; i >= 2; i--) {
			if (level >= storage.getNode(String.valueOf(i-1), "level").getInt()) {
				switchPlaces(i, i-1);
			}
			else { break; }
		}
		try { manager.save(storage); }
		catch (IOException exc) { exc.printStackTrace(); }
	}
	private void switchPlaces(int first, int second) {
		String tempName = storage.getNode(String.valueOf(first), "name").getString();
		int tempLevel = storage.getNode(String.valueOf(first), "level").getInt();
		
		storage.getNode(String.valueOf(first), "name").setValue(storage.getNode(String.valueOf(second), "name").getString());
		storage.getNode(String.valueOf(first), "level").setValue(storage.getNode(String.valueOf(second), "level").getInt());
		
		storage.getNode(String.valueOf(second), "name").setValue(tempName);
		storage.getNode(String.valueOf(second), "level").setValue(tempLevel);
	}
	public TreeMap<Integer, TopWrapper> getTop() {
		TreeMap<Integer, TopWrapper> top = new TreeMap<Integer, TopWrapper>();
		for (ConfigurationNode idNode : storage.getChildrenMap().values()) {
			top.put(Integer.valueOf(idNode.getKey().toString()), new TopWrapper(idNode.getNode("level").getInt(), idNode.getNode("name").getString()));
		}
		return top;
	}
}