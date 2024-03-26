package dev.laux.lobbysystem;

import dev.laux.lobbysystem.commands.CreateEasterEggCommand;
import dev.laux.lobbysystem.commands.CreateVillagerCommand;
import dev.laux.lobbysystem.commands.SetSpawnCommand;
import dev.laux.lobbysystem.commands.SpawnCommand;
import dev.laux.lobbysystem.listeners.*;
import dev.laux.lobbysystem.util.LocationManager;
import dev.laux.lobbysystem.util.RealTimeClock;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LobbySystem extends JavaPlugin {

    private LocationManager locationManager;
    private static LobbySystem instance;

    @Override
    public void onEnable() {
        instance = this;
        // Register
        registerEvents(getServer().getPluginManager());
        registerCommands();
        // Real Time Clock
        RealTimeClock realTimeClock = new RealTimeClock();
        Bukkit.getScheduler().runTaskTimer(this, realTimeClock::syncTimeWithRealWorld, 0L, 20L);
        // Location Manager
        this.locationManager = new LocationManager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerEvents(PluginManager pluginManager) {
        pluginManager.registerEvents(new JoinListener(this), this);
        pluginManager.registerEvents(new ProtectionListener(), this);
        pluginManager.registerEvents(new DoubleJumpListener(), this);
        pluginManager.registerEvents(new CompassListener(this), this);
        pluginManager.registerEvents(new EasterEggListener(this), this);
    }

    private void registerCommands() {
        this.getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        this.getCommand("spawn").setExecutor(new SpawnCommand(this));
        this.getCommand("createvillager").setExecutor(new CreateVillagerCommand(this));
        this.getCommand("createegg").setExecutor(new CreateEasterEggCommand(this));
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public static LobbySystem getInstance() {
        return instance;
    }

}
