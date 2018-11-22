package de.jeffclan.JeffPictureMaps;

import java.awt.image.BufferedImage;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerListener implements Listener {
	
	BlockFace[] allowedBlockFaces = { BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH };
	
	PictureMapsPlugin plugin;
	
	PlayerListener(PictureMapsPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		
		if(e.getHand() == EquipmentSlot.OFF_HAND) {
			return;
		}
		
		if(e.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		
		if(!plugin.currentlyPaintingPlayers.containsKey(e.getPlayer().getUniqueId())) {
			return;
		}
		
		Block block = e.getClickedBlock();
		BufferedImage image = plugin.currentlyPaintingPlayers.get(e.getPlayer().getUniqueId());
		PictureDimension size = plugin.getBlockSize(image);
		
		if(!block.getType().isSolid()) {
			e.getPlayer().sendMessage(plugin.messages.MSG_NOTSOLID);
			e.setCancelled(true);
			return;
		}
		
		BlockFace blockFace = e.getBlockFace();
		boolean isWall = false;
		for(BlockFace allowedBlockFace : allowedBlockFaces) {
			if(blockFace == allowedBlockFace) {
				isWall=true;
			}
		}
		
		if(!isWall) {
			e.getPlayer().sendMessage(plugin.messages.MSG_NOTAWALL);
			e.setCancelled(true);
			return;
		}
		
		Block upperLeft = block.getRelative(blockFace);
		
		BlockFace direction;

		if(blockFace == BlockFace.SOUTH) {
			direction = BlockFace.EAST;
		} else if(blockFace == BlockFace.EAST) {
			direction = BlockFace.NORTH;
		} else if(blockFace == BlockFace.WEST) {
			direction = BlockFace.SOUTH;
		} else {
			direction = BlockFace.WEST;
		}
		
		
		if(upperLeft.getType() != Material.AIR) {
			e.getPlayer().sendMessage(plugin.messages.MSG_WALLTOOSMALL);
			e.setCancelled(true);
			return;
		}
		
		if(!plugin.enoughSpaceForPicture(upperLeft, size.y, size.x, direction)) {
			e.getPlayer().sendMessage(plugin.messages.MSG_WALLTOOSMALL);
			e.setCancelled(true);
			return;
		}
		
		for (int y = 0; y < size.y; y++) {

			for (int x = 0; x < size.x; x++) {
				
				Block blockToSpawn = upperLeft.getRelative(BlockFace.DOWN, y).getRelative(direction, x);
				ItemFrame itemFrame = blockToSpawn.getWorld().spawn(blockToSpawn.getLocation(),ItemFrame.class);
				itemFrame.setFacingDirection(blockFace);
				itemFrame.setItem(arg0);
				
				
			}

		}
		
		plugin.currentlyPaintingPlayers.remove(e.getPlayer().getUniqueId());
		e.setCancelled(true);
		
	}

}
