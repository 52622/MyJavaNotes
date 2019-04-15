https://www.jianshu.com/p/7e47a4503b87



https://github.com/doocs/advanced-java/blob/master/docs/distributed-system/distributed-lock-redis-vs-zookeeper.md



#### RedLock 算法

这个场景是假设有一个 redis cluster，有 5 个 redis master 实例。然后执行如下步骤获取一把锁：

1. 获取当前时间戳，单位是毫秒；
2. 跟上面类似，轮流尝试在每个 master 节点上创建锁，过期时间较短，一般就几十毫秒；
3. 尝试在**大多数节点**上建立一个锁，比如 5 个节点就要求是 3 个节点 `n / 2 + 1`；
4. 客户端计算建立好锁的时间，如果建立锁的时间小于超时时间，就算建立成功了；
5. 要是锁建立失败了，那么就依次之前建立过的锁删除；
6. 只要别人建立了一把分布式锁，你就得**不断轮询去尝试获取锁**。

![redis-redlock](assets/redis-redlock.png) 

