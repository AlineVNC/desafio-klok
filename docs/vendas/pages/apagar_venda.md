# Apager Venda

Delete uma venda.

**URL** : `/api/vendas/:id`

**Método** : `DELETE`

**Requer autenticação** : NÃO

**Parametros de PATH**: 
* id: referência da Venda

## Resposta de Sucesso

**Condição** : Se todas as informações estiverem ok.

**Code** : `204 NO_CONTENT`

## Resposta de erro

**Condição** : Venda não existe na base.

**Code** : `404 NOT_FOUND`