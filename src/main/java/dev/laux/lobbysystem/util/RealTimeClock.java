package dev.laux.lobbysystem.util;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class RealTimeClock {

    public void syncTimeWithRealWorld() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Berlin")); // Zeitzone anpassen
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(calendar.getTime());

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Berechne die aktuelle Zeit in Ticks. Ein Minecraft-Tag startet um 6:00 Uhr.
        int offsetHour = hour - 6;
        if (offsetHour < 0) {
            // Vor 6 Uhr morgens, füge 18 Stunden hinzu, da ein Minecraft-Tag 24 Stunden hat
            offsetHour += 24;
        }

        // Konvertiere Stunden und Minuten in Ticks
        long ticks = (offsetHour * 1000) + ((minute * 1000) / 60);

        // Einstellen der Zeit in Minecraft
        Bukkit.getWorlds().forEach(world -> {
            world.setTime(ticks);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        });

        // Zeige die Zeit in der Action Bar für alle Spieler
        String actionBarMessage = "§8» §a" + time + "§8 «";
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(actionBarMessage));
        }

    }

}
