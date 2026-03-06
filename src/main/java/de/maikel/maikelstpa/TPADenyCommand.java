package de.maikel.maikelstpa;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TPADenyCommand implements CommandExecutor {

    private final MaikelsTPA plugin;
    private final TPAManager manager;

    public TPADenyCommand(MaikelsTPA plugin, TPAManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player target)) {
            return true;
        }

        if (!target.hasPermission("maikelstpa.tpadeny")) {
            target.sendMessage(plugin.getMessage("no_permission"));
            return true;
        }

        if (!manager.hasRequest(target)) {
            target.sendMessage(plugin.getMessage("tpa_no_request"));
            return true;
        }

        UUID senderUUID = manager.getRequest(target);
        Player requester = Bukkit.getPlayer(senderUUID);

        if (requester != null && requester.isOnline()) {

            requester.sendMessage(plugin.getMessage("tpa_denied_sender")
                    .replace("%target%", target.getName()));
        }

        target.sendMessage(plugin.getMessage("tpa_denied"));

        manager.removeRequest(target);

        return true;
    }
}