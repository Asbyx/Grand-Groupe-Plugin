package ch.thechi2000asbyx.common;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Objects;

public class NudeBow extends AbstractListener {
	@EventHandler
	public void onTntProjectileLaunch(ProjectileLaunchEvent event) {
		if (isDisabled()) {
			return;
		}

		if (event.getEntity().getType() == EntityType.ARROW && event.getEntity().getShooter() instanceof Player) {
			Player player = (Player) event.getEntity().getShooter();
			Arrow arrow = (Arrow) event.getEntity();

			if (player.getInventory().getItemInOffHand().getType() == Material.ANVIL) {
				player.getInventory().getItemInOffHand().setAmount(player.getInventory().getItemInOffHand().getAmount() - 1);
				arrow.setCustomName("nudeArrow");
				arrow.setColor(Color.FUCHSIA);
			}
		}
	}

	@EventHandler
	public void onTntProjectileArrive(ProjectileHitEvent event) {
		if (isDisabled()) return;

		if (Objects.equals(event.getEntity().getCustomName(), "nudeArrow") && event.getHitEntity() instanceof Player) {
			PlayerInventory playerInventory = ((Player) event.getHitEntity()).getInventory();
			Material boots = playerInventory.getBoots() == null ? Material.AIR : playerInventory.getBoots().getType();
			Material legging = playerInventory.getLeggings() == null ? Material.AIR : playerInventory.getLeggings().getType();
			Material chest = playerInventory.getChestplate() == null ? Material.AIR : playerInventory.getChestplate().getType();
			Material helmet = playerInventory.getHelmet() == null ? Material.AIR : playerInventory.getHelmet().getType();

			boots = toNude(boots);
			playerInventory.setBoots(new ItemStack(boots));
			legging = toNude(legging);
			playerInventory.setLeggings(new ItemStack(legging));
			chest = toNude(chest);
			playerInventory.setChestplate(new ItemStack(chest));
			helmet = toNude(helmet);
			playerInventory.setLeggings(new ItemStack(helmet));

			event.getEntity().remove();
		}
	}

	private Material toNude(Material material) {
		switch (material){
			case NETHERITE_BOOTS:
				material = Material.DIAMOND_BOOTS;
				break;

			case DIAMOND_BOOTS:
				material = Material.IRON_BOOTS;
				break;

			case IRON_BOOTS:
				material = Material.LEATHER_BOOTS;
				break;

			case LEATHER_BOOTS:
				material = Material.AIR;
				break;
		}
		return material;
	}


}
