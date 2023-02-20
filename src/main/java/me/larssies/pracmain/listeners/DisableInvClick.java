package me.larssies.pracmain.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class DisableInvClick implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if(e.getClickedInventory().getSize() == 9) {
            e.setCancelled(true);
        }
        if(e.getCurrentItem() != null) {
            if(e.getCurrentItem().equals(ItemOnJoin.prac)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMove(InventoryMoveItemEvent e) {
        if(e.getItem() != null) {
            if(e.getItem().getType() == Material.IRON_SWORD || e.getItem().getItemMeta().equals(ItemOnJoin.pracmeta)) {
                e.setCancelled(true);
            }
        }
    }
}
