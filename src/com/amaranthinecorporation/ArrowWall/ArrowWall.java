package com.amaranthinecorporation.ArrowWall;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * ArrowWall for Bukkit
 *
 * @author overlordnyaldee
 */
public class ArrowWall extends JavaPlugin {
	
	
	
	public final Logger log = Logger.getLogger("Minecraft");
	public final String logPrefix = "[ArrowWall]: ";
	
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    
    public ArrowWallConfiguration config;
    
	public int defaultArrowsToSpawn = 10;
    public int arrowLimit = 500;
    public boolean useCleanup = true;
    public int cleanupTime = 5;
    //public boolean shootFromSky = false;
    public boolean useInventory = false;
    public boolean usePermissions = true;
   //public boolean useExemption = true;


    public void onEnable() {
    	
    	config = new ArrowWallConfiguration(this.getDataFolder(), this);
    	config.setupConfiguration();
        config.readConfiguration();
        Permission.initialize(getServer());
        
        ArrowWallCommandExecutor executor = new ArrowWallCommandExecutor(this);
		this.getCommand("aw").setExecutor(executor);    
		this.getCommand("fw").setExecutor(executor);  
		this.getCommand("aws").setExecutor(executor);    
		this.getCommand("fws").setExecutor(executor);  
		this.getCommand("awreload").setExecutor(executor);  

        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( logPrefix + "version " + pdfFile.getVersion() + " enabled." );
        
        
    }
    public void onDisable() {

    	PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( logPrefix + "version " + pdfFile.getVersion() + " disabled." );
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

