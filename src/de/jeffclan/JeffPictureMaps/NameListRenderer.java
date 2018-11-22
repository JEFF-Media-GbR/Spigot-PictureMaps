package de.jeffclan.JeffPictureMaps;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;


	public class NameListRenderer extends MapRenderer {

	    private final ArrayList<String> names = new ArrayList<String>();
	    private final Random random = new Random();

	    @Override
	    public void render(MapView map, MapCanvas canvas, Player player) {
	        if (!names.contains(player.getName())) {
	            names.add(player.getName());
	            
	            String result = "";
	            for (String name : names) {
	                result += "\u00A7" + (4 + random.nextInt(52)) + ";";
	                result += name + "\n";
	            }
	            canvas.drawText(10, 10, MinecraftFont.Font, result);
	        }
	    }

}
