```sql
create database db_name;

drop database db_name;

create table depart (
	dept_id int(11) not null auto_increment,
    dept_name varchar(255) default null,
    primary key (dept_id)
);

create table tab_new like tab_old; -- 会复制完整字段结构和索引，不会复制数据

create table tab_new as select col1,col2,... from tab_old definition only; -- 不复制索引

create tab_new as select * from tab_old; -- 只会复制所有数据，不会复制索引

drop table tb_name;

alter table tb_name add column col_name type;

alter table tb_name add primary key(col);

alter table tb_name drop primary key;

create index idx_name on table(col1,clo2...);
create unique index idx_name on table(col1,clo2...);

drop index idx_name;

create view v_name as select xxx from tb_name;

drop view v_name;

select * from tb_name where clo > xx;
insert into tb_name(col1,col2,...) values(v1,v2,...);
delete from tb_name where col > xx;
update tb_name set col1=v1 where col > xx;
select * from tb1 where col like '%v1%';
select * from tb1 order by col1,col2 desc;
select count(1) as total from tb1;
select sum(col1) as sumvalue from tb1;
-- avg
-- max
-- min

group by

select * from tb1 into tb2;

insert into tb1 (select * from tb2);

replace into tb1 (select * from tb2);

select a,b,c from tb1 where a in (select a from tb2);

select a.col1 from tb1 a
left out join
tb2 b
on a.col1 = b.col2;

select * from (select * from b) a where a.xx = 1;

between

limit

join,left join,right join,outter join




```

