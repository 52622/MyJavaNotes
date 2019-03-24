

```sql
explain select * from person;
-- 模拟优化器执行sql查询语句
```



```sql
id	select_type	table	partitions	type	possible_keys	key	key_len	ref	rows	filtered	Extra
1	SIMPLE	person		ALL					4	100	


```



```sql
id
查询序列号

1. id相同，执行顺序从上到下，select_type=simple
2. id不同，子查询，则id越大的优先级越高，优先被执行，select_type=subquery and primary
3. id相同和不相同的同时存在，id相同的为同一组，从上到下执行，id组内部的按照id值排序，越大的先执行
table=<derived2>指向id为2的行，表示衍生

select_type
查询的类型

1. simple，不包含子查询或union
2. primary，包含子部分，最外层标记，join等
3. subquery，select或者where里面包含了子查询
4. derived，from中包含了子查询，子查询的标记
5. union，第二个select出现在union之后，标记为union，若union包含在from子语句的子查询中，外层的select被标记为derived
6. union result，从union表获取结果的select，id为null

type
访问类型
从好到坏取值：
system > const > eq_ref > ref > fulltext > ref_or_null > index_merge > unique_subquery > index_subquery > range > index > ALL

至少达到range，最好到ref

1. system，表只有一行记录，等于系统表，const类型特例，一般不会出现
2. const，一次索引找到，用于比较primary key或unique索引，例如主键用于where条件
3. eq_ref，唯一性索引扫描，主键或唯一索引
4. ref，非唯一性索引扫描，可能返回多行
5. range，使用一个索引来选择行，key显示了使用的索引，where里面有between/大于小于/in等，不需要扫描全部索引
6. index，读全表的索引树
7. all，全表扫描，硬盘读取


psssible_keys
查询字段有索引，但是不一定被用到，列出来看一下而已

key
实际使用了哪个索引
null表示没有索引

key_len
索引中使用的字节数，最大可能长度，越小越好，根据表定义计算的

ref
索引的哪一列被使用了

row
估算找出记录所需要扫描的行数

extra
其他信息
1. Using filesort 
mysql对数据使用一个外部的索引排序，而不是按照表内的索引进行排序读取。也就是说mysql无法利用索引完成的排序操作成为“文件排序”
索引列在索引的右边，select的时候使用order by右边的一列，那么mysql就会再实现一次排序

2. Using temporary
临时表，出现了order by 和 group by

3. Using index
表示相应的select操作中使用了覆盖索引（Covering Index），避免了访问表的数据行，效率高

和Using where一起出现，表示索引用于执行索引键值的查找
单独出现，表明索引只是用来读取数据，而不是查找动作

覆盖索引（Covering Index）：也叫索引覆盖。就是select列表中的字段，只用从索引中就能获取，不必根据索引再次读取数据文件，换句话说查询列要被所建的索引覆盖。 
注意： 
a、如需使用覆盖索引，select列表中的字段只取出需要的列，不要使用select * 
b、如果将所有字段都建索引会导致索引文件过大，反而降低crud性能

4. Using where
使用了where过滤

5. Using join buffer
使用了链接缓存

6. Impossible WHERE
不可能获取任何行

7. select tables optimized away
没有group by子句，min/max计算的时候，不用等到执行阶段计算

8. distinct
优化操作，找到了第一个值之后旧不再查找同样的值



```















https://blog.csdn.net/wuseyukui/article/details/71512793 MySQL高级 之 explain执行计划详解