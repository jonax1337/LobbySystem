package dev.laux.lobbysystem.listeners;

import dev.laux.coins.Coins;
import dev.laux.lobbysystem.LobbySystem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.sql.SQLException;

public class EasterEggListener implements Listener {

    private final LobbySystem plugin;

    public EasterEggListener(LobbySystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteractEgg(PlayerInteractEvent event) throws SQLException {
        Player player = event.getPlayer();
        // check if dragon egg
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            if (block != null && block.getType() == Material.DRAGON_EGG) {
                // cancel event
                event.setCancelled(true);
                // location
                Location location = block.getLocation();
                // Überprüfen, ob es das gespeicherte Drachenei ist
                if (location.getY() == plugin.getLocationManager().getLocation("easter_egg").getY()) {
                    // Hier die Logik für die Belohnung implementieren
                    player.sendMessage("§7Du hast das Drachenei berührt!\n§7Hier ist deine Belohnung: §a+100000 Coins");
                    Coins.getInstance().getCoinAPI().addCoins(player.getUniqueId(), 100000);
                }
            }
        }
    }
}
