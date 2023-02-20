package me.larssies.pracmain;

import me.larssies.pracmain.Storage.Modes;
import me.larssies.pracmain.commands.LeaveQueueCommand;
import me.larssies.pracmain.listeners.DisableInvClick;
import me.larssies.pracmain.listeners.ItemOnJoin;
import me.larssies.pracmain.modes.BoxingMode;
import me.larssies.pracmain.modes.SumoMode;
import me.larssies.pracmain.utils.ScoreBoard;
import org.bukkit.plugin.java.JavaPlugin;

public final class PracMain extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Plugin starting up...");

        getServer().getPluginManager().registerEvents(new SumoMode(this), this);
        getServer().getPluginManager().registerEvents(new BoxingMode(this), this);
        getServer().getPluginManager().registerEvents(new DisableInvClick(), this);
        getServer().getPluginManager().registerEvents(new ItemOnJoin(), this);
        getServer().getPluginManager().registerEvents(new ScoreBoard(), this);

        getCommand("leave").setExecutor(new LeaveQueueCommand());

    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin shutting down...");

        Modes.sumo.clear();
        Modes.sumoQueue.clear();
        Modes.boxingQueue.clear();
        Modes.boxing.clear();
    }
}
