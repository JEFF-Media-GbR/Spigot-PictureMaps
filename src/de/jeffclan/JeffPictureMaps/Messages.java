package de.jeffclan.JeffPictureMaps;

import org.bukkit.ChatColor;


public class Messages {

	public final String MSG_PLAYERSONLY, MSG_NOTSOLID, MSG_NOTAWALL, MSG_ABORTHANGING, MSG_FILENOTFOUND, MSG_NOFILENAME, MSG_CLICKWALL, MSG_PICTURELOADED, MSG_WALLTOOSMALL;

	Messages(PictureMapsPlugin plugin) {
		//this.plugin = plugin;

		MSG_PLAYERSONLY = ChatColor.translateAlternateColorCodes('&', plugin.getConfig()
				.getString("message-error-players-only", "&cError: This command can only be run by players."));
		
		MSG_NOTSOLID = ChatColor.translateAlternateColorCodes('&', "&cError: This is not a solid block.");
		
		MSG_NOTAWALL = ChatColor.translateAlternateColorCodes('&', "&cError: This is not a wall.");
		
		MSG_ABORTHANGING = ChatColor.translateAlternateColorCodes('&', "&eYou aborted hanging this picture.");
		
		MSG_FILENOTFOUND = ChatColor.translateAlternateColorCodes('&', "&cError: File %s not found or not a valid picture.");
		
		MSG_NOFILENAME = ChatColor.translateAlternateColorCodes('&', "&cError: You must provide a file name.");
		
		MSG_WALLTOOSMALL = ChatColor.translateAlternateColorCodes('&', "&cError: This wall it obstructed or too small.");
		
		MSG_PICTURELOADED = ChatColor.translateAlternateColorCodes('&', "&7Loaded picture %s (%d x %d)");
		
		MSG_CLICKWALL = ChatColor.translateAlternateColorCodes('&', "&aTo place the picture, please click on a wall where the upper left corner should be.");
		
		
	}

}