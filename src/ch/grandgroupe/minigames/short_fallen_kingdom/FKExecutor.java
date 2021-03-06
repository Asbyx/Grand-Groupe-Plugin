package ch.grandgroupe.minigames.short_fallen_kingdom;

import ch.grandgroupe.minigames.short_fallen_kingdom.events.EventsCommands;
import ch.grandgroupe.minigames.short_fallen_kingdom.teams.FKTeamCommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class FKExecutor implements CommandExecutor {
	private final EventsCommands events;
	private final FKTeamCommands teams;

	public FKExecutor(EventsCommands events, FKTeamCommands teams) {
		this.events = events;
		this.teams = teams;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
		if (strings.length < 2) return false;

		if (strings[0].equals("game")) events.onCommand(commandSender, command, s, strings);
			else if (strings[0].equals("teams")) teams.onCommand(commandSender, command, s, strings);
		return true;
	}
}
