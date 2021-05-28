package ch.grandgroupe.common.features;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;
import java.util.Objects;

/**
 * Nude Bow: if a player use a bow and have an emerald in his left hand, the arrow he will throw will downgrade all the armor of the player who has been hit and the emerald will be consumed.
 * Downgrade: if the armor have enchantement it is removed, if not, it does likes this: Netherite -> Diamond -> Iron -> Leather -> Nude :D
 */
public class NudeBow extends AbstractListener {
	@EventHandler
	public void onTntProjectileLaunch(ProjectileLaunchEvent event) {
		if (isDisabled()) {
			return;
		}

		if (event.getEntity().getType() == EntityType.ARROW && event.getEntity().getShooter() instanceof Player) {
			Player player = (Player) event.getEntity().getShooter();
			Arrow arrow = (Arrow) event.getEntity();

			if (player.getInventory().getItemInOffHand().getType() == Material.EMERALD) {
				player.getInventory().getItemInOffHand().setAmount(player.getInventory().getItemInOffHand().getAmount() - 1);
				arrow.setCustomName("nudeArrow");
				arrow.setColor(Color.FUCHSIA);
			}
		}
	}

	@EventHandler
	public void onTntProjectileArrive(ProjectileHitEvent event) {
		if (isDisabled()) return;

		if (Objects.equals(event.getEntity().getCustomName(), "nudeArrow") && event.getHitEntity() instanceof Player) { //fixme generalize
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

			if(playerInventory.getHelmet().getEnchantments().isEmpty()) playerInventory.setHelmet(new ItemStack(helmet));
			else {
				Map<Enchantment, Integer> enchantments = playerInventory.getHelmet().getEnchantments();
				enchantments.forEach((k, v) -> playerInventory.getHelmet().removeEnchantment(k));
			}
			
			if(playerInventory.getChestplate().getEnchantments().isEmpty()) playerInventory.setChestplate(new ItemStack(chest));
			else {
				Map<Enchantment, Integer> enchantments = playerInventory.getChestplate().getEnchantments();
				enchantments.forEach((k, v) -> playerInventory.getChestplate().removeEnchantment(k));
			}
			
			if(playerInventory.getLeggings().getEnchantments().isEmpty()) playerInventory.setLeggings(new ItemStack(legging));
			else {
				Map<Enchantment, Integer> enchantments = playerInventory.getLeggings().getEnchantments();
				enchantments.forEach((k, v) -> playerInventory.getLeggings().removeEnchantment(k));
			}
			
			if(playerInventory.getBoots().getEnchantments().isEmpty()) playerInventory.setBoots(new ItemStack(boots));
			else {
				Map<Enchantment, Integer> enchantments = playerInventory.getBoots().getEnchantments();
				enchantments.forEach((k, v) -> playerInventory.getBoots().removeEnchantment(k));
			}
			
			event.getEntity().remove();
		}
	}
}
