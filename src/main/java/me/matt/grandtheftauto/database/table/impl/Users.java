package me.matt.grandtheftauto.database.table.impl;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.adapter.impl.UserAdapter;
import me.matt.grandtheftauto.database.manager.impl.DatabaseManager;
import me.matt.grandtheftauto.database.table.TableService;
import me.matt.grandtheftauto.users.model.User;
import me.matt.grandtheftauto.util.DateUtil;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

// use more adapter
// use more final
// use log
public class Users implements TableService<User, String> {

    private final DatabaseManager databaseManager;
    private final UserAdapter userAdapter;

    public Users(GrandTheftAuto plugin) {
        this.databaseManager = plugin.getDatabaseManager();
        this.userAdapter = plugin.getAdapterManager().getUserAdapter();
    }

    @Override
    public void createTable() {
        try (val conn = databaseManager.getDataSource().getConnection()) {
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
        try (val conn = databaseManager.getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT nickName FROM gta_users WHERE nickName = ?")) {
                st.setString(1, user.getNickName());

                // TODO: 22/09/2021 verify if possible to switch to hasBy function 
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
        try (val conn = databaseManager.getDataSource().getConnection()) {
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
        if (has(user)) throw new IllegalStateException("Ocorreu um erro na tabela USERS."); // TODO: 22/09/2021 impossible 
        else insert(user);
    }

    @Override
    public void insert(User user) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try (val conn = databaseManager.getDataSource().getConnection()) {
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
                st.setString(19, user.getOrganizationRole() == null ? null : user.getOrganizationRole().toString());
                st.setInt(20, user.getHouse());
                st.setInt(21, user.getCompany());
                st.setInt(22, user.getFarm());
                st.setString(23, sdf.format(user.getLastLogin()));

                st.executeUpdate();
            }
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage("§e" + DateUtil.getTimeStamp() + "Ocorreu um erro na tabela USERS: §r" + exception.getMessage());
        }
    }

    @Override
    public void update(User user) {
        try (val conn = databaseManager.getDataSource().getConnection()) {
            // TODO: 20/09/2021 aaaaaaaaaaaaaaaaaaaaaaaaaaa 
            // TODO: 22/09/2021 do this 
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
        try (val conn = databaseManager.getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM gta_users WHERE nickName = ?")) {
                st.setString(1, nickName);

                try (ResultSet rs = st.executeQuery()) {
                    return userAdapter.adapt(rs);
                }
            }
        } catch (SQLException exception) {
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

        try (val conn = databaseManager.getDataSource().getConnection()) {
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
        List<User> users = new ArrayList<>();
        try (val conn = databaseManager.getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM gta_users")) {

                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        val nickName = rs.getString("nickName");

                        users.add(get(nickName));
                    }
                }
            }
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage("§e" + DateUtil.getTimeStamp() + "Ocorreu um erro na tabela USERS: §r" + exception.getMessage());
        }

        return users;
    }

}
