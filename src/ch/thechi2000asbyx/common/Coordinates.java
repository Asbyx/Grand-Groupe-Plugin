package ch.thechi2000asbyx.common;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Coordinates
{
    private final int x, y, z;

    public Coordinates(Location location)
    {
        x = location.getBlockX();
        y = location.getBlockY();
        z = location.getBlockZ();
    }

    @Override
    public String toString()
    {
        return String.format("[%d, %d, %d]", x, y, z);
    }

    public int distanceTo(Coordinates that)
    {
        int dx = that.x - x,
                dy = that.y - y,
                dz = that.z - z;

        return (int) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public static int distanceBetween(Location a, Location b)
    {
        int dx = a.getBlockX() - b.getBlockX() ,
                dy = a.getBlockY()  - b.getBlockY() ,
                dz = a.getBlockZ()  - b.getBlockZ() ;

        return (int) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public static int distanceBetween(Player a, Player b)
    {
        return distanceBetween(a.getLocation(), b.getLocation());
    }
}
