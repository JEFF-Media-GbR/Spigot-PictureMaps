package de.jeffclan.JeffPictureMaps;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import de.jeffclan.JeffPictureMaps.NameListRenderer;
import de.jeffclan.JeffPictureMaps.PictureMapsPlugin;

public class PictureMapCommand implements CommandExecutor {

	PictureMapsPlugin plugin;
	
	public PictureMapCommand(PictureMapsPlugin jeffPictureMapsPlugin) {
		plugin=jeffPictureMapsPlugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

		if(!(sender instanceof Player)) {
			sender.sendMessage("This command can only be run by players.");
			return true;
		}
		
		if(args==null || args.length==0) {
			sender.sendMessage("Error. You have to specify a file name.");
			return true;
		}
		
		String fileName = args[0];
		Player p = (Player) sender;
		
		
		BufferedImage image;
		try {
			image = plugin.getImage(fileName);
		} catch (IOException e) {
			p.sendMessage(String.format("Error. File %s not found or invalid.",fileName));
			return true;
		}
		
		int height = image.getHeight();
		int width = image.getWidth();
		double dheight = height/128;
		double dwidth   =width/128;
		int width_blocks = (int) Math.ceil(dwidth);
		int height_blocks = (int) Math.ceil(dheight);
		
		for(int x = 0; x < width_blocks;x++) {
			for(int y = 0; y < height_blocks;y++) {
				
				if(!plugin.pictures.containsKey(fileName+"|"+x+"|"+y)) {
				
				
					MapView mapView = plugin.getEmptyMapView(p.getWorld());
					mapView.addRenderer(new PictureRenderer(image,x*128,y*128));
					
					ItemStack itemStack = new ItemStack(Material.FILLED_MAP,1);
					MapMeta mapMeta = (MapMeta) itemStack.getItemMeta();
					 // THIS IS NOT THE TYPE ID! It is just the map id, don't know whey they marked as deprecated
					mapMeta.setMapId(mapView.getId());
					itemStack.setItemMeta(mapMeta);
					
					p.getInventory().addItem(itemStack);
				}
			}
		}
		
		
		
		return false;
	}

}
