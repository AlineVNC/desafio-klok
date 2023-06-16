CREATE TABLE tb_pagamento (
	id uuid PRIMARY KEY,
	data_criacao timestamp(0) NOT NULL,
	data_finalizacao timestamp(0),
	valor decimal NOT NULL,
	status varchar(20) NOT NULL,
	tipo varchar(20) NOT NULL,
	cpf_pagador varchar(14) NOT NULL
);
