package me.matt.grandtheftauto.organizations.commands;

import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.commands.SubCommand;
import me.matt.grandtheftauto.organizations.commands.subcommands.OrganizationsCreateSubCommand;
import me.matt.grandtheftauto.organizations.commands.subcommands.OrganizationsInviteSubCommand;
import me.matt.grandtheftauto.organizations.commands.subcommands.OrganizationsSeeSubCommand;
import me.matt.grandtheftauto.organizations.commands.subcommands.OrganizationsSetLeaderSubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class OrganizationsCommand implements CommandExecutor {

    private final GrandTheftAuto plugin;
    private final List<SubCommand> subCommands = new ArrayList<>();

    public OrganizationsCommand(GrandTheftAuto plugin) {
        this.plugin = plugin;
        plugin.getCommand("organizations").setExecutor(this);
        subCommands.add(new OrganizationsCreateSubCommand(plugin));
        subCommands.add(new OrganizationsSeeSubCommand(plugin));
        subCommands.add(new OrganizationsInviteSubCommand(plugin));
        subCommands.add(new OrganizationsSetLeaderSubCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length > 0) {
            for (SubCommand subCommand : subCommands) {
                if (subCommand.getAliases().contains(args[0].toLowerCase())) return subCommand.execute(sender, args);
            }
        }

        Bukkit.dispatchCommand(sender, "organizations see");

        return false;
    }

}
