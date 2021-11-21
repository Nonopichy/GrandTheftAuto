package me.matt.grandtheftauto.users.commands.rg;

import lombok.experimental.UtilityClass;
import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.users.enums.AccountType;
import me.matt.grandtheftauto.users.enums.Gender;
import me.matt.grandtheftauto.users.model.User;
import org.bukkit.command.CommandSender;

import java.text.SimpleDateFormat;

// analise
@UtilityClass
public class RgUtil {

    public void sendRgMessage(CommandSender sender, User user) {
        val sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        val msg = GrandTheftAuto.getInstance().getMessageManager().getMultiMessage("Rg");

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
                .replace("%cargo%", user.getOrganizationRole() == null ? "Nenhum" : user.getOrganizationRole().toString())
                .replace("%house%", user.getHouse() == -1 ? "Nenhum" : String.valueOf(user.getHouse()))
                .replace("%company%", user.getCompany() == -1 ? "Nenhum" : String.valueOf(user.getCompany()))
                .replace("%farm%", user.getFarm() == -1 ? "Nenhum" : String.valueOf(user.getFarm()))));

    }

}
