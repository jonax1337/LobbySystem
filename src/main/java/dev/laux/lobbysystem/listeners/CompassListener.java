package dev.laux.lobbysystem.listeners;

import dev.laux.lobbysystem.LobbySystem;
import dev.laux.lobbysystem.util.ItemManager;
import dev.laux.lobbysystem.util.LocationManager;
import dev.laux.lobbysystem.util.TeleportManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class CompassListener implements Listener {

    private final LobbySystem plugin;

    public CompassListener(LobbySystem plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();;

        // Setze den Kompass in die Mitte der Hotbar
        inventory.setItem(4, ItemManager.createItem(Material.CLOCK, "§e§lTeleporter"));
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() != null && event.getItem().getType() == Material.CLOCK) {
            // Öffne das vorhandene Inventar
            player.openInventory(TeleportManager.getTeleportInventory());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().equals(TeleportManager.getTeleportInventory())) {
            event.setCancelled(true); // Verhindert das Verschieben von Items

            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem == null || !clickedItem.hasItemMeta()) return;

            switch (clickedItem.getItemMeta().getDisplayName()) {
                case "§7» §bSpawn §7«":
                    Location spawn = plugin.getLocationManager().getLocation("spawn");
                    if (spawn != null) {
                        teleportPlayer(player, spawn);
                    }
                    player.closeInventory();
                    break;
                // Hier Fälle für weitere Teleportziele hinzufügen
                case "arsch":
                    break;
            }
        }
    }

    private void teleportPlayer(Player player, Location location) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 50, 1));
        player.teleport(new Location(player.getWorld(), 0, -200, 0));
        player.playSound(player.getLocation(), Sound.ENTITY_WARDEN_NEARBY_CLOSEST, 1f, 1f);
        player.playEffect(EntityEffect.GUARDIAN_TARGET);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            player.teleport(location);
            player.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
        }, 35L);
    }


}
