package me.matt.grandtheftauto.messages;

import me.matt.grandtheftauto.util.Config;
import me.matt.grandtheftauto.util.DateUtil;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MessageManager {

    private Config messages = new Config("messages.yml");

    private Map<String, String> simpleMessages = new HashMap<>();
    private Map<String, List<String>> multiMessages = new HashMap<>();

    public MessageManager() {
        messages.saveDefaultConfig();
        loadSimpleMessages();
        loadMultiMessages();
    }

    public void loadSimpleMessages() {
        simpleMessages.clear();
        // geral
        simpleMessages.put("NoConsole", messages.getString("NoConsole"));
        simpleMessages.put("Offline", messages.getString("Offline"));
        simpleMessages.put("PlayerTooFar", messages.getString("PlayerTooFar"));

        // organizations
        simpleMessages.put("OrganizationsCreateSubCommandWrong", messages.getString("OrganizationsCreateSubCommandWrong"));
        simpleMessages.put("OrganizationExisting", messages.getString("OrganizationExisting"));
        simpleMessages.put("OrganizationTypeWrong", messages.getString("OrganizationTypeWrong"));
        simpleMessages.put("OrganizationWrongColor", messages.getString("OrganizationWrongColor"));
        simpleMessages.put("OrganizationCreated", messages.getString("OrganizationCreated"));
        simpleMessages.put("OrganizationNameLengthMax", messages.getString("OrganizationNameLengthMax"));
        simpleMessages.put("OrganizationNotFound", messages.getString("OrganizationNotFound"));
        simpleMessages.put("OrganizationNotLeader", messages.getString("OrganizationNotLeader"));
        simpleMessages.put("OrganizationMaxMembers", messages.getString("OrganizationMaxMembers"));
        simpleMessages.put("OrganizationSetLeaderSubCommandWrong", messages.getString("OrganizationSetLeaderSubCommandWrong"));
        simpleMessages.put("OrganizationPlayerAlreadyLeader", messages.getString("OrganizationPlayerAlreadyLeader"));
        simpleMessages.put("OrganizationLeaderSetted", messages.getString("OrganizationLeaderSetted"));

        // users
        simpleMessages.put("UserNotFound", messages.getString("UserNotFound"));

        simpleMessages.put("ShowRgWrongCommand", messages.getString("ShowRgWrongCommand"));

        // locationType
        simpleMessages.put("LocationTypeWrongCommand", messages.getString("LocationTypeWrongCommand"));
        simpleMessages.put("LocationTypeSuccess", messages.getString("LocationTypeSuccess"));
    }

    public void loadMultiMessages() {
        multiMessages.clear();

        // users
        multiMessages.put("Rg", messages.getStringList("Rg"));
    }

    public String getSimpleMessage(String path) {
        return formatMessage(simpleMessages.get(path));
    }

    public List<String> getMultiMessage(String path) {
        return multiMessages.get(path).stream().map(this::formatMessage).collect(Collectors.toList());
    }

    public String formatMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message.replace("%timestamp%", DateUtil.getTimeStamp()));
    }

}
