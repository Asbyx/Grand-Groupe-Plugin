package ch.grandgroupe.minigames.trainingPack;

import ch.grandgroupe.minigames.trainingPack.tests.AbstractTest;
import ch.grandgroupe.minigames.trainingPack.tests.SlotBar;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TrainingMain {
	public static final int HEIGHT = 150;
	public static World world;

	private final Random rng = new Random();
	private AbstractTest current = null;
	private final Player player;
	private final boolean tryhard;
	List<AbstractTest> tests = new ArrayList<>();

	public TrainingMain(Player player, World world, boolean tryhard){
		this.player = player;
		this.tryhard = tryhard;
		TrainingMain.world = world;

		tests.add(new SlotBar(rng, player, world));
	}

	public void update(){
		if (current == null) {
			current = tests.get(rng.nextInt(tests.size()));
			current.init();
		}

		current.update(player);

		if(current.isEnded()) {
			if (tryhard){
				current = null;
			} else {
				//todo : faire un event pour recommencer, pour passer au mini-jeu suivant ou arrêter (arrêter actif même si tryhard)
			}
		}

	}

	public static void clearTerrain(int radius){
		for (int x = -radius; x < radius; x++) {
			for (int y = -radius + HEIGHT; y < radius + HEIGHT; y++) {
				for (int z = -radius; z < radius; z++) {
					world.getBlockAt(x, y, z).setType(Material.AIR);
				}
			}
		}
	}
}
