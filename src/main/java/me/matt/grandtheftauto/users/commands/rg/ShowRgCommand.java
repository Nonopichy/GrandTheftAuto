package me.matt.grandtheftauto.users.commands.rg;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShowRgCommand implements CommandExecutor {

    private final GrandTheftAuto plugin;

    public ShowRgCommand(GrandTheftAuto plugin) {
        this.plugin = plugin;
        plugin.getCommand("showrg").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        val manager = plugin.getMessageManager();
        if (!(sender instanceof Player)) {
            sender.sendMessage(manager.getSimpleMessage("NoConsole"));
            return false;
        }

        if (args.length != 1) {
            sender.sendMessage(manager.getSimpleMessage("ShowRgWrongCommand"));
            return false;
        }

        val p = (Player) sender;
        val target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(manager.getSimpleMessage("Offline"));
            return false;
        }

        val radius = plugin.getConfig().getInt("RadiusShowRg");

        if (p.getLocation().distance(target.getLocation()) > radius) {
            sender.sendMessage(manager.getSimpleMessage("PlayerTooFar"));
            return false;
        }

        val user = plugin.getDatabaseManager().getUsers().get(p.getName());
        RgUtil.sendRgMessage(target, user);
        return true;
    }
}
