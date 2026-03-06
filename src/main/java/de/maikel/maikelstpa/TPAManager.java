package de.maikel.maikelstpa;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TPAManager {

    private final Map<UUID, UUID> requests = new HashMap<>();

    public void sendRequest(Player sender, Player target) {
        requests.put(target.getUniqueId(), sender.getUniqueId());
    }

    public UUID getRequest(Player target) {
        return requests.get(target.getUniqueId());
    }

    public void removeRequest(Player target) {
        requests.remove(target.getUniqueId());
    }

    public boolean hasRequest(Player target) {
        return requests.containsKey(target.getUniqueId());
    }
}