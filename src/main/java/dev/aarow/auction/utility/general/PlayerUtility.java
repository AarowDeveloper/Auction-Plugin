package dev.aarow.auction.utility.general;

import dev.aarow.auction.AuctionPlugin;
import dev.aarow.auction.utility.chat.CC;
import dev.aarow.auction.utility.other.Task;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUtility {

    public static void sendError(Player player, String message){
        player.sendMessage(CC.translate("&7[&cError&7] &c" + message));
    }

    public static void sendSuccess(Player player, String message){
        player.sendMessage(CC.translate("&7[&aSuccess&7] &a" + message));
    }

    public static void giveItem(Player player, ItemStack itemStack){
        if(player.getInventory().firstEmpty() == -1){
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
            return;
        }

        player.getInventory().addItem(itemStack);
    }

    public static void removeItemInHand(Player player){
        Task.newThread(() -> player.setItemInHand(new ItemStack(Material.AIR)));
    }

    public static boolean canPlayerAfford(Player player, int price){
        Economy economy = AuctionPlugin.INSTANCE.getHookManager().getEconomy();

        return economy.has(player, price);
    }
}
