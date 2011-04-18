package com.amaranthinecorporation.ArrowWall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;

import com.amaranthinecorporation.Configuration.Configuration;

public class ArrowWallConfiguration {
	
	private ArrowWall plugin;
	private File folder;
	private Logger log;
	private String logPrefix;
	
	public Configuration config;
	
	// public ArrowWallConfiguration AWConfig;
	
	public ArrowWallConfiguration(File folder, ArrowWall instance) {
		this.folder = folder;
		this.plugin = instance;
		this.log = instance.log;
		this.logPrefix = instance.logPrefix;
		
		instance.getDataFolder().mkdirs();
		config = new Configuration(new File(instance.getDataFolder(), "ArrowWall.yml"));
		
	}
	
	public void setupConfiguration() {
		File config = new File(this.folder, "ArrowWall.yml");
		if (!config.exists()) {
			try {
				config.createNewFile();
				FileWriter fstream = new FileWriter(config);
				BufferedWriter out = new BufferedWriter(fstream);
				
				out.write("#Default arrow amount: (default 10)\n");
				out.write("amount: 10\n");
				out.write("\n");
				out.write("#Arrow limit: (default 500)\n");
				out.write("limit: 500\n");
				out.write("\n");
				out.write("#Arrow cleanup:\n");
				out.write("cleanup: true\n");
				out.write("\n");
				out.write("#Arrow cleanup time: (in milliseconds)\n");
				out.write("time: 5000\n");
				out.write("\n");
				//out.write("#Default style, shoot arrows from player or from the sky at the block they look at.\n");
				//out.write("shootfromsky: false\n");
				//out.write("\n");
				out.write("#Use inventory?\n");
				out.write("inventory: false\n");
				out.write("\n");
				out.write("#Use Permissions and Ops if true, everyone has access if false.\n");
				out.write("permissions: true\n");
				//out.write("#Use inventory exemption if Permissions disabled?\n");
				//out.write("exempt: true\n");
				out.write("\n");
				
				out.close();
				fstream.close();
			} catch (IOException ex) {
				log.info(logPrefix + "Error creating default Configuration File");
				this.plugin.getServer().getPluginManager().disablePlugin((Plugin) this);
			}
		} else {
			updateConfiguration(config);
		}
	}
	
	public void updateConfiguration(File configFile) {
		
		readConfiguration();
		
		// boolean exempt = config.getBoolean("exempt", true);
		if (config.getProperty("amount") == null) {
			try {
				configFile.delete();
				configFile.createNewFile();
				FileWriter fstream = new FileWriter(configFile);
				BufferedWriter out = new BufferedWriter(fstream);
				
				out.write("#Default arrow amount: (default 10)\n");
				out.write("amount: 10\n");
				out.write("\n");
				out.write("#Arrow limit: (default 500)\n");
				out.write("limit: " + config.getInt("limit", 500) + "\n");
				out.write("\n");
				out.write("#Arrow cleanup:\n");
				out.write("cleanup: " + config.getBoolean("cleanup", true) + "\n");
				out.write("\n");
				out.write("#Arrow cleanup time: (in milliseconds)\n");
				out.write("time: " + config.getInt("time", 5000) + "\n");
				out.write("\n");
				//out.write("#Default style, shoot arrows from player or from the sky at the block they look at.\n");
				//out.write("shootfromsky: false\n");
				//out.write("\n");
				out.write("#Use inventory?\n");
				out.write("inventory: " + config.getBoolean("inventory", false) + "\n");
				out.write("\n");
				out.write("#Use Permissions and Ops if true, everyone has access if false.\n");
				out.write("permissions: " + config.getBoolean("permissions", true) + "\n");
				out.write("\n");
				//out.write("#Use inventory exemption if Permissions disabled?\n");
				//out.write("exempt: true\n");
				out.write("\n");
				
				out.close();
				fstream.close();
			} catch (IOException ex) {
				log.severe(logPrefix + "Error updating Configuration File");
				this.plugin.getServer().getPluginManager().disablePlugin((Plugin) this);
			}
		}
		// config.save();
		config = new Configuration(new File(plugin.getDataFolder(), "ArrowWall.yml"));
		readConfiguration();
	}
	
	public void readConfiguration() {
		config.load();
		plugin.defaultArrowsToSpawn = config.getInt("amount", 10);
		plugin.arrowLimit = config.getInt("limit", 500);
		plugin.useCleanup = config.getBoolean("cleanup", true);
		plugin.cleanupTime = config.getInt("time", 5000);
		plugin.useInventory = config.getBoolean("inventory", false);
		plugin.usePermissions = config.getBoolean("permissions", true);
		//plugin.useExemption = config.getBoolean("exempt", true);
		
		Permission.initializeSilent(plugin.getServer());
		
	}
}
