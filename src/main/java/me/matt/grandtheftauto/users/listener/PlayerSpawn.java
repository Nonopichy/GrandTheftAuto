package me.matt.grandtheftauto.users.listener;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.util.parser.LocationParser;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerSpawn implements Listener {

    private final GrandTheftAuto plugin;

    public PlayerSpawn(GrandTheftAuto plugin) {
        Bukkit.getPluginManager().registerEvents(this, this.plugin = plugin);
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        val p = e.getPlayer();
        val user = plugin.getDatabaseManager().getUsers().get(p.getName());
        if (user == null) {
            p.teleport(LocationParser.stringToLoc(plugin.getConfig().getString("Spawn")));
            return;
        }
        switch (user.getLocationType()) {
            case SPAWN:
                p.teleport(LocationParser.stringToLoc(plugin.getConfig().getString("Spawn")));
                break;
            case HQ:
                if (user.getOrganization() == null) p.teleport(LocationParser.stringToLoc(plugin.getConfig().getString("Spawn")));
                else p.teleport(user.getOrganization().getHqLocation());
                break;
        }
    }
}
