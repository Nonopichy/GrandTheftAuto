package me.matt.grandtheftauto.organizations.commands.subcommands;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.commands.SubCommand;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class OrganizationsSeeSubCommand implements SubCommand {

    private GrandTheftAuto plugin;

    public OrganizationsSeeSubCommand(GrandTheftAuto plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("see", "ver");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length >= 2) {

            if (StringUtils.isNumeric(args[1])) {
                val id = Short.parseShort(args[1]);
                val organization = plugin.getDatabaseManager().getOrganizations().getById(id);

                sender.sendMessage((organization == null) ? plugin.getMessageManager().getSimpleMessage("OrganizationNotFound") : organization.toString());
            } else {
                val name = args[1].toLowerCase();
                val organization = plugin.getDatabaseManager().getOrganizations().get(name);

                sender.sendMessage((organization == null) ? plugin.getMessageManager().getSimpleMessage("OrganizationNotFound") : organization.toString());
            }
            return false;

        }

        plugin.getDatabaseManager().getOrganizations().getAll().forEach(it -> {
            sender.sendMessage(it.toString());
        });

        return false;
    }

}
