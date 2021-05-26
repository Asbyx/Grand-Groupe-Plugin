package ch.thechi2000asbyx.short_fallen_kingdom.Commands;

import ch.thechi2000asbyx.common.Misc;

import java.util.List;

import static ch.thechi2000asbyx.short_fallen_kingdom.Commands.Argument.*;

public enum Commands
{
	HELP(false,
			"fkteam",
			"help",
			new ArgumentList("Display the list of the commands", Argument.HELP),
			new ArgumentList("Get the help about a command", Argument.HELP, COMMAND)),
	CREATE(true,
			"fkteam",
			"create",
			new ArgumentList("Create a team with a given name", Argument.CREATE, STRING)),
	REVIVE(true,
			"fkteam",
			"revive",
			new ArgumentList("Revive a given team", Argument.REVIVE, TEAM)),
	TERMINATE(true,
			"fkteam",
			"terminate",
			new ArgumentList("Eliminate a given team", Argument.TERMINATE, TEAM)),
	ADD_PLAYER(true,
			"fkteam",
			"addPlayer",
			new ArgumentList("Add a player to a team", Argument.ADD_PLAYER, TEAM, PLAYER)),
	REMOVE_PLAYER(true,
			"fkteam",
			"removePlayer",
			new ArgumentList("Remove a player from his team", Argument.REMOVE_PLAYER, PLAYER)),
	SET_BASE_LOCATION(true,
			"fkteam",
			"setBaseLocation",
			new ArgumentList("Set the location of your base at your feet", Argument.SET_BASE_LOCATION),
			new ArgumentList("Set the location of a given team at your feet", Argument.SET_BASE_LOCATION, TEAM),
			new ArgumentList("Set the location of your team at the given coordinates", Argument.SET_BASE_LOCATION, COORD_X, COORD_Y, COORD_Z),
			new ArgumentList("Set the location of the given team at the given coordinates", Argument.SET_BASE_LOCATION, TEAM, COORD_X, COORD_Y, COORD_Z)),
	GET_BASE_LOCATION(false,
			"fkteam",
			"getBaseLocation",
			new ArgumentList("Prints you the location of your base", Argument.GET_BASE_LOCATION),
			new ArgumentList("Prints you the location of the base of the given team", Argument.GET_BASE_LOCATION, TEAM)),
	TEAMS(false,
			"fkteam",
			"teams",
			new ArgumentList("Prints you the list of all the teams", Argument.TEAMS)),
	PLAYERS(false,
			"fkteam",
			"players",
			new ArgumentList("Prints you the list of all players from your team", Argument.PLAYERS),
			new ArgumentList("Prints you the list of all players from a given", Argument.PLAYERS, TEAM)),
	START_GAME(true,
			"game",
			"startGame",
			new ArgumentList("Start the game with the default values", Argument.START_GAME, DEFAULT),
			new ArgumentList("Start the game using the values passed as parameters", Argument.START_GAME, INT, INT, INT, INT, INT, INT)),
	STOP_GAME(true,
			"game",
			"stopGame",
			new ArgumentList("Stop the game", Argument.STOP_GAME)),
	GET_GAME_PARAMETERS(false,
			"game",
			"getGameParameters",
			new ArgumentList("Prints all the parameters describing the current game", Argument.GET_GAME_PARAMETERS)),
	MIDDLE_CHEST(false,
			"game",
			"middleChest",
			new ArgumentList("Display the coordinated of the middle chest and respawn it if it was gone", Argument.MIDDLECHEST));
	
	
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
