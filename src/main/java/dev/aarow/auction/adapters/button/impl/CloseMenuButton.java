package dev.aarow.auction.adapters.button.impl;

import dev.aarow.auction.adapters.PaginatedMenu;
import dev.aarow.auction.adapters.button.Button;
import dev.aarow.auction.utility.other.ItemBuilder;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class CloseMenuButton extends Button {

    @Override
    public ItemStack getItem(Player player) {
        ItemBuilder item = new ItemBuilder(Material.BARRIER);
        item.setName("&c&lClose Menu");

        return item.toItemStack();
    }

    @Override
    public void onClick(Player player) {
        player.closeInventory();
    }

    @Override
    public void onClickType(ClickType clickType) {

    }
}
