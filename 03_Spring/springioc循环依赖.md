```java
public class BeanB {
    private BeanA beanA;
}

public class BeanA {
    private BeanB beanB;
}
```

IOC 容器在读到上面的配置时，会按照顺序，先去实例化 beanA。然后发现 beanA 依赖于 beanB，接在又去实例化 beanB ，在容器再次发现 beanB 依赖于 beanA 时，容器会获取 beanA 对象的一个早期的引用（early reference），并把这个早期引用注入到 beanB 中，让 beanB 先完成实例化。beanB 完成实例化，beanA 就可以获取到 beanB 的引用，beanA 随之完成实例化。 

”早期引用“是指向原始对象的引用。

原始对象是指刚创建好的对象，但还未填充属性。 



