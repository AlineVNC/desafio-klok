# Buscar historico de cobranças de uma venda.

Buscar uma venda cadastrada.

**URL** : `/api/vendas/:id/cobrancas`

**Método** : `GET`

**Requer autenticação** : NÃO

**Parametros de PATH**:
* id: referência da venda

## Resposta de Sucesso

**Condição** : Se todas as informações estiverem ok.

**Code** : `200 OK`

**Exemplo de resposta**

```json
[
    {
        "id": "4d23c92a-61e4-42d1-bb58-3df6d289a2d2",
        "valor": 2100.0,
        "status": "EM_ABERTO",
        "dataCriacao": "2023-06-18T17:53:10",
        "dataPagamento": null
    },
    {
        "id": "503d035b-f292-4f17-8b91-b61cb14ecfeb",
        "valor": 1350.0,
        "status": "EXPIRADO",
        "dataCriacao": "2023-06-16T22:47:45",
        "dataPagamento": null
    }
]
```
## Resposta de erro

**Condição** : Se a venda especificada não existe

**Code** : `404 NOT FOUND`