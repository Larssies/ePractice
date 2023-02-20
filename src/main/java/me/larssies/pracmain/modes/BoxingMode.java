package me.larssies.pracmain.modes;

import me.larssies.pracmain.PracMain;
import me.larssies.pracmain.Storage.Modes;
import me.larssies.pracmain.maps.SumoMaps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class BoxingMode implements Listener {

    PracMain plugin;

    public BoxingMode(PracMain plugin) {
        this.plugin = plugin;
    }

    Random r = new Random();

    HashMap<UUID, Integer> map = new HashMap<>();

    //ITEMS
    ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);

    @EventHandler
    public void onClickOfBoxing(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(!(e.getCurrentItem() == null)) {
            if(e.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
                if(!Modes.boxingQueue.contains(p) && !Modes.boxing.contains(p)) {
                    Modes.boxingQueue.add(p);

                    if(Modes.boxingQueue.size() >= 2) {
                        p.closeInventory();
                        Player p1 = Bukkit.getPlayer(p.getName());
                        Player p2 = Modes.boxingQueue.get(r.nextInt(Modes.boxingQueue.size()));
                        Modes.boxingQueue.remove(p1);
                        Modes.boxingQueue.remove(p2);
                        Modes.boxing.add(p1);
                        Modes.boxing.add(p2);
                        p1.getInventory().clear();
                        p2.getInventory().clear();
                        p1.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a" + p1.getName() + " &7VS &c" + p2.getName()));
                        p2.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a" + p1.getName() + " &7VS &c" + p2.getName()));
                        p1.teleport(new Location(Bukkit.getServer().getWorld("boxingWorld"), 1 , 64, 1));
                        p2.teleport(new Location(Bukkit.getServer().getWorld("boxingWorld"), 2 , 64, 2));
                        p1.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 255, true, false));
                        p2.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 255, true, false));
                        p1.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 255, true, false));
                        p2.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 255, true, false));
                        p1.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255, true, false));
                        p2.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255, true, false));
                        new BukkitRunnable() {
                            private int seconds = 5;
                            @Override
                            public void run() {
                                if(seconds == 5) {
                                    p1.sendMessage(ChatColor.GREEN + "Starting in 5 seconds");
                                    p2.sendMessage(ChatColor.GREEN + "Starting in 5 seconds");
                                    seconds--;
                                } else {
                                    if(seconds == 4) {
                                        p1.sendMessage(ChatColor.GREEN + "Starting in 4 seconds");
                                        p2.sendMessage(ChatColor.GREEN + "Starting in 4 seconds");
                                        seconds--;
                                    } else {
                                        if(seconds == 3) {
                                            p1.sendMessage(ChatColor.GREEN + "Starting in 3 seconds");
                                            p2.sendMessage(ChatColor.GREEN + "Starting in 3 seconds");
                                            seconds--;
                                        } else {
                                            if(seconds == 2) {
                                                p1.sendMessage(ChatColor.GREEN + "Starting in 2 seconds");
                                                p2.sendMessage(ChatColor.GREEN + "Starting in 2 seconds");
                                                seconds--;
                                            } else {
                                                if(seconds == 1) {
                                                    p1.sendMessage(ChatColor.GREEN + "Starting in 1 second");
                                                    p2.sendMessage(ChatColor.GREEN + "Starting in 1 second");
                                                    seconds--;
                                                } else {
                                                    if(seconds == 0) {
                                                        sword.addEnchantment(Enchantment.DAMAGE_ALL, 10);
                                                        p1.sendMessage(ChatColor.GREEN + "Match has started!");
                                                        p2.sendMessage(ChatColor.GREEN + "Match has started!");
                                                        p1.removePotionEffect(PotionEffectType.JUMP);
                                                        p2.removePotionEffect(PotionEffectType.JUMP);
                                                        p1.removePotionEffect(PotionEffectType.SLOW);
                                                        p2.removePotionEffect(PotionEffectType.SLOW);
                                                        p1.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, true, false));
                                                        p2.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, true, false));
                                                        p1.getInventory().setItem(1, sword);
                                                        p2.getInventory().setItem(1, sword);
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
                        p.sendMessage(ChatColor.GRAY + "You are now in the " + ChatColor.GREEN + "Boxing" + ChatColor.GRAY + " queue, type /leave to get out.");
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
                if(Modes.boxing.contains(killed) && Modes.boxing.contains(killer)) {
                    killed.sendMessage(ChatColor.RED + "You have been eliminated by " + killer.getName());
                    killer.sendMessage(ChatColor.GREEN + "You have won the game!");
                    ((Player) killed).removePotionEffect(PotionEffectType.SATURATION);
                    ((Player) killer).removePotionEffect(PotionEffectType.SATURATION);
                    ((Player) killed).removePotionEffect(PotionEffectType.SPEED);
                    ((Player) killer).removePotionEffect(PotionEffectType.SPEED);
                    killer.teleport(SumoMaps.locations.get(r.nextInt(SumoMaps.locations.size())));
                    killed.teleport(SumoMaps.locations.get(r.nextInt(SumoMaps.locations.size())));
                    Modes.boxing.remove(killed);
                    Modes.boxing.remove(killer);
                }
            }
        }
    }

    @EventHandler
    public void on100Hits(EntityDamageByEntityEvent e) {
        Entity killed = e.getEntity();
        Entity killer = e.getDamager();

        if(killed instanceof Player) {
            if(killer instanceof Player) {
                if(Modes.boxing.contains(killed) || Modes.boxing.contains(killer)) {
                    int currentHits = map.getOrDefault(killer.getUniqueId(), 0);
                    map.put(killer.getUniqueId(), currentHits + 1);
                }
            }
        }

        if(map.size() == 100) {
            killed.sendMessage(ChatColor.RED + "You have been eliminated by " + killer.getName());
            killer.sendMessage(ChatColor.GREEN + "You have won the game!");
            assert killed instanceof Player;
            ((Player) killed).removePotionEffect(PotionEffectType.SATURATION);
            assert killer instanceof Player;
            ((Player) killer).removePotionEffect(PotionEffectType.SATURATION);
            ((Player) killed).removePotionEffect(PotionEffectType.SPEED);
            ((Player) killer).removePotionEffect(PotionEffectType.SPEED);
            killer.teleport(SumoMaps.locations.get(r.nextInt(SumoMaps.locations.size())));
            killed.teleport(SumoMaps.locations.get(r.nextInt(SumoMaps.locations.size())));
            Modes.boxing.remove(killed);
            Modes.boxing.remove(killer);
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
        if(Modes.boxingQueue.contains(p)) {
            Modes.boxingQueue.remove(p);
        } else {
            if(Modes.boxing.contains(p)) {
                p.damage(20.0);
                Modes.boxing.remove(p);
            }
        }
    }
}