CREATE TABLE tb_cobranca (
	id uuid NOT NULL PRIMARY KEY,
	venda_id uuid REFERENCES tb_venda (id) ON DELETE CASCADE,
	valor decimal NOT NULL,
	status varchar(30) NOT NULL,
	data_criacao timestamp(0) NOT NULL,
	data_pagamento timestamp(0)
);
