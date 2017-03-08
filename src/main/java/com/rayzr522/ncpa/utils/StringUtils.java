/**
 * 
 */
package com.rayzr522.ncpa.utils;

import org.bukkit.ChatColor;

/**
 * @author Rayzr
 *
 */
public class StringUtils {

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
