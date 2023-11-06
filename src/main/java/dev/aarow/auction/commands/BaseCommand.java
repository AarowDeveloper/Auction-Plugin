package dev.aarow.auction.commands;

import dev.aarow.auction.AuctionPlugin;
import dev.aarow.auction.utility.chat.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BaseCommand implements CommandExecutor {
   private final CommandInfo commandInfo;
   public AuctionPlugin plugin = AuctionPlugin.INSTANCE;

   public BaseCommand() {
      this.commandInfo = this.getClass().getDeclaredAnnotation(CommandInfo.class);
      this.plugin.getCommand(this.commandInfo.name()).setExecutor(this);
   }

   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      if (!this.commandInfo.permission().isEmpty() && !commandSender.hasPermission(this.commandInfo.permission())) {
         commandSender.sendMessage(CC.translate("&cNo permission."));
         return true;
      } else if (this.commandInfo.playerOnly()) {
         if (commandSender instanceof ConsoleCommandSender) {
            commandSender.sendMessage(CC.translate("&cOnly players can execute this command!"));
            return true;
         } else {
            this.execute((Player)commandSender, strings);
            return true;
         }
      } else {
         this.execute(commandSender, strings);
         return true;
      }
   }

   public void execute(CommandSender sender, String[] args) {
   }

   public void execute(Player player, String[] args) {
   }
}
