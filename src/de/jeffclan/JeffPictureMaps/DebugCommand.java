package de.jeffclan.JeffPictureMaps;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DebugCommand implements CommandExecutor {
	
	PictureMapsPlugin plugin;

	public DebugCommand(PictureMapsPlugin pictureMapsPlugin) {
		plugin = pictureMapsPlugin;         
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
	String fileName = arg3[0];
	BufferedImage image;
	
	try {
		image = plugin.getImage(fileName);
	} catch (IOException e) {
		arg0.sendMessage(String.format("Error: File %s not found or not a valic picture.", fileName));
		return true;
	}
	
	arg0.sendMessage(String.format("Abmessungen von %s: %d x %d (B x H)", fileName, plugin.getBlockSize(image).x,plugin.getBlockSize(image).y));
		return true;
	}

}
