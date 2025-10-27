-- Dados iniciais para o sistema PsychoManager

-- Inserir usuário administrador padrão
INSERT INTO tb_aut_user (userid, password, ds_nome, id_lastview, st_refreshauto) 
VALUES ('admin', 'admin123', 'Administrador', 0, true);

-- Inserir grupos de usuários
INSERT INTO tb_aut_group (userid, groupid) VALUES ('admin', 'ADMIN');
INSERT INTO tb_aut_group (userid, groupid) VALUES ('admin', 'USER');

-- Inserir produtos/serviços padrão
INSERT INTO tb_produto (ds_nome, vl_preco1, vl_preco2, vl_preco3, vl_preco4) 
VALUES ('Consulta Psicológica', 150.00, 200.00, 250.00, 300.00);

INSERT INTO tb_produto (ds_nome, vl_preco1, vl_preco2, vl_preco3, vl_preco4) 
VALUES ('Avaliação Psicológica', 300.00, 400.00, 500.00, 600.00);

INSERT INTO tb_produto (ds_nome, vl_preco1, vl_preco2, vl_preco3, vl_preco4) 
VALUES ('Terapia de Casal', 200.00, 250.00, 300.00, 350.00);

INSERT INTO tb_produto (ds_nome, vl_preco1, vl_preco2, vl_preco3, vl_preco4) 
VALUES ('Terapia Familiar', 250.00, 300.00, 350.00, 400.00);

INSERT INTO tb_produto (ds_nome, vl_preco1, vl_preco2, vl_preco3, vl_preco4) 
VALUES ('Orientação Vocacional', 180.00, 220.00, 260.00, 300.00);
