package dev.aarow.auction.listeners.impl;

import dev.aarow.auction.data.auction.AuctionItem;
import dev.aarow.auction.listeners.ListenerAdapter;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListeners extends ListenerAdapter {

    @EventHandler
    public void onLogin(PlayerLoginEvent event){
        plugin.getProfileManager().create(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
    }
}
