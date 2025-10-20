-- SELECT * FROM information_schema.SCHEMATA S WHERE schema_name = "psychomanager";
-- ALTER DATABASE psychomanager CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
-- SET FOREIGN_KEY_CHECKS=0; -- desativar foreign key
-- SET FOREIGN_KEY_CHECKS=1; -- ativar foreign key

SELECT @@global.time_zone, @@session.time_zone;

SELECT * FROM TB_AUT_GROUP;
SELECT * FROM TB_AUT_USER;

-- critografia para glassfish
update tb_aut_user set PASSWORD = "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3" where USERID = "fabio";
update tb_aut_user set PASSWORD = "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3" where userid = 'katia';

-- critografia para Wildfly 
#123
update tb_aut_user set PASSWORD = "pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=" where USERID = "fabio";
update tb_aut_user set PASSWORD = "pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=" where userid = 'katia';

#ju449
update tb_aut_user set PASSWORD = "gXT/a0vn8OiY62yTzdGq+DZP9aY31mgXopY0v0rWgTU=" where USERID = "fabio";
update tb_aut_user set PASSWORD = "gXT/a0vn8OiY62yTzdGq+DZP9aY31mgXopY0v0rWgTU=" where userid = 'katia';

                        
-- senha = 123
insert into TB_AUT_USER (userid,password,ds_nome) values('fabio','pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=','Fabio Cesar Tassinari');
insert into TB_AUT_USER (userid,password,ds_nome) values('katia','pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=','Katia Gonçalves');
-- senha = 456
insert into TB_AUT_USER (userid,password,ds_nome) values('cesar','s6jg4fmrG/46NvIx9nb3i7MKUZ0rIebFMMDu6Ou0pdA=','Cesar');
-- senha = 789
insert into TB_AUT_USER (userid,password,ds_nome) values('tassinari','NanjgbGidWdUm1+Kb3g8Fn6/gJ8cTWqeNnJASE2M4oE=','Tassinari');

insert into TB_AUT_GROUP (userid,groupid) values('fabio','Adm');
insert into TB_AUT_GROUP (userid,groupid) values('katia','Adm');
insert into TB_AUT_GROUP (userid,groupid) values('fabio','User');
insert into TB_AUT_GROUP (userid,groupid) values('cesar','User');


select * from tb_paciente where id_paciente = 147;
select * from tb_agenda where id_paciente = 1;
select * from tb_fone where id_paciente = 1;

select * from tb_produto order by id_produto;
select * from tb_senha where id_paciente = 1;

select * from tb_agenda order by dt_consulta;
select * from tb_paciente where id_paciente = 15;


select count(0)  from tb_agenda;
select count(0)  from tb_fone;
select count(0)  from tb_paciente;
select count(0)  from tb_produto;
select count(0)  from tb_senha;

select * from tb_paciente where ds_nome like '%bruno%' ;
select count(0)  from tb_agenda where id_paciente = 15;
select count(0)  from tb_fone where id_paciente = 15;
select count(0)  from tb_senha where id_paciente = 15;



select * from tb_agenda age where age.dt_Consulta >= '2017-12-06 00:00:00' and age.dt_Consulta <= '2017-12-06 23:59:59';

select * from tb_agenda age where age.dt_Consulta >= '2017-05-08 00:00:00' and age.dt_Consulta <= '2017-05-08 23:59:59';

select count(0) from tb_agenda age where age.dt_Consulta >= '2017-01-01 00:00:00' and age.dt_Consulta <= '2017-12-31 23:59:59';



Double preco = (Double) getEntityManager().createQuery("SELECT sum(ag.vlPreco) FROM " + TbAgenda.class.getName() + " ag WHERE "
                + "ag.tbAgendaPK.idPaciente " + (idPaciente == 0 ? "not in(" + idPaciente + ")" : "in(" + idPaciente + ")") + "  AND "
                + "ag.tbAgendaPK.dtConsulta >= '" + formatoData.format(de) + "' AND "
                + "ag.tbAgendaPK.dtConsulta <= '" + formatoData.format(ate) + "' AND "
                + "(ag.stPresenca in( :pres ) OR "
                + "ag.stPresenca in( :pres2 )) AND "
                + (pago == 99 ? "ag.stPagamento not in(" + pago + ")" : "ag.stPagamento in(" + pago + ")") + " AND "
                + "ag.tbProduto.idProduto in (" + idProduto + ") "
        ).setParameter("pres", (presenca == 1 ? Boolean.TRUE : presenca == 2 ? Boolean.FALSE : Boolean.TRUE))
                .setParameter("pres2", (presenca == 1 ? Boolean.TRUE : presenca == 2 ? Boolean.FALSE : Boolean.FALSE))
                .getSingleResult();
                
                
select * from tb_paciente where DS_NOME like 'Amanda%' order by DS_NOME;

select * from tb_paciente where DS_NOME like '%' order by DS_NOME limit 96;

select * from tb_paciente where ID_PACIENTE = 7;


select * from tb_paciente where VL_TERAPIA = null;

update tb_paciente set
DS_ACOMPMEDICO = "-",
DS_BAIRRO = "Tatuapé",
DS_CALMANTE = "-",
DS_EMAIL = "amandaconstatinog@gmail.com",
DS_ENDERECO = "Rua Serra de Botucatu 151",
DS_ESCOLARIDADE = "-",
DS_FICHA = "Amanda Constantino Gonçalves.PDF",
DS_FILHOS = "-",
DS_NOME = "Amanda Constatino Golçalves",
DS_OBSERVACAO = "-",
DS_PROBSAUDE = "-",
DS_PROFISSAO = "-",
DS_QUEIXAS = "Ansiedade, impaciência, criança mimada",
DS_REMEDIOS = "-",
DS_RESULTADO = "-",
NR_CEP = "-",
ST_BEBE = "N",
ST_DROGAS = "N",
ST_ESTADOCIVIL = "-",
ST_FUMA = "N",
ST_INSONIA = "N",
ST_TRATPSIC = "N",
VL_TERAPIA = 0
where
id_paciente = 7;

update tb_paciente set
DS_ACOMPMEDICO = "-",
DS_BAIRRO = "Tatuapé",
DS_CALMANTE = "-",
DS_EMAIL = "-",
DS_ENDERECO = "Rua Pedro Belegarde 208 ap 71",
DS_ESCOLARIDADE = "-",
DS_FICHA = "Fernanda Dovigo.PDF",
DS_FILHOS = "-",
DS_NOME = "Fernanda Dovigo",
DS_OBSERVACAO = "-",
DS_PROBSAUDE = "-",
DS_PROFISSAO = "Bancaria",
DS_QUEIXAS = "Baixa auto estima, saber lidar com a perda, entender o que e melhor para mim",
DS_REMEDIOS = "-",
DS_RESULTADO = "-",
NR_CEP = "03317-080",
ST_BEBE = "N",
ST_DROGAS = "N",
ST_ESTADOCIVIL = "-",
ST_FUMA = "N",
ST_INSONIA = "N",
ST_TRATPSIC = "N",
VL_TERAPIA = 0
where
id_paciente = 37;

update tb_paciente set
DS_ACOMPMEDICO = "-",
DS_BAIRRO = "Pinheiros",
DS_CALMANTE = "-",
DS_EMAIL = "-",
DS_ENDERECO = "Rua Dr Virgilio de Carvalho Pinto 385",
DS_ESCOLARIDADE = "-",
DS_FICHA = "Leonardo Dene.PDF",
DS_FILHOS = "-",
DS_NOME = "Leonardo Dene",
DS_OBSERVACAO = "-",
DS_PROBSAUDE = "-",
DS_PROFISSAO = "Vendedor",
DS_QUEIXAS = "-",
DS_REMEDIOS = "Adoro qualquer um",
DS_RESULTADO = "-",
NR_CEP = "-",
ST_BEBE = "S",
ST_DROGAS = "N",
ST_ESTADOCIVIL = "-",
ST_FUMA = "N",
ST_INSONIA = "S",
ST_TRATPSIC = "N",
VL_TERAPIA = 0
where
id_paciente = 69;


mysql -h localhost -u root -p 

select ds_nome, ds_endereco from tb_paciente where ds_endereco = "" order by ds_nome;
select ds_obs from tb_agenda where id_paciente = 165; 

select ds_obs from tb_agenda where id_paciente = 165 and dt_consulta BETWEEN  CAST('2019-01-01' AS DATE) AND CAST('2019-10-31' AS DATE) order by ds_obs;


select * from tb_paciente where DS_NOME like 'Cristina%' order by DS_NOME;


#Roberto
select * from tb_agenda where id_paciente = 113 and dt_consulta BETWEEN  CAST('2019-05-27' AS DATE) AND CAST('2019-05-28' AS DATE) ;

#Cristina
select * from tb_agenda where id_paciente = 220 and dt_consulta BETWEEN  CAST('2019-02-25' AS DATE) AND CAST('2019-02-28' AS DATE) ;



select * from tb_agenda where dt_consulta BETWEEN  CAST('2019-11-29' AS DATE) AND CAST('2019-11-30' AS DATE) ;

select now();





-- atualizar a categoria de preco por paciente
select * from tb_produto where ID_PRODUTO = 1;


ALTER TABLE tb_paciente
ADD COLUMN ST_CATVALOR2 CHAR NULL;
commit;

select * from tb_paciente where id_paciente = 275;

select id_paciente, ds_nome, vl_terapia, ST_CATVALOR from tb_paciente where vl_terapia is not null order by VL_TERAPIA;

select count(ID_PACIENTE)  from tb_paciente where vl_terapia = 200;
select count(ID_PACIENTE) from tb_paciente where vl_terapia = 180;
select count(ID_PACIENTE) from tb_paciente where vl_terapia = 170;
select count(ID_PACIENTE) from tb_paciente where vl_terapia = 150;
select count(ID_PACIENTE) from tb_paciente where vl_terapia = 140;
select count(ID_PACIENTE) from tb_paciente where vl_terapia = 120;
select count(ID_PACIENTE) from tb_paciente where vl_terapia = 100;




update tb_paciente set st_catvalor = 1 where ID_PACIENTE >=1;


SET SQL_SAFE_UPDATES = 0;
update tb_paciente set st_catvalor = 1 where vl_terapia = 200;
update tb_paciente set st_catvalor = 1 where vl_terapia = 180;
update tb_paciente set st_catvalor = 1 where vl_terapia = 170;
update tb_paciente set st_catvalor = 2 where vl_terapia = 150;
update tb_paciente set st_catvalor = 2 where vl_terapia = 140;
update tb_paciente set st_catvalor = 3 where vl_terapia = 120;
update tb_paciente set st_catvalor = 4 where vl_terapia = 100;


