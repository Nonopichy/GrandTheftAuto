package me.matt.grandtheftauto.database.table.impl;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.database.table.TableService;
import me.matt.grandtheftauto.organizations.enums.OrganizationType;
import me.matt.grandtheftauto.organizations.model.Organization;
import me.matt.grandtheftauto.util.DateUtil;
import me.matt.grandtheftauto.util.parser.LocationParser;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// optimize
// use organizationadapter
// use log
public class Organizations implements TableService<Organization, String> {

    private final GrandTheftAuto plugin;

    public Organizations(GrandTheftAuto plugin) {
        this.plugin = plugin;
    }

    @Override
    public void createTable() {
        try (val conn = plugin.getDatabaseManager().getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("CREATE TABLE IF NOT EXISTS gta_organizations (" +
                    "id SHORT NOT NULL," +
                    "name VARCHAR(32) NOT NULL," +
                    "displayName VARCHAR(32) NOT NULL," +
                    "organizationType VARCHAR(12) NOT NULL," +
                    "color VARCHAR(7) NOT NULL," +
                    "hqLocation VARCHAR(120) NOT NULL," + // TODO: 19/09/2021 verify length of a location string
                    "members VARCHAR(400)," + // 17 * limitMembers;
                    "isVip BOOLEAN NOT NULL)")) {
                st.executeUpdate();
            }
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage("§e" + DateUtil.getTimeStamp() + "Ocorreu um erro na tabela ORGANIZATIONS: §r" + exception.getMessage());
        }
    }

    @Override
    public boolean has(Organization organization) {
        try (val conn = plugin.getDatabaseManager().getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id FROM gta_organizations WHERE id = ?")) {
                st.setShort(1, organization.getId());

                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) return true;
                }
            }
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage("§e" + DateUtil.getTimeStamp() + "Ocorreu um erro na tabela ORGANIZATIONS: §r" + exception.getMessage());
        }
        return false;
    }

    @Override
    public boolean hasBy(String organizationName) {
        try (val conn = plugin.getDatabaseManager().getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT name FROM gta_organizations WHERE name = ?")) {
                st.setString(1, organizationName.toLowerCase());

                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) return true;
                }
            }
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage("§e" + DateUtil.getTimeStamp() + "Ocorreu um erro na tabela ORGANIZATIONS: §r" + exception.getMessage());
        }
        return false;
    }

    public boolean hasByLeader(String leader) {
        try (val conn = plugin.getDatabaseManager().getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT members, id FROM gta_organizations")) {
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        val id = rs.getShort("id");
                        val organization = getById(id);

                        if (organization.getLeader().equals(leader)) return true;
                    }
                }
            }
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage("§e" + DateUtil.getTimeStamp() + "Ocorreu um erro na tabela ORGANIZATIONS: §r" + exception.getMessage()); // use log
        }

        return false;
    }


    @Override
    public boolean hasById(short id) {
        try (val conn = plugin.getDatabaseManager().getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id FROM gta_organizations WHERE id = ?")) {
                st.setShort(1, id);

                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) return true;
                }
            }
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage("§e" + DateUtil.getTimeStamp() + "Ocorreu um erro na tabela ORGANIZATIONS: §r" + exception.getMessage());
        }
        return false;
    }

    @Override
    public void add(Organization organization) {
        if (has(organization)) update(organization); // TODO: 22/09/2021 check
        else insert(organization);
    }

    @Override
    public void insert(Organization organization) {
        try (val conn = plugin.getDatabaseManager().getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO gta_organizations (id, name, displayName, organizationType, color, hqLocation, leader, subLeader, members, isVip) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                st.setShort(1, organization.getId());
                st.setString(2, organization.getName());
                st.setString(3, organization.getDisplayName());
                st.setString(4, organization.getOrganizationType().toString());
                st.setString(5, organization.getColor());
                st.setString(6, LocationParser.locToString(organization.getHqLocation()));
                st.setString(7, null); // TODO: 21/09/2021 this
                st.setString(8, null); // TODO: 21/09/2021 this
                st.setString(9, null); // TODO: 21/09/2021 this
                st.setBoolean(10, organization.isVip());

                st.executeUpdate();
                Bukkit.getConsoleSender().sendMessage("§e" + DateUtil.getTimeStamp() + "Organização " + organization.getDisplayName() + " criada com sucesso.");
            }
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage("§e" + DateUtil.getTimeStamp() + "Ocorreu um erro na tabela ORGANIZATIONS: §r" + exception.getMessage());
        }
    }

    @Override
    public void update(Organization organization) {
        try (val conn = plugin.getDatabaseManager().getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("UPDATE gta_organizations SET id = ?, name = ?, displayName = ?, organizationType = ?, color = ?, hqLocation = ?, leader = ?, subLeader = ?, members = ?, isVip = ?")) {
                st.setShort(1, organization.getId());
                st.setString(2, organization.getName());
                st.setString(3, organization.getDisplayName());
                st.setString(4, organization.getOrganizationType().toString());
                st.setString(5, organization.getColor());
                st.setString(6, LocationParser.locToString(organization.getHqLocation()));
                st.setString(7, organization.getLeader()); // TODO: 22/09/2021
                st.setString(8, organization.getSubLeader()); // TODO: 22/09/2021
                st.setString(9, Organization.toCustomString(organization.getMembers())); // TODO: 22/09/2021 verify
                st.setBoolean(10, organization.isVip());

                st.executeUpdate();
            }
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage("§e" + DateUtil.getTimeStamp() + "Ocorreu um erro na tabela ORGANIZATIONS: §r" + exception.getMessage());
        }
    }

    @Override
    public void delete(String organizationName) {
        // TODO: 13/09/2021 to lowercase 
    }

    @Override
    public Organization get(String organizationName) {
        try (val conn = plugin.getDatabaseManager().getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM gta_organizations WHERE name = ?")) {
                st.setString(1, organizationName);

                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        val id = rs.getShort("id");

                        return getById(id);
                    }
                }
            }
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage("§e" + DateUtil.getTimeStamp() + "Ocorreu um erro na tabela ORGANIZATIONS: §r" + exception.getMessage());
        }
        return null;
    }

    @Override
    public Organization getById(short id) {
        try (val conn = plugin.getDatabaseManager().getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM gta_organizations WHERE id = ?")) {
                st.setShort(1, id);

                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        val name = rs.getString("name");
                        val displayName = rs.getString("displayName");
                        val organizationType = OrganizationType.valueOf(rs.getString("organizationType"));
                        val color = rs.getString("color");
                        val hqLocation = LocationParser.stringToLoc(rs.getString("hqLocation"));
                        val leader = rs.getString("leader");
                        val subLeader = rs.getString("subLeader");
                        val members = Organization.toMap(rs.getString("members"));
                        val isVip = rs.getBoolean("isVip");

                        return new Organization(id, organizationType, name, displayName, color, hqLocation, members, isVip);
                    }
                }

            }
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage("§e" + DateUtil.getTimeStamp() + "Ocorreu um erro na tabela ORGANIZATIONS: §r" + exception.getMessage());
        }
        return null;
    }

    public Organization getByMember(String member) {
        try (val conn = plugin.getDatabaseManager().getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM gta_organizations")) {
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        val id = rs.getShort("id");
                        val organization = getById(id);

                        if (organization.hasMember(member)) return organization;
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public Organization getByLeader(String leader) {
        try (val conn = plugin.getDatabaseManager().getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id, members FROM gta_organizations")) {

                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        val id = rs.getShort("id");
                        val organization = getById(id);

                        if (organization.getLeader().equals(leader)) return organization;
                    }
                }
            }
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage("§e" + DateUtil.getTimeStamp() + "Ocorreu um erro na tabela ORGANIZATIONS: §r" + exception.getMessage());
        }

        return null;
    }

    @Override
    public int getSize() {
        int i = 0;
        try (val conn = plugin.getDatabaseManager().getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id FROM gta_organizations")) {
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) i++;
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return i;
    }

    @Override
    public List<Organization> getAll() {
        List<Organization> list = new ArrayList<>();
        try (val conn = plugin.getDatabaseManager().getDataSource().getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM gta_organizations")) {
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        val id = rs.getShort("id");

                        list.add(getById(id));
                    }
                }
            }
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage("§e" + DateUtil.getTimeStamp() + "Ocorreu um erro na tabela ORGANIZATIONS: §r" + exception.getMessage());
        }

        return list;
    }

}
