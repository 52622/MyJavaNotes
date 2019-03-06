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
 * @Description: 测试类
 */
public class TestGenric
{
	/**
	 * @Author: Travelsky_CLSUN
	 * @Date: Created on 17-5-8
	 * @Description: 测试泛型实参是否被擦除
	 */
	@Test
	public void testGenricTypeExit() throws Exception
	{
		Class<?> sellersTemp = Sellers.class;
		ParameterizedType actualType = null;
		log("====根据反射获取泛型类、泛型字段以及泛型方法的类型====");
		// 测试使用反射获取泛型类的实际参数
		log("泛型类Sellers的泛型实参：" + sellersTemp.getTypeParameters()[0].getBounds()[0].toString());
		// 测试使用反射获取泛型类下泛型变量的实际参数
		Field fieldBerry = sellersTemp.getField("BerryDateList");
		log("泛型字段BerryDateList的类型：" + fieldBerry.getGenericType().toString());
		Method methodAppleList = sellersTemp.getMethod("getAppleList", new Class[]
		{ List.class });
		actualType = (ParameterizedType) methodAppleList.getGenericParameterTypes()[0];
		log("泛型方法getAppleList的形式参数的类型：" + actualType.getActualTypeArguments()[0].toString());
		actualType = (ParameterizedType) methodAppleList.getGenericReturnType();
		log("泛型方法getAppleList的返回值的类型：" + actualType.getActualTypeArguments()[0].toString());
		Class<?> selfEmployed = SelfEmployed.class;
		actualType = (ParameterizedType) selfEmployed.getGenericSuperclass();
		log("泛型子类SelfEmployed的主类实参：" + actualType.getActualTypeArguments()[0]);
	}

	// 测试在泛型方法内动态获取泛型实参
	@Test
	public void testGetParameterType() throws Exception
	{
		Sellers<Apple> appleSellers = new Sellers<Apple>();
		appleSellers.getList(new ArrayList<Apple>());
	}

	// 测试调用静态泛型方法
	@Test
	public void testStaticGernicMethod()
	{
		Sellers.getListByStatic(new ArrayList<Apple>());
	}

	// 再次确认通过类型实例和反射只能获取到签名内容
	@Test
	public void testGetTypeByInstance()
	{
		Sellers<Apple> sellers = new Sellers<Apple>();
		// 返回T则说明没法通过反射动态获取到泛型实参
		log(sellers.getClass().getTypeParameters()[0].toString());
		// 测试通过获取主类的形式获取泛型
		log(((ParameterizedType) sellers.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0].toString());
	}

	// 测试通过匿名内部类获取泛型实参
	@Test
	public void testGetTypeByAnonymousClass()
	{
		Sellers<Apple> sellers = null;
		// 主类类型
		Type sellerType = null;
		// 泛型实参类型
		Type paramType = null;
		log("-------非匿名子类+反射获取到的类类型---------");
		sellers = new Sellers<Apple>();
		sellerType = sellers.getClass().getGenericSuperclass();
		log("Sellers主类带参数泛型实体为:" + sellerType.toString());
		// 无法转换，在方法体内定义的泛型实参，均向上擦除为Object(无签名)
		// paramType = ((ParameterizedType) sellerType).getActualTypeArguments()[0];
		log("--------匿名子类+反射获取到的类类型---------");
		sellers = new Sellers<Apple>()
		{
		};
		sellerType = sellers.getClass().getGenericSuperclass();
		// 返回T则说明没法通过反射动态获取到泛型实参
		log("Sellers主类带参数泛型实体为:" + sellerType.toString());
		paramType = ((ParameterizedType) sellerType).getActualTypeArguments()[0];
		log("泛型实体的类型为：" + paramType);
	}

	/**
	 * @Author: Travelsky_CLSUN
	 * @Date: Created on 17-5-8
	 * @Description: 输出函数
	 */
	private void log(String str)
	{
		System.out.println(str);
	}
}
