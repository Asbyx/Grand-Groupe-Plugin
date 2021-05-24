package ch.Asbyx.tomb;

import ch.thechi2000asbyx.common.Coordinates;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;


public class DeathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        event.setDeathMessage(event.getEntity().getDisplayName() + " est mort, quelle merde ! Son inventaire est aux coordonnées: " + new Coordinates(event.getEntity().getLocation()));
        World world = event.getEntity().getWorld();

        world.getBlockAt(event.getEntity().getLocation()).setType(Material.CHEST);
        ((Chest) world.getBlockAt(event.getEntity().getLocation()).getState()).getBlockInventory().addItem(event.getDrops().toArray(new ItemStack[0]));

        event.getDrops().clear();
    }
}
