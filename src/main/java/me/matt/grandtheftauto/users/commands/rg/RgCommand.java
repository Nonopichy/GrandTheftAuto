package me.matt.grandtheftauto.users.commands.rg;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RgCommand implements CommandExecutor {

    private final GrandTheftAuto plugin;

    public RgCommand(GrandTheftAuto plugin) {
        this.plugin = plugin;
        plugin.getCommand("rg").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        val manager = plugin.getMessageManager();
        if (!(sender instanceof Player)) {
            sender.sendMessage(manager.getSimpleMessage("NoConsole"));
            return false;
        }
        val data = plugin.getDatabaseManager().getUsers();
        if (args.length == 1) {
            val name = args[0];
            val user = data.get(name);
            if (user == null) {
                sender.sendMessage(manager.getSimpleMessage("UserNotFound"));
                return false;
            }
            RgUtil.sendRgMessage(sender, user);
            return false;
        }
        val p = (Player) sender;
        RgUtil.sendRgMessage(p, data.get(p.getName()));
        return false;
    }
}
