package com.amaranthinecorporation.ArrowWall;

import java.util.Timer;
import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ArrowWallUtils {
	
	private ArrowWall plugin;
	
	public ArrowWallUtils(ArrowWall plugin) {
		this.plugin = plugin;
	}
	
	static int radius = 4;
	
	public boolean spawnArrows(Player target, int arrowCount, boolean onFire, boolean shootFromSky) {
		
		if (target != null) {
			
			// store arrows
			ArrayList<Arrow> arrows = new ArrayList<Arrow>();
			
			for (int i = 0; i < arrowCount; i++) {
				
				int rad = radius;
				
				Location loc;
				Vector vec;
				
				if (shootFromSky) {
					TargetBlock b = new TargetBlock(target);
					Block v = b.getTargetBlock();
					
					// check if target found
					if (v != null) {
						loc = v.getLocation();
					} else {
						target.sendMessage(ChatColor.RED + "Cannot see target block.");
						return false;
					}
					
					loc.setZ((loc.getZ() - rad) + (rad * 2 + 2) * Math.random());
					loc.setY(loc.getY() + (25 + (3 * Math.random())));
					loc.setX((loc.getX() - rad) + (rad * 2 + 2) * Math.random());
					vec = new Vector(0.0, -1.0, 0.0);
				} else {
					loc = target.getLocation();
					loc.setX((loc.getX() - rad) + (rad * 2 + 1) * Math.random());
					loc.setY(loc.getY() + (4 + (3 * Math.random())));
					loc.setZ((loc.getZ() - rad) + (rad * 2 + 2) * Math.random());
					vec = loc.getDirection();
				}
				
				Arrow arrow = target.getWorld().spawnArrow(loc, vec, (float) 1.0, (float) 12);
				
				if (onFire) {
					arrow.setFireTicks(plugin.cleanupTime);
				}
				
				arrows.add(arrow);
				
			}
			
			if (plugin.useCleanup) {
				// schedule a timer to remove the arrows in 5 seconds
				Timer arrowTimer = new Timer(true);
				arrowTimer.schedule(new ArrowCleanupTimer(arrows), (long) plugin.cleanupTime, (long) (plugin.cleanupTime));
			}
			
			return true;
			
		}
		
		return false;
		
	}
	
}
