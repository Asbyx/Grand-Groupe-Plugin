package ch.thechi2000asbyx.common;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;


public class DeathListener extends AbstractListener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        if(isDisabled()) return;

        event.setDeathMessage(event.getEntity().getDisplayName() + " est mort, quelle merde ! Son inventaire est aux coordonnées: " + new Coordinates(event.getEntity().getLocation()));
        World world = event.getEntity().getWorld();

        world.getBlockAt(event.getEntity().getLocation()).setType(Material.CHEST);
        ((Chest) world.getBlockAt(event.getEntity().getLocation()).getState()).getBlockInventory().addItem(event.getDrops().toArray(new ItemStack[0]));

        event.getDrops().clear();
    }
}
