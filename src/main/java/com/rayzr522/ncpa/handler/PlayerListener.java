/**
 * 
 */
package com.rayzr522.ncpa.handler;

import org.bukkit.BanEntry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import com.rayzr522.ncpa.utils.BanUtils;

/**
 * @author Rayzr
 *
 */
public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLogin(PlayerLoginEvent e) {

        // Fix ban messages and their crap
        if (e.getResult() == Result.KICK_BANNED) {
            BanEntry entry = BanUtils.getBanEntry(e.getPlayer());
            if (entry == null) {
                return;
            }

            String reason = entry.getReason();
            if (reason.startsWith(ViolationManager.VIOLATION_IDENTIFIER)) {
                e.setKickMessage(reason);
            }
        }
    }

}
