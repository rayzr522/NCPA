/**
 * 
 */
package com.rayzr522.ncpa.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.neatmonster.nocheatplus.checks.CheckType;

/**
 * @author Rayzr
 *
 */
public class StringUtils {

    /**
     * Replace the variables of the ban-reason message
     * 
     * @param message The base message
     * @param player The player that is being banned
     * @param hackType The type of the last hack
     * @return
     */
    public static String replaceVariables(String message, Player player, CheckType hackType, int days) {
        return ChatColor.translateAlternateColorCodes('&', message)
                .replace("{user}", player.getDisplayName())
                .replace("{hack}", hackType.getName().toUpperCase().replace(".", "-"))
                .replace("{time}", Integer.toString(days));
    }

    /**
     * Hides a message with the section symbol (ChatColor.COLOR_CHAR)
     * 
     * @param message The message to hide
     * @return The hidden message
     */
    public static String hideMessage(String message) {
        StringBuilder output = new StringBuilder();

        for (char c : message.toCharArray()) {
            output.append(ChatColor.COLOR_CHAR).append(c);
        }

        return output.toString();
    }

}
