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
public class BanProviderIP implements BanProvider {

    /*
     * (non-Javadoc)
     * 
     * @see com.rayzr522.ncpa.ban.BanProvider#tempBan(org.bukkit.entity.Player, long)
     */
    @Override
    public void tempBan(Player player, String reason, Date expiration) {
        Bukkit.getBanList(BanList.Type.IP).addBan(player.getAddress().getAddress().getHostAddress(), reason, expiration, "");
    }

}
