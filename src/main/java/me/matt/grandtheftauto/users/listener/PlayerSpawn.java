package me.matt.grandtheftauto.users.listener;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.util.parser.LocationParser;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerSpawn implements Listener {

    private GrandTheftAuto plugin;

    public PlayerSpawn(GrandTheftAuto plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        val user = plugin.getDatabaseManager().getUsers().get(event.getPlayer().getName());
        if (user == null) event.getPlayer().teleport(LocationParser.stringToLoc(plugin.getConfig().getString("Spawn")));

        switch (user.getLocationType()) {
            case SPAWN:
                event.getPlayer().teleport(LocationParser.stringToLoc(plugin.getConfig().getString("Spawn")));
                break;
            case HQ:
                if (user.getOrganization() == null) event.getPlayer().teleport(LocationParser.stringToLoc(plugin.getConfig().getString("Spawn")));
                else event.getPlayer().teleport(user.getOrganization().getHqLocation());
                break;
        }

    }

}
