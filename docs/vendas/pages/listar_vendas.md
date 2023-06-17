# Listar Vendas

Listas todas as vendas cadastradas.

**URL** : `/api/vendas/`

**Método** : `GET`

**Requer autenticação** : NÃO

**Parametros de PATH**: NENHUM

## Resposta de Sucesso

**Condição** : Se todas as informações estiverem ok.

**Code** : `200 OK`

**Exemplo de resposta**

```json
[
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
]
```
