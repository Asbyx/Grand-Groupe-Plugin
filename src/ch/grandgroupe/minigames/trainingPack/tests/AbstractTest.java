package ch.grandgroupe.minigames.trainingPack.tests;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Random;

public abstract class AbstractTest implements Listener {
	protected final Random rng;
	protected final Player player;

	protected AbstractTest(Random rng, Player player) {
		this.rng = rng;
		this.player = player;
	}

	public abstract void init();

	public abstract void update(Player player);

	public abstract boolean isEnded();
}
