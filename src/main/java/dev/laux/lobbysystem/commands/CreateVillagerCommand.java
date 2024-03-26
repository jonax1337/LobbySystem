package dev.laux.lobbysystem.commands;

import dev.laux.lobbysystem.LobbySystem;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.jetbrains.annotations.NotNull;

public class CreateVillagerCommand implements CommandExecutor {

    private final LobbySystem plugin;

    public CreateVillagerCommand(LobbySystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command.");
            return true;
        }

        Player player = (Player) sender;
        Villager villager = (Villager) player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);
        villager.setCustomName("§6Tägliche Belohnung");
        villager.setCustomNameVisible(true);
        villager.setProfession(Villager.Profession.CLERIC);
        villager.setAI(false); // Villager kann sich nicht bewegen

        player.sendMessage("§aVillager wurde gesetzt!");

        plugin.getLocationManager().saveLocation("reward", player.getLocation());

        return true;
    }
}
