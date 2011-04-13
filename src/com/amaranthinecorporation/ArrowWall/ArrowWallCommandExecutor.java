package com.amaranthinecorporation.ArrowWall;

//import org.bukkit.Location;
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

	public static int defaultArrowsToSpawn = 10;

	public ArrowWallCommandExecutor(ArrowWall plugin) {
		this.plugin = plugin;
		util = new ArrowWallUtils(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		
		if (commandLabel.equalsIgnoreCase("awreload"))  {
			if (hasPermission("arrowwall.awreload", sender)) {
				plugin.config.readConfiguration();
				sender.sendMessage("ArrowWall configuration reloaded.");
				plugin.log.log(Level.INFO, plugin.logPrefix + "Configuration reloaded.");
				return true;
			} else {
				sender.sendMessage(ChatColor.RED
						+ "You don't have permission to use that command.");
				return true;
			}
		}


		// check if command user is an actual player
		if (!(sender instanceof Player)) {
			sender.sendMessage("Must be in game to use.");
			return true;
		}
		

		// check permission and spawn arrows
		if (hasPermission("arrowwall.aw", sender)) {
			Player player = (Player) sender;

			int arrowsToSpawn = defaultArrowsToSpawn;

			if (args.length >= 1) {
				try {
					arrowsToSpawn = Integer.parseInt(args[0]);
				} catch (NumberFormatException e) {
					return false;
				}
			}

			// check arrow limits
			if (arrowsToSpawn > plugin.arrowLimit) {
				sender.sendMessage(ChatColor.RED
						+ "Too many arrows. There is a limit of " + plugin.arrowLimit
						+ " arrows.");
				//arrowsToSpawn = arrowLimit;
				return true;
			}
			
			// check if player has enough items in inventory
			if ((plugin.useInventory == true) && !(hasPermission("arrowwall.exempt", sender))) {
				PlayerInventory inv = player.getInventory();
				if (!inv.contains(Material.ARROW, arrowsToSpawn)) {
					sender.sendMessage(ChatColor.RED
							+ "You don't have enough arrows.");
					return true;
				} else {
					HashMap<Integer, ItemStack> difference = inv.removeItem(new ItemStack(Material.ARROW, arrowsToSpawn));
					for (ItemStack s : difference.values())
					  System.out.println("ERROR: not enough actual arrows for: " + s.toString() + ", shouldn't ever happen.");
				}
			}
			
			//check if fire arrows
			if (commandLabel.equalsIgnoreCase("aw")) {
				util.spawnArrows(player, arrowsToSpawn, false);
			} else if (commandLabel.equalsIgnoreCase("fw")) {
				util.spawnArrows(player, arrowsToSpawn, true);
			}
		} else {
			sender.sendMessage(ChatColor.RED
					+ "You don't have permission to use that command.");
		}

		return true;

	}

	public static boolean hasPermission(String permission, CommandSender sender) {
		return (!(sender instanceof Player) || Permission.generic(
				(Player) sender, permission));
	}

}
