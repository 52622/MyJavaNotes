package com.genric.domain;

/**
 * @Author: Travelsky_CLSUN
 * @Date: Created on 17-5-8
 * @Description: 卖橙的个体户
 * 在继承泛型类的时候必须明确继承类的泛型实参，如果父类属于不确定的泛型内容，则必须把子类也声明为不确定的泛型
 * eg：class SelfEmployed extends Sellers<T> 错误，必须指明子类的泛型参数
 * 正确签名如下：
 * 1.class SelfEmployed<T> extends Sellers<T>
 * 2.class SelfEmployed extends Sellers<Fruit>
 **/
public class SelfEmployed extends Sellers<Orange>
{

}
