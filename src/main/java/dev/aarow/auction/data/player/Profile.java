package dev.aarow.auction.data.player;

import dev.aarow.auction.AuctionPlugin;
import dev.aarow.auction.data.auction.AuctionItem;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
public class Profile {

    private UUID uuid;

    private Map<AuctionItem, UUID> sold = new HashMap<>();

    public Profile(UUID uuid){
        this.uuid = uuid;
    }

    public List<AuctionItem> getCurrentlyListed(){
        return AuctionPlugin.INSTANCE.getAuctionManager().getAuctionItems().stream().filter(auctionItem -> auctionItem.getUuid().equals(uuid)).toList();
    }
}
