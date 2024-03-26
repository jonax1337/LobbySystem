package dev.laux.lobbysystem.listeners;

import io.papermc.paper.event.player.PlayerPickItemEvent;
import org.bukkit.GameMode;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class ProtectionListener implements Listener {

    // Disable Item Drops
    @EventHandler
    public void onDropItem (PlayerDropItemEvent event) {
        // Get Player
        Player player = event.getPlayer();
        // Check Game-mode
        if (player.getGameMode() != GameMode.CREATIVE) {
            // Cancel Event
            event.setCancelled(true);
        }
    }

    // Disable Item Pick
    @EventHandler
    public void onPickItem (PlayerPickItemEvent event) {
        // Get Player
        Player player = event.getPlayer();
        // Check Game-mode
        if (player.getGameMode() != GameMode.CREATIVE) {
            // Cancel Event
            event.setCancelled(true);
        }
    }

    // Disable Arrow Pick
    @EventHandler
    public void onArrowPickup (PlayerPickupArrowEvent event) {
        // Get Player
        Player player = event.getPlayer();
        // Check Game-mode
        if (player.getGameMode() != GameMode.CREATIVE) {
            // Cancel Event
            event.setCancelled(true);
        }
    }

    // Disable Hand Swap
    @EventHandler
    public void onHandSwap (PlayerSwapHandItemsEvent event) {
        // Get Player
        Player player = event.getPlayer();
        // Check Game-mode
        if (player.getGameMode() != GameMode.CREATIVE) {
            // Cancel Event
            event.setCancelled(true);
        }
    }

    // Disable Entity Damage by Entity
    @EventHandler
    public void onHurt (EntityDamageByEntityEvent event) {
        // Cancel Event
        event.setCancelled(true);
    }

    // Disable all Entity Damage
    @EventHandler
    public void onDamage (EntityDamageEvent event) {
        // Cancel Event
        event.setCancelled(true);
    }

    // Disable Break
    @EventHandler
    public void onBreak (BlockBreakEvent event) {
        // Get Player
        Player player = event.getPlayer();
        // Cancel event
        event.setCancelled(true);
        // Check if permission & allow
        if (player.hasPermission("lobby.build") && player.getGameMode() == GameMode.CREATIVE) {
            event.setCancelled(false);
        }
    }

    // Disable Build
    @EventHandler
    public void onBuild (BlockPlaceEvent event) {
        // Get Player
        Player player = event.getPlayer();
        // Cancel event
        event.setCancelled(true);
        // Check if permission & allow
        if (player.hasPermission("lobby.build") && player.getGameMode() == GameMode.CREATIVE) {
            event.setCancelled(false);
        }
    }

    // Disable Hunger
    @EventHandler
    public void onHunger (FoodLevelChangeEvent event) {
        // Cancel Event
        event.setCancelled(true);
    }

    // Disable Inventory Click
    @EventHandler
    public void onInventoryClick (InventoryClickEvent event) {
        // Get Player
        @NotNull HumanEntity humanEntity = event.getWhoClicked();
        // Get Inventory
        Inventory inventory = event.getClickedInventory();
        // Check if it is a Player
        if (humanEntity instanceof Player) {
            // Get the Player
            Player player = ((Player) humanEntity).getPlayer();
            // Check the GameMode
            assert player != null;
            if (player.getGameMode() == GameMode.CREATIVE && inventory == player.getInventory()) {
                return;
            }
            // Cancel the Event
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeatherChange (WeatherChangeEvent event) {
        event.setCancelled(true);
    }

}
