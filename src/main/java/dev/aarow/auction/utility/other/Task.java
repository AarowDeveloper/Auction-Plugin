package dev.aarow.auction.utility.other;

import dev.aarow.auction.AuctionPlugin;
import org.bukkit.Bukkit;

public class Task {
    public static void newThread(Caller caller) {
        Bukkit.getScheduler().runTaskLater(AuctionPlugin.INSTANCE, caller::call, 1L);
    }

    public interface Caller {
        void call();
    }
}
