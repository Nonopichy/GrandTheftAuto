package me.matt.grandtheftauto.users.commands.rg;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RgCommand implements CommandExecutor {

    private GrandTheftAuto plugin;

    public RgCommand(GrandTheftAuto plugin) {
        this.plugin = plugin;
        plugin.getCommand("rg").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 1) {
                val name = args[0];
                val user = plugin.getDatabaseManager().getUsers().get(name);

                if (user == null) {
                    sender.sendMessage(plugin.getMessageManager().getSimpleMessage("UserNotFound"));
                    return false;
                }

                RgUtil.sendRgMessage(sender, user);
            } else {
                sender.sendMessage(plugin.getMessageManager().getSimpleMessage("NoConsole"));
            }
            return false;
        }

        val player = (Player) sender;
        val user = plugin.getDatabaseManager().getUsers().get(player.getName());

        RgUtil.sendRgMessage(player, user);

        return false;
    }

}
