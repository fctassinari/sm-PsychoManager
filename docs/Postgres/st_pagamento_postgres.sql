update tb_agenda set st_pagamento = 0;

select count(1) from tb_agenda where st_pg = true;
 
update tb_agenda set st_pagamento =  1 where st_pg = true;

select count(1) from tb_agenda where st_pagamento = 1;
 
 
----------------------------------------------------------------------------------------- 
    select
        tbagenda0_.DT_CONSULTA as DT_CONSU1_0_,
        tbagenda0_.ID_PACIENTE as ID_PACIE2_0_,
        tbagenda0_.DS_OBS as DS_OBS3_0_,
        tbagenda0_.DT_CONSULTA_ATE as DT_CONSU4_0_,
        tbagenda0_.ID_PRODUTO as ID_PRODU5_0_,
        tbagenda0_.ST_PAGAMENTO as ST_PAGAM6_0_,
        tbagenda0_.ST_PG as ST_PG7_0_,
        tbagenda0_.ST_PRESENCA as ST_PRESE8_0_,
        tbagenda0_.VL_PRECO as VL_PRECO9_0_ 
    from
        TB_AGENDA tbagenda0_ cross 
    join
        TB_PACIENTE tbpaciente1_ 
    where
        tbagenda0_.ID_PACIENTE=tbpaciente1_.ID_PACIENTE 
        and (
            tbagenda0_.ID_PACIENTE in (
                189
            )
        ) 
        and tbagenda0_.DT_CONSULTA>='2016-01-01 00:00:00' 
        and tbagenda0_.DT_CONSULTA<='2016-12-31 23:59:59' 
        and (
            tbagenda0_.ST_PRESENCA in (
                true
            ) 
            or tbagenda0_.ST_PRESENCA in (
                false
            )
        ) 
        and (
            tbagenda0_.ST_PAGAMENTO in (
                0,1,2,3
            )
        ) 
        and (
            tbagenda0_.ID_PRODUTO in (
                1 , 6 , 3 , 5 , 2 , 4
            )
        ) 
    order by
        tbagenda0_.DT_CONSULTA,
        tbpaciente1_.DS_NOME; 
----------------------------------------------------------------------------------------
--Consulta por periodo
 select
        tbagenda0_.DT_CONSULTA as DT_CONSU1_0_,
        tbagenda0_.ID_PACIENTE as ID_PACIE2_0_,
        tbagenda0_.DS_OBS as DS_OBS3_0_,
        tbagenda0_.DT_CONSULTA_ATE as DT_CONSU4_0_,
        tbagenda0_.ID_PRODUTO as ID_PRODU5_0_,
        tbagenda0_.ST_PAGAMENTO as ST_PAGAM6_0_,
        tbagenda0_.ST_PG as ST_PG7_0_,
        tbagenda0_.ST_PRESENCA as ST_PRESE8_0_,
        tbagenda0_.VL_PRECO as VL_PRECO9_0_ 
    from
        TB_AGENDA tbagenda0_ cross 
    join
        TB_PACIENTE tbpaciente1_ 
    where
        tbagenda0_.ID_PACIENTE=tbpaciente1_.ID_PACIENTE 
        and tbagenda0_.DT_CONSULTA>='2016-01-01 00:00:00' 
        and tbagenda0_.DT_CONSULTA<='2016-12-31 23:59:59'
        and tbagenda0_.ST_PAGAMENTO=0 
        and tbagenda0_.ST_PRESENCA='true' 
    order by
        tbpaciente1_.DS_NOME,
        tbagenda0_.DT_CONSULTA