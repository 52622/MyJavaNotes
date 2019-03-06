link

https://github.com/CyC2018/CS-Notes/blob/master/docs/notes/Java%20%E5%9F%BA%E7%A1%80.md



基本类型

byte 8

char/short 16

int/float 32

long/double 64

boolean

**boolean 只有两个值：true、false，可以使用 1 bit 来存储，但是具体大小没有明确规定。JVM 会在编译时期将 boolean 类型的数据转换为 int，使用 1 来表示 true，0 表示 false。JVM 并不支持 boolean 数组，而是使用 byte 数组来表示 int 数组来表示。**



包装类型

```java
Integer x = 2;     // 装箱
int y = x;         // 拆箱
```





