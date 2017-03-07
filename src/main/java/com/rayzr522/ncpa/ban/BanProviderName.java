/**
 * 
 */
package com.rayzr522.ncpa.ban;

import java.util.Date;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author Rayzr
 *
 */
public class BanProviderName implements BanProvider {

    @Override
    public void tempBan(Player player, String reason, Date expiration) {
        Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), reason, expiration, "");
    }

}
