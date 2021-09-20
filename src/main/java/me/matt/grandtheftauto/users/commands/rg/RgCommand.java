package me.matt.grandtheftauto.users.commands.rg;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.users.enums.AccountType;
import me.matt.grandtheftauto.users.enums.Gender;
import me.matt.grandtheftauto.users.model.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;

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

                sendRgMessage(sender, user);
            } else {
                sender.sendMessage(plugin.getMessageManager().getSimpleMessage("NoConsole"));
            }
            return false;
        }

        val player = (Player) sender;

        val user = plugin.getDatabaseManager().getUsers().get(player.getName());

        sendRgMessage(player, user);

        return false;
    }

    private void sendRgMessage(CommandSender sender, User user) {
        val sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        val msg = plugin.getMessageManager().getMultiMessage("Rg");


        msg.forEach(it -> sender.sendMessage(it
                .replace("%nickName%", user.getNickName())
                .replace("%firstLogin%", sdf.format(user.getFirstLogin()))
                .replace("%level%", String.valueOf(user.getLevel()))
                .replace("%gender%", user.getGender() == Gender.MALE ? "Homem" : "Mulher")
                .replace("%money%", String.valueOf(user.getMoneyBalance()))
                .replace("%moneyInBank%", String.valueOf(user.getMoneyInBank()))
                .replace("%cashBalance%", String.valueOf(user.getCashBalance()))
                .replace("%golds%", String.valueOf(user.getGolds()))
                .replace("%warns%", String.valueOf(user.getWarns()))
                .replace("%accountType%", user.getAccountType() == AccountType.FREE ? "Free" : "Premium")
                .replace("%vipTime%", "?????") //
                .replace("%none%", "?????") //
                .replace("%playedHours%", "?????") //
                .replace("%cellphone%", "?????") //
                .replace("%%credits%", "?????") //
                .replace("%respectHave%", "?????") //
                .replace("%respectMeta%", "?????") //
                .replace("%crimes%", String.valueOf(user.getCrimes()))
                .replace("%stars%", String.valueOf(user.getStars()))
                .replace("%killedTimes%", String.valueOf(user.getKilledTimes()))
                .replace("%diedTimes%", String.valueOf(user.getDiedTimes()))
                .replace("%job%", user.getJob() == null ? "Nenhum" : user.getJob().getName())
                .replace("%organization%", user.getOrganization() == null ? "Nenhum" : user.getOrganization().getDisplayName())
                .replace("%cargo%", "?????") // TODO: 20/09/2021 cargo(?) in the org
                .replace("%house%", user.getHouse() == -1 ? "Nenhum" : String.valueOf(user.getHouse()))
                .replace("%company%", user.getCompany() == -1 ? "Nenhum" : String.valueOf(user.getCompany()))
                .replace("%farm%", user.getFarm() == -1 ? "Nenhum" : String.valueOf(user.getFarm()))));
    }

}
