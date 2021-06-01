package ch.grandgroupe.minigames.trainingPack;

import ch.grandgroupe.common.worlds.Worlds;
import ch.grandgroupe.minigames.trainingPack.tests.AbstractTest;
import ch.grandgroupe.minigames.trainingPack.tests.SlotBar;
import ch.grandgroupe.minigames.trainingPack.tests.Parcours;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TrainingMain {
	public static World world;

	public static final Location SPAWN_LOCATION = new Location(world, 0, 150, 0);
	public static final Location HOTBAR_LOCATION = new Location(world, 300, 150, 0);
	public static final Location PARCOURS_LOCATION = new Location(world, 600, 150, 0);
	public static final Location CRAFT_LOCATION = new Location(world, 900, 150, 0);

	private final Random rng = new Random();
	private AbstractTest current = null;
	private final Player player;
	private final boolean tryhard;
	List<AbstractTest> tests = new ArrayList<>();

	public TrainingMain(Player player, boolean tryhard){
		init();

		player.setGameMode(GameMode.SURVIVAL);
		player.setBedSpawnLocation(SPAWN_LOCATION); //fixme
		this.player = player;

		this.tryhard = tryhard;

		tests.add(new SlotBar(rng, player));
		tests.add(new Parcours(rng, player));
	}

	public TrainingMain(Player player, String current){
		this(player, false);
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

	private void init(){
		world = Worlds.TRAINING.get();
		SPAWN_LOCATION.setWorld(world);
		HOTBAR_LOCATION.setWorld(world);
		PARCOURS_LOCATION.setWorld(world);
		CRAFT_LOCATION.setWorld(world);
	}
}
