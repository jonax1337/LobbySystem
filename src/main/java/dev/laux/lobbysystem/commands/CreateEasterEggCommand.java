package dev.laux.lobbysystem.commands;

import dev.laux.lobbysystem.LobbySystem;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.ArrayList;
import java.util.List;

public class CreateEasterEggCommand implements CommandExecutor, TabCompleter {

    private final LobbySystem plugin;

    public CreateEasterEggCommand(LobbySystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command.");
            return true;
        }

        Player player = (Player) sender;
        player.getLocation().getBlock().setType(Material.DRAGON_EGG);

        player.sendMessage("§aEaster Egg wurde gesetzt!");

        plugin.getLocationManager().saveLocation("easter_egg", player.getLocation());

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
