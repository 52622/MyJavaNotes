package com.genric.test;

public class Foo<T>
{

	Class<T> type;

	public Foo()
	{
		System.out.println(getClass());
		this.type = (Class<T>) getClass();
	}

}
