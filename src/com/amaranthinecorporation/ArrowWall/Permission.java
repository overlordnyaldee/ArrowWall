package com.amaranthinecorporation.ArrowWall;

import com.nijikokun.bukkit.Permissions.Permissions;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Permission {

	@SuppressWarnings("unused")
	
	private static Permissions permissionsPlugin;
	private static boolean permissionsEnabled = false;
	
	private static ArrowWall plugin;

	public static void initialize(Server server) {
		//Plugin test = server.getPluginManager().getPlugin("Permissions");
		plugin = (ArrowWall) server.getPluginManager().getPlugin("ArrowWall");
		Logger log = plugin.log;
		
		initializeSilent(server);
		
		if ((permissionsEnabled) && (plugin.usePermissions)) {

		//if ((test != null) && (plugin.usePermissions)) {
			//permissionsPlugin = (Permissions) test;
			//permissionsEnabled = true;
			log.log(Level.INFO, plugin.logPrefix + "Permissions enabled.");
		} else {
			if (!plugin.usePermissions){
				log.log(Level.INFO,
						plugin.logPrefix + "Permissions not in use, commands can only be used by anyone.");
			} else {
				log.log(Level.SEVERE,
						plugin.logPrefix + "Permissions isn't loaded, commands can only be used by ops.");
			}
			
		}
	}
	
	public static void initializeSilent(Server server) {
		Plugin test = server.getPluginManager().getPlugin("Permissions");
		plugin = (ArrowWall) server.getPluginManager().getPlugin("ArrowWall");
		if ((test != null) && (plugin.usePermissions)) {
			permissionsPlugin = (Permissions) test;
			permissionsEnabled = true;
		} 
	}

	public static boolean isAdmin(Player player) {
		if (permissionsEnabled) {
			return permission(player, "");
		}
		return player.isOp();
	}

	private static boolean permission(Player player, String string) {
		return Permissions.Security.permission(player, string);
	}

	public static boolean check(Player player) {
		if (permissionsEnabled) {
			return permission(player, "");
		}
		return player.isOp();
	}

	public static boolean generic(Player player, String string) {
		if (permissionsEnabled) {
			return permission(player, string);
		} else if (!plugin.usePermissions) {
			return true;
		}
		return player.isOp();
	}

}
