package de.maikel.maikelstpa;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class MaikelsTPA extends JavaPlugin {

    private static MaikelsTPA instance;
    private TPAManager tpaManager;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        tpaManager = new TPAManager();

        getCommand("tpa").setExecutor(new TPACommand(this, tpaManager));
        getCommand("tpaaccept").setExecutor(new TPAAcceptCommand(this, tpaManager));
        getCommand("tpadeny").setExecutor(new TPADenyCommand(this, tpaManager));
        getCommand("tpareload").setExecutor(new TPAReloadCommand(this));

        getLogger().info("MaikelsTPA erfolgreich aktiviert.");
    }

    @Override
    public void onDisable() {
        getLogger().info("MaikelsTPA deaktiviert.");
    }

    public static MaikelsTPA getInstance() {
        return instance;
    }

    public String getMessage(String path) {
        FileConfiguration config = getConfig();
        return config.getString("messages." + path, "§cMessage not found: " + path);
    }
}