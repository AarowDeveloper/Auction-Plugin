package dev.aarow.auction.menus;

import dev.aarow.auction.AuctionPlugin;
import dev.aarow.auction.adapters.PaginatedMenu;
import dev.aarow.auction.adapters.button.Button;
import dev.aarow.auction.adapters.button.impl.CloseMenuButton;
import dev.aarow.auction.adapters.button.impl.NextPageButton;
import dev.aarow.auction.adapters.button.impl.PreviousPageButton;
import dev.aarow.auction.data.auction.AuctionItem;
import dev.aarow.auction.data.player.Profile;
import dev.aarow.auction.menus.submenus.AuctionListedMenu;
import dev.aarow.auction.utility.general.PlayerUtility;
import dev.aarow.auction.utility.general.StringUtility;
import dev.aarow.auction.utility.other.ItemBuilder;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Stream;

public class AuctionMainMenu extends PaginatedMenu {

    {
        setAutoRefresh(true);
    }

    public AuctionMainMenu(Player player) {
        super(player);
    }

    @Override
    public String getName() {
        return "&e&lMain Menu &7&l - Page#" + (getPage()+1);
    }

    private AuctionSort auctionSort = AuctionSort.NO_FILTERS;

    @Override
    public Map<Integer, Button> getButtons() {
        Map<Integer, Button> buttons = new HashMap<>();

        int currentItem = 1;

        List<AuctionItem> auctionItems = new ArrayList<>(plugin.getAuctionManager().getAuctionItems().stream().toList());

        switch(auctionSort){
            case HIGHEST_PRICE:
                auctionItems.sort(Comparator.comparingInt(AuctionItem::getPrice));

                Collections.reverse(auctionItems);
                break;
            case LOWEST_PRICE:
                auctionItems.sort(Comparator.comparingInt(AuctionItem::getPrice));
                break;
            case ENDING_SOON:
                auctionItems.sort(Comparator.comparingLong(AuctionItem::getBeforeExpiry));
                break;
        }

        for(AuctionItem auctionItem : auctionItems){
            if(auctionItem.didExpire()) continue;
            buttons.put(currentItem, new ListedItemButton(auctionItem));
            currentItem++;
        }

        return buttons;
    }

    @Override
    public Map<Integer, Button> getAlways() {
        Map<Integer, Button> buttons = new HashMap<>();
        buttons.put(1, new AuctionSortButton());
        buttons.put(2, new PreviousPageButton(this));
        buttons.put(5, new CloseMenuButton());
        buttons.put(6, new BalanceButton());
        buttons.put(8, new NextPageButton(this));
        buttons.put(9, new AuctionListedButton());

        return buttons;
    }


    @Override
    public void onClose() {

    }


    @AllArgsConstructor
    private class ListedItemButton extends Button {

        private AuctionItem auctionItem;

        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder itemBuilder = new ItemBuilder(auctionItem.getItemStack().clone());
            itemBuilder.setName(Objects.requireNonNull(auctionItem.getItemStack().getItemMeta()).getDisplayName());
            itemBuilder.addLoreLine("&7• &fListed For&7: &a" + StringUtility.formatNumber(auctionItem.getPrice()) + "$");
            itemBuilder.addLoreLine("&7• &fExpires in&7: &c" + StringUtility.getTimeString(auctionItem.getExpires() - System.currentTimeMillis()));
            itemBuilder.addLoreLine("");

            if(auctionItem.getUuid().equals(player.getUniqueId())) {
                itemBuilder.addLoreLine("&c&oYou cannot purchase an item you listed.");
                return itemBuilder.toItemStack();
            }

            if(PlayerUtility.canPlayerAfford(player, auctionItem.getPrice())) itemBuilder.addLoreLine("&a&oClick here to purchase this item.");
            if(!PlayerUtility.canPlayerAfford(player, auctionItem.getPrice())) itemBuilder.addLoreLine("&c&oYou don't have enough money to purchase this item.");

            return itemBuilder.toItemStack();
        }

        @Override
        public void onClick(Player player) {
            if(auctionItem.getUuid().equals(player.getUniqueId())){
                PlayerUtility.sendError(player, "You listed this item, therefore you cannot buy it!");
                return;
            }

            if(!PlayerUtility.canPlayerAfford(player, auctionItem.getPrice())){
                PlayerUtility.sendError(player, "You don't have enough money to purchase this item!");
                return;
            }

            plugin.getAuctionManager().getAuctionItems().remove(auctionItem);
            PlayerUtility.giveItem(player, auctionItem.getItemStack());

            Profile merchantProfile = plugin.getProfileManager().get(auctionItem.getUuid());

            merchantProfile.getSold().put(auctionItem, player.getUniqueId());

            PlayerUtility.sendSuccess(player, "You successfully purchased this item for &e&l" + StringUtility.formatNumber(auctionItem.getPrice()) + "$&a.");
        }

        @Override
        public void onClickType(ClickType clickType) {

        }
    }

    private class AuctionListedButton extends Button {

        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder item = new ItemBuilder(Material.CHEST);

            Profile profile = plugin.getProfileManager().get(player);

            item.setName("&e&lYour Listed Items");
            item.addLoreLine("&7• &fTotal&7: &b" + (profile.getSold().size() + profile.getCurrentlyListed().size()));
            item.addLoreLine("&7• &fCurrently Listed&7: &b" + profile.getCurrentlyListed().stream().filter(auctionItem -> !auctionItem.didExpire()).count());
            item.addLoreLine("&7• &fSold&7: &b" + profile.getSold().size());
            item.addLoreLine("&7• &fExpired&7: &b" + profile.getCurrentlyListed().stream().filter(AuctionItem::didExpire).count());

            return item.toItemStack();
        }

        @Override
        public void onClick(Player player) {
            new AuctionListedMenu(player).open(0);
        }

        @Override
        public void onClickType(ClickType clickType) {

        }
    }

    private class AuctionSortButton extends Button {

        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder item = new ItemBuilder(Material.HOPPER);

            item.setName("&3&lAuction Sort");
            item.addLoreLine("&7• " + (auctionSort == AuctionSort.NO_FILTERS ? "&a" : "&c") + " No Filters");
            item.addLoreLine("&7• " + (auctionSort == AuctionSort.HIGHEST_PRICE ? "&a" : "&c") + " Highest Price");
            item.addLoreLine("&7• " + (auctionSort == AuctionSort.LOWEST_PRICE ? "&a" : "&c") + " Lowest Price");
            item.addLoreLine("&7• " + (auctionSort == AuctionSort.ENDING_SOON ? "&a" : "&c") + " Ending Soon");


            return item.toItemStack();
        }

        @Override
        public void onClick(Player player) {
            int index = Stream.of(AuctionSort.values()).toList().indexOf(auctionSort);

            if(index == AuctionSort.values().length-1) index = -1;

            index++;

            auctionSort = Stream.of(AuctionSort.values()).toList().get(index);
        }

        @Override
        public void onClickType(ClickType clickType) {

        }
    }

    private class BalanceButton extends Button {

        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder item = new ItemBuilder(Material.EMERALD);

            item.setName("&2&lBalance");
            item.addLoreLine("&7• &a$" + StringUtility.formatNumber(AuctionPlugin.INSTANCE.getHookManager().getEconomy().getBalance(player)));

            return item.toItemStack();
        }

        @Override
        public void onClick(Player player) {

        }

        @Override
        public void onClickType(ClickType clickType) {

        }
    }

    public enum AuctionSort {
        NO_FILTERS, HIGHEST_PRICE, LOWEST_PRICE, ENDING_SOON;
    }
}
