package me.larssies.pracmain.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.*;

public class ScoreBoard implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            createBoard(player);
        }
    }

    public void createBoard(Player p) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective obj = board.registerNewObjective("ScoreBoard", "dummy", ChatColor.translateAlternateColorCodes('&', "     &c&lPractice    "));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score = obj.getScore(ChatColor.YELLOW + "--------------------");
        score.setScore(5);

        Score score1 = obj.getScore("");
        score1.setScore(4);

        Score score2 = obj.getScore(ChatColor.AQUA + "Online:");
        score2.setScore(3);

        Score score3 = obj.getScore(ChatColor.WHITE + "" + Bukkit.getOnlinePlayers().size());
        score3.setScore(2);

        Score score4 = obj.getScore("");
        score4.setScore(1);

        Score score5 = obj.getScore(ChatColor.YELLOW + "--------------------");
        score5.setScore(0);

        p.setScoreboard(board);
    }
}
