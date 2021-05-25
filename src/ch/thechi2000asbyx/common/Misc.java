package ch.thechi2000asbyx.common;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class Misc
{
	public static <T> List<T> list()
	{
		return new ImmutableList.Builder<T>().build();
	}
	@SafeVarargs
	public static <T> List<T> list(T... args)
	{
		return new ImmutableList.Builder<T>().add(args).build();
	}
	public static <T> List<T> list(Iterable<T> iterable)
	{
		return new ImmutableList.Builder<T>().addAll(iterable).build();
	}
}
