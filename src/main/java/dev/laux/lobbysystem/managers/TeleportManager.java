package dev.laux.lobbysystem.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class TeleportManager {

    private static Inventory teleportInventory;

    public static Inventory getTeleportInventory() {
        if (teleportInventory == null) {
            // Erstelle das Inventar nur einmal
            teleportInventory = Bukkit.createInventory(null, 9*3, "Locations");
            // Beispiel für das Hinzufügen von Teleport-Zielen (Hier musst du eigene Locations definieren)
            initializeItems();
        }
        return teleportInventory;
    }

    private static void initializeItems() {
        teleportInventory.setItem(9 + 4, ItemManager.createItem(Material.ENDER_EYE, "§7» §bSpawn §7«"));
        // Hier weitere Items hinzufügen
    }


}
