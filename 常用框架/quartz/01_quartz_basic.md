官网

http://www.quartz-scheduler.org/overview/features.html

调度框架

可以单独运行，也可以集成到Java应用里面，支持集群

几个概念

Job - 作业，有JobName和JobGroup

Trigger - 触发器，有triggerName和triggerGroup

Scheduler - 调度器，jobName+JobGroup构成唯一特征(相同的只能被添加一次)，但是同一个Job可以有多个不同的触发器

JobDetail - 实现了Job接口的类，定义了执行逻辑，可以由quartz初始化，也可以由spring等容器框架初始化

JobListener / TriggerListener - 定义了作业执行前后的操作，比如一个作业完成后，就从secheduler中删除这个作业

JobCompletionCode - 作业完成后的返回给scheduler的错误码，可以用于判断是否重新执行失败作业的功能等



持久化

JobStore - 持久化接口

JDBCJobStore - 关系型数据库的持久化接口

RAMJobStore  - 持久化到内存，重启后丢失



快速入门

maven构建

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.cp</groupId>
    <artifactId>quartzdemo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <dependency>
            <groupId>org.quartz-scheduler</groupId>
            <artifactId>quartz</artifactId>
            <version>2.2.3</version>
        </dependency>
        <dependency>
            <groupId>org.quartz-scheduler</groupId>
            <artifactId>quartz-jobs</artifactId>
            <version>2.2.3</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-simple -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-simple</artifactId>
            <version>1.7.25</version>
        </dependency>
    </dependencies>

</project>
```

配置文件

quartz.properties

参考: http://www.quartz-scheduler.org/documentation/quartz-2.2.x/configuration

引入实例不需要配置文件

QuartzTest.java

```java
package com.cp;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author copywang
 * @description
 * @create 2018-10-14 15:30
 */
public class QuartzTest {
    public static void main(String[] args) {

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            JobDetail job = JobBuilder.newJob(HelloJob.class)
                    .withIdentity("job1","jobGroup1")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1","triggerGroup1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder
                            .simpleSchedule()
                            .withIntervalInSeconds(10)
                            .repeatForever())
                    .build();
            scheduler.scheduleJob(job,trigger);
            //scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}

```

HelloJob.java

```java
package com.cp;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.sound.midi.Soundbank;
import java.util.Date;

/**
 * @author copywang
 * @description
 * @create 2018-10-14 15:33
 */
public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("hello working " + new Date());
    }
}

```

运行main方法，可以看到控制台每隔10s打印一次




