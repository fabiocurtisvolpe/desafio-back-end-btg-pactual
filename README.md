Desafio Engenheiro de software - BTG Pactual

Escopo
Processar pedidos e gerar relatório.

Atividades
* Crie uma aplicação, na tecnologia de sua preferência: Java 22
* Modele e implemente uma base de dados: MongoDB.
* Crie um micro serviço que consuma dados de uma fila RabbitMQ e grave os dados para conseguir listar as informações:
  * Valor total do pedido
  * Quantidade de Pedidos por Cliente
  * Lista de pedidos realizados por cliente
  * Exemplo da mensagem que deve ser consumida:

   {
       "codigoPedido": 1001,
       "codigoCliente":1,
       "itens": [
           {
               "produto": "lápis",
               "quantidade": 100,
               "preco": 1.10
           },
           {
               "produto": "caderno",
               "quantidade": 10,
               "preco": 1.00
           }
       ]
   }

* Crie uma API REST, em que permita o consultar as seguintes informações:
  * Valor total do pedido
  * Quantidade de Pedidos por Cliente
  * Lista de pedidos realizados por cliente
  * RabbitMQ Management: http://localhost:15672/
