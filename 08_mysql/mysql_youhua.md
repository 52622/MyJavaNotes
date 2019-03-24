## 数据库优化

1. 合适的字段属性和长度

字段宽度尽量小，char代替varchar，mediumint代替bigint

字段尽量设置为notnull，避免比较null值

定义枚举值，比如性别，数值比文本检索快



2. 使用join代替subqueries

不需要创建临时表



3. union代替手动创建临时表



4. 使用事务

```sql
begin;
insert into xxx values (xx,xx);
commit;
-- 事务锁定数据库，方便回滚操作，避免脏数据

```



5. 锁定表

```sql
-- 只锁定表，避免独占数据库
locktable person write select name from person where age=18;
...
update person set age = 20 where name = 'xiaoming';
unlocktables

-- 保证sql执行过程中不会有其他插入或者删除操作

```



6. 使用外键保证数据关联性和删除
7. 使用索引

索引列选择join/where/order by的

大量重复值的，不能建索引

索引字段不要使用函数操作，否则不走索引

尽量不用like，可以用<>

尽量避免自动类型转换



8个方法

1. 加索引
2. 复合索引，最常用的放左边
3. 索引尽量不要包含null值的列，为null则复合索引无效
4. 短索引，选前几个唯一性比较大的做索引，不要全列索引
5. 排序问题，查询只会使用一个索引，如果where里面用了，order by里面不会使用，尽量不要使用多个列排序，需要时创建复合索引
6. like，用右百分号
7. 列上不要使用函数
8. 不使用not in和<>，都不走索引，not in可以使用not exists，id<>3 用 id>3 or id <3





## sql优化

1. 优先在where和order by上建索引

2. where条件避免null值判断，会全表扫描

3. where条件不要使用!= <>，会全表扫描

4. where条件避免使用or连接，可以使用union all把两个连接起来

5. in 和 not in，连续值使用between，exists代替in，

   ```sql
   select num from a where exists(select 1 from b where num=a.num);
   ```

6. like，使用右%

7. where不要带参数，不要带计算表达式

   ```sql
   select id from t where num = @num
   ->
   select id from t with(index(index_for_num)) where num = @num
   -- 强制查询使用索引
   
   
   ```

8. Where不要使用表达式

   ```sql
   select id from t where num/2 = 100;
   ->
   select id from t where num = 100*2;
   ```

9. 避免where语句中的函数操作

   ```sql
   select id from t where substring(name,1,3) = ’abc’       -–name以abc开头的id
   select id from t where datediff(day,createdate,’2005-11-30′) = 0    -–‘2005-11-30’    --生成的id
   
   ->
   
   select id from t where name like 'abc%'
   select id from t where createdate >= '2005-11-30' and createdate < '2005-12-1'
   ```

10. 复合索引，如果没有用到第一个字段作为条件，索引是不生效的

11. 大数据量join，先分页，再join

12. 不要用select count(*) from xxx;

13. 一个表的索引最好不要超过6个

14. 建历史表的时候，数据量大用select into，减少log，数据量小，先create table再insert

15. 避免使用游标，效率低











https://blog.csdn.net/baidu_37107022/article/details/77460464 MySQL/Oracle数据库优化总结（非常全面）