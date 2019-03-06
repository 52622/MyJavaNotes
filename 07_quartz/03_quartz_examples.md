参考

http://www.quartz-scheduler.org/documentation/quartz-2.2.x/examples/#where-to-find-the-examples



初始化Scheduler

```java
SchedulerFactory sf = new StdSchedulerFactory();
Scheduler sched = sf.getScheduler();
```

Job和Trigger

```java
JobDetail job1 = newJob(ColorJob.class).withIdentity("job1", "group1").build();

SimpleTrigger trigger1 = newTrigger().withIdentity("trigger1", "group1").startAt(startTime)
        .withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(4)).build();

```

参数传递

```java
job1.getJobDataMap().put("favorite color", "Green");
job1.getJobDataMap().put("count", 1);
```

取出参数 - Job实现类

```java
public void execute(JobExecutionContext context)
        throws JobExecutionException {

        // This job simply prints out its job name and the
        // date and time that it is running
        JobKey jobKey = context.getJobDetail().getKey();
        
        // Grab and print passed parameters
        JobDataMap data = context.getJobDetail().getJobDataMap();
        String favoriteColor = data.getString(FAVORITE_COLOR);
        int count = data.getInt(EXECUTION_COUNT);
    }
```

注册到Scheduler

```java
sched.scheduleJob(job1, trigger1);
```

启动

```java
sched.start();
```

获取已执行的作业数量

```java
sched.getMetaData().getNumberOfJobsExecuted()
```



### missFire

https://www.cnblogs.com/skyLogin/p/6927629.html



### 恢复机制

```java
job = newJob(SimpleRecoveryJob.class).withIdentity("job_" + count, schedId) // put triggers in group named after
                                                                                  // the cluster node instance just to
                                                                                  // distinguish (in logging) what was
                                                                                  // scheduled from where
          .requestRecovery() // ask scheduler to re-execute this job if it was in progress when the scheduler went
                             // down...
          .build();
```



### 持久化到数据库

配置样例

```xml

#============================================================================
# Configure Main Scheduler Properties  
#============================================================================

org.quartz.scheduler.instanceName: TestScheduler
org.quartz.scheduler.instanceId: instance_one

org.quartz.scheduler.skipUpdateCheck: true

#============================================================================
# Configure ThreadPool  
#============================================================================

org.quartz.threadPool.class: org.quartz.simpl.SimpleThreadPool
org.quartz.threadPool.threadCount: 5
org.quartz.threadPool.threadPriority: 5

#============================================================================
# Configure JobStore  
#============================================================================

org.quartz.jobStore.misfireThreshold: 60000

org.quartz.jobStore.class=org.quartz.impl.jdbcjobstore.JobStoreTX
org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.PostgreSQLDelegate
org.quartz.jobStore.useProperties=false
org.quartz.jobStore.dataSource=myDS
org.quartz.jobStore.tablePrefix=QRTZ_
org.quartz.jobStore.isClustered=true

#============================================================================
# Other Example Delegates
#============================================================================
#org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.DB2v6Delegate
#org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.DB2v7Delegate
#org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.DriverDelegate
#org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.HSQLDBDelegate
#org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.MSSQLDelegate
#org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.PointbaseDelegate
#org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.PostgreSQLDelegate
#org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.StdJDBCDelegate
#org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.WebLogicDelegate
#org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.oracle.OracleDelegate
#org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.oracle.WebLogicOracleDelegate

#============================================================================
# Configure Datasources  
#============================================================================

org.quartz.dataSource.myDS.driver: org.postgresql.Driver
org.quartz.dataSource.myDS.URL: jdbc:postgresql://localhost:5432/quartz
org.quartz.dataSource.myDS.user: quartz
org.quartz.dataSource.myDS.password: quartz
org.quartz.dataSource.myDS.maxConnections: 5
org.quartz.dataSource.myDS.validationQuery: select 0

#============================================================================
# Configure Plugins 
#============================================================================

#org.quartz.plugin.shutdownHook.class: org.quartz.plugins.management.ShutdownHookPlugin
#org.quartz.plugin.shutdownHook.cleanShutdown: true


#org.quartz.plugin.triggHistory.class: org.quartz.plugins.history.LoggingJobHistoryPlugin

```

