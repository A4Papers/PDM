package a4.papers.privatedeath.message;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Main extends JavaPlugin implements Listener
	{

		public ArrayList<String> pdmtoggle = new ArrayList<String>();
		public ArrayList<String> cmdblock = new ArrayList<String>();

		public File killLogging;
		public FileConfiguration logger;

		public Logger log;

		public boolean toggled = true;
		public boolean radius = true;
		public boolean update = true;

		public String nVersionT = "";
		public String thisVersionT = "";
		public double nVersion = 0;
		public double cVersion = 0;

		@Override
		public void onEnable()
			{
				getCommand("pdm").setExecutor(new CmdExecutor(this));
				log = this.getLogger();
				log.info("Enabled!");
				PluginManager pm = getServer().getPluginManager();
				pm.registerEvents(new Fights(this), this);
				pm.registerEvents(new ListenersClass(this), this);
				loadConfiguration();
				thisVersionT = getDescription().getVersion().split("-")[0];
				cVersion = Double.valueOf(thisVersionT.replaceFirst("\\.", ""));
				loadConfiguration();
				killLogging = new File(getDataFolder(), "killLogging.yml");
				logger = YamlConfiguration.loadConfiguration(killLogging);
				if (update == true)
					{
						log.info("Checking for Updates... ");
						nVersion = update(cVersion);
						if (nVersion > cVersion)
							{
								log.info("Latest Version: " + nVersionT + " is out! You are still have version: " + thisVersionT);
								log.info("Get the update at: http://dev.bukkit.org/bukkit-plugins/privatedeath/");
							}
						else if (cVersion > nVersion)
							{
								log.info("You are useing a newer version: " + thisVersionT);
							}
						else
							{
								log.info("No new version available");
							}
					}
				else
					{
						log.warning("Update checking is disabled.");
					}
				Metrics metrics;
				try
					{
						metrics = new Metrics(this);
						metrics.start();
					}
				catch (IOException e)
					{
						e.printStackTrace();
					}
			}

		@Override
		public void onDisable()
			{
				saveLog();
				log.info("Disabled!");
			}

		public double update(double cVersion)
			{
				try
					{
						String vURL = "https://api.curseforge.com/servermods/files?projectids=70154";
						URL url = new URL(vURL);
						URLConnection connect = url.openConnection();
						BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
						String response = reader.readLine();
						JSONArray array = (JSONArray) JSONValue.parse(response);
						if (array.size() == 0)
							{
								log.warning("No files found.");
								return cVersion;
							}
						nVersionT = ((String) ((JSONObject) array.get(array.size() - 1)).get("name")).replace("PrivateDeathMessage v", "");
						return Double.valueOf(nVersionT.replaceFirst("\\.", ""));
					}
				catch (Exception e)
					{
						log.warning("There was a problem checking for the latest version.");
					}
				return cVersion;
			}

		public void loadConfiguration()
			{
				final FileConfiguration config = this.getConfig();
				config.options().header(
						"PrivateDeathMessage configuration file! \nFor more info go to 'http://dev.bukkit.org/bukkit-plugins/privatedeath/pages/config/'");
				config.addDefault("CmdBlock.use", true);
				config.addDefault("CmdBlock.blockTime", 5);
				config.addDefault("Prefix.text", "[PrivateDeath]");
				config.addDefault("Prefix.colour", "&4");
				config.addDefault("Colour.killed", "&a");
				config.addDefault("Colour.killer", "&c");
				config.addDefault("Colour.by", "&b");
				config.addDefault("Update-check", true);
				toggled = config.getBoolean("CmdBlock.use", true);
				update = config.getBoolean("Update-check", true);
				config.options().copyDefaults(true);
				saveConfig();
			}

		public void saveLog()
			{
				try
					{
						logger.save(killLogging);
					}
				catch (IOException e)
					{
						log.severe("Error saving kill logging");
					}
			}

	}