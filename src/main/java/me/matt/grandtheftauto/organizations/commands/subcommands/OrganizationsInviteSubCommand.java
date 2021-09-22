package me.matt.grandtheftauto.organizations.commands.subcommands;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class OrganizationsInviteSubCommand implements SubCommand {

    private GrandTheftAuto plugin;

    public OrganizationsInviteSubCommand(GrandTheftAuto plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("invite", "convidar");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(plugin.getMessageManager().getSimpleMessage("NoConsole"));
            return false;
        }

        val player = (Player) sender;

        val organization = plugin.getDatabaseManager().getOrganizations().getByLeader(player.getName());

        if (organization == null) {
            player.sendMessage(plugin.getMessageManager().getSimpleMessage("OrganizationNotLeader"));
            return false;
        }

        if (organization.getMembers().size() > 30) {
            player.sendMessage(plugin.getMessageManager().getSimpleMessage("OrganizationMaxMembers"));
            return false;
        }

        // TODO: 20/09/2021 check this 

        return false;
    }

}
