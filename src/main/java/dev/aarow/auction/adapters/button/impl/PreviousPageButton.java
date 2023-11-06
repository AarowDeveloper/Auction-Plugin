package dev.aarow.auction.adapters.button.impl;

import dev.aarow.auction.adapters.PaginatedMenu;
import dev.aarow.auction.adapters.button.Button;
import dev.aarow.auction.utility.other.ItemBuilder;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class PreviousPageButton extends Button {

    private PaginatedMenu paginatedMenu;

    @Override
    public ItemStack getItem(Player player) {
        ItemBuilder item = new ItemBuilder(Material.PAPER);
        item.setName("&e&lPrevious Page");

        return item.toItemStack();
    }

    @Override
    public void onClick(Player player) {
        if(paginatedMenu.getForPage(paginatedMenu.getPage()-1).isEmpty()) return;

        paginatedMenu.open(paginatedMenu.getPage()-1);
    }

    @Override
    public void onClickType(ClickType clickType) {

    }
}
