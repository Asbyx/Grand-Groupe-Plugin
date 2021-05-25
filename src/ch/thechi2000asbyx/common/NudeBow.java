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

			switch (boots){
				case NETHERITE_BOOTS:
					boots = Material.DIAMOND_BOOTS;
					break;

				case DIAMOND_BOOTS:
					boots = Material.IRON_BOOTS;
					break;

				case IRON_BOOTS:
					boots = Material.LEATHER_BOOTS;
					break;

				case LEATHER_BOOTS:
					boots = Material.AIR;
					break;
			}
			switch (legging){
				case NETHERITE_LEGGINGS:
					legging = Material.DIAMOND_LEGGINGS;
					break;

				case DIAMOND_LEGGINGS:
					legging = Material.IRON_LEGGINGS;
					break;

				case IRON_LEGGINGS:
					legging = Material.LEATHER_LEGGINGS;
					break;

				case LEATHER_LEGGINGS:
					legging = Material.AIR;
					break;
			}
			switch (chest){
				case NETHERITE_CHESTPLATE:
					chest = Material.DIAMOND_CHESTPLATE;
					break;

				case DIAMOND_CHESTPLATE:
					chest = Material.IRON_CHESTPLATE;
					break;

				case IRON_CHESTPLATE:
					chest = Material.LEATHER_CHESTPLATE;
					break;

				case LEATHER_CHESTPLATE:
					chest = Material.AIR;
					break;
			}
			switch (helmet){
				case NETHERITE_HELMET:
					helmet = Material.DIAMOND_HELMET;
					break;

				case DIAMOND_HELMET:
					helmet = Material.IRON_HELMET;
					break;

				case IRON_HELMET:
					helmet = Material.LEATHER_HELMET;
					break;

				case LEATHER_HELMET:
					helmet = Material.AIR;
					break;
			}

			playerInventory.setBoots(new ItemStack(boots));
			playerInventory.setLeggings(new ItemStack(legging));
			playerInventory.setChestplate(new ItemStack(chest));
			playerInventory.setHelmet(new ItemStack(helmet));

			event.getEntity().remove();
		}
	}
}
