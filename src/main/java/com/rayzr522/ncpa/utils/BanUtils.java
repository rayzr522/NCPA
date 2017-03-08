/**
 * 
 */
package com.rayzr522.ncpa.utils;

import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author Rayzr
 *
 */
public class BanUtils {

    /**
     * Attempts to find the {@link BanEntry} for a player, first by checking the NAME ban list, and then the IP ban list if none were found in the NAME list.
     * 
     * @param player The player to find the entry for
     * @return The {@link BanEntry}
     */
    public static BanEntry getBanEntry(Player player) {
        BanEntry entry = Bukkit.getBanList(BanList.Type.NAME).getBanEntry(player.getName());
        if (entry == null) {
            entry = Bukkit.getBanList(BanList.Type.IP).getBanEntry(player.getAddress().getAddress().getHostAddress());
        }
        return entry;
    }

}
