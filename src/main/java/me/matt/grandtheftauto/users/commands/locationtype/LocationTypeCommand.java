package me.matt.grandtheftauto.users.commands.locationtype;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.users.enums.LocationType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocationTypeCommand implements CommandExecutor {

    private GrandTheftAuto plugin;

    public LocationTypeCommand(GrandTheftAuto plugin) {
        this.plugin = plugin;
        plugin.getCommand("locationtype").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getMessageManager().getSimpleMessage("NoConsole"));
            return false;
        }

        if (args.length != 1) {
            // TODO: 23/09/2021 show atual locationtype
            sender.sendMessage(plugin.getMessageManager().getSimpleMessage("LocationTypeCommandWrong"));
            return false;
        }

        val player = (Player) sender;
        val user = plugin.getDatabaseManager().getUsers().get(player.getName());

        switch (args[0].toLowerCase()) {
            case "spawn":
                // TODO: 20/09/2021 check
                user.setLocationType(LocationType.SPAWN);
                plugin.getDatabaseManager().getUsers().update(user);
                player.sendMessage(plugin.getMessageManager().getSimpleMessage("LocationTypeSuccess").replace("%locationType%", args[0].toUpperCase()));
                break;
            case "hq":
                user.setLocationType(LocationType.HQ);
                plugin.getDatabaseManager().getUsers().update(user);
                player.sendMessage(plugin.getMessageManager().getSimpleMessage("LocationTypeSuccess").replace("%locationType%", args[0].toUpperCase()));
                break;
            default:
                player.sendMessage(plugin.getMessageManager().getSimpleMessage("LocationTypeWrongCommand"));
                break;
        }

        return false;
    }

}
