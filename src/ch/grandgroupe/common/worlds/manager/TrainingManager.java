package ch.grandgroupe.common.worlds.manager;

import ch.grandgroupe.common.worlds.VoidGenerator;
import ch.grandgroupe.common.worlds.WorldManager;
import ch.grandgroupe.minigames.trainingPack.TrainingMain;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;

public class TrainingManager extends WorldManager{
	public TrainingManager(String name) {
		super(name);
	}

	@Override
	protected boolean hasSpecialGeneration() {
		return true;
	}
	@Override
	protected boolean canBeRegenerated() {
		return false;
	}
	@Override
	protected void generate() {
		set(new WorldCreator("world_training").generator(new VoidGenerator()).createWorld());

		loadParcours();
		loadPlatform(TrainingMain.SPAWN_LOCATION);
		loadPlatform(TrainingMain.HOTBAR_LOCATION);
	}

	private void loadPlatform(Location baseLocation){
		for (int x = -2; x < 3; x++) {
			for (int z = -2; z < 3; z++) {
				setBlock(baseLocation, x, 0, z);
			}
		}
	}

	private void loadParcours(){
		loadPlatform(TrainingMain.PARCOURS_LOCATION);
		setBlock(TrainingMain.PARCOURS_LOCATION, 0, 0, 6);
		setBlock(TrainingMain.PARCOURS_LOCATION, 0, 0, 11);
		setBlock(TrainingMain.PARCOURS_LOCATION, 1, 1, 15);

		for (int y = -2; y < 3; y++) {
			for (int z = 20; z < 27; z++) {
				setBlock(TrainingMain.PARCOURS_LOCATION, 4, y, z);
			}
		}
		setBlock(TrainingMain.PARCOURS_LOCATION, 3, 0, 29);
		setBlock(TrainingMain.PARCOURS_LOCATION, 0, 0, 32);
		setBlock(TrainingMain.PARCOURS_LOCATION, -4, 0, 36);

		for (int y = -2; y < 3; y++) {
			for (int z = 38; z < 45; z++) {
				setBlock(TrainingMain.PARCOURS_LOCATION, -6, y, z);
			}
		}
	}

	private void setBlock(Location baseLocation, int x, int y, int z){
		get().getBlockAt(baseLocation.clone().add(x, y, z)).setType(Material.OBSIDIAN);
	}

	@Override
	public String toString() {
		return "Training world";
	}
}
