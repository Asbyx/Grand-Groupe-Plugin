package ch.thechi2000asbyx.common.tabCompleter;

import ch.thechi2000asbyx.common.utils.Misc;

import java.util.List;

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
