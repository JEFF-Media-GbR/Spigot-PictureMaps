package de.jeffclan.JeffPictureMaps;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Pic2WallCommand implements CommandExecutor {

	PictureMapsPlugin plugin;

	public Pic2WallCommand(PictureMapsPlugin pictureMapsPlugin) {
		this.plugin = pictureMapsPlugin;
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {

		if (!(arg0 instanceof Player)) {
			arg0.sendMessage(plugin.messages.MSG_PLAYERSONLY);
			return true;
		}

		Player p = (Player) arg0;

		if (plugin.currentlyPaintingPlayers.containsKey(p.getUniqueId())) {
			// Player is already hanging, so end it
			plugin.currentlyPaintingPlayers.remove(p.getUniqueId());
			p.sendMessage(plugin.messages.MSG_ABORTHANGING);
			return true;
		}

		if (arg3 == null || arg3.length == 0) {
			p.sendMessage(plugin.messages.MSG_NOFILENAME);
			return true;
		}

		String fileName = arg3[0];

		// Player starts hanging a picture
		BufferedImage img;
		try {
			img = plugin.getImage(fileName);
		} catch (IOException e) {
			p.sendMessage(String.format(plugin.messages.MSG_FILENOTFOUND, fileName));
			return true;
		}
		PictureDimension size = plugin.getBlockSize(img);
		p.sendMessage(String.format(plugin.messages.MSG_PICTURELOADED,fileName,size.x,size.y));
		plugin.currentlyPaintingPlayers.put(p.getUniqueId(), img);
		p.sendMessage(plugin.messages.MSG_CLICKWALL);
		return true;

	}

}
