package ch.Asbyx.firstPlugin;

import ch.thechi2000asbyx.common.Coordinates;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

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
}
