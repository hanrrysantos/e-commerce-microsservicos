# E-commerce com Arquitetura de Microsserviços

## Descrição do Projeto

Este projeto implementa uma plataforma de e-commerce utilizando uma arquitetura baseada em microsserviços, desenvolvida em Java com Spring Boot. A comunicação assíncrona entre os serviços é gerenciada pelo RabbitMQ, e os dados são persistidos em bancos de dados PostgreSQL.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.14**
- **Maven**
- **RabbitMQ**
- **PostgreSQL**
- **Docker** e **Docker Compose**
- **Lombok**
- **MapStruct**

## Arquitetura

A aplicação é composta pelos seguintes microsserviços:

- **`order-service`**: Responsável pelo gerenciamento de pedidos.
- **`notification-service`**: Envia notificações (e-mails, etc.) relacionadas a eventos do sistema.
- **`deliveries-service`**: Gerencia o processo de entrega dos pedidos.
- **`payment-service`**: Processa pagamentos.
- **`user-service`**: Gerencia usuários e autenticação.
- **`product-service`**: Gerencia o catálogo de produtos.

A comunicação entre os microsserviços é realizada principalmente através de mensagens assíncronas via RabbitMQ, garantindo desacoplamento e resiliência. Cada serviço possui seu próprio banco de dados PostgreSQL.

## Como Executar o Projeto

Para subir a aplicação localmente, siga os passos abaixo:

1.  **Pré-requisitos:**
    - Docker e Docker Compose instalados.
    - Java 21 e Maven (para desenvolvimento e compilação, se necessário).

2.  **Clonar o repositório:**
    ```bash
    git clone https://github.com/hanrrysantos/e-commerce-microsservicos.git
    cd e-commerce-microsservicos
    ```

3.  **Configurar variáveis de ambiente:**
    Crie um arquivo `.env` na raiz do projeto com as seguintes variáveis (exemplo):
    ```
    POSTGRES_USER=admin
    POSTGRES_PASSWORD=admin
    SPRING_RABBITMQ_ADDRESSES=rabbitmq:5672
    SPRING_MAIL_USERNAME=your-email@example.com
    SPRING_MAIL_PASSWORD=your-email-password
    ```

4.  **Subir os serviços com Docker Compose:**
    ```bash
    docker-compose up --build
    ```
    Isso irá construir as imagens Docker para cada microsserviço, configurar os bancos de dados PostgreSQL e o RabbitMQ, e iniciar todos os contêineres.

## Endpoints da API (Exemplos)

(Esta seção será preenchida com exemplos de endpoints de cada serviço após uma análise mais aprofundada do código-fonte de cada microsserviço.)

## Contribuição

Sinta-se à vontade para contribuir com o projeto. Para isso, siga os passos:

1.  Faça um fork do repositório.
2.  Crie uma nova branch (`git checkout -b feature/sua-feature`).
3.  Faça suas alterações e commit (`git commit -am 'feat: Adiciona nova feature'`).
4.  Envie para a branch (`git push origin feature/sua-feature`).
5.  Abra um Pull Request.

## Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
