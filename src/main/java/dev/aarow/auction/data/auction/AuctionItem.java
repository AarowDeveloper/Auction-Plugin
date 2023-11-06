package dev.aarow.auction.data.auction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class AuctionItem {

    private UUID uuid;
    private ItemStack itemStack;
    private int price;
    private long expires;

    public long getBeforeExpiry(){
        return expires - System.currentTimeMillis();
    }

    public boolean didExpire(){
        return getBeforeExpiry() < 0;
    }
}
