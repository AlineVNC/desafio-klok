# Listar Pagamentos

Listas todas os pagamentos realizados.

**URL** : `/api/pagamentos/`

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
        "id": "a2a80d5c-e991-4b70-86d7-b17a6e2d985c",
        "vendaId": "967dc072-f23f-4700-b2ac-633d6030e27e",
        "dataCriacao": "2023-06-16T22:45:48",
        "dataFinalizacao": "2023-06-16T22:45:49",
        "valor": 50.0,
        "status": "REJEITADO",
        "tipo": "PIX",
        "cpfPagador": "000.888.666-10",
        "rejeitadoPor": "Valores divergentes"
    },
    {
        "id": "3632ab05-2e3f-410e-a977-b60f33e1b458",
        "vendaId": "967dc072-f23f-4700-b2ac-633d6030e27e",
        "dataCriacao": "2023-06-16T22:46:19",
        "dataFinalizacao": "2023-06-16T22:46:19",
        "valor": 1350.0,
        "status": "FINALIZADO",
        "tipo": "PIX",
        "cpfPagador": "000.888.666-10",
        "rejeitadoPor": null
    }
]
```
