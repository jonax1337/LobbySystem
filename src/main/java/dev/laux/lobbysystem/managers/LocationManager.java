package dev.laux.lobbysystem.managers;

import dev.laux.lobbysystem.LobbySystem;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public class LocationManager {
    private final LobbySystem plugin;
    private File configFile;
    private FileConfiguration config;

    public LocationManager(LobbySystem plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        configFile = new File(plugin.getDataFolder(), "locations.yml");
        if (!configFile.exists()) {
            try {
                configFile.getParentFile().mkdirs(); // Stellt sicher, dass das Verzeichnis existiert
                configFile.createNewFile(); // Erstellt die Datei, wenn sie nicht existiert
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveLocation(String path, Location location) {
        config.set(path + ".world", location.getWorld().getName());
        config.set(path + ".x", location.getX());
        config.set(path + ".y", location.getY());
        config.set(path + ".z", location.getZ());
        config.set(path + ".yaw", location.getYaw());
        config.set(path + ".pitch", location.getPitch());
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Location getLocation(String path) {
        if (!config.contains(path)) {
            return null;
        }
        String world = config.getString(path + ".world");
        double x = config.getDouble(path + ".x");
        double y = config.getDouble(path + ".y");
        double z = config.getDouble(path + ".z");
        float yaw = (float) config.getDouble(path + ".yaw");
        float pitch = (float) config.getDouble(path + ".pitch");
        return new Location(plugin.getServer().getWorld(world), x, y, z, yaw, pitch);
    }

    public void deleteLocation(String path) {
        if (config.contains(path)) {
            config.set(path, null); // Entfernt den spezifischen Pfad aus der Konfiguration
            try {
                config.save(configFile); // Speichert die Änderungen in der Datei
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
