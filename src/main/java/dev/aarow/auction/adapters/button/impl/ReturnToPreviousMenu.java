package dev.aarow.auction.adapters.button.impl;

import dev.aarow.auction.AuctionPlugin;
import dev.aarow.auction.adapters.PaginatedMenu;
import dev.aarow.auction.adapters.button.Button;
import dev.aarow.auction.data.auction.AuctionItem;
import dev.aarow.auction.utility.other.ItemBuilder;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class ReturnToPreviousMenu extends Button {

    private PaginatedMenu paginatedMenu;

    @Override
    public ItemStack getItem(Player player) {
        ItemBuilder item = new ItemBuilder(Material.BARRIER);
        item.setName("&c&lReturn To The Previous Menu");

        return item.toItemStack();
    }

    @Override
    public void onClick(Player player) {
        paginatedMenu.open(0);
    }

    @Override
    public void onClickType(ClickType clickType) {

    }
}
