# Lancar pagamento

Lança uma tentativa de pagamento para uma venda específica.

**URL** : `/api/pagamentos/`

**Método** : `POST`

**Requer autenticação** : NÃO

**Parametros de PATH**: NENHUM

Request body:

```json
{
    "vendaId": [uuid] id do venda,
    "valor": [integer] valor do pagamento,
    "tipo": [string] tipo do pagamento: PIX | DEPOSITO | DINHEIRO | TRANSFERENCIA | CREDITO | DEBITO,
    "cpfPagador": [string] cpf do pagador
}
```

**Data example**

```json
{
    "vendaId": "967dc072-f23f-4700-b2ac-633d6030e27e",
    "valor": 1350.0,
    "tipo": "PIX",
    "cpfPagador": "000.888.666-10"
}
```

## Resposta de Sucesso

**Condição** : Se todas as informações estiverem ok.

**Code** : `201 CREATED`

**Exemplo de resposta**

```json
{
    "id": "d66d604d-cf35-481c-9243-a76c7d36f720",
    "vendaId": "967dc072-f23f-4700-b2ac-633d6030e27e",
    "dataCriacao": "2023-06-18T17:44:50.089477663",
    "dataFinalizacao": null,
    "valor": 1350.0,
    "status": "LANCADO",
    "tipo": "PIX",
    "cpfPagador": "000.888.666-10",
    "rejeitadoPor": null
}
```
## Resposta de erro

**Condição** : Campos passados de maneira incorreta

**Code** : `400 BAD_REQUEST`