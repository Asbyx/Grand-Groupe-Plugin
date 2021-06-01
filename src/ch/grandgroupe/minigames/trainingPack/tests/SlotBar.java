package ch.grandgroupe.minigames.trainingPack.tests;

import ch.grandgroupe.common.Main;
import ch.grandgroupe.minigames.trainingPack.TrainingMain;
import org.bukkit.*;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * On doit sélectionner le bon slot de barre d'items tout en esquivant des projectiles venant de face et sans tomber
 */
public class SlotBar extends AbstractTest{
	long time;
	int slot = -1;


	public SlotBar(Random rng, Player player, World world) {
		super(rng, player, world);
	}

	@Override
	public void init() {
		TrainingMain.clearTerrain(30);

		for (int x = -9; x < 9; x++) {
			for (int z = -9; z < 9; z++) {
				world.getBlockAt(x, TrainingMain.HEIGHT - 1, z).setType(Material.GRASS_BLOCK);
			}
		}
		player.teleport(new Location(world, 0, TrainingMain.HEIGHT, 0));
	}


	@Override
	public void update(Player player) {
		if(slot == -1 && world.getGameTime() - time >= rng.nextInt(60) - 30) rebuild();

		if(player.getInventory().getHeldItemSlot() == slot) {
			player.sendMessage(ChatColor.GOLD + "Great ! It took " + (world.getGameTime() - time) + " ticks !");
			time = world.getGameTime();
			slot = -1;
		}

		if (rng.nextInt(20) == 1) {
			Fireball fireball = world.spawn(new Location(world, 0, TrainingMain.HEIGHT + 1, 16), Fireball.class);
			fireball.setDirection(new Vector(player.getLocation().getX(), 0, player.getLocation().getZ() - 8).normalize());
			fireball.setIsIncendiary(false);
		}
	}

	@Override
	public boolean isEnded() {
		return false;
	}

	private void rebuild(){
		time = world.getGameTime();
		do {
			slot = rng.nextInt(9);
		} while (slot == player.getInventory().getHeldItemSlot());

		player.getInventory().setItem(slot, new ItemStack(Material.TARGET));

		for (int i = 0; i < 9; i++) {
			if(i == slot) continue;

			ItemStack item = new ItemStack(Material.values()[rng.nextInt(Material.values().length)]);
			item.setAmount(Math.min(item.getMaxStackSize(), rng.nextInt(16)));
			player.getInventory().setItem(i, item);
		}
	}
}
