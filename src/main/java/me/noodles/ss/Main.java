package me.noodles.ss;

import me.noodles.ss.freezecommand.FreezeCommand;
import me.noodles.ss.freezecommand.FreezeEvents;
import me.noodles.ss.updatechecker.JoinEvent;
import me.noodles.ss.updatechecker.UpdateChecker;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

    public static Main plugin;
    private UpdateChecker checker;
    private static Plugin instance;


    public void onEnable() {
        this.createFiles();
        if (getConfig().getBoolean("SilentStart.Enabled")) {
            Main.plugin = this;
            instance = this;
            PluginDescriptionFile VarUtilType = this.getDescription();
            MetricsLite metrics = new MetricsLite(this);
            getServer().getPluginManager().registerEvents(new FreezeEvents(), this);
            getServer().getPluginManager().registerEvents(new JoinEvent(), this);
            this.registerCommands();
            setEnabled(true);
            Logger.log(Logger.LogLevel.SUCCESS, "Freeze Version: " + Settings.VERSION + " Loaded.");
            if (getConfig().getBoolean("CheckForUpdates.Enabled")) {
                new UpdateChecker(this, 44518).getLatestVersion(version -> {
                    if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                        Logger.log(Logger.LogLevel.SUCCESS, ("UltimateFreeze is up to date!"));
                    } else {
                        Logger.log(Logger.LogLevel.OUTLINE, "*********************************************************************");
                        Logger.log(Logger.LogLevel.WARNING, ("UltimateFreeze is outdated!"));
                        Logger.log(Logger.LogLevel.WARNING, ("Newest version: " + version));
                        Logger.log(Logger.LogLevel.WARNING, ("Your version: " + Settings.VERSION));
                        Logger.log(Logger.LogLevel.WARNING, ("Please Update Here: " + Settings.PLUGIN_URL));
                        Logger.log(Logger.LogLevel.OUTLINE, "*********************************************************************");
                    }
                });
            }
        } else {
            Main.plugin = this;
            instance = this;
            PluginDescriptionFile VarUtilType = this.getDescription();
            Logger.log(Logger.LogLevel.OUTLINE, "*********************************************************************");
            Logger.log(Logger.LogLevel.INFO, "Initializing Freeze Version: " + Settings.VERSION);
            Logger.log(Logger.LogLevel.INFO, "Created by: " + Settings.DEVELOPER_NAME);
            Logger.log(Logger.LogLevel.INFO, "Website: " + Settings.DEVELOPER_URL);
            Logger.log(Logger.LogLevel.INFO, "Spigot Link: " + Settings.PLUGIN_URL);
            Logger.log(Logger.LogLevel.INFO, "Support Link: " + Settings.SUPPORT_DISCORD_URL);
            Logger.log(Logger.LogLevel.OUTLINE, "*********************************************************************");
            Logger.log(Logger.LogLevel.INFO, "Plugin Loading...");
            Logger.log(Logger.LogLevel.INFO, "Registering Managers...");
            MetricsLite metrics = new MetricsLite(this);
            Logger.log(Logger.LogLevel.INFO, "Managers Registered!");
            Logger.log(Logger.LogLevel.INFO, "Registering Listeners...");
            getServer().getPluginManager().registerEvents(new FreezeEvents(), this);
            getServer().getPluginManager().registerEvents(new JoinEvent(), this);
            Logger.log(Logger.LogLevel.INFO, "Listeners Registered!");
            Logger.log(Logger.LogLevel.INFO, "Registering Commands...");
            this.registerCommands();
            Logger.log(Logger.LogLevel.INFO, "Commands Registered!");
            Logger.log(Logger.LogLevel.SUCCESS, "Freeze Version: " + Settings.VERSION + " Loaded.");
            setEnabled(true);
            Logger.log(Logger.LogLevel.OUTLINE, "*********************************************************************");
            if (getConfig().getBoolean("CheckForUpdates.Enabled")) {
                Logger.log(Logger.LogLevel.INFO, "Checking for updates...");
                new UpdateChecker(this, 44518).getLatestVersion(version -> {
                    if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                        Logger.log(Logger.LogLevel.SUCCESS, ("UltimateFreeze is up to date!"));
                    } else {
                        Logger.log(Logger.LogLevel.OUTLINE, "*********************************************************************");
                        Logger.log(Logger.LogLevel.WARNING, ("UltimateFreeze is outdated!"));
                        Logger.log(Logger.LogLevel.WARNING, ("Newest version: " + version));
                        Logger.log(Logger.LogLevel.WARNING, ("Your version: " + Settings.VERSION));
                        Logger.log(Logger.LogLevel.WARNING, ("Please Update Here: " + Settings.PLUGIN_URL));
                        Logger.log(Logger.LogLevel.OUTLINE, "*********************************************************************");
                    }
                });

            }
        }
    }

    public void registerCommands() {
        this.getCommand("freeze").setExecutor(new FreezeCommand());

    }

    public static Plugin getInstance() {
        return instance;
    }

    public static Main getPlugin() {
        return (Main) JavaPlugin.getPlugin((Class) Main.class);
    }


    private File configf, configmessages2, configgui2;
    private FileConfiguration config, configmessages, configgui;

    public FileConfiguration getmessagesConfig() {
        return this.configmessages;
    }

    public FileConfiguration getguiConfig() {
        return this.configgui;
    }

    private void createFiles() {
        configf = new File(getDataFolder(), "config.yml");
        configmessages2 = new File(getDataFolder(), "messages.yml");
        configgui2 = new File(getDataFolder(), "gui.yml");

        if (!configf.exists()) {
            configf.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }
        if (!configmessages2.exists()) {
            configmessages2.getParentFile().mkdirs();
            saveResource("messages.yml", false);
        }
        if (!configgui2.exists()) {
            configgui2.getParentFile().mkdirs();
            saveResource("gui.yml", false);
        }
        config = new YamlConfiguration();
        configmessages = new YamlConfiguration();
        configgui = new YamlConfiguration();
        try {
            config.load(configf);
            configmessages.load(configmessages2);
            configgui.load(configgui2);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}