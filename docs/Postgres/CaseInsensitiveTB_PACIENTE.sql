--Setando campo para case insensitive
CREATE EXTENSION citext;
ALTER TABLE tb_paciente ALTER COLUMN ds_nome TYPE citext;
CREATE UNIQUE INDEX unique_name_on_tb_paciente ON tb_paciente (ds_nome);

select * from tb_paciente where ds_nome like '%trist%'


-- verificar quantas conexões ativas
show max_connections;
SELECT state FROM pg_stat_activity;


SELECT 
    pid
    ,state
    ,datname
    ,usename
    ,application_name
    ,client_hostname
    ,client_port
    ,backend_start
    ,query_start
    ,query  
FROM pg_stat_activity
WHERE state <> 'idle'
--AND pid<>pg_backend_pid();

select min_val,max_val from pg_settings where name='max_connections'

--------------------------------------------------------------------
select * from tb_fone

select * from tb_senha

select * from tb_agenda

select * from tb_paciente where id_paciente = 138