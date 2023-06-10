CREATE TABLE tb_cobranca(
    id uuid PRIMARY KEY,
    data timestamp(0) NOT null,
    valor money not null, 
	status varchar(20) not null,
	data_pagamento timestamp(0) NOT null
);