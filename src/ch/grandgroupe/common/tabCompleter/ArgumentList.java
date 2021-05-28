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

	/**
	 * Constructor for the features
	 * @param arg the feature's argument
	 * @param name the feature's name
	 */
	public ArgumentList(Argument arg, String name){
		this.description = "Enable or disable the " + name;
		this.arguments = Misc.list(arg, Argument.BOOLEAN);
	}
}
