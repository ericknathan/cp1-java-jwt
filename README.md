# 🔐 Sitema de Gestão de Tarefas com Autenticação JWT

## Integrantes
- Erick Nathan Capito Pereira (RM99565)

## Descrição
Seu time foi contratado para desenvolver um sistema de gestão de tarefas para uma pequena empresa. O sistema permitirá que os usuários registrem, visualizem, atualizem e removam tarefas, além de gerenciar o status dessas tarefas (por exemplo, "Em Andamento", "Concluída", "Pendente"). Para garantir a segurança das informações, o sistema deve implementar autenticação utilizando JWT (JSON Web Token).

## Funcionalidades implementadas
- Autenticação de usuários utilizando JWT.
- Documentação da API utilizando Swagger.
- Validação de campos obrigatórios com Bean Validation.
- Manipulação do banco de dados com JPA.

## Executando e acessando a documentação da API
1. Insira as credenciais de autenticação do banco de dados no arquivo `application.properties`
2. Execute o projeto em sua IDE ou utilizando o Maven
3. Acesse a documentação da API em [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Requisitos Funcionais
### Cadastro de usuários
- [x] Criar uma rota `/auth/register` para o cadastro de novos usuários. O cadastro deverá incluir informações básicas, como nome, e-mail e senha.
- [x] O sistema deve garantir que o e-mail do usuário seja único.

### Autenticação e Autorização:
- [x] Criar uma rota `/auth/login` que permita o login dos usuários utilizando e-mail e senha. Caso as credenciais sejam válidas, um token JWT deverá ser gerado e retornado.
- [x] O token JWT deve ser utilizado para autenticar todas as requisições às rotas protegidas.

### Gestão de Tarefas:
#### Públicas:
- [x] `/public/status`: Esse endpoint retorna uma lista de possíveis status para as tarefas. Isso permite que usuários (mesmo antes de autenticação) possam visualizar os diferentes estados em que uma tarefa pode estar, como "Pendente", "Em Andamento", “Concluída” etc.

#### Protegidas:
- [x] Criar Tarefa: Enpoint POST `/tasks` que permita ao usuário criar uma nova tarefa. Uma tarefa deve incluir título, descrição, data de conclusão prevista, e status inicial ("Pendente").
- [x] Visualizar Tarefas: Rota GET `/tasks` que permita ao usuário visualizar todas as suas tarefas.
- [x] Atualizar Tarefa: Rota PUT `/tasks/{id}` que permita ao usuário atualizar as informações de uma tarefa específica.
- [x] Excluir Tarefa: Rota DELETE `/tasks/{id}` que permita ao usuário excluir uma tarefa específica.

### Validações:
- [x] O sistema deve validar se os campos obrigatórios estão preenchidos corretamente.
- [x] Garantir que apenas o dono da tarefa possa atualizá-la ou excluí-la.