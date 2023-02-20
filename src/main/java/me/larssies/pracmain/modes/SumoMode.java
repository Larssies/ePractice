package me.larssies.pracmain.modes;

import me.larssies.pracmain.PracMain;
import me.larssies.pracmain.Storage.Modes;
import me.larssies.pracmain.listeners.ItemOnJoin;
import me.larssies.pracmain.maps.SumoMaps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class SumoMode implements Listener {

    PracMain plugin;

    public SumoMode(PracMain plugin) {
        this.plugin = plugin;
    }

    Random r = new Random();

    @EventHandler
    public void onClickOfSumo(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(!(e.getCurrentItem() == null)) {
            if(e.getCurrentItem().getType() == Material.SLIME_BALL) {
                if(!Modes.sumoQueue.contains(p) && !Modes.sumo.contains(p)) {
                    Modes.sumoQueue.add(p);

                    if(Modes.sumoQueue.size() >= 2) {
                        p.closeInventory();
                        Player p1 = Bukkit.getPlayer(p.getName());
                        Player p2 = Modes.sumoQueue.get(r.nextInt(Modes.sumoQueue.size()));
                        Modes.sumoQueue.remove(p1);
                        Modes.sumoQueue.remove(p2);
                        Modes.sumo.add(p1);
                        Modes.sumo.add(p2);
                        p1.teleport(new Location(Bukkit.getServer().getWorld("world"), 1, 64, 1));
                        p2.teleport(new Location(Bukkit.getServer().getWorld("world"), 1, 64, 1));
                        p1.sendMessage(ChatColor.GREEN + p1.getName() + ChatColor.AQUA + " VS " + ChatColor.RED + p2.getName());
                        p2.sendMessage(ChatColor.GREEN + p2.getName() + ChatColor.AQUA + " VS " + ChatColor.RED + p1.getName());
                        p1.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 255, true, false));
                        p2.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 255, true, false));
                        p1.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255, true, false));
                        p2.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255, true, false));
                        new BukkitRunnable() {
                            private int seconds = 5;
                            @Override
                            public void run() {
                                if(seconds == 5) {
                                    p1.sendMessage(ChatColor.GREEN + "Starting in 5 seconds...");
                                    p2.sendMessage(ChatColor.GREEN + "Starting in 5 seconds...");
                                    seconds--;
                                } else {
                                    if(seconds == 4) {
                                        p1.sendMessage(ChatColor.GREEN + "Starting in 4 seconds...");
                                        p2.sendMessage(ChatColor.GREEN + "Starting in 4 seconds...");
                                        seconds--;
                                    } else {
                                        if(seconds == 3) {
                                            p1.sendMessage(ChatColor.GREEN + "Starting in 3 seconds...");
                                            p2.sendMessage(ChatColor.GREEN + "Starting in 3 seconds...");
                                            seconds--;
                                        } else {
                                            if(seconds == 2) {
                                                p1.sendMessage(ChatColor.GREEN + "Starting in 2 seconds...");
                                                p2.sendMessage(ChatColor.GREEN + "Starting in 2 seconds...");
                                                seconds--;
                                            } else {
                                                if(seconds == 1) {
                                                    p1.sendMessage(ChatColor.GREEN + "Starting in 1 second...");
                                                    p2.sendMessage(ChatColor.GREEN + "Starting in 1 second...");
                                                    seconds--;
                                                } else {
                                                    if(seconds == 0) {
                                                        p1.sendMessage(ChatColor.GREEN + "GO!");
                                                        p2.sendMessage(ChatColor.GREEN + "GO!");
                                                        p1.removePotionEffect(PotionEffectType.JUMP);
                                                        p2.removePotionEffect(PotionEffectType.JUMP);
                                                        p1.removePotionEffect(PotionEffectType.SLOW);
                                                        p2.removePotionEffect(PotionEffectType.SLOW);
                                                        p1.getInventory().clear();
                                                        p2.getInventory().clear();
                                                        p1.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 255, true, false));
                                                        p2.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 255, true, false));
                                                        cancel();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }.runTaskTimer(plugin, 0L, 20L);
                    } else {
                        p.getInventory().clear();
                        p.closeInventory();
                        p.sendMessage(ChatColor.GRAY + "You are now in the " + ChatColor.GREEN + "Sumo" + ChatColor.GRAY + " queue, type /leave to get out.");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "You have already queued, leave this one to be able to queue another game!");
                }
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDamageByEntityEvent e) {
        Entity killed = e.getEntity();
        Entity killer = e.getDamager();

        SumoMaps.locations.add(new Location(Bukkit.getServer().getWorld("world"), 3, 64, 3));

        if(killed instanceof Player) {
            if(killer instanceof Player) {
                if(Modes.sumo.contains(killed) && Modes.sumo.contains(killer)) {
                    killed.sendMessage(ChatColor.RED + "You have been eliminated by " + killer.getName());
                    killer.sendMessage(ChatColor.GREEN + "You have won the game!");
                    ((Player) killed).removePotionEffect(PotionEffectType.SATURATION);
                    ((Player) killer).removePotionEffect(PotionEffectType.SATURATION);
                    killer.teleport(SumoMaps.locations.get(r.nextInt(SumoMaps.locations.size())));
                    killed.teleport(SumoMaps.locations.get(r.nextInt(SumoMaps.locations.size())));
                    Modes.sumo.remove(killed);
                    Modes.sumo.remove(killer);
                }
            }
        }
    }

    @EventHandler
    public void onTouchOfWater(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Material m = e.getPlayer().getLocation().getBlock().getType();
        if(m == Material.LEGACY_STATIONARY_WATER || m == Material.WATER) {
            p.damage(20.0);
        }
    }

    @EventHandler
    public void onDeathEvent(PlayerDeathEvent e) {
        Player p = e.getPlayer();
        p.spigot().respawn();
    }

    @EventHandler
    public void onPlayerLeaveEvent(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(Modes.sumoQueue.contains(p)) {
            Modes.sumoQueue.remove(p);
        } else {
            if(Modes.sumo.contains(p)) {
                p.damage(20.0);
                Modes.sumo.remove(p);
            }
        }
    }
}