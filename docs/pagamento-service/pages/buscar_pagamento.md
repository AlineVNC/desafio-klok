# Buscar Pagamento

Buscar um pagamento cadastrad.

**URL** : `/api/pagamentos/:id`

**Método** : `GET`

**Requer autenticação** : NÃO

**Parametros de PATH**:
* id: referência do pagamento

## Resposta de Sucesso

**Condição** : Se todas as informações estiverem ok.

**Code** : `200 OK`

**Exemplo de resposta**

```json
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
    }
```
## Resposta de erro

**Condição** : Se a venda especificada não existe

**Code** : `404 NOT FOUND`