select 
@c2 last_c2,@c2:=c2 cur_c2
from
testlag,
(select @c2:=0) r
;

/*
select @x:=ifnull(@x,0)+1 as rn2,@lead lead_a,@lead:=a as 'a' from 
(select @x:=ifnull(@x,0)+1 as rn1, a from test order by rn1 desc) x,(select @lead:='') r order by rn2 desc;
*/
-- https://tdcq.iteye.com/blog/1931981