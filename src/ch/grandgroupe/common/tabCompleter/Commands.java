package ch.grandgroupe.common.tabCompleter;

import ch.grandgroupe.common.utils.Misc;

import java.util.List;

import static ch.grandgroupe.common.tabCompleter.Argument.*;

/**
 * Enumeration of all commands available Please add yours with respect of the convention, at the right place
 */
public enum Commands
{
	//fixme make the help command global
	HELP(false,
			"fk",
			"help",
			new ArgumentList("Display the list of the commands", Argument.HELP),
			new ArgumentList("Get the help about a command", Argument.HELP, COMMAND)
	),
	
	/*RULES*/
	TNT_BOW(true,
			"rules",
			"tntBow",
			new ArgumentList(Argument.TNT_BOW, "tnt bow"),
			new ArgumentList("Set the power of the tnt bow", Argument.TNT_BOW, FLOAT)
	),
	NUDE_BOW(true,
			"rules",
			"nudeBow",
			new ArgumentList(Argument.NUDE_BOW, "nude bow")
	),
	DEATH_CHEST(true,
			"rules",
			"deathChest",
			new ArgumentList(Argument.DEATH_CHEST, "death chest")
	),
	HARVESTER(true,
			"rules",
			"harvester",
			new ArgumentList(Argument.HARVESTER, "harvester"),
			new ArgumentList("Set the radius of action of the harvester", Argument.HARVESTER, INT)
	),
	GRAPPIN(true,
			"rules",
			"grappin",
			new ArgumentList(Argument.GRAPPIN, "grappin")
	),
	COMPASS_TARGETING(true,
			"rules",
			"compassTargeting",
			new ArgumentList(Argument.COMPASS_TARGETING, "compassTargeting")
	),
	
	/*#####################################################################################################################*/
	/*FK*/
	CREATE(true,
			"fk",
			"teams",
			new ArgumentList("Create a team with a given name", Argument.TEAMS, Argument.CREATE, STRING)
	),
	REVIVE(true,
			"fk",
			"teams",
			new ArgumentList("Revive a given team", Argument.TEAMS, Argument.REVIVE, TEAM)
	),
	TERMINATE(true,
			"fk",
			"teams",
			new ArgumentList("Eliminate a given team", Argument.TEAMS, Argument.TERMINATE, TEAM)
	),
	ADD_PLAYER(true,
			"fk",
			"teams",
			new ArgumentList("Add a player to a team", Argument.TEAMS, Argument.ADD_PLAYER, TEAM, PLAYER)
	),
	REMOVE_PLAYER(true,
			"fk",
			"teams",
			new ArgumentList("Remove a player from his team", Argument.TEAMS, Argument.REMOVE_PLAYER, PLAYER)
	),
	SET_BASE_LOCATION(true,
			"fk",
			"teams",
			new ArgumentList("Set the location of your base at your feet", Argument.TEAMS, Argument.SET_BASE_LOCATION),
			new ArgumentList("Set the location of a given team at your feet", Argument.TEAMS, Argument.SET_BASE_LOCATION, TEAM),
			new ArgumentList("Set the location of your team at the given coordinates", Argument.TEAMS, Argument.SET_BASE_LOCATION, COORD_X, COORD_Y, COORD_Z),
			new ArgumentList("Set the location of the given team at the given coordinates", Argument.TEAMS, Argument.SET_BASE_LOCATION, TEAM, COORD_X, COORD_Y, COORD_Z)
	),
	GET_BASE_LOCATION(false,
			"fk",
			"teams",
			new ArgumentList("Prints you the location of your base", Argument.TEAMS, Argument.GET_BASE_LOCATION),
			new ArgumentList("Prints you the location of the base of the given team", Argument.TEAMS, Argument.GET_BASE_LOCATION, TEAM)
	),
	TEAMS(false,
			"fk",
			"teams",
			new ArgumentList("Prints you the list of all the teams", Argument.TEAMS, Argument.TEAMS)
	),
	PLAYERS(false,
			"fk",
			"teams",
			new ArgumentList("Prints you the list of all players from your team", Argument.TEAMS, Argument.PLAYERS),
			new ArgumentList("Prints you the list of all players from a given", Argument.TEAMS, Argument.PLAYERS, TEAM)
	),
	START_GAME(true,
			"fk",
			"game",
			new ArgumentList("Start the fk with the default values", Argument.GAME, Argument.START_GAME, DEFAULT),
			new ArgumentList("Start the fk using the values passed as parameters", Argument.GAME, Argument.START_GAME, INT, INT, INT, INT, INT, INT)
	),
	STOP_GAME(true,
			"fk",
			"game",
			new ArgumentList("Stop the fk", Argument.GAME, Argument.STOP_GAME)
	),
	GET_GAME_PARAMETERS(false,
			"fk",
			"game",
			new ArgumentList("Prints all the parameters describing the current fk", Argument.GAME, Argument.GET_GAME_PARAMETERS)
	),
	MIDDLE_CHEST(false,
			"fk",
			"game",
			new ArgumentList("Display the coordinated of the middle chest and respawn it if it was gone", Argument.GAME, Argument.MIDDLE_CHEST)
	),
	/*#####################################################################################################################*/
	/*SPEEDRUN*/
	SPEEDRUN_START(true,
			"speedrun",
			"speedrun",
			new ArgumentList("Start the speedrun to a given objective", Argument.START_GAME, OBJECTIVE)),
	SPEEDRUN_STOP(true,
			"speedrun",
			"speedrun",
			new ArgumentList("Start the speedrun to a given objective", Argument.STOP_GAME));
	
	/*#####################################################################################################################*/;
	
	
	Commands(
			boolean opRequired, String
			commandLabel,
			String commandName, List<ArgumentList> argumentsList) {
		this.opRequired    = opRequired;
		this.commandLabel  = commandLabel;
		this.commandName   = commandName;
		this.argumentsList = argumentsList;
	}
	
	Commands(boolean opRequired, String commandLabel, String commandName, ArgumentList... args) {
		this(opRequired, commandLabel, commandName, Misc.list(args));
	}
	
	public final boolean opRequired;
	public final String commandLabel, commandName;
	public final List<ArgumentList> argumentsList;
	public static final List<Commands> ALL = Misc.list(values());
}
