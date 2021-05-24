package ch.Asbyx.firstPlugin;

import ch.thechi2000asbyx.common.Coordinates;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.util.Vector;

public class ConcreteListener implements Listener {
    @EventHandler
    public void onDiamondMined(BlockBreakEvent event){
        if(event.getBlock().getType() == Material.DIAMOND_ORE && event.getPlayer().getStatistic(Statistic.MINE_BLOCK, Material.DIAMOND_ORE) == 0){
            Bukkit.broadcastMessage(
                    event.getPlayer().getDisplayName() + " a miné du diamand aux coordonnées " + new Coordinates(event.getBlock().getLocation()) + ".\n" +
                    "Après vous faites ce que vous voulez mais on vous conseille de lui péter la gueule. "
            );
        }
    }

    @EventHandler
    public void onTrap(BlockPlaceEvent event){
        if (event.getBlock().getType() == Material.WHITE_WOOL){
            World world = Bukkit.getWorld("world");

            if (    world.getBlockAt(event.getBlock().getLocation().add(new Vector(0, -1, 0))).getType() == Material.PINK_WOOL &&
                    world.getBlockAt(event.getBlock().getLocation().add(new Vector(0, -2, 0))).getType() == Material.PINK_WOOL &&
                    world.getBlockAt(event.getBlock().getLocation().add(new Vector(1, -3, 0))).getType() == Material.PINK_WOOL &&
                    world.getBlockAt(event.getBlock().getLocation().add(new Vector(-1, -3, 0))).getType() == Material.PINK_WOOL
            ){
                world.createExplosion(event.getBlock().getLocation().add(0, -4, 0), 20F);
            }
        }

    }
}
