package ch.grandgroupe.common.tabCompleter;

import ch.grandgroupe.common.utils.Misc;
import ch.grandgroupe.minigames.short_fallen_kingdom.Teams.FKTeam;
import ch.grandgroupe.minigames.speedrun.Objective;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Enumeration of all possible Arguments
 * Please add yours with respect of the convention, at the right place
 */
public enum Argument {
	/*RULES*/
	TNT_BOW("tntBow"),
	NUDE_BOW("nudeBow"),
	DEATH_CHEST("deathChest"),
	HARVESTER("harvester"),
	GRAPPIN("grappin"),
	COMPASS_TARGETING("compassTargeting"),

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

	/*#####################################################################################################################*/
	/*SPEEDRUN*/
	OBJECTIVE("The objective to achieve", c -> Arrays.stream(Objective.values())
													 .map(o -> o.name()
																.toLowerCase()
																.replaceAll("([A-Z])", "\\L$1")
																.replaceAll("_([a-z])", "\\u$1"))
													 .collect(Collectors.toList())),

	/*#####################################################################################################################*/
	/*GLOBAL*/
	PLAYER("Name of a player", c -> Bukkit.getOnlinePlayers().stream()
										  .map(Player::getName)
										  .collect(Collectors.toList())),
	COORD_X("X-axis coordinate", c -> c instanceof Player ? Misc.list(String.valueOf(((Player) c).getLocation().getX()), "~")
														  : Misc.list()),
	COORD_Y("Y-axis coordinate", c -> c instanceof Player ? Misc.list(String.valueOf(((Player) c).getLocation().getY()), "~")
														  : Misc.list()),
	COORD_Z("Z-axis coordinate", c -> c instanceof Player ? Misc.list(String.valueOf(((Player) c).getLocation().getZ()), "~")
														  : Misc.list()),
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
}
