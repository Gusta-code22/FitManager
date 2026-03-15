# FitManager 🚀

**FitManager** é um sistema backend completo para gerenciamento de academias, desenvolvido em **Java com Spring Boot**. Facilita o controle de alunos, professores, exercícios, treinos e admins, com autenticação segura e APIs RESTful.

Perfeito para personal trainers ou academias no Ceará, como um projeto open-source inspirado em stacks modernas.

## ✨ Funcionalidades Principais
- **Gerenciamento de Usuários**: Cadastro e CRUD para Aluno, Professor, Admin e Usuario.
- **Treinos Personalizados**: Criação, edição e atribuição de exercícios.
- **Segurança**: Autenticação com tokens e controllers dedicados.
- **Validações Robustas**: Para dados de academia e treinos.
- **Arquitetura Limpa**: Models, Repositories, Services e Controllers organizados.

## 🛠️ Tech Stack
| Categoria     | Tecnologias                  |
|---------------|------------------------------|
| Backend      | Java, Spring Boot, Spring Security |
| Dados        | JPA/Hibernate (futuro: PostgreSQL) |
| APIs         | RESTful com validações       |
| Ferramentas  | Maven, Git, Docker (em breve) |

## 📁 Estrutura do Projeto
src/main/java/com/academia/
├── config/
├── controller/ # Admin, Aluno, Exercicio, etc.
├── model/ # Entidades
├── repository/ # CRUD ops
├── service/ # Lógica de negócio
└── validator/ # Validações



## 🚀 Como Rodar
1. Clone o repo: `git clone https://github.com/Gusta-code22/FitManager.git`
2. Entre na pasta: `cd FitManager`
3. Instale dependências: `mvn clean install`
4. Rode: `mvn spring-boot:run`
5. Acesse: `http://localhost:8080` (Swagger em `/swagger-ui.html` em breve)

## 🤝 Contribuições
- Fork o projeto!
- Crie uma branch: `git checkout -b feature/nova-funcionalidade`
- Commit: `git commit -m 'feat: adiciona X'`
- Push e PR.

Obrigado por visitar! 💪 **Desenvolvido por Gustavo Miranda Brito (Cariré-CE).**

[![Star](https://img.shields.io/github/stars/Gusta-code22/FitManager?style=social)](https://github.com/Gusta-code22/FitManager/stargazers/)
![License](https://img.shields.io/badge/license-MIT-blue.svg)
