# 接口的默认方法

主要是解决父接口增加方法，所有子实现都必须改的问题

```java
interface Formula {
    double calculate(int a);
    
    default double sqrt(int a) {
        return Math.sqrt(a)
    }
}
```























































**书**

java 8 in action

**面试题**

https://github.com/Snailclimb/JavaGuide/blob/master/docs/java/What's%20New%20in%20JDK8/Java8Tutorial.md



