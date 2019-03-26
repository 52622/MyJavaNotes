分布式系统不可能同时满足一致性（C：Consistency）、可用性（A：Availability）和分区容忍性（P：Partition Tolerance），最多只能同时满足其中两项 



多个数据副本保持数据一致



系统可用时间占所有时间的比重，4个9表示99.99%



区域划分，多个网络分区



P必须，因为网络不可靠

C和A做权衡



保持一致性，不能访问还没同步数据的节点，失去了部分可用性



保持可用性，数据不一致



一致性协议

2pc 两阶段提交











https://tech.youzan.com/cap-coherence-protocol-and-application-analysis/

https://github.com/CyC2018/CS-Notes/blob/master/docs/notes/%E5%88%86%E5%B8%83%E5%BC%8F.md#%E4%B8%89cap

