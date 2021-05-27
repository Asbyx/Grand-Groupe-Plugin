package ch.grandgroupe.common.tabCompleter;

import org.bukkit.command.*;
import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

import static ch.grandgroupe.common.tabCompleter.Commands.*;


public class TabCompleter implements org.bukkit.command.TabCompleter
{
	@Override
	public List<String> onTabComplete(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String label, String[] strings) {
		return getValidArgumentLists(commandSender, label, strings).stream()
																   .map(l -> l.arguments.get(strings.length - 1)
																		   .tabCompletion.apply(commandSender).stream()
																						 .filter(s -> s.startsWith(strings[strings.length - 1]))
																						 .collect(Collectors.toList()))
																   .collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
	}
	
	private List<ArgumentList> getValidArgumentLists(CommandSender sender, String label, String[] strings) {
		return ALL.stream().filter(c -> !c.opRequired || sender.isOp())
				  .filter(c -> label.equals(c.commandLabel))
				  .map(c -> c.argumentsList.stream().filter(l ->
				  {
					  if (l.arguments.size() < strings.length) return false;
			
					  for (int i = 0; i < strings.length - 1; ++i) {
						  String s = strings[i];
						  Argument arg = l.arguments.get(i);
				
						  if (!arg.tabCompletion.apply(sender).contains(s)) return false;
					  }
			
					  return l.arguments.get(strings.length - 1)
							  .tabCompletion.apply(sender).stream().anyMatch(s -> s.startsWith(strings[strings.length - 1]));
				  })).collect(ArrayList::new, (list, stream) -> list.addAll(stream.collect(Collectors.toList())), ArrayList::addAll);
	}
}
