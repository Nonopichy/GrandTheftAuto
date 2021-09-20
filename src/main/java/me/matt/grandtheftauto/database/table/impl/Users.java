package me.matt.grandtheftauto.database.table.impl;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.database.table.TableService;
import me.matt.grandtheftauto.users.enums.AccountType;
import me.matt.grandtheftauto.users.enums.Gender;
import me.matt.grandtheftauto.users.enums.LocationType;
import me.matt.grandtheftauto.users.model.User;
import me.matt.grandtheftauto.util.DateUtil;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Users implements TableService<User, String> {

    private GrandTheftAuto plugin;

    public Users(GrandTheftAuto plugin) {
        this.plugin = plugin;
    }

    @Override
    public void createTable() {
        try (val conn = plugin.getDatabaseManager().getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("CREATE TABLE IF NOT EXISTS gta_users (" +
                    "nickName VARCHAR(16) NOT NULL," +
                    "firstLogin VARCHAR(20) NOT NULL," +
                    "level INT NOT NULL," +
                    "gender VARCHAR(6) NOT NULL," +
                    "money DOUBLE NOT NULL," +
                    "moneyInBank DOUBLE NOT NULL," +
                    "cashBalance DOUBLE NOT NULL," +
                    "golds INT," +
                    "warns INT," +
                    "accountType VARCHAR(7) NOT NULL," +
                    "locationType VARCHAR(5) NOT NULL," +
                    "vipTime LONG," +
                    "crimes INT," +
                    "stars INT," +
                    "killedTimes INT," +
                    "diedTimes INT," +
                    "job VARCHAR(32)," +
                    "organization VARCHAR(32)," +
                    "house INT," +
                    "company INT," +
                    "farm INT," +
                    "lastLogin VARCHAR(20))")) {
                st.executeUpdate();
            }
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage("§e" + DateUtil.getTimeStamp() + "Ocorreu um erro na tabela USERS: §r" + exception.getMessage());
        }
    }

    @Override
    public boolean has(User user) {
        try (val conn = plugin.getDatabaseManager().getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT nickName FROM gta_users WHERE nickName = ?")) {
                st.setString(1, user.getNickName());

                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) return true;
                }
            }
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage("§e" + DateUtil.getTimeStamp() + "Ocorreu um erro na tabela USERS: §r" + exception.getMessage());
        }
        return false;
    }

    @Override
    public boolean hasBy(String nickName) {
        try (val conn = plugin.getDatabaseManager().getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT nickName FROM gta_users WHERE nickName = ?")) {
                st.setString(1, nickName);

                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) return true;
                }
            }
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage("§e" + DateUtil.getTimeStamp() + "Ocorreu um erro na tabela USERS: §r" + exception.getMessage());
        }
        return false;
    }

    @Override
    public boolean hasById(short id) {
        // Not used.
        return false;
    }

    @Override
    public void add(User user) {
        if (has(user)) {
            update(user);
        } else {
            insert(user);
        }
    }

    @Override
    public void insert(User user) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try (val conn = plugin.getDatabaseManager().getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO gta_users (nickName, firstLogin, level, gender, money, moneyInBank, cashBalance, golds, warns, accountType, locationType, vipTime, crimes, stars, killedTimes, diedTimes, job, organization, house, company, farm, lastLogin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ")) {
                st.setString(1, user.getNickName());
                st.setString(2, sdf.format(user.getFirstLogin()));
                st.setInt(3, user.getLevel());
                st.setString(4, user.getGender().toString());
                st.setDouble(5, user.getMoneyBalance());
                st.setDouble(6, user.getMoneyInBank());
                st.setDouble(7, user.getCashBalance());
                st.setInt(8, user.getGolds());
                st.setInt(9, user.getWarns());
                st.setString(10, user.getAccountType().toString());
                st.setString(11, user.getLocationType().toString());
                st.setLong(12, user.getVipTime());
                st.setInt(13, user.getCrimes());
                st.setInt(14, user.getStars());
                st.setInt(15, user.getKilledTimes());
                st.setInt(16, user.getDiedTimes());
                st.setString(17, user.getJob() == null ? null : user.getJob().getName());
                st.setString(18, user.getOrganization() == null ? null : user.getOrganization().getName());
                st.setInt(19, user.getHouse());
                st.setInt(20, user.getCompany());
                st.setInt(21, user.getFarm());
                st.setString(22, sdf.format(user.getLastLogin()));

                st.executeUpdate();
            }
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage("§e" + DateUtil.getTimeStamp() + "Ocorreu um erro na tabela USERS: §r" + exception.getMessage());
        }
    }

    @Override
    public void update(User user) {
        try (val conn = plugin.getDatabaseManager().getDataSource().getConnection()) {
            // TODO: 20/09/2021 aaaaaaaaaaaaaaaaaaaaaaaaaaa 
        } catch (SQLException exception) {

        }
    }

    @Override
    public void delete(String nickName) {
        // Not used
    }

    @Override
    public User get(String nickName) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try (val conn = plugin.getDatabaseManager().getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM gta_users WHERE nickName = ?")) {
                st.setString(1, nickName);

                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        val nick = rs.getString("nickName");
                        val firstLogin = sdf.parse(rs.getString("firstLogin"));
                        val level = rs.getInt("level");
                        val gender = Gender.valueOf(rs.getString("gender"));
                        val money = rs.getDouble("money");
                        val moneyInBank = rs.getDouble("moneyInBank");
                        val cashBalance = rs.getDouble("cashBalance");
                        val golds = rs.getInt("golds");
                        val warns = rs.getInt("warns");
                        val accountType = AccountType.valueOf(rs.getString("accountType"));
                        val locationType = LocationType.valueOf(rs.getString("locationType"));
                        val vipTime = rs.getLong("vipTime");
                        val crimes = rs.getInt("crimes");
                        val stars = rs.getInt("stars");
                        val killedTimes = rs.getInt("killedTimes");
                        val diedTimes = rs.getInt("diedTimes");
                        val job = rs.getString("job");
                        val organization = rs.getString("organization");
                        val house = rs.getInt("house");
                        val company = rs.getInt("company");
                        val farm = rs.getInt("farm");
                        val lastLogin = sdf.parse(rs.getString("lastLogin")); // 167, 188

                        return new User(nick, firstLogin, level, gender, money, moneyInBank, cashBalance, golds, warns, accountType, locationType, vipTime, crimes, stars, killedTimes, diedTimes, plugin.getDatabaseManager().getJobs().get(job), plugin.getDatabaseManager().getOrganizations().get(organization), house, company, farm, lastLogin);
                    }
                }
            }
        } catch (SQLException | ParseException exception) {
            Bukkit.getConsoleSender().sendMessage("§e" + DateUtil.getTimeStamp() + "Ocorreu um erro na tabela USERS: §r" + exception.getMessage());
        }
        return null;
    }

    @Override
    public User getById(short id) {
        // Not used.
        return null;
    }

    @Override
    public int getSize() {
        int count = 0;

        try (val conn = plugin.getDatabaseManager().getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT nickName FROM gta_users")) {
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) count++;
                }
            }
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage("&e" + DateUtil.getTimeStamp() + "Ocorreu um erro na tabela USERS: &r" + exception.getMessage());
        }

        return count;
    }

    @Override
    public List<User> getAll() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        List<User> users = new ArrayList<>();
        try (val conn = plugin.getDatabaseManager().getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM gta_users")) {

                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        val nickName = rs.getString("nickName");
                        val firstLogin = sdf.parse(rs.getString("firstLogin"));
                        val level = rs.getInt("level");
                        val gender = Gender.valueOf(rs.getString("gender"));
                        val money = rs.getDouble("money");
                        val moneyInBank = rs.getDouble("moneyInBank");
                        val cashBalance = rs.getDouble("cashBalance");
                        val golds = rs.getInt("golds");
                        val warns = rs.getInt("warns");
                        val accountType = AccountType.valueOf(rs.getString("accountType"));
                        val locationType = LocationType.valueOf(rs.getString("locationType"));
                        val vipTime = rs.getLong("vipTime");
                        val crimes = rs.getInt("crimes");
                        val stars = rs.getInt("stars");
                        val killedTimes = rs.getInt("killedTimes");
                        val diedTimes = rs.getInt("diedTimes");
                        val job = rs.getString("job");
                        val organization = rs.getString("organization");
                        val house = rs.getInt("house");
                        val company = rs.getInt("company");
                        val farm = rs.getInt("farm");
                        val lastLogin = sdf.parse(rs.getString("lastLogin"));

                        users.add(new User(nickName, firstLogin, level, gender, money, moneyInBank, cashBalance, golds, warns, accountType, locationType, vipTime, crimes, stars, killedTimes, diedTimes, plugin.getDatabaseManager().getJobs().get(job), plugin.getDatabaseManager().getOrganizations().get(organization), house, company, farm, lastLogin));
                    }
                }
            }
        } catch (SQLException | ParseException exception) {
            Bukkit.getConsoleSender().sendMessage("§e" + DateUtil.getTimeStamp() + "Ocorreu um erro na tabela USERS: §r" + exception.getMessage());
        }

        return users;
    }

}
