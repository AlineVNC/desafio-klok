# Alterar Quantidade de Item na Venda

AAltera a quantidade de item existente.

**URL** : `/api/vendas/:idVenda/itens/:idItem`

**Método** : `PATCH`

**Requer autenticação** : NÃO

**Parametros de PATH**: 
* idVenda: referência da Venda
* idItem: referência do Item

Request body:

```json
{
    "quantidade": [integer] nova quantidade
}
```

**Data example**

```json
{
    "quantidade": 10
}
```

## Resposta de Sucesso

**Condição** : Se todas as informações estiverem ok.

**Code** : `200 OK`

## Resposta de erro

**Condição** : Item não encontrado na base

**Code** : `404 NOT_FOUND`