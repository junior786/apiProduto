DROP TABLE IF EXISTS tb_produto;
CREATE TABLE tb_produto
(
    id          INTEGER,
    nome        VARCHAR(250) NOT NULL,
    description VARCHAR(250) NOT NULL,
    date        DATE         ,
    price       DOUBLE       NOT NULL,
    estoque     INTEGER NOT NULL
);
INSERT INTO tb_produto (id, nome, description, date, price, estoque)
VALUES (3, 'Teste', 'Teste', '2020-05-05', 50.0, 10);
