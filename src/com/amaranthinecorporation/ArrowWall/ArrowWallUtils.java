package com.amaranthinecorporation.ArrowWall;

import java.util.Timer;
import java.util.ArrayList;
import org.bukkit.entity.Arrow;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ArrowWallUtils {

	static int radius = 4;

    public static boolean spawnArrows(Player target, int arrowCount) {
    	
		if (target != null) {
			
			// store arrows
			ArrayList<Arrow> arrows = new ArrayList<Arrow>();
			
			for (int i = 0; i < arrowCount; i++) {
				
				int rad = radius;
				
				Location loc = target.getLocation();
				
				// magic from VoidMage
				// sets a random location above you
				loc.setX((loc.getX()-rad)+ (rad*2+1)*Math.random());
				loc.setY(loc.getY()+(4+(3*Math.random())));
				loc.setZ((loc.getZ()-rad)+ (rad*2+2)*Math.random());
				
				// get the direction the player is facing
				Vector vec = loc.getDirection();
				
				// shoot arrows and add to list of arrows
				arrows.add(target.getWorld().spawnArrow(loc, vec, (float)1.0, (float)12));				
			}
			
			// schedule a timer to remove the arrows in 5 seconds
			Timer arrowTimer = new Timer(true);
			arrowTimer.schedule(new ArrowCleanupTimer(arrows), (long)5000, (long)(5000));
			
			return true;
	
		}
		
		return false;
		
	}
    
}
