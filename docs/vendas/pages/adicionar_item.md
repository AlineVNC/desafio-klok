# Adicionar Item na Venda

Adiciona um novo item em uma venda.

**URL** : `/api/vendas/:id/itens`

**Método** : `POST`

**Requer autenticação** : NÃO

**Parametros de PATH**: 
* id: referência da Venda

Request body:

```json
{
    "produtoId": [uuid] id do produto,
    "quantidade": [integer] quantidade do mesmo produto
}
```

**Data example**

```json
{
    "produtoId": "793599c2-e8cd-4e9d-ad75-32e499cb2861",
    "quantidade": 5
}
```

## Resposta de Sucesso

**Condição** : Se todas as informações estiverem ok.

**Code** : `201 CREATED`

**Exemplo de resposta**

```json
{
        "id": "8d42c87b-57f1-49ba-9d0f-d446f1575834",
        "cpfComprador": "0887774-55",
        "status": "COBRANCA_GERADA",
        "data": "2023-06-16T22:47:42",
        "items": [
            {
                "id": "fff0f7b1-81b7-47b4-919a-154d184b8be6",
                "nomeProduto": "Camisa Polo",
                "quantidade": 10,
                "precoPraticado": 100.0,
                "subTotal": 1000.0
            },
            {
                "id": "05932fa0-148e-441b-9d13-426cc6e59219",
                "nomeProduto": "Short",
                "quantidade": 5,
                "precoPraticado": 70.0,
                "subTotal": 350.0
            }
        ],
        "total": 1350.0
    }
```
## Resposta de erro

**Condição** : Campos passados de maneira incorreta

**Code** : `400 BAD_REQUEST`


**Condição** : Venda não existe na base.

**Code** : `404 NOT_FOUND`