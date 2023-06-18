# Listar Produtos Cadastrados

Listas todos os produtos cadastrados.

**URL** : `/api/produtos/`

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
        "id": "d29c88f9-b068-4701-89ca-c75fea5e38e1",
        "nome": "Camisa Polo",
        "preco": 100.0
    },
    {
        "id": "e864b83e-9ba9-4e4a-8ec3-5ee500a82507",
        "nome": "Short",
        "preco": 70.0
    },
    {
        "id": "793599c2-e8cd-4e9d-ad75-32e499cb2861",
        "nome": "Vestido",
        "preco": 150.0
    },
    {
        "id": "420ef8aa-0204-4294-9f05-3b8530557a69",
        "nome": "Calça Jeans",
        "preco": 100.0
    }
]
```
