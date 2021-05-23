package fr.thechi2000asbyx.common;

import org.bukkit.Location;

public class Coordinates {
    private final int x, y, z;

    public Coordinates(Location location) {
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }
}
