package ch.thechi2000asbyx.common;

import com.google.common.collect.*;

import java.util.*;

public class Misc
{
	public static <T> List<T> list() {
		return new ImmutableList.Builder<T>().build();
	}
	@SafeVarargs
	public static <T> List<T> list(T... args) {
		return new ImmutableList.Builder<T>().add(args).build();
	}
	public static <T> List<T> list(Iterable<T> iterable) {
		return new ImmutableList.Builder<T>().addAll(iterable).build();
	}
	
	public static <K, V> Map<K, V> map() {
		return new ImmutableMap.Builder<K, V>().build();
	}
}
