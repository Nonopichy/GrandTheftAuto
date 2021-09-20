package me.matt.grandtheftauto.users.commands.rg;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.users.enums.AccountType;
import me.matt.grandtheftauto.users.enums.Gender;
import me.matt.grandtheftauto.users.model.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;

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

        val radius = plugin.getConfig().getInt("Radius");

        if (player.getLocation().distance(target.getLocation()) > radius) {
            sender.sendMessage(plugin.getMessageManager().getSimpleMessage("PlayerTooFar"));
            return false;
        }

        val user = plugin.getDatabaseManager().getUsers().get(player.getName());
        sendRgMessage(target, user);

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
                .replace("%vipTime%", "?????")
                .replace("%none%", "?????")
                .replace("%playedHours%", "?????")
                .replace("%cellphone%", "?????")
                .replace("%%credits%", "?????")
                .replace("%respectHave%", "?????")
                .replace("%respectMeta%", "?????")
                .replace("%crimes%", String.valueOf(user.getCrimes()))
                .replace("%stars%", String.valueOf(user.getStars()))
                .replace("%killedTimes%", String.valueOf(user.getKilledTimes()))
                .replace("%diedTimes%", String.valueOf(user.getDiedTimes()))
                .replace("%job%", user.getJob() == null ? "Nenhum" : user.getJob().getName())
                .replace("%organization%", user.getOrganization() == null ? "Nenhum" : user.getOrganization().getDisplayName())
                .replace("%cargo%", "?????")
                .replace("%house%", user.getHouse() == -1 ? "Nenhum" : String.valueOf(user.getHouse()))
                .replace("%company%", user.getCompany() == -1 ? "Nenhum" : String.valueOf(user.getCompany()))
                .replace("%farm%", user.getFarm() == -1 ? "Nenhum" : String.valueOf(user.getFarm()))));
    }

}
