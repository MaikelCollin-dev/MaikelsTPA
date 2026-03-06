package de.maikel.maikelstpa;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPACommand implements CommandExecutor {

    private final MaikelsTPA plugin;
    private final TPAManager manager;

    public TPACommand(MaikelsTPA plugin, TPAManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        if (!player.hasPermission("maikelstpa.tpa")) {
            player.sendMessage(plugin.getMessage("no_permission"));
            return true;
        }

        if (args.length != 1) {
            player.sendMessage("§cUsage: /tpa <player>");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null || !target.isOnline()) {
            player.sendMessage(plugin.getMessage("tpa_not_online").replace("%target%", args[0]));
            return true;
        }

        if (target.equals(player)) {
            player.sendMessage(plugin.getMessage("tpa_self"));
            return true;
        }

        manager.sendRequest(player, target);

        player.sendMessage(plugin.getMessage("tpa_sent").replace("%target%", target.getName()));

        sendRequestMessage(player, target);

        return true;
    }

    private void sendRequestMessage(Player sender, Player target) {

        String boxLine = plugin.getMessage("tpa_box_line");

        target.spigot().sendMessage(new TextComponent(boxLine));

        target.spigot().sendMessage(
                new TextComponent(plugin.getMessage("tpa_received")
                        .replace("%sender%", sender.getName()))
        );

        TextComponent accept = new TextComponent(plugin.getMessage("tpa_button_accept"));
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaaccept"));
        accept.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(plugin.getMessage("tpa_hover_accept")).create()
        ));

        TextComponent deny = new TextComponent(" " + plugin.getMessage("tpa_button_deny"));
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpadeny"));
        deny.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(plugin.getMessage("tpa_hover_deny")).create()
        ));

        TextComponent buttons = new TextComponent();
        buttons.addExtra(accept);
        buttons.addExtra(deny);

        target.spigot().sendMessage(buttons);

        target.spigot().sendMessage(new TextComponent(boxLine));
    }
}