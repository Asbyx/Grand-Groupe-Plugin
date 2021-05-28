package ch.grandgroupe.common.tabCompleter;

import ch.grandgroupe.common.utils.Misc;

import java.util.List;

/**
 * Class used for the Tab Completer
 * Do not modify
 */
public class ArgumentList
{
	public final String description;
	public final List<Argument> arguments;
	
	public ArgumentList(String description, List<Argument> arguments) {
		this.description = description;
		this.arguments   = arguments;
	}
	public ArgumentList(String description, Argument ... args)
	{
		this(description, Misc.list(args));
	}
}
