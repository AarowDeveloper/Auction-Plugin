package dev.aarow.auction.menus.submenus;

import dev.aarow.auction.adapters.PaginatedMenu;
import dev.aarow.auction.adapters.button.Button;
import dev.aarow.auction.adapters.button.impl.CloseMenuButton;
import dev.aarow.auction.adapters.button.impl.NextPageButton;
import dev.aarow.auction.adapters.button.impl.PreviousPageButton;
import dev.aarow.auction.adapters.button.impl.ReturnToPreviousMenu;
import dev.aarow.auction.data.auction.AuctionItem;
import dev.aarow.auction.data.player.Profile;
import dev.aarow.auction.menus.AuctionMainMenu;
import dev.aarow.auction.utility.general.PlayerUtility;
import dev.aarow.auction.utility.general.StringUtility;
import dev.aarow.auction.utility.other.ItemBuilder;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class AuctionListedMenu extends PaginatedMenu {

    {
        setAutoRefresh(true);
    }

    public AuctionListedMenu(Player player) {
        super(player);
    }

    @Override
    public String getName() {
        return "&e&lYour Listings &7&l- Page#" + (getPage()+1);
    }

    @Override
    public Map<Integer, Button> getButtons() {
        Map<Integer, Button> buttons = new HashMap<>();

        Profile profile = plugin.getProfileManager().get(player);

        int currentItem = 1;
        for(AuctionItem auctionItem : profile.getCurrentlyListed()){
            buttons.put(currentItem, new ListedButton(false, auctionItem));
            currentItem++;
        }

        for(AuctionItem soldAuctionItem : profile.getSold().keySet()){
            buttons.put(currentItem, new ListedButton(true, soldAuctionItem));
            currentItem++;
        }

        return buttons;
    }

    @Override
    public Map<Integer, Button> getAlways() {
        Map<Integer, Button> buttons = new HashMap<>();
        buttons.put(2, new PreviousPageButton(this));
        buttons.put(5, new ReturnToPreviousMenu(new AuctionMainMenu(player)));
        buttons.put(8, new NextPageButton(this));

        return buttons;
    }

    @Override
    public void onClose() {

    }

    @AllArgsConstructor
    private class ListedButton extends Button {

        private boolean sold;
        private AuctionItem auctionItem;

        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder itemBuilder = new ItemBuilder(auctionItem.getItemStack().clone());

            Profile profile = plugin.getProfileManager().get(player);

            if(sold) {
                itemBuilder.addLoreLine("&7• &fSold For&7: &a" + StringUtility.formatNumber(auctionItem.getPrice()) + "$");
                itemBuilder.addLoreLine("&7• &fSold To&7: &e" + Bukkit.getOfflinePlayer(profile.getSold().get(auctionItem)).getName());
                itemBuilder.addLoreLine("");
                itemBuilder.addLoreLine("&a&oClick here to claim your money.");
            }else if(auctionItem.didExpire()){
                itemBuilder.addLoreLine("&7• &fListed For&7: &e" + StringUtility.formatNumber(auctionItem.getPrice()) + "$");
                itemBuilder.addLoreLine("&7• &fExpires in&7: &cItem has expired.");
                itemBuilder.addLoreLine("");
                itemBuilder.addLoreLine("&b&oClick here to collect your expired item.");
            }else{
                itemBuilder.addLoreLine("&7• &fListed For&7: &e" + StringUtility.formatNumber(auctionItem.getPrice()) + "$");
                itemBuilder.addLoreLine("&7• &fExpires in&7: &c" + StringUtility.getTimeString(auctionItem.getBeforeExpiry()));
                itemBuilder.addLoreLine("");
                itemBuilder.addLoreLine("&c&oClick here to un-list this item.");
            }

            return itemBuilder.toItemStack();
        }

        @Override
        public void onClick(Player player) {
            Profile profile = plugin.getProfileManager().get(player);

            if(sold){
                profile.getSold().remove(auctionItem);
                plugin.getHookManager().getEconomy().depositPlayer(player, auctionItem.getPrice());

                PlayerUtility.sendSuccess(player, "&aYou successfully collected the money from your sold item.");
                return;
            }
            plugin.getAuctionManager().getAuctionItems().remove(auctionItem);
            PlayerUtility.giveItem(player, auctionItem.getItemStack());
            if(auctionItem.didExpire()){
                PlayerUtility.sendSuccess(player, "&aYou successfully collected your expired item.");
            }else{
                PlayerUtility.sendSuccess(player, "&aYou successfully un-listed the item from the auction.");
            }
        }

        @Override
        public void onClickType(ClickType clickType) {

        }
    }
}
