package ch.Asbyx.firstPlugin;

import ch.thechi2000asbyx.common.Coordinates;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
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
    public void buildTotem(PlayerDropItemEvent event){
        if (event.getItemDrop().getItemStack().getType() == Material.DIAMOND_HOE) {
            event.getItemDrop().remove();
            World world = event.getPlayer().getWorld();
            world.getBlockAt(event.getPlayer().getLocation().add(new Vector(1, 0, 0))).setType(Material.OAK_FENCE);
            world.getBlockAt(event.getPlayer().getLocation().add(new Vector(1, 1, 0))).setType(Material.OAK_FENCE);
            world.getBlockAt(event.getPlayer().getLocation().add(new Vector(1, 2, 0))).setType(Material.OAK_FENCE);
            world.getBlockAt(event.getPlayer().getLocation().add(new Vector(1, 2, 1))).setType(Material.DIAMOND_BLOCK);
            world.getBlockAt(event.getPlayer().getLocation().add(new Vector(1, 2, -1))).setType(Material.DIAMOND_BLOCK);

            Bukkit.broadcastMessage(event.getPlayer().getDisplayName() + " est maintenant riche !");
        }
    }
}
