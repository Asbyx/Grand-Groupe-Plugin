package ch.thechi2000.player_pointers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DistanceBoardsManager
{
    private final List<DistanceBoard> boards;

    public DistanceBoardsManager()
    {
        boards = new ArrayList<>();

        Bukkit.getOnlinePlayers().forEach(this::addPlayer);
    }

    public void addPlayer(Player player)
    {
        boards.add(new DistanceBoard(player));
        boards.forEach(b -> b.addPlayer(player));
    }

    public void removePlayer(Player player)
    {
        boards.forEach(b -> b.removePlayer(player));
    }

    public void updatePlayer(Player player)
    {
        boards.forEach(b -> b.updatePlayer(player));
    }
}
