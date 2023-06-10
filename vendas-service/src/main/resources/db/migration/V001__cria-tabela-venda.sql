CREATE TABLE tb_venda (
    id uuid PRIMARY KEY,
    cpf_comprador varchar NOT NULL,
    data timestamp(0) NOT NULL,
    status varchar(30) NOT NULL,
    data_pagamento timestamp(0)
);
