package me.matt.grandtheftauto.organizations.model;

import lombok.*;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.organizations.enums.OrganizationRole;
import me.matt.grandtheftauto.organizations.enums.OrganizationType;
import me.matt.grandtheftauto.util.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Organization {

    private short id;
    private OrganizationType organizationType;
    private String name;
    private String displayName;
    private String color;
    private Location hqLocation;
    private Map<String, OrganizationRole> members;
    private boolean isVip;

    // in database ::

    // xMattNice_-3;SOekd-2
    // name - cargo ; name - cargo

    //

    /*
     * Function to register new organization.
     * */
    public Organization(String displayName, OrganizationType organizationType, String color, Location location) {
        this.id = (short) (GrandTheftAuto.getInstance().getDatabaseManager().getOrganizations().getSize() + 1);
        this.organizationType = organizationType;
        this.name = displayName.toLowerCase();
        this.displayName = displayName.replace("_", " ");
        this.color = color;
        this.hqLocation = location;

        members = new HashMap<>();
    }

    public void addMember(String name, OrganizationRole cargo) {
        members.put(name, cargo);
    }

    public void removeMember(String name) {
        members.remove(name);
    }

    public boolean hasMember(String name) {
        return members.containsKey(name);
    }

    public boolean hasSubLeader() {
        for (Map.Entry<String, OrganizationRole> member : members.entrySet()) {
            val value = member.getValue();
            if (value == OrganizationRole.SUB_LEADER) return true;
        }

        return false;
    }

    public boolean isSubLeader(String name) {
        val value = members.get(name);
        return value == OrganizationRole.SUB_LEADER;
    }

    public String getSubLeader() {
        for (Map.Entry<String, OrganizationRole> member : members.entrySet()) {
            val value = member.getValue();
            if (value == OrganizationRole.SUB_LEADER) return member.getKey();
        }

        return null;
    }

    public boolean hasLeader() {
        for (Map.Entry<String, OrganizationRole> member : members.entrySet()) {
            val value = member.getValue();
            if (value == OrganizationRole.LEADER) return true;
        }

        return false;
    }

    public boolean isLeader(String name) {
        val value = members.get(name);
        return value == OrganizationRole.LEADER;
    }

    public String getLeader() {
        for (Map.Entry<String, OrganizationRole> member : members.entrySet()) {
            val value = member.getValue();
            if (value == OrganizationRole.LEADER) return member.getKey();
        }

        return null;
    }

    public int getMembersSize() {
        return members.size();
    }

    public boolean hasSpace() {
        return members.size() < 40;
    }

    //
    //
    //
    //
    //
    //
    //
    //


    public static String toCustomString(Map<String, OrganizationRole> members) {
        if (members.size() < 1) return null;

        int size = members.size();
        val sb = new StringBuilder();


        for (Map.Entry<String, OrganizationRole> member : members.entrySet()) {
            size--;
            sb.append(member.getKey());
            sb.append("-" + member.getValue().toString());

            if (size > 1) sb.append(";");
        }

        return sb.toString();
    }

    public static Map<String, OrganizationRole> toMap(String customString) {
        Map<String, OrganizationRole> map = new HashMap<>();
        val split = customString.split(";");

        for (String string : split) {
            val split2 = string.split("-");

            val name = split2[0];
            val cargo = split2[1];

            map.put(name, OrganizationRole.valueOf(cargo));
        }

        return map;
    }

    @Override
    public String toString() {
        var i = 0;

        for (Map.Entry<String, OrganizationRole> member : members.entrySet()) {
            val player = Bukkit.getPlayer(member.getKey());
            if (player != null) i++;
        }

        return ChatUtils.parseColors("&" + getColor()
                + getId()
                + " | Nome: "
                + getDisplayName()
                + " | Tipo: "
                + getOrganizationType()
                + " | Líder: "
                + ((getLeader() == null) ? "Ninguém" : getLeader()
                + " | Membros Online: " + i));
    }

}
