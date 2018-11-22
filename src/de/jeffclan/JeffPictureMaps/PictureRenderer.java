package de.jeffclan.JeffPictureMaps;

import java.awt.image.BufferedImage;
import java.time.LocalTime;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;

public class PictureRenderer extends MapRenderer {
	
	BufferedImage image;
	int offset_x, offset_y;
	
	PictureRenderer(BufferedImage image, int offset_x, int offset_y) {
		this.image=image;
		this.offset_x=offset_x;
		this.offset_y=offset_y;
	}

    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
       canvas.drawImage(-offset_x, -offset_y, image);
       //canvas.drawText(0, 0, MinecraftFont.Font, ""+LocalTime.now().getSecond());
    }
}