package me.larssies.pracmain.listeners;

import me.larssies.pracmain.Storage.Modes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemOnJoin implements Listener {

    public static ItemStack prac = new ItemStack(Material.IRON_SWORD);
    public static ItemMeta pracmeta = prac.getItemMeta();

    public static ArrayList<String> sumolore = new ArrayList<>();
    public static ArrayList<String> boxinglore = new ArrayList<>();


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.getInventory().clear();

        pracmeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Duel");
        pracmeta.setUnbreakable(true);

        prac.setItemMeta(pracmeta);

        p.getInventory().setItem(0, prac);

    }

    @EventHandler
    public void usePracItem(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(!(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)) {
            if(!(p.getItemInHand().getType() == Material.AIR)) {
                if(p.getItemInHand() != null) {
                    if(e.getItem() != null || e.getItem().getItemMeta() != null) {
                        if(e.getItem().getType() == Material.IRON_SWORD && p.getItemInHand().getItemMeta().equals(pracmeta)) {
                            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

                                Inventory duel = Bukkit.createInventory(p, 9, "Duels");

                                //Sumo
                                ItemStack sumo = new ItemStack(Material.SLIME_BALL);
                                ItemMeta sumometa = sumo.getItemMeta();

                                if(Modes.sumo.size() > 0) {
                                    sumo.setAmount(Modes.sumo.size());
                                } else {
                                    sumo.setAmount(1);
                                }

                                sumometa.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lSumo"));

                                if(sumolore.size() > 0) {
                                    sumolore.clear();
                                }
                                sumolore.add("");
                                sumolore.add(ChatColor.GRAY + "• Queue: " + ChatColor.YELLOW + Modes.sumoQueue.size());
                                sumolore.add(ChatColor.GRAY + "• Playing: " + ChatColor.YELLOW + Modes.sumo.size());

                                sumometa.setLore(sumolore);
                                sumo.setItemMeta(sumometa);

                                //Boxing
                                ItemStack boxing = new ItemStack(Material.DIAMOND_SWORD);
                                ItemMeta boxingMeta = boxing.getItemMeta();

                                if(Modes.boxing.size() > 0) {
                                    sumo.setAmount(Modes.boxing.size());
                                } else {
                                    sumo.setAmount(1);
                                }

                                boxingMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lBoxing"));

                                if(boxinglore.size() > 0) {
                                    boxinglore.clear();
                                }
                                boxinglore.add("");
                                boxinglore.add(ChatColor.GRAY + "• Queue: " + ChatColor.YELLOW + Modes.boxingQueue.size());
                                boxinglore.add(ChatColor.GRAY + "• Playing: " + ChatColor.YELLOW + Modes.boxing.size());

                                boxingMeta.setLore(boxinglore);
                                boxing.setItemMeta(boxingMeta);

                                duel.addItem(sumo, boxing);

                                p.openInventory(duel);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void changeWorldEvent(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        p.getInventory().clear();

        pracmeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Duel");
        pracmeta.setUnbreakable(true);

        prac.setItemMeta(pracmeta);

        p.getInventory().setItem(0, prac);
    }
}
