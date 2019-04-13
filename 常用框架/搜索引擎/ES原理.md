https://github.com/doocs/advanced-java/blob/master/docs/high-concurrency/es-architecture.md



## elasticsearch

分布式搜索引擎，底层lucene

多台机器上启动多个es进程实例，形成一个es集群

es的master节点：维护索引元数据，负责切换primary和replica shard





组成

index

type

mapping

document

field



订单index，一个index多个type

实物type订单

虚拟商品type订单

一个type一个mapping，相当于表结构定义

一个document就是一个记录，field就是字段值

![es-index-type-mapping-document-field](assets/es-index-type-mapping-document-field.png) 



索引有多个shard，分别存储部分数据，支持横向扩展

shard有primary shard（主，写入，同步给副本）和replica shard（副本）

![es-cluster](assets/es-cluster.png) 



## 写数据过程

