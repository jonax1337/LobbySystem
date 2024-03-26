package dev.laux.lobbysystem.listeners;

import dev.laux.coins.Coins;
import dev.laux.lobbysystem.LobbySystem;
import dev.laux.lobbysystem.util.ItemManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class JoinListener implements Listener {

    private final LobbySystem plugin;

    public JoinListener(LobbySystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayJoin(PlayerJoinEvent event) throws SQLException {
        // Get Player
        Player player = event.getPlayer();
        // Set Health
        player.setMaxHealth(20d);
        player.setHealth(20d);
        // Set Sat
        player.setFoodLevel(20);
        // Clear Inv
        player.getInventory().clear();
        // No Message
        event.setJoinMessage("");
        // Fülle das Inventar mit grauem Glas, außer Rüstung, Schild und Off-Hand
        player.getInventory().clear();
        for (int i = 9; i < 36; i++) {
            if (player.getInventory().getItem(i) == null) {
                player.getInventory().setItem(i, ItemManager.createItem(Material.GRAY_STAINED_GLASS_PANE, " "));
            }
        }
        // Teleport Player to Spawn Location on first Join
        if (!player.hasPlayedBefore()) {
            Location spawn = plugin.getLocationManager().getLocation("spawn");
            if (spawn != null) {
                player.teleport(spawn);
            } else {
                player.sendMessage("§cSpawn wurde noch nicht gesetzt!");
            }
        }

    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        // Get Player
        Player player = event.getPlayer();
        // No Message
        event.setQuitMessage("");
    }

}
