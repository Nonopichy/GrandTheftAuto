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
        val manager = plugin.getMessageManager();
        if (!(sender instanceof Player)) {
            sender.sendMessage(manager.getSimpleMessage("NoConsole"));
            return false;
        }

        if (args.length != 1) {
            // TODO: 23/09/2021 show atual locationtype
            sender.sendMessage(manager.getSimpleMessage("LocationTypeCommandWrong"));
            return false;
        }

        val p = (Player) sender;
        val data = plugin.getDatabaseManager().getUsers();
        val user = data.get(p.getName());

        switch (args[0].toLowerCase()) {
            case "spawn":
                // TODO: 20/09/2021 check
                user.setLocationType(LocationType.SPAWN);
                data.update(user);
                p.sendMessage(manager.getSimpleMessage("LocationTypeSuccess").replace("%locationType%", args[0].toUpperCase()));
                break;
            case "hq":
                user.setLocationType(LocationType.HQ);
                data.getUsers().update(user);
                p.sendMessage(manager.getSimpleMessage("LocationTypeSuccess").replace("%locationType%", args[0].toUpperCase()));
                break;
            default:
                p.sendMessage(manager.getSimpleMessage("LocationTypeWrongCommand"));
                break;
        }
        return true;
    }
}
