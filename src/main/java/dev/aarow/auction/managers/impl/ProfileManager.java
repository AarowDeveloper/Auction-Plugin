package dev.aarow.auction.managers.impl;

import dev.aarow.auction.data.player.Profile;
import dev.aarow.auction.managers.Manager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileManager extends Manager {

    private Map<UUID, Profile> profiles = new HashMap<>();

    @Override
    public void setup() {}

    public void create(UUID uuid){
        this.profiles.putIfAbsent(uuid, new Profile(uuid));
    }

    public Profile get(Player player){
        return this.profiles.get(player.getUniqueId());
    }

    public Profile get(UUID uuid){
        this.create(uuid);

        return this.profiles.get(uuid);
    }
}
