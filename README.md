# FórumHub API

Bem-vindo à documentação técnica da API FórumHub. Este projeto consiste em uma API REST completa para uma plataforma de fórum, permitindo que os usuários discutam tópicos relacionados a diversos cursos. A API foi desenvolvida seguindo as melhores práticas de desenvolvimento back-end, incluindo autenticação segura, arquitetura em camadas e documentação interativa.

## Principais Funcionalidades

-   **Autenticação e Autorização**: Sistema de segurança baseado em JSON Web Tokens (JWT) para proteger os endpoints.
-   **CRUD de Tópicos**: Operações completas para criar, listar, detalhar, atualizar e excluir tópicos.
-   **Validação de Dados**: Regras de negócio e validação de dados de entrada para garantir a integridade das informações.
-   **Tratamento de Erros**: Respostas de erro padronizadas e centralizadas para uma melhor experiência do desenvolvedor.
-   **Documentação Interativa**: Geração automática de documentação com Swagger (OpenAPI 3).

---

## Tecnologias Utilizadas

-   **Java 21**: Versão mais recente da linguagem Java.
-   **Spring Boot 3.x**: Framework principal para a construção da aplicação.
-   **Spring Security 6**: Módulo de segurança para autenticação e autorização.
-   **Spring Data JPA & Hibernate**: Para persistência de dados e mapeamento objeto-relacional.
-   **PostgreSQL**: Banco de dados relacional.
-   **Maven**: Ferramenta para gerenciamento de dependências e build do projeto.
-   **Lombok**: Biblioteca para reduzir código boilerplate (getters, setters, etc.).
-   **SpringDoc (OpenAPI 3)**: Para geração automática da documentação da API.

---

## Configuração do Ambiente Local

Siga os passos abaixo para executar o projeto em sua máquina local.

### Pré-requisitos

-   JDK 21 ou superior
-   Maven 3.8 ou superior
-   PostgreSQL 14 ou superior
-   Um cliente de API como [Postman](https://www.postman.com/) ou [Insomnia](https://insomnia.rest/)

### 1. Banco de Dados

A aplicação espera um banco de dados PostgreSQL. Utilize o script SQL fornecido no início do projeto (`DDL`) para criar todas as tabelas e relacionamentos necessários.

### 2. Variáveis de Ambiente

A aplicação utiliza variáveis de ambiente para dados sensíveis. Configure-as no seu sistema ou na sua IDE (Run Configuration) antes de iniciar.

-   `DB_URL`: A URL de conexão JDBC com o banco. Ex: `jdbc:postgresql://localhost:5432/forumhub_db`
-   `DB_USER`: O nome de usuário do banco de dados.
-   `DB_PASSWORD`: A senha do banco de dados.
-   `API_JWT_SECRET`: Uma string longa e secreta para assinar os tokens JWT.

### 3. Executando a Aplicação

Com o ambiente configurado, você pode iniciar a API com o seguinte comando Maven:

```bash
mvn spring-boot:run
```
A aplicação estará disponível em http://localhost:8080.

## Documentação da API (Swagger)
A maneira mais fácil de explorar e testar os endpoints é através da documentação interativa do Swagger. Com a aplicação rodando, acesse:

```bash
http://localhost:8080/swagger-ui.html
```

Para testar os endpoints protegidos, siga estes passos:

- Envie uma requisição POST para /login com as credenciais de um usuário válido para obter um token JWT.

- Clique no botão "Authorize" no canto superior direito da página do Swagger.

- Na janela que se abre, cole seu token no campo "Value" e clique em "Authorize".

Agora você pode executar requisições para os endpoints protegidos diretamente pela interface.

---

## Endpoints da API
Autenticação
### POST /login
Autentica um usuário e retorna um token JWT para ser usado nas requisições subsequentes.

Request Body:


```JSON
{
  "email": "user@test.com",
  "senha": "123456"
}
```
Success Response (200 OK):

```JSON

{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```
## Tópicos
Todos os endpoints de Tópicos requerem autenticação (Bearer Token).

### POST /topicos
Cria um novo tópico no fórum.

Request Body:

```JSON

{
  "titulo": "Título do novo tópico",
  "mensagem": "Corpo da mensagem do novo tópico, com detalhes da dúvida ou discussão.",
  "idAutor": 1,
  "idCurso": 1
}
```
Success Response (201 Created): Retorna os detalhes do tópico recém-criado.

### GET /topicos
Lista todos os tópicos de forma paginada.

Query Parameters (Opcionais):

- size: Número de itens por página (padrão: 10).

- page: Número da página a ser exibida (padrão: 0).

- sort: Critério de ordenação. Ex: dataCriacao,asc.

- cursoNome: Filtra os tópicos pelo nome exato do curso.

Success Response (200 OK): Retorna um objeto de paginação com a lista de tópicos.

### GET /topicos/{id}
Busca os detalhes de um tópico específico pelo seu ID.

Success Response (200 OK): Retorna o objeto completo do tópico.

### PUT /topicos/{id}
Atualiza os dados de um tópico existente. Apenas os campos fornecidos no corpo da requisição serão alterados.

Request Body:

```JSON

{
  "titulo": "Título atualizado do tópico",
  "mensagem": "Mensagem atualizada e corrigida."
}
```
Success Response (200 OK): Retorna o objeto completo do tópico com os dados atualizados.

### DELETE /topicos/{id}
Exclui um tópico do sistema.

Success Response (204 No Content): N/A (sem corpo de resposta).
