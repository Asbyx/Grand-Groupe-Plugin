package ch.grandgroupe.common.tabCompleter;

import ch.grandgroupe.common.utils.Misc;
import ch.grandgroupe.minigames.short_fallen_kingdom.Teams.FKTeam;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Argument {
	/*RULES*/
	TNT_BOW("tntBow"),
	NUDE_BOW("nudeBow"),
	DEATH_CHEST("deathChest"),
	HARVESTER("harvester"),

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

	public final String description;
	public final Function<CommandSender, List<String>> tabCompletion;
	public final boolean ignoredInHelp;

	Argument(String description, Function<CommandSender, List<String>> tabCompletion, boolean ignoredInHelp) {
		this.description = description;
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