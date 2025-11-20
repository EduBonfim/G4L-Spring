# Integração Angular ↔ Spring Boot (guia)

Esse diretório contém arquivos exemplo e instruções para integrar o frontend Angular (https://github.com/EduBonfim/Games_For_Life_Angular) com esse backend Spring Boot.

## Passos rápidos (desenvolvimento)

1. No frontend, adicionar o `environment.ts` abaixo em `src/environments/` ou usar o código existente:

```ts
export const environment = {
  production: false,
  apiBaseUrl: 'http://localhost:8080'
};
```

2. Adicionar `proxy.conf.json` e alterar o `start` script em `package.json` para:

```json
"start": "ng serve --proxy-config proxy.conf.json"
```

3. Criar serviços no Angular para chamar os endpoints do backend (ex.: `AuthService`, `GameService`). Exemplos estão neste diretório.

4. No backend Spring Boot, rodar a API:

```powershell
mvnw.cmd -DskipTests clean package
mvnw.cmd spring-boot:run
```

5. No frontend Angular, rodar:

```bash
npm install
npm start
```

6. Testar login: chamar `POST http://localhost:8080/api/auth/login` com body { username, password } — se as credenciais forem inválidas, backend retorna 401 com mensagem.

## Observações de segurança
- O backend atualmente retorna um `ClientDto` na autenticação. Isso permite que o frontend guarde `currentUser` no `localStorage`. Para uma solução mais segura, implemente JWT para autorizar endpoints e proteger dados.

## Banco de dados (MySQL / MySQL Workbench)

1. Crie o banco de dados usando o script `db/init.sql` deste repositório ou crie manualmente um schema `usuario` no MySQL.
2. Se for usar o MySQL local com username/password padrão ou outro usuário, ajuste `src/main/resources/application.properties` ou passe as variáveis de ambiente `DB_URL`, `DB_USER`, `DB_PASSWORD` no momento de execução. Exemplo para Windows PowerShell:

```powershell
setx DB_URL "jdbc:mysql://localhost:3306/usuario"
setx DB_USER "root"
setx DB_PASSWORD "your_password"
# Em seguida abrir um novo terminal e rodar: .\mvnw.cmd spring-boot:run
```

3. Observação: o `spring.jpa.hibernate.ddl-auto=update` fará as tabelas com base nas entidades JPA do projeto — isso é útil para desenvolvimento, mas não recomendado para produção.

## JDK e Build

O projeto usa `java.version=21` por padrão no `pom.xml`. Se o seu ambiente não tiver JDK 21, você pode:

- Instalar JDK 21 e definir `JAVA_HOME` para apontar para ele (recomendado para compatibilidade com Spring Boot 3.3+);
- Ou ajustar o `pom.xml` para `java.version=17` se preferir usar JDK 17 (compatível com Spring Boot 3.x), porém é melhor manter o JDK mais recente quando possível.

Exemplo para Windows PowerShell (instalação manual omitida):

```powershell
setx JAVA_HOME "C:\\Program Files\\Java\\jdk-21"
setx PATH "%JAVA_HOME%\\bin;%PATH%"
```

Depois disso, reabra o terminal para que as variáveis tenham efeito e rode:

```powershell
.\mvnw.cmd -DskipTests clean package
.\mvnw.cmd spring-boot:run
```

## Arquivos de exemplo
- `environment.ts` - contém o `apiBaseUrl`.
- `proxy.conf.json` - proxy de desenvolvimento para evitar CORS locais.
- `auth.service.ts` - exemplo de serviço Angular para autenticação.
- `game.service.ts` - exemplo de serviço Angular para jogos.

## Atualizações de backend realizadas
- Adicionado `ApiExceptionHandler` (`@ControllerAdvice`) para devolver mensagens amigáveis em HTTP
- Corrigido `AuthService` para disparar `ResponseStatusException` em erros (401, 409, 404)
- Adicionado `CorsConfig` como filtro global para permitir origem `http://localhost:4200` e métodos GET/POST/PUT/DELETE/OPTIONS

Se quiser, eu posso preparar PRs automáticos com esses arquivos no repositório do Angular: me autorize a criar arquivos ou envie acesso para eu commitar.
