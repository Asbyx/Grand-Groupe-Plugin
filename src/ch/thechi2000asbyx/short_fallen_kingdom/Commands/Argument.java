package ch.thechi2000asbyx.short_fallen_kingdom.Commands;

import ch.thechi2000asbyx.common.Misc;
import ch.thechi2000asbyx.short_fallen_kingdom.Teams.FKTeam;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Argument
{
	HELP("help", c -> Misc.list("help"), true),
	CREATE("create", c -> Misc.list("create"), true),
	REVIVE("revive", c -> Misc.list("revive"), true),
	TERMINATE("terminate", c -> Misc.list("terminate"), true),
	ADD_PLAYER("addPlayer", c -> Misc.list("addPlayer"), true),
	REMOVE_PLAYER("removePlayer", c -> Misc.list("removePlayer"), true),
	SET_BASE_LOCATION("setBaseLocation", c -> Misc.list("setBaseLocation"), true),
	GET_BASE_LOCATION("getBaseLocation", c -> Misc.list("getBaseLocation"), true),
	TEAMS("teams", c -> Misc.list("teams"), true),
	PLAYERS("players", c -> Misc.list("players"), true),
	PLAYER("Name of a player", c -> Bukkit.getOnlinePlayers().stream()
										  .map(Player::getName)
										  .collect(Collectors.toList())),
	TEAM("Name of a team", c -> FKTeam.allTeams.stream()
											   .map(FKTeam::getName)
											   .collect(Collectors.toList())),
	COORD_X("X-axis coordinate", c -> c instanceof Player ? Misc.list(String.valueOf(((Player) c).getLocation().getX()), "~")
														  : Misc.list()),
	COORD_Y("Y-axis coordinate", c -> c instanceof Player ? Misc.list(String.valueOf(((Player) c).getLocation().getY()), "~")
														  : Misc.list()),
	COORD_Z("Z-axis coordinate", c -> c instanceof Player ? Misc.list(String.valueOf(((Player) c).getLocation().getZ()), "~")
														  : Misc.list()),
	COMMAND("A command", c -> Commands.ALL.stream().map(co -> co.commandName).collect(Collectors.toList())),
	STRING("A string", c -> Misc.list());
	
	public final String description;
	public final Function<CommandSender, List<String>> tabCompletion;
	public final boolean ignoredInHelp;
	
	Argument(String description, Function<CommandSender, List<String>> tabCompletion, boolean countedInHelp) {
		this.description   = description;
		this.tabCompletion = tabCompletion;
		this.ignoredInHelp = countedInHelp;
	}
	Argument(String description, Function<CommandSender, List<String>> tabCompletion) {
		this.description   = description;
		this.tabCompletion = tabCompletion;
		this.ignoredInHelp = false;
	}
}
