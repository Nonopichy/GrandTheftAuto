package me.matt.grandtheftauto.organizations.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.organizations.enums.OrganizationType;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Data
public class Organization {

    private short id;
    private OrganizationType organizationType;
    private String name;
    private String displayName;
    private String color;
    private Location hqLocation;
    private String leader;
    private String subLeader;
    private List<String> members;
    private boolean isVip;

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
    }

    public void addMember(String name) {
        members.add(name);
    }

    public void removeMember(String name) {
        members.remove(name);
    }

    /*
    * Function to compact a list in a string with ;
    * @param names = List of names to compact in string
    * @returns = compacted string
    * */
    public static String listToString(List<String> names) {
        if (names == null) return null;
        if (names.isEmpty()) return null;
        int size = names.size();
        StringBuilder sb = new StringBuilder();

        for (String name : names) {
            size--;
            sb.append(name);
            if (size > 1) sb.append(";");
        }

        return sb.toString();
    }

    /*
    * Reverse function listToString
    * @param names = compact string with names
    * @returns = a list of string with names.
    * */
    public static List<String> stringToList(String names) {
        if (names == null) return null;
        val parts = names.split(";");

        return new ArrayList<>(Arrays.asList(parts));
    }

    @Override
    public String toString() {
        return "ID: " + getId() + " | Nome: " + getDisplayName() + " | Tipo: " + organizationType + " | Líder: " + ((getLeader() == null) ? "Ninguém" : getLeader());
    }

}
