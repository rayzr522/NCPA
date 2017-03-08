/**
 * 
 */
package com.rayzr522.ncpa.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.time.DateUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.rayzr522.ncpa.ban.BanProvider;
import com.rayzr522.ncpa.ban.BanProviderIP;
import com.rayzr522.ncpa.ban.BanProviderName;
import com.rayzr522.ncpa.utils.StringUtils;

import fr.neatmonster.nocheatplus.checks.CheckType;
import fr.neatmonster.nocheatplus.checks.access.IViolationInfo;
import fr.neatmonster.nocheatplus.hooks.NCPHook;
import fr.neatmonster.nocheatplus.hooks.NCPHookManager;

/**
 * @author Rayzr
 *
 */
public class ViolationManager {
    public static final String VIOLATION_IDENTIFIER = StringUtils.hideMessage("NCPA");

    private Plugin plugin;
    private NCPHook hook;

    private Map<UUID, Integer> violations = new HashMap<>();

    private BanProvider nameBan = new BanProviderName();
    private BanProvider ipBan = new BanProviderIP();

    public ViolationManager(Plugin plugin) {
        this.plugin = plugin;

        this.hook = new NCPHook() {
            @Override
            public boolean onCheckFailure(CheckType type, Player player, IViolationInfo info) {
                setViolations(player.getUniqueId(), getViolations(player.getUniqueId()) + 1);
                checkViolations(player, type);

                return false;
            }

            @Override
            public String getHookVersion() {
                return plugin.getDescription().getVersion();
            }

            @Override
            public String getHookName() {
                return "[NCPA] Violation Listener";
            }
        };

        NCPHookManager.addHook(CheckType.ALL, hook);

    }

    public void onDisable() {
        if (hook != null) {
            NCPHookManager.removeHook(hook);
        }
    }

    /**
     * @param id The UUID of the player
     * @param amount The amount of violations to set
     */
    public void setViolations(UUID id, int amount) {
        violations.put(id, amount);
    }

    /**
     * @param id The UUID of the player
     * @return The amount of violations for that player
     */
    public int getViolations(UUID id) {
        if (!violations.containsKey(id)) {
            setViolations(id, 0);
        }
        return violations.get(id);
    }

    /**
     * Checks the violation count against the variables specified in the config
     * 
     * @param player The player to check
     * @param type The type of the last hack
     */
    public void checkViolations(Player player, CheckType type) {
        int violations = getViolations(player.getUniqueId());

        FileConfiguration config = plugin.getConfig();

        if (violations >= config.getInt("violations")) {
            BanProvider provider = (config.getBoolean("ip-ban") ? ipBan : nameBan);

            int banDuration = config.getInt("ban-duration");

            String message = VIOLATION_IDENTIFIER + StringUtils.replaceVariables(config.getString("ban-message"), player, type, banDuration);

            player.kickPlayer(message);
            provider.tempBan(player, message, DateUtils.addDays(new Date(), banDuration));

            setViolations(player.getUniqueId(), 0);
        }
    }

}
