package dev.laux.lobbysystem.listeners;

import dev.laux.coins.Coins;
import dev.laux.lobbysystem.managers.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

public class DailyRewardVillager implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) throws SQLException {
        if (event.getRightClicked().getType() == EntityType.VILLAGER) {
            Villager villager = (Villager) event.getRightClicked();
            if (Objects.requireNonNull(villager.getCustomName()).contains("Tägliche Belohnung")) {
                event.setCancelled(true);
                openRewardInventory(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) throws SQLException {
        if (event.getClickedInventory() == null) return;
        if (!event.getView().getTitle().equals("Daily Rewards")) return;

        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        UUID playerUUID = player.getUniqueId();

        if (clickedItem.getType() == Material.CHEST_MINECART) {
            Coins.getInstance().getCoinAPI().addCoins(playerUUID, 100);
            Coins.getInstance().getCoinAPI().updateRewards(playerUUID, new Timestamp(Calendar.getInstance().getTimeInMillis()));
            player.sendMessage("\n§7Du hast deine tägliche Belohnung von §a100 Coins §7erhalten!\n ");
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            player.closeInventory();
            openRewardInventory(player);
        } else if (clickedItem.getType() == Material.TNT_MINECART) {
            if (Coins.getInstance().getCoinAPI().getConsecutiveDays(playerUUID) == 3) {
                Coins.getInstance().getCoinAPI().addCoins(playerUUID, 500);
                Coins.getInstance().getCoinAPI().updateConsecutiveDays(playerUUID);
                player.sendMessage("\n§7Du hast deine Belohnung von §a500 Coins §7für 3 aufeinanderfolgende Tage erhalten!\n ");
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                player.closeInventory();
                openRewardInventory(player);
            } else {
                player.sendMessage("\n§cDu musst 3 Tage in Folge eine Belohnung abholen, um diese Belohnung zu erhalten!\n ");
            }
        } else if (clickedItem.getType() == Material.MINECART) {
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
        }
    }

    private void openRewardInventory(Player player) throws SQLException {
        // Inventory
        Inventory inv = Bukkit.createInventory(null, 9, "Daily Rewards");
        // Items
        ItemStack oneDayReward = ItemManager.createItem(Material.CHEST_MINECART, "§aTägliche Belohnung");
        ItemStack oneDayRewardTaken = ItemManager.createItem(Material.MINECART, "§7Tägliche Belohnung");
        ItemStack threeDaysReward = ItemManager.createItem(Material.TNT_MINECART, "§a3 Tage in Folge");
        ItemStack threeDaysRewardTaken = ItemManager.createItem(Material.MINECART, "§73 Tage in Folge");
        // Three Days Reward
        if (Coins.getInstance().getCoinAPI().getConsecutiveDays(player.getUniqueId()) == 3) {
            inv.setItem(5, threeDaysReward);
        } else {
            inv.setItem(5, threeDaysRewardTaken);
        }
        // One Day Reward
        Timestamp lastRewarded = Coins.getInstance().getCoinAPI().getRewards(player.getUniqueId());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Timestamp yesterday = new Timestamp(calendar.getTimeInMillis());
        // Check if last reward was yesterday
        if (lastRewarded.before(yesterday)) {
            inv.setItem(3, oneDayReward);
        } else {
            inv.setItem(3, oneDayRewardTaken);
        }
        // Open Inventory
        player.openInventory(inv);
    }
}
