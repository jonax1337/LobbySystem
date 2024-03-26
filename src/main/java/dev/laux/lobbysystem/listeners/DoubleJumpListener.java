package dev.laux.lobbysystem.listeners;

import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class DoubleJumpListener implements Listener {

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

        event.setCancelled(true); // Prevent the default flight toggle.
        player.setAllowFlight(false); // Disable flight right after the double jump to prevent continuous flying.
        player.setFlying(false); // Ensure the player isn't marked as flying.

        // Play Sound
        player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1f, 1.5f);

        // Create Particles
        player.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, player.getLocation(), 5, 0.5, 0.5, 0.5, 0.5);

        // Apply a vector to the player to "push" them into the air for the double jump.
        Vector jump = player.getLocation().getDirection().multiply(1.25).setY(1);
        player.setVelocity(player.getVelocity().add(jump));
    }

}
