package ch.thechi2000.player_pointers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;


public class Main extends JavaPlugin implements Listener
{
    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
    }

    public void createBoard(Player player)
    {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective o = board.registerNewObjective("test", "dummy", ChatColor.translateAlternateColorCodes('&', "&a&l<< &2&lCodedRed &a&l>>"));
        o.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score s1 = o.getScore(ChatColor.RED + "Test1");
        s1.setScore(1);
        Score s2 = o.getScore(ChatColor.RED + "Test2");
        s2.setScore(2);
        Score s3 = o.getScore(ChatColor.RED + "Test3");
        s3.setScore(3);
        Score s4 = o.getScore(ChatColor.RED + "Test4");
        s4.setScore(4);

        player.setScoreboard(board);
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        event.setJoinMessage("Welcome !");
        createBoard(event.getPlayer());
    }
}
