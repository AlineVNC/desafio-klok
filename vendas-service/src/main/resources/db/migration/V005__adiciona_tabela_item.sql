CREATE TABLE tb_item (
id uuid NOT NULL PRIMARY KEY,
id_venda uuid REFERENCES tb_venda (id) ON DELETE CASCADE,
id_produto uuid REFERENCES tb_produto (id) ON DELETE SET NULL,
precoPraticado money NOT NULL,
quantidade numeric NOT NULL DEFAULT 1
);
