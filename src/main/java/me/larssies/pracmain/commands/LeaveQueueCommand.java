package me.larssies.pracmain.commands;

import me.larssies.pracmain.Storage.Modes;
import me.larssies.pracmain.listeners.ItemOnJoin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LeaveQueueCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p;
        if(sender instanceof Player) {
            p = (Player) sender;
            if(Modes.sumoQueue.contains(p) || Modes.boxingQueue.contains(p)) {
                Modes.sumoQueue.remove(p);
                Modes.boxingQueue.remove(p);
                p.getInventory().clear();
                ItemOnJoin.pracmeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Duel");
                ItemOnJoin.pracmeta.setUnbreakable(true);

                ItemOnJoin.prac.setItemMeta(ItemOnJoin.pracmeta);
                p.getInventory().addItem(ItemOnJoin.prac);
                p.sendMessage(ChatColor.RED + "You have left the queue.");
            } else {
                if(Modes.sumo.contains(p) || Modes.boxing.contains(p)) {
                    p.getInventory().clear();
                    p.damage(20.0);
                    Modes.sumo.remove(p);
                    Modes.boxing.remove(p);
                    p.sendMessage(ChatColor.RED + "You have left your current game!");
                } else {
                    p.sendMessage(ChatColor.RED + "You can't use this while not being in a game or queue!");
                }
            }
        }
        return true;
    }
}
