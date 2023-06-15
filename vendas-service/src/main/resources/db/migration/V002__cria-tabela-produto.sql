CREATE TABLE tb_produto(
    id uuid PRIMARY KEY,
    nome varchar(100) NOT NULL,
    preco decimal NOT NULL, 
    data timestamp(0) NOT NULL
);
