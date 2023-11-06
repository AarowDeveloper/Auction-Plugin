package dev.aarow.auction.adapters;

import dev.aarow.auction.AuctionPlugin;
import dev.aarow.auction.adapters.button.Button;
import dev.aarow.auction.utility.chat.CC;
import dev.aarow.auction.utility.other.Task;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;

@Getter
public abstract class Menu {

    public abstract int getSize();
    public abstract String getName();
    public abstract Map<Integer, Button> getButtons();
    public abstract void onClose();

    public AuctionPlugin plugin = AuctionPlugin.INSTANCE;

    public Economy economy = plugin.getHookManager().getEconomy();

    private Player player;

    public void open(Player player){
        this.player = player;

        player.closeInventory();

        Task.newThread(() -> {
            Inventory inventory = Bukkit.createInventory(player, getSize(), CC.translate(getName()));

            getButtons().keySet().forEach(slot -> {
                Button button = getButtons().get(slot);

                inventory.setItem(slot-1, button.getItem(player));
            });

            for(int i = 0; i < getSize(); i++){
                if(getButtons().containsKey(i)) continue;

                inventory.setItem(i, Button.PLACEHOLDER.getItem(player));
            }

            plugin.getMenuManager().getMenuCache().put(player.getUniqueId(), this);

            player.openInventory(inventory);
        });
    }
}
