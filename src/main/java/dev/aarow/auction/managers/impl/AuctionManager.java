package dev.aarow.auction.managers.impl;

import dev.aarow.auction.data.auction.AuctionItem;
import dev.aarow.auction.managers.Manager;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AuctionManager extends Manager {

    private List<AuctionItem> auctionItems = new ArrayList<>();

    @Override
    public void setup() {}
}
