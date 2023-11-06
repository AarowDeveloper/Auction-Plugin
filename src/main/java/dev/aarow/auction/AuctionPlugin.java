package dev.aarow.auction;

import dev.aarow.auction.commands.impl.AuctionCommand;
import dev.aarow.auction.listeners.impl.MenuListeners;
import dev.aarow.auction.listeners.impl.PlayerListeners;
import dev.aarow.auction.managers.impl.AuctionManager;
import dev.aarow.auction.managers.impl.HookManager;
import dev.aarow.auction.managers.impl.MenuManager;
import dev.aarow.auction.managers.impl.ProfileManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class AuctionPlugin extends JavaPlugin {

    public static AuctionPlugin INSTANCE;

    private HookManager hookManager;
    private MenuManager menuManager;
    private AuctionManager auctionManager;
    private ProfileManager profileManager;

    @Override
    public void onEnable(){
        INSTANCE = this;

        this.hookManager = new HookManager();
        this.menuManager = new MenuManager();
        this.auctionManager = new AuctionManager();
        this.profileManager = new ProfileManager();

        this.registerCommands();
        this.registerListeners();
    }

    protected void registerCommands(){
        new AuctionCommand();
    }
    protected void registerListeners(){
        new MenuListeners();
        new PlayerListeners();
    }
}
