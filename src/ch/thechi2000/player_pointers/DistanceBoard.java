package ch.thechi2000.player_pointers;

import ch.thechi2000asbyx.common.Coordinates;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;

public class DistanceBoard
{
    private static int count = 0;

    private final Scoreboard scoreboard;
    private final Map<Player, Score> distances;
    private final Objective objective;
    private final Player owner;

    public DistanceBoard(Player player)
    {
        this.owner = player;
        distances = new HashMap<>();
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("distance_board-" + count, "dummy", "Distances");

        Bukkit.getOnlinePlayers().stream().filter(p -> p != player).forEach(p ->
                distances.put(p, objective.getScore(p.getName())).setScore(Coordinates.distanceBetween(p, player)) // TODO
        );

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(scoreboard);

        ++count;
    }

    public void addPlayer(Player player)
    {
        if (player != owner)
            distances.put(player, objective.getScore(player.getName()))
                    .setScore(Coordinates.distanceBetween(owner, player)); // TODO
    }

    public void removePlayer(Player player)
    {
        if (player != owner)
            distances.get(player).setScore(-1);
    }

    public void updatePlayer(Player player)
    {
        if (player == owner)
            distances.forEach((p, s) -> s.setScore(Coordinates.distanceBetween(p, player)));
        else
            distances.get(player).setScore(Coordinates.distanceBetween(owner, player));
    }
}
