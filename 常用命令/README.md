记录一些常用的命令

## MySQL

```sql
sql server 数表: 
select count(1) from sysobjects where xtype=’U’
数视图: 
select count(1) from sysobjects where xtype=’V’
数存储过程 
select count(1) from sysobjects where xtype=’P’
所有表名 
SELECT * FROM INFORMATION_SCHEMA.TABLES
查询表的所有字段名： 
SELECT NAME FROM SYSCOLUMNS WHERE ID=OBJECT_ID(’ 表名’ )
SELECT * FROM INFORMATION_SCHEMA.TABLES
SELECT * FROM INFORMATION_SCHEMA.VIEWS
SELECT * FROM INFORMATION_SCHEMA.COLUMNS


查询数据库中所有表名
select table_name from information_schema.tables where table_schema='csdb' and table_type='base table';


查询指定数据库中指定表的所有字段名column_name
select column_name from information_schema.columns where table_schema='csdb' and table_name='users'
```



## centos

```shell
1、打开centos的yum文件夹
输入命令cd  /etc/yum.repos.d/

2、用wget下载repo文件
输入命令wget  http://mirrors.aliyun.com/repo/Centos-7.repo
如果wget命令不生效，说明还没有安装wget工具，输入yum -y install wget 回车进行安装。
当前目录是/etc/yum.repos.d/，刚刚下载的Centos-7.repo也在这个目录上

3、备份系统原来的repo文件
mv  CentOs-Base.repo CentOs-Base.repo.bak
即是重命名 CentOs-Base.repo -> CentOs-Base.repo.bak

4、替换系统原理的repo文件
mv Centos-7.repo CentOs-Base.repo
即是重命名 Centos-7.repo -> CentOs-Base.repo

5、执行yum源更新命令
yum clean all
yum makecache
yum update
依次执行上述三条命令即配置完毕。
```

