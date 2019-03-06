package com.genric.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.genric.domain.Apple;
import com.genric.domain.SelfEmployed;
import com.genric.domain.Sellers;

/**
 * @Author: Travelsky_CLSUN
 * @Date: Created on 17-5-8
 * @Description: ������
 */
public class TestGenric
{
	/**
	 * @Author: Travelsky_CLSUN
	 * @Date: Created on 17-5-8
	 * @Description: ���Է���ʵ���Ƿ񱻲���
	 */
	@Test
	public void testGenricTypeExit() throws Exception
	{
		Class<?> sellersTemp = Sellers.class;
		ParameterizedType actualType = null;
		log("====���ݷ����ȡ�����ࡢ�����ֶ��Լ����ͷ���������====");
		// ����ʹ�÷����ȡ�������ʵ�ʲ���
		log("������Sellers�ķ���ʵ�Σ�" + sellersTemp.getTypeParameters()[0].getBounds()[0].toString());
		// ����ʹ�÷����ȡ�������·��ͱ�����ʵ�ʲ���
		Field fieldBerry = sellersTemp.getField("BerryDateList");
		log("�����ֶ�BerryDateList�����ͣ�" + fieldBerry.getGenericType().toString());
		Method methodAppleList = sellersTemp.getMethod("getAppleList", new Class[]
		{ List.class });
		actualType = (ParameterizedType) methodAppleList.getGenericParameterTypes()[0];
		log("���ͷ���getAppleList����ʽ���������ͣ�" + actualType.getActualTypeArguments()[0].toString());
		actualType = (ParameterizedType) methodAppleList.getGenericReturnType();
		log("���ͷ���getAppleList�ķ���ֵ�����ͣ�" + actualType.getActualTypeArguments()[0].toString());
		Class<?> selfEmployed = SelfEmployed.class;
		actualType = (ParameterizedType) selfEmployed.getGenericSuperclass();
		log("��������SelfEmployed������ʵ�Σ�" + actualType.getActualTypeArguments()[0]);
	}

	// �����ڷ��ͷ����ڶ�̬��ȡ����ʵ��
	@Test
	public void testGetParameterType() throws Exception
	{
		Sellers<Apple> appleSellers = new Sellers<Apple>();
		appleSellers.getList(new ArrayList<Apple>());
	}

	// ���Ե��þ�̬���ͷ���
	@Test
	public void testStaticGernicMethod()
	{
		Sellers.getListByStatic(new ArrayList<Apple>());
	}

	// �ٴ�ȷ��ͨ������ʵ���ͷ���ֻ�ܻ�ȡ��ǩ������
	@Test
	public void testGetTypeByInstance()
	{
		Sellers<Apple> sellers = new Sellers<Apple>();
		// ����T��˵��û��ͨ�����䶯̬��ȡ������ʵ��
		log(sellers.getClass().getTypeParameters()[0].toString());
		// ����ͨ����ȡ�������ʽ��ȡ����
		log(((ParameterizedType) sellers.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0].toString());
	}

	// ����ͨ�������ڲ����ȡ����ʵ��
	@Test
	public void testGetTypeByAnonymousClass()
	{
		Sellers<Apple> sellers = null;
		// ��������
		Type sellerType = null;
		// ����ʵ������
		Type paramType = null;
		log("-------����������+�����ȡ����������---------");
		sellers = new Sellers<Apple>();
		sellerType = sellers.getClass().getGenericSuperclass();
		log("Sellers�������������ʵ��Ϊ:" + sellerType.toString());
		// �޷�ת�����ڷ������ڶ���ķ���ʵ�Σ������ϲ���ΪObject(��ǩ��)
		// paramType = ((ParameterizedType) sellerType).getActualTypeArguments()[0];
		log("--------��������+�����ȡ����������---------");
		sellers = new Sellers<Apple>()
		{
		};
		sellerType = sellers.getClass().getGenericSuperclass();
		// ����T��˵��û��ͨ�����䶯̬��ȡ������ʵ��
		log("Sellers�������������ʵ��Ϊ:" + sellerType.toString());
		paramType = ((ParameterizedType) sellerType).getActualTypeArguments()[0];
		log("����ʵ�������Ϊ��" + paramType);
	}

	/**
	 * @Author: Travelsky_CLSUN
	 * @Date: Created on 17-5-8
	 * @Description: �������
	 */
	private void log(String str)
	{
		System.out.println(str);
	}
}
