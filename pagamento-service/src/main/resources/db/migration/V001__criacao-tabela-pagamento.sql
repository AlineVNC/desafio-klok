CREATE TABLE tb_pagamento (
	id uuid PRIMARY KEY,
	venda_id uuid NOT NULL,
	data_criacao timestamp(0) NOT NULL,
	data_finalizacao timestamp(0),
	valor decimal NOT NULL,
	status varchar(20) NOT NULL,
	rejeitado_por varchar(250),
	tipo varchar(20) NOT NULL,
	cpf_pagador varchar(14) NOT NULL
);
