package dev.laux.lobbysystem.commands;

import dev.laux.lobbysystem.LobbySystem;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpawnCommand implements CommandExecutor, TabCompleter {
    private final LobbySystem plugin;

    public SpawnCommand(LobbySystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cNur Spieler können diesen Befehl verwenden.");
            return true;
        }

        Player player = (Player) sender;
        Location spawn = plugin.getLocationManager().getLocation("spawn");

        if (args.length == 1 && args[0].equalsIgnoreCase("remove")) {
            if (spawn != null) {
                plugin.getLocationManager().deleteLocation("spawn");
                sender.sendMessage("§cSpawn wurde entfernt!");
            } else {
                player.sendMessage("§cSpawn wurde noch nicht gesetzt!");
            }
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("set")) {
            plugin.getLocationManager().saveLocation("spawn", player.getLocation());
            player.sendMessage("§aSpawn wurde gesetzt!");
            return true;
        }

        if (spawn != null) {
            player.teleport(spawn);
        } else {
            player.sendMessage("§cSpawn wurde noch nicht gesetzt!");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }

        Player player = (Player) sender;

        if (args.length == 1) {
            if (player.hasPermission("*")) {
                // create list
                List<String> arrayList = new ArrayList<>();
                // add commands
                arrayList.add("set");
                arrayList.add("remove");
                // return list
                return arrayList;
            }
        }

        // Wenn mehr als ein Argument eingegeben wird, geben Sie eine leere Liste zurück
        return new ArrayList<>();
    }
}
