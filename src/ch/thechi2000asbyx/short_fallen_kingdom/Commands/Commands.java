package ch.thechi2000asbyx.short_fallen_kingdom.Commands;

import ch.thechi2000asbyx.common.Misc;

import java.util.List;

import static ch.thechi2000asbyx.short_fallen_kingdom.Commands.Argument.*;

public enum Commands
{
	HELP(false,
			"help",
			new ArgumentList("Display the list of the commands", Argument.HELP),
			new ArgumentList("Get the help about a command", Argument.HELP, COMMAND)),
	CREATE(true,
			"create",
			new ArgumentList("Create a team with a given name", Argument.CREATE, STRING)),
	REVIVE(true,
			"revive",
			new ArgumentList("Revive a given team", Argument.REVIVE, TEAM)),
	TERMINATE(true,
			"terminate",
			new ArgumentList("Eliminate a given team", Argument.TERMINATE, TEAM)),
	ADD_PLAYER(true,
			"addPlayer",
			new ArgumentList("Add a player to a team", Argument.ADD_PLAYER, TEAM, PLAYER)),
	REMOVE_PLAYER(true,
			"removePlayer",
			new ArgumentList("Remove a player from his team", Argument.REMOVE_PLAYER, PLAYER)),
	SET_BASE_LOCATION(true,
			"setBaseLocation",
			new ArgumentList("Set the location of your base at your feet", Argument.SET_BASE_LOCATION),
			new ArgumentList("Set the location of a given team at your feet", Argument.SET_BASE_LOCATION, TEAM),
			new ArgumentList("Set the location of your team at the given coordinates", Argument.SET_BASE_LOCATION, COORD_X, COORD_Y, COORD_Z),
			new ArgumentList("Set the location of the given team at the given coordinates", Argument.SET_BASE_LOCATION, TEAM, COORD_X, COORD_Y, COORD_Z)),
	GET_BASE_LOCATION(false,
			"getBaseLocation",
			new ArgumentList("Prints you the location of your base", Argument.GET_BASE_LOCATION),
			new ArgumentList("Prints you the location of the base of the given team", Argument.GET_BASE_LOCATION, TEAM)),
	TEAMS(false,
			"teams",
			new ArgumentList("Prints you the list of all the teams", Argument.TEAMS)),
	PLAYERS(false,
			"players",
			new ArgumentList("Prints you the list of all players from your team", Argument.PLAYERS),
			new ArgumentList("Prints you the list of all players from a given", Argument.PLAYERS, TEAM)
	);
	
	Commands(boolean opRequired, String commandName, List<ArgumentList> argumentsList) {
		this.opRequired    = opRequired;
		this.commandName   = commandName;
		this.argumentsList = argumentsList;
	}
	Commands(boolean opRequired, String commandName, ArgumentList... args) {
		this(opRequired, commandName, Misc.list(args));
	}
	
	public final boolean opRequired;
	
	public final String commandName;
	public final List<ArgumentList> argumentsList;
	
	public static final List<Commands> ALL = Misc.list(values());
}
