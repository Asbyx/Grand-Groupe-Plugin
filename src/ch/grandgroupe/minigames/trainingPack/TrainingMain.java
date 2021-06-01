package ch.grandgroupe.minigames.trainingPack;

import ch.grandgroupe.minigames.trainingPack.tests.AbstractTest;
import ch.grandgroupe.minigames.trainingPack.tests.SlotBar;
import ch.grandgroupe.minigames.trainingPack.tests.Parcours;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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
		player.setGameMode(GameMode.SURVIVAL);
		this.player = player;
		this.tryhard = tryhard;
		TrainingMain.world = world;

		tests.add(new SlotBar(rng, player, world));
		tests.add(new Parcours(rng, player, world));
	}

	public TrainingMain(Player player, World world, String current){
		this(player, world, false);
		switch (current){
			case "hotbar":
				this.current = tests.get(0);
				beginning("hot bar");
				break;
			case "parcours":
				this.current = tests.get(1);
				beginning("parcours");
				break;

			default:
				player.sendMessage(ChatColor.RED + "Unknown training type: " + current);
				throw new IllegalArgumentException();
		}
	}

	private void beginning(String arg) {
		player.sendMessage(ChatColor.GREEN + "Training " + arg + " started !" + ChatColor.GOLD + " Have Fun !");
		current.init();
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
