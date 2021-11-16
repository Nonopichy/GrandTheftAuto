package me.matt.grandtheftauto.log;

import me.matt.grandtheftauto.util.DateUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class LogManager {

    private final ConsoleCommandSender consoleCommandSender = Bukkit.getConsoleSender();

    public void log(String message) {
        consoleCommandSender.sendMessage("[LOG] " + DateUtil.getTimeStamp() + message);
    }

    public void warn(String message) {
        log("[WARN] " + DateUtil.getTimeStamp() + message);
    }

}
