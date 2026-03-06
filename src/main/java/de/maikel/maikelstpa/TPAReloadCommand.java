package de.maikel.maikelstpa;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPAReloadCommand implements CommandExecutor {

    private final MaikelsTPA plugin;

    public TPAReloadCommand(MaikelsTPA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player) && !sender.hasPermission("maikelstpa.tpareload")) {
            sender.sendMessage(plugin.getMessage("no_permission"));
            return true;
        }

        plugin.reloadConfig();
        sender.sendMessage("§aMaikelsTPA Konfiguration wurde neu geladen!");

        return true;
    }
}