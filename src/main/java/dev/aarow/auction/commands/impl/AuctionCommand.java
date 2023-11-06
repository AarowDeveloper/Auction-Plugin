package dev.aarow.auction.commands.impl;

import dev.aarow.auction.commands.BaseCommand;
import dev.aarow.auction.commands.CommandInfo;
import dev.aarow.auction.data.auction.AuctionItem;
import dev.aarow.auction.menus.AuctionMainMenu;
import dev.aarow.auction.utility.chat.CC;
import dev.aarow.auction.utility.general.ItemUtility;
import dev.aarow.auction.utility.general.NumberUtility;
import dev.aarow.auction.utility.general.PlayerUtility;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.TimeUnit;

@CommandInfo(name = "auction", playerOnly = true)
public class AuctionCommand extends BaseCommand {

    @Override
    public void execute(Player player, String[] args) {
        if(args.length != 2 && args.length != 0){
            this.sendUsage(player);
            return;
        }
        if(args.length == 0) {
            new AuctionMainMenu(player).open(0);
            return;
        }
        if(args[0].equalsIgnoreCase("sell") & NumberUtility.isInteger(args[1])){
            ItemStack hand = player.getItemInHand();
            if(!ItemUtility.isValid(hand)) {
                PlayerUtility.sendError(player, "You need to hold a valid item in your hand!");
                return;
            }

            AuctionItem auctionItem = new AuctionItem(player.getUniqueId(), hand, Integer.parseInt(args[1]), System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
            plugin.getAuctionManager().getAuctionItems().add(auctionItem);

            PlayerUtility.removeItemInHand(player);

            PlayerUtility.sendSuccess(player, "Your item has been successfully listed on the auction!");
            return;
        }

        this.sendUsage(player);
    }

    public void sendUsage(Player player){
        player.sendMessage(CC.translate("&7[&7&m---------&7] &3&lAuction Help &7[&7&m---------&7]"));
        player.sendMessage(CC.translate("&7• &e/auction &7- &fOpens up the auction menu."));
        player.sendMessage(CC.translate("&7• &e/auction sell <price> &7- &fHold the item in hand and sell it."));
        player.sendMessage(CC.translate("&7[&7&m---------&7] &3&lAuction Help &7[&7&m---------&7]"));

    }
}
