package dev.aarow.auction.listeners.impl;

import dev.aarow.auction.adapters.Menu;
import dev.aarow.auction.adapters.PaginatedMenu;
import dev.aarow.auction.listeners.ListenerAdapter;
import dev.aarow.auction.utility.other.MenuUtility;
import dev.aarow.auction.utility.other.Task;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Arrays;
import java.util.stream.Stream;

public class MenuListeners extends ListenerAdapter {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();

        if(!plugin.getMenuManager().getMenuCache().containsKey(player.getUniqueId())) return;

        Menu menu = plugin.getMenuManager().getMenuCache().get(player.getUniqueId());

        event.setCancelled(true);

        if(!menu.getButtons().containsKey(event.getSlot())) return;

        menu.getButtons().get(event.getSlot()+1).onClick(player);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();

        if(!plugin.getMenuManager().getMenuCache().containsKey(player.getUniqueId())) return;

        Menu menu = plugin.getMenuManager().getMenuCache().get(player.getUniqueId());

        Task.newThread(() -> {
            menu.onClose();

            plugin.getMenuManager().getMenuCache().remove(player.getUniqueId());
        });
    }

    @EventHandler
    public void onPaginatedClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();

        if(!plugin.getMenuManager().getPaginatedMenuCache().containsKey(player.getUniqueId())) return;

        PaginatedMenu menu = plugin.getMenuManager().getPaginatedMenuCache().get(player.getUniqueId());

        event.setCancelled(true);

        int slot = event.getSlot()+1;

        if(slot > 45){
            if(!menu.getAlways().containsKey(slot - 45)) return;

            if(event.getClick() == ClickType.LEFT){
                menu.getAlways().get(slot - 45).onClick(player);
            }else{
                menu.getAlways().get(slot - 45).onClickType(event.getClick());
            }
            return;
        }

        if(Arrays.stream(PaginatedMenu.paginatedSlots).noneMatch(paginatedSlot -> paginatedSlot == slot)) return;

        int itemIndex = (menu.getPage() * PaginatedMenu.paginatedSlots.length) + MenuUtility.IndexFromPaginatedSlot(slot);

        if(!menu.getButtons().containsKey(itemIndex)) return;

        if(event.getClick() == ClickType.LEFT){
            menu.getButtons().get(itemIndex).onClick(player);
        }else{
            menu.getButtons().get(itemIndex).onClickType(event.getClick());
        }
    }

    @EventHandler
    public void onPaginatedClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();

        if(!plugin.getMenuManager().getPaginatedMenuCache().containsKey(player.getUniqueId())) return;

        PaginatedMenu menu = plugin.getMenuManager().getPaginatedMenuCache().get(player.getUniqueId());

        Task.newThread(() -> {
            menu.onClose();

            plugin.getMenuManager().getPaginatedMenuCache().remove(player.getUniqueId());
        });
    }
}
