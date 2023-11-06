package dev.aarow.auction.listeners;

import dev.aarow.auction.AuctionPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ListenerAdapter implements Listener{

    public AuctionPlugin plugin = AuctionPlugin.INSTANCE;

    public ListenerAdapter(){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
}
