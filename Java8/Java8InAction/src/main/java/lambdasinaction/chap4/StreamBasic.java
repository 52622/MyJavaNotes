package lambdasinaction.chap4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class StreamBasic {

    /**
     * 功能描述:
     *
     * 对比了 java7和8 对于集合的处理形式
     * 很明显的可以看到
     * 7对于集合的处理，代码量多，需要自己生产多个集合来存放筛选后的结果，以及存放返回的结果
     * 8使用的stream流处理，提供了基本的API，把7上的一些需要自己实现的筛选过程做了封装，实现了链式调用的函数式编程方式
     *
     * 函数式编程：告诉应用程序怎么做，比如先筛选，在排序，然后取出结果，映射成一个列表
     * 面向对象编程：告诉应用程序，每一步针对每个对象做什么，最后返回什么
     *
     */
    public static void main(String...args){
        // Java 7
        getLowCaloricDishesNamesInJava7(Dish.menu).forEach(System.out::println);

        System.out.println("---");

        // Java 8
        getLowCaloricDishesNamesInJava8(Dish.menu).forEach(System.out::println);

    }

    public static List<String> getLowCaloricDishesNamesInJava7(List<Dish> dishes){
        // 遍历，筛选
        List<Dish> lowCaloricDishes = new ArrayList<>();
        for(Dish d: dishes){
            if(d.getCalories() < 400){
                lowCaloricDishes.add(d);
            }
        }
        // 排序
        List<String> lowCaloricDishesName = new ArrayList<>();
        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            public int compare(Dish d1, Dish d2){
                return Integer.compare(d1.getCalories(), d2.getCalories());
            }
        });
        // 遍历，取出菜的名称
        for(Dish d: lowCaloricDishes){
            lowCaloricDishesName.add(d.getName());
        }
        return lowCaloricDishesName;
    }

    public static List<String> getLowCaloricDishesNamesInJava8(List<Dish> dishes){
        return dishes.stream() //生成流
                .filter(d -> d.getCalories() < 400) //过滤器，里面带的是一个函数式接口
                .sorted(comparing(Dish::getCalories)) //排序，传入一个Comparator接口的实现，这里采用了comparing工具类，接收一个Function参数，接受一个T返回一个R
                .map(Dish::getName)//map，作用到集合里的每个元素上，对元素做处理，并返回一个值
                .collect(toList());//collect，把结果映射成一个List
    }
}
