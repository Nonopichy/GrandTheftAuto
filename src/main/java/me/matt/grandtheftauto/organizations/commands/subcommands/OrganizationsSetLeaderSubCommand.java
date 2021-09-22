package me.matt.grandtheftauto.organizations.commands.subcommands;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class OrganizationsSetLeaderSubCommand implements SubCommand {

    private GrandTheftAuto plugin;

    public OrganizationsSetLeaderSubCommand(GrandTheftAuto plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("setleader");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        // TODO: 20/09/2021
        if (!(sender.hasPermission("grandtheftauto.organizations.setleader"))) {
            sender.sendMessage(plugin.getMessageManager().getSimpleMessage("NoPermission"));
            return false;
        }

        // organization setleader organization name
        if (args.length != 3) {
            sender.sendMessage(plugin.getMessageManager().getSimpleMessage("OrganizationSetLeaderSubCommandWrong"));
            return false;
        }

        val organizationName = args[1].toLowerCase();

        val organization = plugin.getDatabaseManager().getOrganizations().get(organizationName);

        if (organization == null) {
            sender.sendMessage(plugin.getMessageManager().getSimpleMessage("OrganizationNotFound"));
            return false;
        }

        val player = Bukkit.getPlayer(args[2]);

        if (player == null) {
            sender.sendMessage(plugin.getMessageManager().getSimpleMessage("Offline"));
            return false;
        }

        if (plugin.getDatabaseManager().getOrganizations().hasByLeader(player.getName())) {
            sender.sendMessage(plugin.getMessageManager().getSimpleMessage("OrganizationPlayerAlreadyLeader"));
            return false;
        }

        organization.setLeader(player.getName());

        plugin.getDatabaseManager().getOrganizations().update(organization);

        sender.sendMessage(plugin.getMessageManager().getSimpleMessage("OrganizationLeaderSetted")
                .replace("%player%", player.getName())
                .replace("%organization%", organization.getDisplayName()));

        return false;
    }
    
}
