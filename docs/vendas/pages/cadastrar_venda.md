# Cadastrar Venda

Cadastra uma venda com ou sem itens.

**URL** : `/api/vendas/`

**Método** : `POST`

**Requer autenticação** : NÃO

**Parametros de PATH**: NENHUM

Request body:

```json
{
    "cpfComprador": [string] cpf do comprador,
    "items": [
        {"produtoId": [uuid] id do produto, "quantidade": [integer] quantidade do mesmo produto}
    ]
}
```

**Data example**

```json
{
    "cpfComprador": "0887774-55",
    "items": [
        {"produtoId": "d29c88f9-b068-4701-89ca-c75fea5e38e1", "quantidade": 10},
        {"produtoId": "e864b83e-9ba9-4e4a-8ec3-5ee500a82507", "quantidade": 5}
    ]
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