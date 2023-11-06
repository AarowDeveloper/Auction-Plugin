package dev.aarow.auction.managers.impl;

import dev.aarow.auction.managers.Manager;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.logging.Level;

@Getter
public class HookManager extends Manager {

    private Economy economy;

    @Override
    public void setup() {
        this.registerVault();
    }

    private void registerVault(){
        if(Bukkit.getPluginManager().getPlugin("Vault") == null){
            Bukkit.getLogger().log(Level.SEVERE, "There is no vault plugin installed!");
            Bukkit.getPluginManager().disablePlugin(plugin);
            return;
        }

        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);

        if(rsp == null){
            Bukkit.getLogger().log(Level.SEVERE, "There is no economy plugin installed!");
            Bukkit.getPluginManager().disablePlugin(plugin);
            return;
        }

        this.economy = rsp.getProvider();
    }
}
