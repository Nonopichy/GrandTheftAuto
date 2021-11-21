package me.matt.grandtheftauto;

import lombok.Getter;
import lombok.val;
import me.matt.grandtheftauto.adapter.manager.AdapterManager;
import me.matt.grandtheftauto.database.manager.impl.DatabaseManager;
import me.matt.grandtheftauto.log.LogManager;
import me.matt.grandtheftauto.messages.MessageManager;
import me.matt.grandtheftauto.organizations.commands.OrganizationsCommand;
import me.matt.grandtheftauto.users.commands.gender.ChangeGenderCommand;
import me.matt.grandtheftauto.users.commands.locationtype.LocationTypeCommand;
import me.matt.grandtheftauto.users.commands.rg.RgCommand;
import me.matt.grandtheftauto.users.commands.rg.ShowRgCommand;
import me.matt.grandtheftauto.users.listener.PlayerJoin;
import me.matt.grandtheftauto.users.listener.PlayerLastLogin;
import me.matt.grandtheftauto.users.listener.PlayerSpawn;
import me.matt.grandtheftauto.util.DateUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public final class GrandTheftAuto extends JavaPlugin {

    private static Optional<GrandTheftAuto> instance; // ANALISE TOTAL (IREI RETIRAR)

    @Getter private DatabaseManager databaseManager;
    @Getter private MessageManager messageManager;

    @Getter private LogManager logManager;

    @Getter private AdapterManager adapterManager;

    @Override
    public void onEnable() {
        instance = Optional.of(this);
        logManager.log("Servidor carregando.");


        loadModules();
        registerCommands();
        registerEvents();
        initDatabase();

        logManager.log("Servidor carregado com sucesso.");
    }

    @Override
    public void onDisable() {
        logManager.log("Desligando o servidor.");
        databaseManager.unload();
        logManager.log("§e" + DateUtil.getTimeStamp() + "Servidor desligado com sucesso.");
    }

    private void registerCommands() {
        // organizations
        new OrganizationsCommand(this);

        // user.rg
        new RgCommand(this);
        new ShowRgCommand(this);

        // user.locationtype
        new LocationTypeCommand(this);

        // user.gender
        new ChangeGenderCommand(this);

        logManager.log("Comandos registrados com sucesso.");
    }

    private void registerEvents() {
        // user
        new PlayerJoin(this);
        new PlayerSpawn(this);
        new PlayerLastLogin(this);

        logManager.log("Eventos registrados com sucesso.'");
    }

    private void loadModules() {
        logManager = new LogManager();
        logManager.log("Carregando modulos.");
        val start = System.currentTimeMillis();

        saveDefaultConfig();
        messageManager = new MessageManager();

        // adapter
        adapterManager = new AdapterManager(this);

        val result = System.currentTimeMillis() - start;
        logManager.log("Modulos carregados com sucesso em " + result + "ms.");
    }

    private void initDatabase() {
        logManager.log("Abrindo conexao com banco de dados.");
        val start = System.currentTimeMillis();

        databaseManager = new DatabaseManager(this);
        databaseManager.load();

        val result = System.currentTimeMillis() - start;
        logManager.log("Conexao com banco de dados aberta com sucesso em " + result + "ms.");
    }

    public static GrandTheftAuto getInstance() {
        return instance.orElseThrow(() -> new IllegalStateException("GrandTheftAuto instance is null"));
    }

}
