package me.matt.grandtheftauto;

import lombok.Getter;
import lombok.val;
import me.matt.grandtheftauto.database.manager.impl.DatabaseManager;
import me.matt.grandtheftauto.messages.MessageManager;
import me.matt.grandtheftauto.organizations.commands.OrganizationsCommand;
import me.matt.grandtheftauto.users.commands.locationtype.LocationTypeCommand;
import me.matt.grandtheftauto.users.commands.rg.RgCommand;
import me.matt.grandtheftauto.users.commands.rg.ShowRgCommand;
import me.matt.grandtheftauto.users.listener.PlayerJoin;
import me.matt.grandtheftauto.users.listener.PlayerLastLogin;
import me.matt.grandtheftauto.users.listener.PlayerSpawn;
import me.matt.grandtheftauto.util.DateUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public final class GrandTheftAuto extends JavaPlugin {

    private static Optional<GrandTheftAuto> instance;

    @Getter private DatabaseManager databaseManager;
    @Getter private MessageManager messageManager;

    private final ConsoleCommandSender consoleCommandSender = Bukkit.getConsoleSender();

    @Override
    public void onEnable() {
        instance = Optional.of(this);
        consoleCommandSender.sendMessage("§e" + DateUtil.getTimeStamp() + "Servidor carregando.");

        loadModules();
        registerCommands();
        registerEvents();
        initDatabase();

        consoleCommandSender.sendMessage("§e" + DateUtil.getTimeStamp() + "Servidor carregado com sucesso.");
    }

    @Override
    public void onDisable() {
        databaseManager.unload();
    }

    private void registerCommands() {
        // organizations
        new OrganizationsCommand(this);

        // user.rg
        new RgCommand(this);
        new ShowRgCommand(this);

        // user.locationtype
        new LocationTypeCommand(this);

        consoleCommandSender.sendMessage("§e" + DateUtil.getTimeStamp() + "Comandos registrados com sucesso.");
    }

    private void registerEvents() {
        // user
        new PlayerJoin(this);
        new PlayerSpawn(this);
        new PlayerLastLogin(this);

        consoleCommandSender.sendMessage("§e" + DateUtil.getTimeStamp() + "Eventos registrados com sucesso.'");
    }

    private void loadModules() {
        consoleCommandSender.sendMessage("§e" + DateUtil.getTimeStamp() + "Carregando modulos...");
        val start = System.currentTimeMillis();

        // config
        saveDefaultConfig();

        // messagemanager
        messageManager = new MessageManager();

        val result = System.currentTimeMillis() - start;
        consoleCommandSender.sendMessage("§e" + DateUtil.getTimeStamp() + "Modulos carregados com sucesso em " + result + "ms.");
    }

    private void initDatabase() {
        val start = System.currentTimeMillis();

        databaseManager = new DatabaseManager(this);
        databaseManager.load();

        val result = System.currentTimeMillis() - start;
        consoleCommandSender.sendMessage("§e" + DateUtil.getTimeStamp() + "Conexao com banco de dados aberta com sucesso em " + result + "ms.");
    }

    public static GrandTheftAuto getInstance() {
        return instance.orElseThrow(() -> new IllegalStateException("GrandTheftAuto instance is null"));
    }

}
