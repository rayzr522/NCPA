package com.rayzr522.ncpa;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.rayzr522.creativelynamedlib.config.Messages;

/**
 * @author Rayzr
 */
public class NCPA extends JavaPlugin {
    private static NCPA instance;

    private Messages lang = new Messages();

    @Override
    public void onEnable() {
        instance = this;

        reload();
    }
    
    @Override
    public void onDisable() {
        instance = null;
    }
    
    /**
     * (Re)loads all configs from the disk
     */
    public void reload() {
        saveDefaultConfig();
        reloadConfig();
        
        lang.load(getConfig("messages.yml"));
    }

    /**
     * If the file is not found and there is a default file in the JAR, it saves the default file to the plugin data folder first
     * 
     * @param path The path to the config file (relative to the plugin data folder)
     * @return The {@link YamlConfiguration}
     */
    public YamlConfiguration getConfig(String path) {
        if (!getFile(path).exists() && getResource(path) != null) {
            saveResource(path, true);
        }
        return YamlConfiguration.loadConfiguration(getFile(path));
    }
    
    /**
     * Attempts to save a {@link YamlConfiguration} to the disk, and any {@link IOException}s are printed to the console
     * 
     * @param config The config to save
     * @param path The path to save the config file to (relative to the plugin data folder)
     */
    public void saveConfig(YamlConfiguration config, String path) {
        try {
            config.save(getFile(path));
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Failed to save config", e);
        }
    }

    /**
     * @param path The path of the file (relative to the plugin data folder)
     * @return The {@link File}
     */
    public File getFile(String path) {
        return new File(getDataFolder(), path.replace('/', File.pathSeparatorChar));
    }
    
    /**
     * Returns a message from the language file
     * 
     * @param key The key of the message to translate
     * @param objects The formatting objects to use
     * @return The formatted message
     */
    public String tr(String key, Object... objects) {
        return lang.tr(key, objects);
    }

    /**
     * Returns a message from the language file without adding the prefix
     * 
     * @param key The key of the message to translate
     * @param objects The formatting objects to use
     * @return The formatted message
     */
    public String trRaw(String key, Object... objects) {
        return lang.trRaw(key, objects);
    }

    /**
     * @return The {@link Messages} instance for this plugin
     */
    public Messages getLang() {
        return lang;
    }
    
    public static NCPA getInstance() {
        return instance;
    }

}
