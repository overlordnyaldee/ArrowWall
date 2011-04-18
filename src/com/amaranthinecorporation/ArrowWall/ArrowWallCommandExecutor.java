package com.amaranthinecorporation.ArrowWall;

import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ArrowWallCommandExecutor implements CommandExecutor {
	
	private ArrowWall plugin;
	private static ArrowWallUtils util;
		
	public ArrowWallCommandExecutor(ArrowWall plugin) {
		this.plugin = plugin;
		util = new ArrowWallUtils(plugin);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		
		if (commandLabel.equalsIgnoreCase("awreload")) {
			if (hasPermission("arrowwall.awreload", sender)) {
				plugin.config.readConfiguration();
				sender.sendMessage("ArrowWall configuration reloaded.");
				plugin.log.log(Level.INFO, plugin.logPrefix + "Configuration reloaded.");
				return true;
			} else {
				sender.sendMessage(ChatColor.RED + "You don't have permission to use that command.");
				return true;
			}
		}
		
		// check if command user is an actual player
		if (!(sender instanceof Player)) {
			sender.sendMessage("Must be in game to use.");
			return true;
		}
		
		Player player = (Player) sender;
		
		int arrowsToSpawn = plugin.defaultArrowsToSpawn;
		
		if (args.length >= 1) {
			try {
				arrowsToSpawn = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				sender.sendMessage(ChatColor.RED + "Please enter a number for amount.");
				return false;
			}
		}
		
		// check arrow limits
		if (arrowsToSpawn > plugin.arrowLimit) {
			sender.sendMessage(ChatColor.RED + "Too many arrows. There is a limit of " + plugin.arrowLimit + " arrows.");
			return true;
		}
		
		// check if player has enough items in inventory
		if (plugin.useInventory) {
			// Permissions in use?
			if (plugin.usePermissions) {
				// Yes
				// Exempt from inventory?
				if (!hasPermission("arrowwall.exempt", sender)) {
					// Not exempt
					// Do they have enough arrows?
					if (checkInventory(player, Material.ARROW, arrowsToSpawn)) { // Yes
						if (attemptSpawnArrows(commandLabel, player, arrowsToSpawn)) {
							takeFromInventory(player, Material.ARROW, arrowsToSpawn);
						}
					} else { // Not enough arrows
						player.sendMessage(ChatColor.RED + "You don't have enough arrows.");
					}
				} else { // Yes, exempt
					attemptSpawnArrows(commandLabel, player, arrowsToSpawn);
				}
			} else { // Not using permissions
				/*if (plugin.useExemption) { // exemption
					attemptSpawnArrows(commandLabel, player, arrowsToSpawn);
				} else {*/
					if (checkInventory(player, Material.ARROW, arrowsToSpawn)) {
						if (attemptSpawnArrows(commandLabel, player, arrowsToSpawn)) {
							takeFromInventory(player, Material.ARROW, arrowsToSpawn);
						}
					} else { // Not enough arrows
						player.sendMessage(ChatColor.RED + "You don't have enough arrows.");
					}
			//}
			}
		} else {
			attemptSpawnArrows(commandLabel, player, arrowsToSpawn);
		}
		
		return true;
		
	}
	
	private static boolean attemptSpawnArrows(String commandLabel, Player player, int arrowsToSpawn) {
		if (commandLabel.equalsIgnoreCase("aw") && (hasPermission("arrowwall.aw", player))) {
			util.spawnArrows(player, arrowsToSpawn, false, false);
		} else if (commandLabel.equalsIgnoreCase("fw") && (hasPermission("arrowwall.aw.fw", player))) {
			util.spawnArrows(player, arrowsToSpawn, true, false);
		} else if (commandLabel.equalsIgnoreCase("aws") && (hasPermission("arrowwall.aw.aws", player))) {
			util.spawnArrows(player, arrowsToSpawn, false, true);
		} else if (commandLabel.equalsIgnoreCase("fws") && (hasPermission("arrowwall.aw.fws", player))) {
			util.spawnArrows(player, arrowsToSpawn, true, true);
		} else {
			player.sendMessage(ChatColor.RED + "You don't have permission to use that command.");
			return false;
		}
		return true;
	}
	
	private static boolean checkInventory(Player player, Material type, int amount) {
		PlayerInventory inv = player.getInventory();
		if (inv.contains(type, amount)) {
			return true;
		} else {
			return false;
		}
	}
	
	private static void takeFromInventory(Player player, Material type, int amount) {
		PlayerInventory inv = player.getInventory();
		
		HashMap<Integer, ItemStack> difference = inv.removeItem(new ItemStack(type, amount));
		for (ItemStack s : difference.values())
			System.out.println("ERROR: not enough actual arrows for: " + s.toString() + ", shouldn't ever happen.");
		
	}
	
	public static boolean hasPermission(String permission, CommandSender sender) {
		return (!(sender instanceof Player) || Permission.generic((Player) sender, permission));
	}
	
}
