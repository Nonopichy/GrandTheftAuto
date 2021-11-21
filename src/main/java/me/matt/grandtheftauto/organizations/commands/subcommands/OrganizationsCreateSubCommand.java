package me.matt.grandtheftauto.organizations.commands.subcommands;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.commands.SubCommand;
import me.matt.grandtheftauto.organizations.enums.OrganizationType;
import me.matt.grandtheftauto.organizations.model.Organization;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class OrganizationsCreateSubCommand implements SubCommand {

    private final GrandTheftAuto plugin;

    public OrganizationsCreateSubCommand(GrandTheftAuto plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("create");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getMessageManager().getSimpleMessage("NoConsole");
            return false;
        }

        val player = (Player) sender;

        if (args.length != 4) {
            player.sendMessage(plugin.getMessageManager().getSimpleMessage("OrganizationsCreateSubCommandWrong"));
            return false;
        }

        val name = args[1];

        if (plugin.getDatabaseManager().getOrganizations().hasBy(name)) {
            player.sendMessage(plugin.getMessageManager().getSimpleMessage("OrganizationExisting"));
            return false;
        }

        if (name.length() > 32) {
            player.sendMessage(plugin.getMessageManager().getSimpleMessage("OrganizationNameLengthMax"));
            return false;
        }

        val type = OrganizationType.detect(args[2]);

        if (type == null) {
            player.sendMessage(plugin.getMessageManager().getSimpleMessage("OrganizationTypeWrong"));
            return false;
        }

        val color = args[3];

        if (color.length() != 7 || !color.startsWith("#")) {
            player.sendMessage(plugin.getMessageManager().getSimpleMessage("OrganizationWrongColor"));
            return false;
        }

        plugin.getDatabaseManager().getOrganizations().add(new Organization(name, type, color, player.getLocation()));
        player.sendMessage(plugin.getMessageManager().getSimpleMessage("OrganizationCreated"));

        return false;
    }

}
