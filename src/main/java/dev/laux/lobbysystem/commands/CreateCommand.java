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

public class CreateCommand implements CommandExecutor, TabCompleter {

    private final LobbySystem plugin;

    public CreateCommand(LobbySystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command.");
            return true;
        }
        // Player
        Player player = (Player) sender;
        // Easter Egg
        if (args.length == 1 && args[0].equalsIgnoreCase("egg")) {
            player.sendMessage("§aEaster Egg wird gesetzt!");
            player.getLocation().getBlock().setType(Material.DRAGON_EGG);
            player.sendMessage("§aEaster Egg wurde gesetzt!");
            plugin.getLocationManager().saveLocation("easter_egg", player.getLocation());
            return true;
        }
        // Villager
        if (args.length == 1 && args[0].equalsIgnoreCase("reward")) {
            Villager villager = (Villager) player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);
            villager.setCustomName("§6Tägliche Belohnung");
            villager.setCustomNameVisible(true);
            villager.setProfession(Villager.Profession.CLERIC);
            villager.setAI(false); // Villager kann sich nicht bewegen
            player.sendMessage("§aVillager wurde gesetzt!");
            plugin.getLocationManager().saveLocation("reward", player.getLocation());
            return true;
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
                arrayList.add("reward");
                arrayList.add("egg");
                // return list
                return arrayList;
            }
        }

        // Wenn mehr als ein Argument eingegeben wird, geben Sie eine leere Liste zurück
        return new ArrayList<>();
    }

}
