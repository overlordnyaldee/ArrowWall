package com.amaranthinecorporation.ArrowWall;

//import org.bukkit.Location;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArrowWallCommandExecutor implements CommandExecutor {

	private ArrowWall plugin;

	public ArrowWallCommandExecutor(ArrowWall plugin) {
		this.plugin = plugin;
	}
	
	public static final int arrowLimit = 500;
	public static final int defaultArrowsToSpawn = 10;

	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel,
			String[] args) {
		
		// check if command user is an actual player
		if (!(sender instanceof Player)) {
			sender.sendMessage("Must be in game to use.");
			return true;
		}
		
		plugin.isEnabled();

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
		if (arrowsToSpawn > arrowLimit) {
			sender.sendMessage(ChatColor.RED + "Too many arrows. There is a limit of " + arrowLimit + " arrows.");
			arrowsToSpawn = arrowLimit;
		}
		
		// check permission and spawn arrows
		if (hasPermission("arrowwall.aw", sender)) {
			ArrowWallUtils.spawnArrows(player, arrowsToSpawn);
		} else {
			sender.sendMessage(ChatColor.RED + "You don't have permission to use that command.");
		}
		
		return true;
		
	}
	
	public static boolean hasPermission(String permission, CommandSender sender) {
		return (!(sender instanceof Player) || Permission.generic(
				(Player) sender, permission));
	}

}
