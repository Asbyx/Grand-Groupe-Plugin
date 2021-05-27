package ch.thechi2000asbyx.common.listeners;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.Objects;

public class TntBow extends AbstractListener {
	private float power = 2F;

	@EventHandler
	public void onTntProjectileLaunch(ProjectileLaunchEvent event) {
		if (isDisabled()) {
			return;
		}

		if (event.getEntity().getType() == EntityType.ARROW && event.getEntity().getShooter() instanceof Player) {
			Player player = (Player) event.getEntity().getShooter();
			Arrow arrow = (Arrow) event.getEntity();

			if (player.getInventory().getItemInOffHand().getType() == Material.TNT) {
				player.getInventory().getItemInOffHand().setAmount(player.getInventory().getItemInOffHand().getAmount() - 1);
				arrow.setCustomName("tntArrow");
				arrow.setColor(Color.RED);
			}
		}
	}

	@EventHandler
	public void onTntProjectileArrive(ProjectileHitEvent event) {
		if (isDisabled()) return;

		if (Objects.equals(event.getEntity().getCustomName(), "tntArrow")) {
			event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), power);
			event.getEntity().remove();
		}
	}

	public void setPower(float value){
		power = value;
	}
}
