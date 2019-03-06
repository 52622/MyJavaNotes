中文 - 应该是谷歌翻译，然后稍加修改而已。。翻译的不好

https://www.w3cschool.cn/quartz_doc/quartz_doc-2put2clm.html

英文

http://www.quartz-scheduler.org/documentation/quartz-2.2.x/tutorials/tutorial-lesson-02.html



## Quartz API

- Scheduler - the main API for interacting with the scheduler.
- Job - an interface to be implemented by components that you wish to have executed by the scheduler.
- JobDetail - used to define instances of Jobs.
- Trigger - a component that defines the schedule upon which a given Job will be executed.
- JobBuilder - used to define/build JobDetail instances, which define instances of Jobs.
- TriggerBuilder - used to define/build Trigger instances.



Scheduler的生命期，从SchedulerFactory创建它时开始，到Scheduler调用shutdown()方法时结束

Scheduler只有在调用start()方法后，才会真正地触发trigger（即执行job）



### Job/JobDetail

实现Job接口的类，表明该job需要完成什么类型的任务 



JobDetail实例是通过JobBuilder类创建

传给scheduler一个JobDetail实例，因为我们在创建JobDetail时，将要执行的job的类名传给了JobDetail，所以scheduler就知道了要执行何种类型的job；每次当scheduler执行job时，在调用其execute(…)方法之前会创建该类的一个新的实例；执行完毕，对该实例的引用就被丢弃了，实例会被垃圾回收 



JobDataMap，execute()执行过程中取出数据，jobDetail和trigger实例化的时候可以放进去，两者使用同一个map，后来的值会覆盖之前的，这里应该是有3个map的

```java
//只能取出jobDetail存储的属性值
JobDataMap dataMap = context.getJobDetail().getJobDataMap();

//只能取出Trigger存储的属性值
JobDataMap dataMap = context.getTrigger().getJobDataMap();

//取出jobDetail和Trigger存储的属性值的并集 - 后来值会覆盖之前的
JobDataMap dataMap = context.getMergedJobDataMap();
```

自动注入，只要写一个变量名和key相同的变量，定义set方法即可



可以只创建一个job类，然后创建多个与该job关联的JobDetail实例，每一个实例都有自己的属性集和JobDataMap，最后，将所有的实例都加到scheduler中 

一个trigger被触发时，与之关联的JobDetail实例会被加载，JobDetail引用的job类通过配置在Scheduler上的JobFactory进行初始化。默认的JobFactory实现，仅仅是调用job类的newInstance()方法，然后尝试调用JobDataMap中的key的setter方法 



Job类注解：

@DisallowConcurrentExecution，不要并发地执行同一个job定义的多个实例，JobDetail

@PersistJobDataAfterExecution，在成功执行了job类的execute方法后（没有发生任何异常），更新JobDetail中JobDataMap的数据



### Trigger



TriggerBuilder 获取，属性：

- jobKey trigger触发要执行哪个job
- startTime 从什么时候开始生效
- endTime 失效时间
- priority 优先级，多个触发器同时触发的时候，比较优先级
- misfire instruction 错过执行，scheduler 重启的时候会先扫描这些触发器
- Calendar 用于排除时间段，比如每天7点闹钟，但是排除节假日



#### SimpleTrigger 

SimpleTrigger include: a start-time, and end-time, a repeat count, and a repeat interval 

SimpleTrigger 获取 SimpleTrigger 触发器

属性：

​	SimpleScheduleBuilder 获取 simpleSchedule 执行策略

​	DateBuilder 获取执行开始和结束时间

#### CronTrigger

表达式参考：https://www.cnblogs.com/lazyInsects/p/8075487.html

更多可选的时间

cron表达式

```shell
1.Seconds （秒）
2.Minutes（分）
3.Hours（小时）
4.Day-of-Month  （天）
5.Month（月）
6.Day-of-Week （周）
7.Year（年)
```



注意一个字符

```shell
? ：用在Day-of-Month和Day-of-Week中，指“没有具体的值”。当两个子表达式其中一个被指定了值以后，为了避免冲突，需要将另外一个的值设为“?”。例如：想在每月20日触发调度，不管20号是星期几，只能用如下写法：0 0 0 20 * ?，其中最后以为只能用“?”，而不能用“*”。
```



### Listeners

触发器或者Job，调用的时候，各个生命周期的钩子函数而已

TriggerListener

JobListeners

```java
scheduler.getListenerManager().addJobListener()
```

SchedulerListeners

```java
scheduler.getListenerManager().addSchedulerListener(mySchedListener);
scheduler.getListenerManager().removeSchedulerListener(mySchedListener);
```



### Job Stores

jobs, triggers, calendars等运行数据存放的位置



RAMJobStore - 内存存放，速度快，重启后无法恢复原来的调度

```shell
org.quartz.jobStore.class = org.quartz.simpl.RAMJobStore
```



jdbcjobstore - 存放到数据库中，对数据库延迟要求比较高(网速等)

```shell
//由quartz管理事务
org.quartz.jobStore.class = org.quartz.impl.jdbcjobstore.JobStoreTX
//由Spring或者其他管理事务
org.quartz.jobStore.class = org.quartz.impl.jdbcjobstore.JobStoreCMT

//数据库驱动
org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate

//表前缀
org.quartz.jobStore.tablePrefix = QRTZ_

//数据库连接池
org.quartz.jobStore.dataSource = myDS

```



其他的一些配置，线程池，数据库连接池，持久化方式，调度器实例

- ThreadPool
- JobStore
- DataSources (if necessary)
- The Scheduler itself



