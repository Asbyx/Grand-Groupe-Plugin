package ch.grandgroupe.common.tabCompleter;

import ch.grandgroupe.common.utils.Misc;
import ch.grandgroupe.common.worlds.Worlds;
import ch.grandgroupe.minigames.short_fallen_kingdom.teams.FKTeam;
import ch.grandgroupe.minigames.speedrun.Objective;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Enumeration of all possible Arguments Please add yours with respect of the convention, at the right place
 */
public enum Argument
{
	/*RULES*/
	TNT_BOW("tntBow"),
	NUDE_BOW("nudeBow"),
	DEATH_CHEST("deathChest"),
	HARVESTER("harvester"),
	HOOK("hook"),
	COMPASS_TARGETING("compassTargeting"),
	TOMB("tomb"),
	
	/*MAIN ARGUMENTS*/
	/*FK*/
	GAME("game"),
	TEAMS("teams"),
	
	/*#####################################################################################################################*/
	/*FK*/
	/*TEAMS*/
	HELP("help"),
	CREATE("create"),
	REVIVE("revive"),
	TERMINATE("terminate"),
	ADD_PLAYER("addPlayer"),
	REMOVE_PLAYER("removePlayer"),
	SET_BASE_LOCATION("setBaseLocation"),
	GET_BASE_LOCATION("getBaseLocation"),
	PLAYERS("players"),
	TEAM("Name of a team", c -> FKTeam.allTeams.stream()
											   .map(FKTeam::getName)
											   .collect(Collectors.toList())),
	
	/*GAME*/
	START_GAME("startGame"),
	STOP_GAME("stopGame"),
	GET_GAME_PARAMETERS("getGameParameters", c -> Misc.list("getGameParameters", "getParameters", "parameters"), true),
	MIDDLE_CHEST("middleChest"),
	
	/*#####################################################################################################################*/
	/*TRAINING PACK*/
	TRAINING_START("start"),
	TRAINING_STOP("stop"),
	TRAINING_TYPE("type of training", c -> Misc.list("hotbar", "parkour")),
	
	/*#####################################################################################################################*/
	/*SPEEDRUN*/
	OBJECTIVE("The objective to achieve", Objective.class),
	
	/*#####################################################################################################################*/
	/*GLOBAL*/
	PLAYER("Name of a player", c -> Bukkit.getOnlinePlayers().stream()
										  .map(Player::getName)
										  .collect(Collectors.toList())),
	COORDINATE_X("X-axis coordinate", c -> c instanceof Player ? Misc.list(String.valueOf(((Player) c).getLocation().getX()), "~")
															   : Misc.list()),
	COORDINATE_Y("Y-axis coordinate", c -> c instanceof Player ? Misc.list(String.valueOf(((Player) c).getLocation().getY()), "~")
															   : Misc.list()),
	COORDINATE_Z("Z-axis coordinate", c -> c instanceof Player ? Misc.list(String.valueOf(((Player) c).getLocation().getZ()), "~")
															   : Misc.list()),
	WORLD("A world", Worlds.Type.class),
	TP("tp"),
	
	COMMAND("A command", c -> Commands.ALL.stream().map(co -> co.commandName).collect(Collectors.toList())),
	STRING("A string", c -> Misc.list()),
	INT("An integer", c -> Misc.list()),
	FLOAT("A float", c -> Misc.list()),
	BOOLEAN("A boolean", c -> Misc.list("true", "false")),
	DEFAULT("default", c -> Misc.list("default"));
	/*########################################################################################################################*/
	public final String description;
	public final Function<CommandSender, List<String>> tabCompletion;
	public final boolean ignoredInHelp;
	
	Argument(String description, Function<CommandSender, List<String>> tabCompletion, boolean ignoredInHelp) {
		this.description   = description;
		this.tabCompletion = tabCompletion;
		this.ignoredInHelp = ignoredInHelp;
	}
	Argument(String description, Function<CommandSender, List<String>> tabCompletion) {
		this(description, tabCompletion, false);
	}
	Argument(String name) {
		this(name, c -> Misc.list(name), true);
	}
	Argument(String name, Class<? extends Enum<?>> enumeration) {
		Enum<?>[] values = enumeration.getEnumConstants();
		
		List<String> convertedValues =
				Arrays.stream(values)
					  .map(e ->
					  {
						  StringBuilder s = new StringBuilder(e.name().toLowerCase());
					
						  int index = s.indexOf("_");
						  while (index != -1) {
							  s.deleteCharAt(index);
							  s.setCharAt(index, (char) (s.charAt(index) - 32));
							  index = s.indexOf("_");
						  }
					
						  return s.toString();
					  })
					  .collect(Collectors.toList());
		
		this.description   = name;
		this.tabCompletion = c -> convertedValues;
		this.ignoredInHelp = false;
	}
}
