package dev.aarow.auction.utility.chat;

import org.bukkit.ChatColor;

import java.util.List;

public class CC {

    public static String translate(String input){
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static List<String> translate(List<String> input){
        return input.stream().map(CC::translate).toList();
    }
}
