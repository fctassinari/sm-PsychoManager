Postgres

CREATE TABLE tb_aut_user(userid VARCHAR(10) PRIMARY KEY, password VARCHAR(255) NOT NULL, ds_nome VARCHAR(100) NOT NULL);
CREATE TABLE tb_aut_group(userid VARCHAR(10), groupid VARCHAR(20) NOT NULL, PRIMARY KEY (userid, groupid));
ALTER TABLE tb_aut_group ADD CONSTRAINT FK_USERID FOREIGN KEY(userid) REFERENCES tb_aut_user(userid);

SELECT * FROM TB_AUT_GROUP;
SELECT * FROM TB_AUT_USER;

senha = 123
insert into TB_AUT_USER (userid,password,ds_nome) values('fabio','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3','Fabio Cesar Tassinari');
senha = 456
insert into TB_AUT_USER (userid,password,ds_nome) values('cesar','b3a8e0e1f9ab1bfe3a36f231f676f78bb30a519d2b21e6c530c0eee8ebb4a5d0','Cesar');
senha = 789
insert into TB_AUT_USER (userid,password,ds_nome) values('tassinari','35a9e381b1a27567549b5f8a6f783c167ebf809f1c4d6a9e367240484d8ce281','Tassinari');

insert into TB_AUT_GROUP (userid,groupid) values('fabio','Adm');
insert into TB_AUT_GROUP (userid,groupid) values('fabio','User');
insert into TB_AUT_GROUP (userid,groupid) values('cesar','User');
