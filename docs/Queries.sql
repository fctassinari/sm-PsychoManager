SELECT prd.id_Produto, prd.ds_Nome, extract(month from age.dt_Consulta) as mes, 
case when extract(month from age.dt_Consulta) = 1      then 'Janeiro'      else  
case when extract(month from age.dt_Consulta) = 2      then 'Fevereiro'      else  
case when extract(month from age.dt_Consulta) = 3      then 'Março'      else  
case when extract(month from age.dt_Consulta) = 4      then 'Abril'      else  
case when extract(month from age.dt_Consulta) = 5      then 'Maio'      else  
case when extract(month from age.dt_Consulta) = 6      then 'Junho'      else  
case when extract(month from age.dt_Consulta) = 7      then 'Julho'      else  
case when extract(month from age.dt_Consulta) = 8      then 'Agosto'      else  
case when extract(month from age.dt_Consulta) = 9      then 'Setembro'      else  
case when extract(month from age.dt_Consulta) = 10     then 'Outubro'      else  
case when extract(month from age.dt_Consulta) = 11     then 'Novembro'      else  
case when extract(month from age.dt_Consulta) = 12     then 'Dezembro'      else ''  
end end end end end end end end end end end end as descr, 
COUNT(age.id_Paciente) as qtd, 
sum(age.vl_Preco) as vl     
FROM Tb_Agenda age, tb_produto prd
where 
age.id_Produto = prd.id_Produto and
prd.id_produto = 4 and
age.dt_Consulta >= '2016-01-01 00:00:00' and
age.dt_Consulta <= '2016-12-31 23:59:59'
GROUP BY extract(month from age.dt_Consulta),prd.id_Produto  
ORDER BY mes;


SELECT prd.id_Produto, prd.ds_Nome,extract(month from age.dt_Consulta) as mes, COUNT(age.id_Paciente) as qtd
from 
	tb_agenda age, tb_produto prd 
where 
	age.id_produto = 1 and
	age.dt_Consulta >= '2016-04-01 00:00:00' and
	age.dt_Consulta <= '2016-04-30 23:59:59' and
    prd.id_Produto = age.id_Produto
group by  prd.id_produto,extract(month from age.dt_Consulta) 
    
    
    
select 
	count(age.id_Paciente),sum(age.vl_Preco) as vl  
from
	tb_agenda age 
where
	age.id_produto = 1 and	age.dt_Consulta >= '2016-01-01 00:00:00' and age.dt_Consulta <= '2016-12-31 23:59:59'


  

select prd.id_Produto, prd.ds_Nome,count(age.id_Paciente),sum(age.vl_Preco) as vl,4,
case when 4 = 1      then 'Janeiro'      else  
case when 4 = 2      then 'Fevereiro'      else  
case when 4 = 3      then 'Março'      else  
case when 4 = 4      then 'Abril'      else  
case when 4 = 5      then 'Maio'      else  
case when 4 = 6      then 'Junho'      else  
case when 4 = 7      then 'Julho'      else  
case when 4 = 8      then 'Agosto'      else  
case when 4 = 9      then 'Setembro'      else  
case when 4 = 10     then 'Outubro'      else  
case when 4 = 11     then 'Novembro'      else  
case when 4 = 12     then 'Dezembro'      else ''  
end end end end end end end end end end end end
from 	
	tb_agenda age, tb_produto prd   
where 
	age.id_produto = 1 and
	extract(month from age.dt_Consulta) = 6 and
	extract(year from age.dt_Consulta)= 2016 and
    prd.id_Produto = age.id_Produto
group by  prd.id_produto    
    
    
