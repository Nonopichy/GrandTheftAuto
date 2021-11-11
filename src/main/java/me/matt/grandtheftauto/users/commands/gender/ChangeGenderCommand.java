package me.matt.grandtheftauto.users.commands.gender;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.users.enums.Gender;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangeGenderCommand implements CommandExecutor {

    private GrandTheftAuto plugin;

    public ChangeGenderCommand(GrandTheftAuto plugin) {
        this.plugin = plugin;
        plugin.getCommand("changegender").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(plugin.getMessageManager().getSimpleMessage("NoConsole"));
            return false;
        }

        val player = (Player) sender;

        val user = plugin.getDatabaseManager().getUsers().get(player.getName());
        val gender = user.getGender();

        user.setGender(gender == Gender.MALE ? Gender.FEMALE : Gender.MALE);

        plugin.getDatabaseManager().getUsers().update(user);
        sender.sendMessage(plugin.getMessageManager().getSimpleMessage("GenderChanged")
                .replace("%gender%", user.getGender() == Gender.MALE ? "Homem" : "Mulher"));
        return false;
    }
}
