package de.jeffclan.JeffPictureMaps;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;
import org.bukkit.plugin.java.JavaPlugin;

public class PictureMapsPlugin extends JavaPlugin {

	HashMap<UUID, BufferedImage> currentlyPaintingPlayers;
	HashMap<String, Short> pictures;
	Messages messages;

	void createImageDirectory() {
		File imageDirectory = new File(getDataFolder().getPath() + File.separator + "pictures");
		if (!imageDirectory.getAbsoluteFile().exists()) {
			imageDirectory.mkdirs();
		}
	}

	boolean enoughSpaceForPicture(Block upperLeft, int height, int width, BlockFace direction) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (upperLeft.getRelative(BlockFace.DOWN, y).getRelative(direction, x).getType() != Material.AIR) {
					return false;
				}
			}
		}
		return true;
	}

	PictureDimension getBlockSize(BufferedImage image) {
		int x = (int) Math.ceil(image.getWidth() / 128);
		int y = (int) Math.ceil(image.getHeight() / 128);
		return new PictureDimension(x, y);
	}

	MapView getEmptyMapView(World world) {
		MapView mapView = getServer().createMap(world);
		mapView.getRenderers().clear();
		mapView.setScale(Scale.CLOSEST);

		return mapView;
	}

	BufferedImage getImage(String fileName) throws IOException {
		BufferedImage img = null;
		File file = new File(getDataFolder().getPath() + File.separator + "pictures" + File.separator + fileName);
		img = ImageIO.read(file);

		return img;
	}

//	ItemStack getPartOfPicture(BufferedImage image, int x, int y, World world) {
//
//		MapView mapView = getEmptyMapView(world);
//		mapView.addRenderer(new PictureRenderer(image, x * 128, y * 128));
//
//		ItemStack itemStack = new ItemStack(Material.FILLED_MAP, 1);
//		MapMeta mapMeta = (MapMeta) itemStack.getItemMeta();
//		mapMeta.setMapId(mapView.getId());
//		itemStack.setItemMeta(mapMeta);
//
//		return itemStack;
//	}

	@Override
	public void onEnable() {
		createImageDirectory();
		registerCommands();
		currentlyPaintingPlayers = new HashMap<UUID, BufferedImage>();
		pictures = new HashMap<String, Short>();
		messages = new Messages(this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
	}

	void registerCommands() {
		//getCommand("pic2map").setExecutor(new PictureMapCommand(this));
		getCommand("pic2wall").setExecutor(new Pic2WallCommand(this));
		getCommand("debug").setExecutor(new DebugCommand(this));
	}

}
