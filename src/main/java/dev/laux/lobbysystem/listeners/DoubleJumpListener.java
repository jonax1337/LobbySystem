package dev.laux.lobbysystem.listeners;

import dev.laux.lobbysystem.LobbySystem;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Locale;

public class DoubleJumpListener implements Listener {

    private final LobbySystem plugin;

    public DoubleJumpListener(LobbySystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if ((player.getGameMode() != GameMode.ADVENTURE && player.getGameMode() != GameMode.SURVIVAL) || player.isFlying()) {
            return;
        }

        // Check if the player is on the ground.
        if (player.isOnGround()) {
            player.setAllowFlight(true);
        }
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return; // Don't interfere with creative or spectator mode functionality.
        }

        Location location = player.getLocation();

        event.setCancelled(true); // Prevent the default flight toggle.
        player.setAllowFlight(false); // Disable flight right after the double jump to prevent continuous flying.
        player.setFlying(false); // Ensure the player isn't marked as flying.

        // Play Sound
        player.playSound(location, Sound.ENTITY_BAT_TAKEOFF, 1f, 1.5f);

        // Create Particles
        player.spawnParticle(Particle.CLOUD, location.getX(), location.getY(), location.getZ(), 0, 0, 0, 0, 0);

        // Follow the Player for 20 ticks
        new BukkitRunnable() {
            int counter = 0;
            @Override
            public void run() {
                if (counter >= 5) {
                    this.cancel();
                }
                Location loc = player.getLocation();
                Bukkit.getServer().getWorld(loc.getWorld().getName()).spawnParticle(Particle.CLOUD, loc.getX(), loc.getY(), loc.getZ(), 0);
                counter++;
            }
        }.runTaskTimerAsynchronously(plugin, 0L, 1L);

        // Apply a vector to the player to "push" them into the air for the double jump.
        Vector jump = player.getLocation().getDirection().multiply(1.25).setY(1);
        player.setVelocity(player.getVelocity().add(jump));
    }

}
