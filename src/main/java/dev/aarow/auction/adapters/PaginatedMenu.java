package dev.aarow.auction.adapters;

import dev.aarow.auction.AuctionPlugin;

import dev.aarow.auction.adapters.button.Button;
import dev.aarow.auction.utility.chat.CC;
import dev.aarow.auction.utility.other.Task;
import lombok.Getter;
import lombok.Setter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public abstract class PaginatedMenu {

    public AuctionPlugin plugin = AuctionPlugin.INSTANCE;

    @Setter private boolean autoRefresh = false;

    public abstract String getName();
    public abstract Map<Integer, Button> getButtons();
    public abstract void onClose();
    public abstract Map<Integer, Button> getAlways();

    @Setter @Getter private int page = 0;

    public Player player;

    private Inventory inventory;

    public static int[] paginatedSlots = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

    public PaginatedMenu(Player player){
        this.player = player;
    }

    public void open(int newPage){
        this.page = newPage;

        this.inventory = Bukkit.createInventory(player, 54, CC.translate(getName()));

        Map<Integer, Button> pageItems = getForPage(newPage);

        this.setBorders();

        pageItems.keySet().forEach(index -> inventory.addItem(pageItems.get(index).getItem(player)));

        getAlways().keySet().forEach(index -> {
            int slot = index + 44;

            inventory.setItem(slot, getAlways().get(index).getItem(player));
        });

        Task.newThread(() -> {
            player.closeInventory();
            player.openInventory(inventory);
            Task.newThread(() -> plugin.getMenuManager().getPaginatedMenuCache().put(player.getUniqueId(), this));
        });

        if(autoRefresh) {
            new BukkitRunnable(){
                public void run(){
                    if(!plugin.getMenuManager().getPaginatedMenuCache().containsKey(player.getUniqueId())){
                        this.cancel();
                        return;
                    }
                    if(!plugin.getMenuManager().getPaginatedMenuCache().get(player.getUniqueId()).getName().equals(getName())){
                        this.cancel();
                        return;
                    }

                    refresh();
                }
            }.runTaskTimer(plugin, 5L, 5L);
        }
    }

    public void refresh(){
        Task.newThread(() -> {
            inventory.clear();

            this.setBorders();

            Map<Integer, Button> pageItems = getForPage(getPage());

            if(pageItems.isEmpty()){
                if(page > 0) this.open(page-1);
            }

            pageItems.keySet().forEach(index -> {
                inventory.addItem(pageItems.get(index).getItem(player));
            });

            getAlways().keySet().forEach(index -> {
                int slot = index + 44;

                inventory.setItem(slot, getAlways().get(index).getItem(player));
            });

            player.updateInventory();
        });

    }

    public Map<Integer, Button> getForPage(int page){
        Map<Integer, Button> forPage = new HashMap<>();
        Map<Integer, Button> buttons = getButtons();

        int start = (page * paginatedSlots.length) + 1;
        int end = (page * paginatedSlots.length) + paginatedSlots.length;
        for(int i = start; i <= end; i++){
            if(!buttons.containsKey(i)) break;

            forPage.put(i, buttons.get(i));
        }

        return forPage;
    }

    private void setBorders(){
        for(int x = 0; x < 54; x++){
            if(x < 9 || x > 44){
                inventory.setItem(x, Button.PLACEHOLDER.getItem(player));
                continue;
            }
            if(x % 9 == 0){
                inventory.setItem(x, Button.PLACEHOLDER.getItem(player));
                inventory.setItem(x-1, Button.PLACEHOLDER.getItem(player));
                continue;
            }
        }
        inventory.setItem(44, Button.PLACEHOLDER.getItem(player));
    }
}
