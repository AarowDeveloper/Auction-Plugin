package dev.aarow.auction.managers;

import dev.aarow.auction.AuctionPlugin;

public abstract class Manager {

    public AuctionPlugin plugin = AuctionPlugin.INSTANCE;

    public abstract void setup();

    public Manager(){
        this.setup();
    }
}
