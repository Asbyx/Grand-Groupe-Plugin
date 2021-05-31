package ch.grandgroupe.minigames.speedrun;

import ch.grandgroupe.common.utils.Misc;

import java.util.List;

public enum Objective
{
	KILL(Types.KILL, "kill a player"),
	DIAMOND(Types.ACHIEVEMENT, "find a diamond", "story/mine_diamond"),
	POTION(Types.ACHIEVEMENT, "brew a potion", "nether/brew_potion"),
	NETHER(Types.ACHIEVEMENT, "enter the nether", "story/enter_the_nether"),
	END(Types.ACHIEVEMENT, "kill the EnderDragon", "end/kill_dragon");
	
	public static final List<String> strings = Misc.list("kill", "diamond", "potion", "nether", "end");
	
	protected enum Types
	{
		KILL,
		ACHIEVEMENT;
	}
	
	public final Types type;
	public final String description;
	public final Object[] data;
	
	Objective(Types type, String description, Object... data) {
		this.type        = type;
		this.description = description;
		this.data        = data;
	}
	Objective(Types type, String description) {
		this.type        = type;
		this.description = description;
		data             = null;
	}
}
