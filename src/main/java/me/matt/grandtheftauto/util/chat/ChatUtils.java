package me.matt.grandtheftauto.util.chat;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class ChatUtils {

    private final Pattern HEX_COLOR_PATTERN = Pattern.compile("&#([0-9A-Fa-f]{6})");

    public String parseColors(String text) {
        Matcher matcher = HEX_COLOR_PATTERN.matcher(text);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());
        }

        text = matcher.appendTail(buffer).toString();

        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
