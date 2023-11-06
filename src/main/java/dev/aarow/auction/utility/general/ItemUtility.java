package dev.aarow.auction.utility.general;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemUtility {

    public static boolean isValid(ItemStack itemStack){
        if(itemStack == null) return false;
        if(itemStack.getType() == Material.AIR) return false;
        return true;
    }


}
