package ch.grandgroupe.minigames.speedrun;

import ch.grandgroupe.common.utils.Misc;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

import static org.bukkit.potion.PotionEffectType.*;

public enum Objective
{
	KILL(Types.KILL, "kill a player"),
	POTION_KILL(Types.POTION_KILL, "kill a player with a potion effect on your side"),
	DIAMOND(Types.ACHIEVEMENT, "find a diamond", "story/mine_diamond"),
	POTION(Types.ACHIEVEMENT, "brew a potion", "nether/brew_potion"),
	NETHER(Types.ACHIEVEMENT, "enter the nether", "story/enter_the_nether"),
	END(Types.ACHIEVEMENT, "kill the EnderDragon", "end/kill_dragon");
	
	public static final List<String> strings = Misc.list("kill", "diamond", "potion", "nether", "end");
	public static final List<PotionEffectType> bonusEffects = Misc.list(FIRE_RESISTANCE,
			DAMAGE_RESISTANCE,
			FIRE_RESISTANCE,
			WATER_BREATHING,
			INVISIBILITY,
			NIGHT_VISION,
			SLOW_FALLING,
			SPEED,
			REGENERATION,
			JUMP,
			INCREASE_DAMAGE);
	public static final List<PotionEffectType> malusEffects = Misc.list(SLOW,
			BLINDNESS,
			WEAKNESS,
			POISON);
	
	protected enum Types
	{
		KILL,
		POTION_KILL,
		ACHIEVEMENT
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
