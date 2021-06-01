package ch.grandgroupe.minigames.trainingPack.tests;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Random;

public abstract class AbstractTest implements Listener {
	protected final Random rng;
	protected final Player player;
	protected final World world;

	protected AbstractTest(Random rng, Player player, World world) {
		this.rng = rng;
		this.player = player;
		this.world = world;
	}

	public abstract void init();

	public abstract void update(Player player);

	public abstract boolean isEnded();
}
