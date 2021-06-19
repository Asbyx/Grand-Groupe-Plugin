package ch.grandgroupe.common.features;

import ch.grandgroupe.common.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.util.Vector;

import java.util.Objects;

//todo ! (unfinished feature)
public class Hook extends AbstractListener {
	int id = -1;
	double length;
	private final ShapedRecipe recipe1; //todo
	private final NamespacedKey key1 = new NamespacedKey(Main.PLUGIN, "grappin");


	public Hook(){
		ItemStack item = new ItemStack(Material.FISHING_ROD);
		recipe1 = null;
	}

	@EventHandler
	public void onHook(PlayerFishEvent event) {
		if (isDisabled() || !Objects.requireNonNull(event.getPlayer().getInventory().getItemInMainHand().getItemMeta()).getDisplayName().equals("Grappin")) return;

		if (id != -1) {
			Main.SCHEDULER.cancelTask(id);
			id = -1;
			return;
		}
		World world = event.getPlayer().getWorld();
		Player player = event.getPlayer();
		Location blocLoc = null;

		Vector sight = player.getEyeLocation().getDirection().normalize();
		for (int i = 0; i < 32; i++) {
			Material material = world.getBlockAt(player.getEyeLocation().clone().add(sight.clone().add(sight.clone().multiply(i)).toLocation(world))).getType();
			if (!material.isAir()) {
				blocLoc = player.getEyeLocation().clone().add(sight.clone().add(sight.clone().multiply(i)).toLocation(world));
				break;
			}
		}
		if (blocLoc == null) {
			event.setCancelled(true);
			return;
		}

		Location finalBlocLoc = blocLoc;
		event.getHook().teleport(blocLoc.clone().add(new Vector(0, 1, 0)));
		event.getHook().setVelocity(new Vector(0, 0, 0));

		length = blocLoc.clone().subtract(player.getLocation().clone().toVector()).toVector().length();
		id = Main.SCHEDULER.runTaskTimer(Main.PLUGIN, () -> hang(player, finalBlocLoc), 0, 1).getTaskId();
	}

	//unfinished: the goal was to set up a boost with firework: if u press left click while holding one, u re tracked toward the grappin point

	public void hang(Player player, Location blockLocation) {
		Vector line = blockLocation.clone().subtract(player.getLocation().clone().toVector()).toVector();
		double power = (line.length() - length) / 4.0;

		Vector vect = line.clone().normalize().multiply(power <= 0 ? 0 : power);
		if (player.isSprinting()) vect.add(player.getLocation().getDirection().multiply(0.025));
		player.setVelocity(player.getVelocity().add(vect));
	}
}
