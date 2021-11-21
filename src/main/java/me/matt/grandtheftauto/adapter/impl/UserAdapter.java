package me.matt.grandtheftauto.adapter.impl;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.adapter.Adapter;
import me.matt.grandtheftauto.organizations.enums.OrganizationRole;
import me.matt.grandtheftauto.users.enums.AccountType;
import me.matt.grandtheftauto.users.enums.Gender;
import me.matt.grandtheftauto.users.enums.LocationType;
import me.matt.grandtheftauto.users.model.User;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UserAdapter implements Adapter<User, ResultSet> {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private final GrandTheftAuto plugin;

    public UserAdapter(GrandTheftAuto plugin) {
        this.plugin = plugin;
    }

    @Override
    public User adapt(ResultSet resultSet) {
        try {

            val nick = resultSet.getString("nickName");
            val firstLogin = simpleDateFormat.parse(resultSet.getString("firstLogin"));
            val level = resultSet.getInt("level");
            val gender = Gender.valueOf(resultSet.getString("gender"));
            val money = resultSet.getDouble("money");
            val moneyInBank = resultSet.getDouble("moneyInBank");
            val cashBalance = resultSet.getDouble("cashBalance");
            val golds = resultSet.getInt("golds");
            val warns = resultSet.getInt("warns");
            val accountType = AccountType.valueOf(resultSet.getString("accountType"));
            val locationType = LocationType.valueOf(resultSet.getString("locationType"));
            val vipTime = resultSet.getLong("vipTime");
            val crimes = resultSet.getInt("crimes");
            val stars = resultSet.getInt("stars");
            val killedTimes = resultSet.getInt("killedTimes");
            val diedTimes = resultSet.getInt("diedTimes");
            val job = plugin.getDatabaseManager().getJobs().get(resultSet.getString("job"));
            val organization = plugin.getDatabaseManager().getOrganizations().get(resultSet.getString("organization"));
            val organizationRole = OrganizationRole.getOrganizationRoleByNumber(resultSet.getInt("organizationRole"));
            val house = resultSet.getInt("house");
            val company = resultSet.getInt("company");
            val farm = resultSet.getInt("farm");
            val lastLogin = simpleDateFormat.parse(resultSet.getString("lastLogin"));

            return User.builder()
                    .nickName(nick)
                    .firstLogin(firstLogin)
                    .level(level)
                    .gender(gender)
                    .moneyBalance(money)
                    .moneyInBank(moneyInBank)
                    .cashBalance(cashBalance)
                    .golds(golds)
                    .warns(warns)
                    .accountType(accountType)
                    .locationType(locationType)
                    .vipTime(vipTime)
                    .crimes(crimes)
                    .stars(stars)
                    .killedTimes(killedTimes)
                    .diedTimes(diedTimes)
                    .job(job)
                    .organization(organization)
                    .organizationRole(organizationRole)
                    .house(house)
                    .company(company)
                    .farm(farm)
                    .lastLogin(lastLogin)
                    .build();

        } catch (SQLException | ParseException exception) {
            Bukkit.getConsoleSender().sendMessage("Ocorreu um erro em USERADAPTER: " + exception.getMessage());
        }
        return null;
    }

}
