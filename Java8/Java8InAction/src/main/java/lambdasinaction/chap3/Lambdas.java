package lambdasinaction.chap3;

import java.util.*;

public class Lambdas {
	/**
	 * 功能描述:
	 *
	 * lambdas表达式
	 * 函数式编程：how to do
	 * 面向对象编程:what to do
	 *
	 * 类型推导
	 * 方法推导（可以是实例，类）
	 */
	public static void main(String ...args){

		// Simple example
		Runnable r = () -> System.out.println("Hello!");
		r.run();
		// Runnable是使用了@FunctionalInterface注解的函数式接口，可以直接使用方法推导
		new Thread(System.out::println).start();

		// Filtering with lambdas
		List<Apple> inventory = Arrays.asList(new Apple(80,"green"), new Apple(155, "green"), new Apple(120, "red"));

		// [Apple{color='green', weight=80}, Apple{color='green', weight=155}]	
		List<Apple> greenApples = filter(inventory, (Apple a) -> "green".equals(a.getColor()));
		System.out.println(greenApples);
		//这么写也可以，类型可以自动推导
		filter(inventory,apple -> "green".equals(apple.getColor()));


		//比较
		Comparator<Apple> c = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
		//使用工具类的写法
		Comparator.comparing(Apple::getWeight);

		// [Apple{color='green', weight=80}, Apple{color='red', weight=120}, Apple{color='green', weight=155}]
		inventory.sort(c);//使用List的sort方法
		System.out.println(inventory);
	}

	public static List<Apple> filter(List<Apple> inventory, ApplePredicate p){
		List<Apple> result = new ArrayList<>();
		for(Apple apple : inventory){
			if(p.test(apple)){
				result.add(apple);
			}
		}
		return result;
	}   

	public static class Apple {
		private int weight = 0;
		private String color = "";

		public Apple(int weight, String color){
			this.weight = weight;
			this.color = color;
		}

		public Integer getWeight() {
			return weight;
		}

		public void setWeight(Integer weight) {
			this.weight = weight;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String toString() {
			return "Apple{" +
					"color='" + color + '\'' +
					", weight=" + weight +
					'}';
		}
	}

	interface ApplePredicate{
		public boolean test(Apple a);
	}
}