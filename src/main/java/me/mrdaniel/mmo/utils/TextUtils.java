package me.mrdaniel.mmo.utils;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.serializer.TextSerializers;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.enums.SkillType;

public class TextUtils {
	
	public static Text color(String message) {
		Text result = Text.of();
    	result = Text.builder().append(TextSerializers.formattingCode('&').deserialize(message)).build();
    	return result;
	}
	public static Text setCommandClick(String messageWithColors, String command, String hoverWithColors) { 
		Text txt = Text.builder().append(color(messageWithColors)).onHover(TextActions.showText(color(hoverWithColors))).onClick(TextActions.runCommand(command)).build();
		return txt;
	}
	public static List<String> getSkillsSuggestions(String current) {
		List<String> s = new ArrayList<String>();
		if (current.equals("")) { for (SkillType type : SkillType.values()) { s.add(type.name); } }
		else { for (SkillType type : SkillType.values()) { if (type.name.toLowerCase().startsWith(current.toLowerCase())) { s.add(type.name.toLowerCase()); } } }
		return s;
	}
	public static List<String> getPlayerSuggestions(String current) {
		List<String> s = new ArrayList<String>();
		if (current.equals("")) { for (Player p : Main.getInstance().getGame().getServer().getOnlinePlayers()) { s.add(p.getName()); } }
		else { for (Player p : Main.getInstance().getGame().getServer().getOnlinePlayers()) { if (p.getName().toLowerCase().startsWith(current.toLowerCase())) { s.add(p.getName()); } } }
		return s;
	}
}