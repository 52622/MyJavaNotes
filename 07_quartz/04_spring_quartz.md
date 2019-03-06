集成quartz

https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/integration.html#scheduling-quartz

将定时任务交由spring容器来管理

Spring提供：

参考：https://blog.csdn.net/earl_yuan/article/details/50668864

### 作业

JobDetailFactoryBean - 用于创建Quartz JobDetail实例，可以传递参数

任务类需要继承QuartzJobBean类，并覆盖其executeInternal方法

```java
public class SimpleJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext arg0)
            throws JobExecutionException {
        System.out.println("现在时间为："+new Date());
        //可以通过上下文获取到JobDataMap，这里面可以存放一些参数类型的数据
        JobDataMap dataMap=arg0.getMergedJobDataMap();
        String wish=(String) dataMap.get("wish");
        System.out.println(wish);
    }
}
```

```xml
<bean id="jobDetailFactoryBeanExample" class="org.springframework.scheduling.quartz.JobDetailFactoryBean">
    <!-- 
        参考源码，我们可以看到属性jobClass为Class类型，所以不能使用ref来引用一个bean，否则就会因为不能将bean转换为Class类型而出现异常。
        <property name="jobClass" ref="simpleJob"/>
        必须使用value对jobClass赋值。 
        <property name="jobClass" value="com.earl.quartz.spring.job.SimpleJob"/>    
    -->
    <property name="jobClass" value="com.earl.quartz.spring.job.SimpleJob"/>
    <!-- 这里设置的jobDataAsMap可以传递一些参数给作业任务 -->
    <property name="jobDataAsMap">
        <map>
            <entry key="wish" value="hello"/>
        </map>
    </property>
</bean>
```



MethodInvokingJobDetailFactoryBean - 可以指定作业调度时执行的内容

任务类，不需要实现任何接口，可以直接指定要执行的方法

```java
public class ExampleJob{

    public void execute(){
        System.out.println("现在是"+new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(new Date()));
    }

}
```



```xml
<!-- 
    如果两个触发器触发同一个作业，那么第二个作业可能在第一个作业完成之前被触发。
    将作业类实现StatefulJob接口就可以避免这种情况。
    将concurrent设置为false可以避免并发的发生。
-->
<!-- 使用MethodInvokingJobDetailFactoryBean来创建作业对象 -->
<bean id="exampleJob" class="com.earl.quartz.spring.job.ExampleJob"/>
<bean id="methodInvokingJobDetailFactoryBeanExample" class="org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean">
<!-- 目标对象，指的是作业任务的实现类 -->
<property name="targetObject" ref="exampleJob"/>
<!-- 目标方法，指的是指定实现类中的哪个方法作为调度时的执行方法 -->
<property name="targetMethod" value="execute"/>
<!-- 是否并发 -->
<property name="concurrent" value="false"/>
</bean>
```



### 触发器

SimpleTriggerFactoryBean

CronTriggerFactoryBean

```xml
<bean id="simpleTrigger" class="org.springframework.scheduling.quartz.SimpleTriggerFactoryBean">
    <!-- see the example of method invoking job above -->
    <property name="jobDetail" ref="jobDetail"/>
    <!-- 10 seconds -->
    <property name="startDelay" value="10000"/>
    <!-- repeat every 50 seconds -->
    <property name="repeatInterval" value="50000"/>
</bean>

<bean id="cronTrigger" class="org.springframework.scheduling.quartz.CronTriggerFactoryBean">
    <property name="jobDetail" ref="exampleJob"/>
    <!-- run every morning at 6 AM -->
    <property name="cronExpression" value="0 0 6 * * ?"/>
</bean>
```

### 调度器

```xml
<bean class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
    <property name="triggers">
        <list>
            <ref bean="cronTrigger"/>
            <ref bean="simpleTrigger"/>
        </list>
    </property>
</bean>
```



SpringBoot的参考

https://www.jianshu.com/p/df13ec7d9169

把Job和Scheduler交给Spring管理

https://www.liangzl.com/get-article-detail-16666.html

