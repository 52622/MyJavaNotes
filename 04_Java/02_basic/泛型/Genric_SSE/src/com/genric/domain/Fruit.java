package com.genric.domain;

/**
 * @Author: Travelsky_CLSUN
 * @Date: Created on 17-5-8.����3:39
 * @Description: ˮ����
 */
public class Fruit
{
	private String name;
	private String color;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}

	public void showInfo()
	{
		System.out.println(this.name + ":" + this.color);
	}

}
