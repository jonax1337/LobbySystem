package dev.laux.lobbysystem.commands;

import dev.laux.lobbysystem.LobbySystem;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CreateVillagerCommand implements CommandExecutor, TabCompleter {

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

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }
        // Wenn mehr als ein Argument eingegeben wird, geben Sie eine leere Liste zurück
        return new ArrayList<>();
    }

}
