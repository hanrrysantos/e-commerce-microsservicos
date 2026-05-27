# E-commerce Microservices

![Java](https://img.shields.io/badge/java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![Render](https://img.shields.io/badge/Render-46E3B7?style=for-the-badge&logo=render&logoColor=black)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

---

## Visao Geral

O **E-commerce Microservices** e uma plataforma de e-commerce desenvolvida com **Java, Spring Boot e arquitetura de microsservicos**, criada para simular um fluxo real de compra: autenticacao, catalogo de produtos, carrinho, criacao de pedido, pagamento via Mercado Pago, confirmacao por webhook, entrega e notificacoes.

O projeto foi construido com foco em **comunicacao assincrona**, **baixo acoplamento entre servicos** e **responsabilidade isolada por dominio**. Cada microsservico possui seu proprio banco de dados PostgreSQL, enquanto eventos de negocio sao trafegados via RabbitMQ.

---

## Funcionalidades Principais

**Autenticacao com JWT:** login centralizado no `auth-service`, validacao de token pelo `api-gateway` e propagacao do usuario autenticado para os servicos internos.

**Catalogo de produtos:** cadastro, listagem, filtro por categoria/status, atualizacao de preco, controle de estoque e populacao inicial via Flyway.

**Carrinho de compras:** gerenciamento do carrinho do usuario autenticado, adicao/remocao de itens, alteracao de quantidade e checkout integrado ao `order-service`.

**Pedidos:** criacao de pedidos com status inicial `PENDING`, calculo do valor total, validacao de produtos e publicacao de eventos no RabbitMQ.

**Pagamentos:** integracao com Mercado Pago para geracao de pagamento PIX, persistencia do pagamento e recebimento de webhooks de aprovacao.

**Comunicacao assincrona:** eventos como `order.pending`, `payment.approved` e `order.confirmed` trafegam pelo RabbitMQ, reduzindo dependencias diretas entre os servicos.

**Entrega e notificacoes:** apos a confirmacao do pedido, os servicos de entrega e notificacao consomem eventos e executam suas responsabilidades de forma independente.

**Documentacao:** Swagger centralizado no API Gateway, reunindo a documentacao OpenAPI dos microsservicos.

---

## Microsservicos

| Servico | Responsabilidade |
| :--- | :--- |
| `api-gateway` | Entrada unica da aplicacao, roteamento, validacao JWT e Swagger centralizado |
| `auth-service` | Autenticacao e emissao de tokens JWT |
| `user-service` | Cadastro, consulta e gerenciamento de usuarios |
| `product-service` | Catalogo de produtos, estoque, categorias e status |
| `cart-service` | Carrinho do usuario e checkout |
| `order-service` | Criacao de pedidos, calculo de totais e eventos de pedido |
| `payment-service` | Integracao com Mercado Pago, PIX e webhooks |
| `delivery-service` | Criacao e atualizacao de entregas |
| `notification-service` | Envio e registro de notificacoes por e-mail |

---

## Fluxo Principal

1. O cliente realiza login e recebe um token JWT.
2. O cliente envia as requisicoes para o API Gateway.
3. O API Gateway valida o token e encaminha a requisicao para o servico correto.
4. O cliente adiciona produtos ao carrinho.
5. No checkout, o `cart-service` cria o pedido no `order-service`.
6. O `order-service` cria o pedido com status `PENDING` e publica o evento `order.pending`.
7. O `payment-service` consome o evento e cria o pagamento PIX no Mercado Pago.
8. O Mercado Pago notifica o `payment-service` via webhook quando o pagamento e aprovado.
9. O `payment-service` publica o evento `payment.approved`.
10. O `order-service` consome o evento, confirma o pedido e publica `order.confirmed`.
11. `delivery-service` e `notification-service` consomem o evento e continuam o fluxo.

<div align="center">
  <img width="950" height="650" alt="Image" src="https://github.com/user-attachments/assets/c73e7fd8-8e00-4a38-9554-c082771e40a6" />
</div>

---

## Demonstracao Visual

<table width="100%">
  <tr>
    <td align="center" width="50%">
      <b>Deploy dos Microsservicos</b><br>
      <img width="1004" height="488" alt="Image" src="https://github.com/user-attachments/assets/83b0578c-ec74-4554-bf34-859459e97489"/>
      <p><i>Servicos Docker publicados individualmente no Render.</i></p>
    </td>
    <td align="center" width="50%">
      <b>Swagger Centralizado</b><br>
      <img width="575" height="209" alt="Image" src="https://github.com/user-attachments/assets/7351d173-7b93-4569-9dab-eebeb5043075" />
      <p><i>Documentacao OpenAPI agregada pelo API Gateway.</i></p>
    </td>
  </tr>
  <tr>
    <td align="center" width="50%">
      <b>Pedido Recebido</b><br>
      <img width="538" height="254" alt="Image" src="https://github.com/user-attachments/assets/f7a024a3-a922-417b-bb48-d06cf3082d2b" />
      <p><i>Notificacao enviada apos criacao do pedido.</i></p>
    </td>
    <td align="center" width="50%">
      <b>Pagamento Aprovado</b><br>
      <img width="762" height="223" alt="Image" src="https://github.com/user-attachments/assets/38286f37-34f7-43f1-921f-73b03531e6de" />
      <p><i>Notificacao enviada apos confirmacao do pagamento.</i></p>
    </td>
  </tr>
</table>

<div align="center">
  <b>Webhook Mercado Pago</b><br>
  <img width="800" height="253" alt="Image" src="https://github.com/user-attachments/assets/5eb2b54b-0530-40e4-b205-afee9641e8f5" />
  <p><i>Evento de pagamento entregue ao payment-service via webhook.</i></p>
</div>

---

## Principais Endpoints

| Recurso | Metodo | Endpoint | Descricao |
| :--- | :---: | :--- | :--- |
| Auth | POST | `/api/auth/login` | Autentica usuario e retorna token JWT |
| Usuarios | POST | `/api/users/register` | Cadastra um novo usuario |
| Usuarios | GET | `/api/users` | Lista usuarios |
| Produtos | GET | `/api/products` | Lista produtos |
| Produtos | POST | `/api/products` | Cadastra produto |
| Produtos | PATCH | `/api/products/{id}/stock` | Atualiza estoque |
| Carrinho | GET | `/api/carts/me` | Busca carrinho do usuario autenticado |
| Carrinho | POST | `/api/carts/me/items` | Adiciona item ao carrinho |
| Carrinho | PATCH | `/api/carts/me/items/{productId}` | Atualiza quantidade de um item |
| Carrinho | DELETE | `/api/carts/me/items/{productId}` | Remove item do carrinho |
| Carrinho | POST | `/api/carts/me/checkout` | Finaliza carrinho e cria pedido |
| Pedidos | POST | `/api/orders` | Cria pedido |
| Pagamentos | GET | `/api/payments/order/{orderId}` | Busca pagamento por pedido |
| Webhook | POST | `/webhooks/mercadopago` | Recebe notificacao do Mercado Pago |
| Entregas | PATCH | `/api/deliveries/{id}/status` | Atualiza status da entrega |

---

## Tecnologias Utilizadas

**Backend**

Java 21, Spring Boot 3, Spring Security, Spring Cloud Gateway, Spring Data JPA, OpenFeign, RabbitMQ, MapStruct, Lombok, Maven

**Banco de Dados**

PostgreSQL, Flyway, Hibernate, Supabase

**Infraestrutura**

Docker, Docker Compose, Render, GitHub Actions, Swagger/OpenAPI, Mercado Pago API

---

## Arquitetura

O projeto utiliza **microsservicos orientados a eventos**, combinando comunicacao HTTP sincronizada quando a resposta imediata e necessaria e comunicacao assincrona via RabbitMQ para eventos de negocio.

- **API Gateway:** centraliza o acesso externo e protege endpoints com JWT.
- **Database per Service:** cada servico possui seu proprio banco de dados.
- **Event-driven Architecture:** eventos de pedido e pagamento sao publicados no RabbitMQ.
- **OpenFeign:** comunicacao interna entre servicos quando ha necessidade de consulta direta.
- **Flyway:** versionamento de schema e dados iniciais.
- **Docker Compose:** orquestracao local dos bancos, microsservicos e gateway.

---

## CI/CD

O projeto possui pipeline com **GitHub Actions**, executando build e testes dos servicos Java configurados a cada push ou pull request na branch `main`.

Os deploys dos microsservicos foram preparados para ambiente cloud com **Render**, utilizando variaveis de ambiente para credenciais, URLs internas e configuracoes sensiveis.

---

## Teste Agora

A documentacao centralizada pode ser acessada pelo Swagger do API Gateway:

https://ecommerce-api-gateway-to0z.onrender.com/swagger-ui.html

> Por estar em hospedagem gratuita, o primeiro acesso pode demorar alguns segundos devido ao cold start dos servicos.

Fluxo sugerido para teste:

1. Acesse o Swagger.
2. Faca login em `/api/auth/login`.
3. Copie o token JWT retornado.
4. Clique em `Authorize`.
5. Informe o token no formato `Bearer {token}`.
6. Teste os endpoints protegidos pelo gateway.

Usuarios demo sugeridos:

| Perfil | Email | Senha |
| :--- | :--- | :--- |
| Admin | `admin@demo.com` | `123456` |
| Cliente | `cliente@demo.com` | `123456` |

---

## Instalacao e Execucao Local

### Pre-requisitos

- Docker e Docker Compose instalados
- Java 21 e Maven, caso queira rodar os servicos fora do Docker
- Conta/configuracao de e-mail para notificacoes
- Token de teste do Mercado Pago

### 1. Clone o repositorio

```bash
git clone https://github.com/hanrrysantos/e-commerce-microservicos.git
cd e-commerce-microservicos
```

### 2. Configure as variaveis de ambiente

Crie um arquivo `.env` na raiz do projeto:

```env
POSTGRES_USER=admin
POSTGRES_PASSWORD=admin
SPRING_RABBITMQ_ADDRESSES=amqp://rabbitmq:5672
SPRING_MAIL_USERNAME=seu-email@example.com
SPRING_MAIL_PASSWORD=sua-senha-de-app
MERCADOPAGO_ACCESS_TOKEN=seu-token-de-teste
JWT_SECRET=sua-chave-secreta
JWT_EXPIRATION=86400000
```

### 3. Suba a aplicacao

```bash
docker-compose up --build
```

### 4. Acesse os servicos

| Aplicacao | URL |
| :--- | :--- |
| API Gateway | `http://localhost:8080` |
| Swagger | `http://localhost:8080/swagger-ui.html` |

---

## Estrutura do Projeto

```text
e-commerce/
|-- api-gateway/
|-- auth-service/
|-- user-service/
|-- product-service/
|-- cart-service/
|-- order-service/
|-- payment-service/
|-- delivery-service/
|-- notification-service/
|-- docs/
|-- docker-compose.yml
`-- README.md
```

---

## Autor

**Hanrry** - Desenvolvedor Backend Java em formacao, com foco em Spring Boot, microsservicos e arquitetura de software.

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/hanrrysantos)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/hanrrysantos)

---

## Licenca

Este projeto e de uso livre para fins de estudo e portfolio.
