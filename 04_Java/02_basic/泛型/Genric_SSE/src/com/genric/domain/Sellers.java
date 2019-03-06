package com.genric.domain;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Travelsky_CLSUN
 * @Date: Created on 17-5-8
 * @Description: ��������ԱȺ�壬��Ⱥ��ֻ��ˮ��
 */
public class Sellers<T>
{
	// ʹ�����Լ�¼����ĳ��ˮ�����������
	public List<Strawberry> BerryDateList = new ArrayList<Strawberry>();
	public List<Apple> AppleDateList = new ArrayList<Apple>();
	public List<Orange> OrangeDateList = new ArrayList<Orange>();

	// ��ȡ�����ˮ��Ʒ������ʱ���б�
	public List<Apple> getAppleList(List<Apple> eList)
	{
		ArrayList<Apple> appleList = new ArrayList<Apple>();
		return null;
	}

	// ʹ�÷���extends�������ʵ�巽�����ƴ������쳣
	// public void getInfo(T t)
	// {
	// t.showInfo();
	// }

	// ��ȡĳ����ˮ�����������б�
	public List<T> getList(List<T> eProductList)
	{
		Class<?> paramClass = eProductList.getClass();
		System.out.println("before transfromer: " + paramClass);
		Type paramType = paramClass.getTypeParameters()[0].getBounds()[0];
		if (paramType instanceof ParameterizedType)
		{
			System.out.println(paramType);
			ParameterizedType genricType = (ParameterizedType) ((ParameterizedType) paramType)
					.getActualTypeArguments()[0];
			System.out.println("actualType of eProductList is :" + genricType);
		}
		else
		{
			System.out.println("Type of eProductList is :" + paramType);
		}
		return null;
	}

	// ���Է��͵ľ�̬����
	public static <T> T getListByStatic(List<T> eList)
	{
		Class<?> paramClass = eList.getClass();
		System.out.println("before transfromer: " + paramClass);
		Type paramType = paramClass.getTypeParameters()[0].getBounds()[0];
		if (paramType instanceof ParameterizedType)
		{
			System.out.println(paramType);
			ParameterizedType genricType = (ParameterizedType) ((ParameterizedType) paramType)
					.getActualTypeArguments()[0];
			System.out.println("actualType of eList is :" + genricType);
		}
		else
		{
			System.out.println("Type of eList is :" + paramType);
		}
		return null;
	}

}
