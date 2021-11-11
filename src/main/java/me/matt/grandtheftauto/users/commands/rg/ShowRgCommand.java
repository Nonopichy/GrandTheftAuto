package me.matt.grandtheftauto.users.commands.rg;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShowRgCommand implements CommandExecutor {

    private GrandTheftAuto plugin;

    public ShowRgCommand(GrandTheftAuto plugin) {
        this.plugin = plugin;
        plugin.getCommand("showrg").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getMessageManager().getSimpleMessage("NoConsole"));
            return false;
        }

        if (args.length != 1) {
            sender.sendMessage(plugin.getMessageManager().getSimpleMessage("ShowRgWrongCommand"));
            return false;
        }

        val player = (Player) sender;
        val target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(plugin.getMessageManager().getSimpleMessage("Offline"));
            return false;
        }

        val radius = plugin.getConfig().getInt("RadiusShowRg");

        if (player.getLocation().distance(target.getLocation()) > radius) {
            sender.sendMessage(plugin.getMessageManager().getSimpleMessage("PlayerTooFar"));
            return false;
        }

        val user = plugin.getDatabaseManager().getUsers().get(player.getName());
        RgUtil.sendRgMessage(target, user);

        return false;
    }

}
