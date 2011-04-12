package com.amaranthinecorporation.ArrowWall;

//import java.io.File;
import java.util.HashMap;
import org.bukkit.entity.Player;
//import org.bukkit.Server;
//import org.bukkit.event.Event.Priority;
//import org.bukkit.event.Event;
//import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
//import org.bukkit.plugin.PluginLoader;
//import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * ArrowWall for Bukkit
 *
 * @author overlordnyaldee
 */
public class ArrowWall extends JavaPlugin {
	
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();

    public void onEnable() {
        
        ArrowWallCommandExecutor executor = new ArrowWallCommandExecutor(this);
		this.getCommand("aw").setExecutor(executor);    
		//this.getCommand("se").setExecutor(executor);       

        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " enabled." );
        
    }
    public void onDisable() {

    	PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " disabled." );
    }
    
    
    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
}

