/**
 * 
 */
package com.rayzr522.ncpa.ban;

import java.util.Date;

import org.bukkit.entity.Player;

/**
 * @author Rayzr
 *
 */
public interface BanProvider {

    public void tempBan(Player player, String reason, Date expiration);

}
