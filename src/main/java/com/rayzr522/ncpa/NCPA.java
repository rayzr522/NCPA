package com.rayzr522.ncpa;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.apache.commons.lang.time.DateUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.rayzr522.ncpa.ban.BanProvider;
import com.rayzr522.ncpa.ban.BanProviderIP;
import com.rayzr522.ncpa.ban.BanProviderName;

import fr.neatmonster.nocheatplus.checks.CheckType;
import fr.neatmonster.nocheatplus.checks.access.IViolationInfo;
import fr.neatmonster.nocheatplus.hooks.NCPHook;
import fr.neatmonster.nocheatplus.hooks.NCPHookManager;

/**
 * @author Rayzr
 */
public class NCPA extends JavaPlugin {
    private Map<UUID, Integer> violations = new HashMap<>();

    private BanProvider nameBan = new BanProviderName();
    private BanProvider ipBan = new BanProviderIP();

    @Override
    public void onEnable() {
        reload();
        NCPHookManager.addHook(CheckType.ALL, new NCPHook() {
            @Override
            public boolean onCheckFailure(CheckType type, Player player, IViolationInfo info) {
                setViolations(player.getUniqueId(), getViolations(player.getUniqueId()) + 1);
                return false;
            }

            @Override
            public String getHookVersion() {
                return getDescription().getVersion();
            }

            @Override
            public String getHookName() {
                return "[NCPA] Violation Listener";
            }
        });
    }

    @Override
    public void onDisable() {
    }

    /**
     * (Re)loads all configs from the disk
     */
    public void reload() {
        saveDefaultConfig();
        reloadConfig();
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
        return new File(getDataFolder(), path.replace('/', File.separatorChar));
    }

    public void setViolations(UUID id, int amount) {
        violations.put(id, amount);
    }

    public int getViolations(UUID id) {
        if (!violations.containsKey(id)) {
            setViolations(id, 0);
        }
        return violations.get(id);
    }

    public void checkViolations(Player player) {
        int violations = getViolations(player.getUniqueId());

        if (violations >= getConfig().getInt("violations")) {
            BanProvider provider = (getConfig().getBoolean("ip-ban") ? ipBan : nameBan);

            provider.tempBan(player, getConfig().getString("ban-message"),
                    DateUtils.addDays(new Date(), getConfig().getInt("ban-duration")));

            setViolations(player.getUniqueId(), 0);
        }
    }

}
