CREATE TABLE tb_item (
id uuid NOT NULL PRIMARY KEY,
venda_id uuid REFERENCES tb_venda (id) ON DELETE CASCADE,
produto_id uuid REFERENCES tb_produto (id) ON DELETE SET NULL,
preco_praticado decimal NOT NULL,
quantidade numeric NOT NULL DEFAULT 1
);
