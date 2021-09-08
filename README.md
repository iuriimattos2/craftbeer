# API Craftbeer
## Especificação do projeto
A beer house é uma empresa que possui um catálogo de cervejas artesanais. Esta empresa está buscando entrar no mundo digital.
Para entrar no mundo digital a beer house dicidiu começar pelas APIs. As APIs serão utilizadas para compartilhar dados com os parceiros e também para o seu sistema web.

Especificação Swagger localizada em:

    craftbeer
    |
    |docs
    |    |___craftbeer-spec

Dica: Para melhor visualização, utilizar o [Swagger Editor](https://editor.swagger.io/).

## Instruções para executar o projeto
### Para executar o projeto foram disponibilizadas algumas formas, utilizando ou não Docker.
#### Requisições e chamadas:
- Para localizar as chamadas e retornos possíveis pode se usar o Swagger, que ficará disponível no endereço http://localhost:9000/swagger-ui.html.
- Para fazer as chamadas pode-se usar além do Swagger, a collection do Postman disponibilizada no diretório [/docs](https://github.com/HeitorAmaral/craftbeer/blob/main/docs/craftbeer.postman_collection.json).

### 1. Executar sem Docker
1. Requisitos:
    - Possuir o Java 8 instalado e configurado na máquina.
    - Possuir o [Lombok](https://projectlombok.org/) instalado.
2. Download do projeto do Git:
    ```sh
    $ git clone https://github.com/HeitorAmaral/craftbeer.git
    ```
3. Executar o projeto:
    - Para iniciar a aplicação, execute a classe Main: Application.java
    - A aplicação será hospedada no endereço http://localhost:9000 por padrão.

### 2. Executar com Docker
1. Requisitos:
    - Possuir o Docker instalado e configurado na máquina.
2. Download do projeto do Git:
    ```sh
    $ git clone https://github.com/HeitorAmaral/craftbeer.git
    ```
3. Executar o projeto com Docker (Criação de imagem e container manual):
    - Executar o comando para gerar a imagem da aplicação.
        ```sh
        $ docker build -t image-craftbeer .
        ```
    - Executar o comando para criar e iniciar o container da aplicação.
        ```sh
        $ docker run -d --name container-craftbeer -p 9000:9000 image-craftbeer
        ```
4. Executar o projeto com Docker Compose (Criação de imagem e container automátizada):
    - Para fazer isso deve se estar na raiz do projeto e executar o seguinte comando:
        ```sh
        $ docker-compose up
        ```