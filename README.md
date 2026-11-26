# Lar em Ordem

Sistema de organização de tarefas domésticas e divisão de responsabilidades.

Java 21, Spring Boot, Thymeleaf, Spring Data JPA e H2.

Execute `./mvnw spring-boot:run` na raiz do projeto. Veja `GUIA.txt` para consultar o H2 no ambiente privado.

No Render, use o Dockerfile. O H2 Console fica desabilitado por padrão. Para uma demonstração temporária com dados fictícios, habilite H2_CONSOLE_ENABLED=true e H2_CONSOLE_REMOTE=true, defina CONSOLE_USER, CONSOLE_PASSWORD (senha exclusiva com pelo menos 16 caracteres) e DB_PASSWORD no Render. Desabilite o console após a apresentação. Não utilize dados pessoais: o CRUD do site é público. O banco em arquivo requer armazenamento persistente para sobreviver a reinicializações e novos deploys; sem disco persistente, use apenas dados de demonstração.
