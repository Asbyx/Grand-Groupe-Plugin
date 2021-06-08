package ch.grandgroupe.minigames.trainingPack.tests;

import ch.grandgroupe.minigames.trainingPack.TrainingMain;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Parcours extends AbstractTest{
	public Parcours(Random rng, Player player, World world) {
		super(rng, player, world);
	}

	@Override
	public void init() {
		TrainingMain.clearTerrain(50);
		createTerrain();
		player.teleport(new Location(world, 0, TrainingMain.HEIGHT + 2, 0));
		player.setFallDistance(0);
		player.setFoodLevel(50);
		player.getInventory().clear();
		player.getInventory().setItem(0, new ItemStack(Material.DIRT, 64));
		player.setHealthScale(20.0);
	}

	@Override
	public void update(Player player) {
		if (player.getLocation().getY() <= TrainingMain.HEIGHT - 10) init();
		if (player.getLocation().getX() == -4 && player.getLocation().getY() == 151 && player.getLocation().getZ() == 36) player.getInventory().setItem(0, new ItemStack(Material.SAND, 64));
	}

	@Override
	public boolean isEnded() {
		return false;
	}

	private void createTerrain() {
		for (int x = -2; x <= 2; x++) {
			for (int z = -2; z <= 2; z++) {
				setBlock(x, -1, z);
				setBlock(x -2, -1, z + 50);
			}
		}
		setBlock(0, 0, 6);
		setBlock(0, 0, 11);
		setBlock(1, 1, 15);

		for (int y = -2; y < 3; y++) {
			for (int z = 20; z < 27; z++) {
				setBlock(4, y, z);
			}
		}
		setBlock(3, 0, 29);
		setBlock(0, 0, 32);
		setBlock(-4, 0, 36);

		for (int y = -2; y < 3; y++) {
			for (int z = 38; z < 45; z++) {
				setBlock(-6, y, z);
			}
		}
	}

	private void setBlock(int x, int y, int z) {
		world.getBlockAt(x, TrainingMain.HEIGHT + y, z).setType(Material.COBBLESTONE);
	}


}
