package com.genric.test;

import java.util.List;

public class Test2
{

	public static void main(String[] args)
	{

		Foo<List<String>> foo = new Foo<List<String>>()
		{

		};

		System.out.println(foo.getClass().getGenericSuperclass());

	}

}
