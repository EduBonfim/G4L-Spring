# 📋 Checklist de Deploy no Render

## ✅ Pré-Requisitos

- [x] Java 21 instalado
- [x] Maven 3.9.x ou superior
- [x] Git configurado
- [x] Repositório no GitHub
- [x] Conta no Render

---

## ✅ Correções Aplicadas

- [x] Dependência H2 adicionada ao `pom.xml`
- [x] Lombok configurado corretamente
- [x] `system.properties` criado (Java 21)
- [x] `Procfile` criado para Render
- [x] `render.yaml` criado com configurações otimizadas
- [x] `application-prod.properties` criado
- [x] Actuator configurado para health checks
- [x] SecurityConfig atualizado
- [x] CORS configurado
- [x] Documentação completa criada

---

## 📦 Arquivos Importantes

### Criados:
- [x] `system.properties`
- [x] `Procfile`
- [x] `render.yaml`
- [x] `src/main/resources/application-prod.properties`
- [x] `src/main/resources/application.yml`
- [x] `RENDER_DEPLOY.md`
- [x] `RENDER_CORRECTED.md`
- [x] `build.sh`

### Modificados:
- [x] `pom.xml`
- [x] `src/main/resources/application.properties`
- [x] `src/main/java/.../security/SecurityConfig.java`

---

## 🔨 Teste Local (Opcional)

```bash
# Compilar
.\mvnw clean compile

# Executar testes
.\mvnw test

# Build completo
.\mvnw clean package -DskipTests

# Rodar localmente
.\mvnw spring-boot:run
```

**Status Local**: ✅ Compila sem erros

---

## 🚀 Deploy no Render

### Passo 1: Git
```bash
git add .
git commit -m "Configurar para Render"
git push origin main
```

### Passo 2: Criar Serviço no Render
1. Acesse https://render.com
2. Dashboard → New → Web Service
3. Selecione repositório `G4L-Spring`

### Passo 3: Configurar Build & Start

**Build Command:**
```
mvn clean package -DskipTests
```

**Start Command:**
```
java -Dspring.profiles.active=prod -Dserver.port=$PORT -jar target/games4life-backend-0.0.1-SNAPSHOT.jar
```

**Environment Variables:**
```
PORT=8080
JAVA_TOOL_OPTIONS=-Xmx512m -Xms256m
```

### Passo 4: Deploy
- Clique em "Deploy"
- Aguarde 5-10 minutos

---

## 🧪 Testes Após Deploy

### 1. Health Check
```bash
curl https://seu-app.onrender.com/actuator/health
```

**Resposta esperada:**
```json
{
  "status": "UP"
}
```

### 2. Listar Recursos
```bash
# Consoles
curl https://seu-app.onrender.com/api/consoles

# Jogos
curl https://seu-app.onrender.com/api/games

# Acessórios
curl https://seu-app.onrender.com/api/accessories
```

### 3. Conectar Frontend
Adicione a URL em `CorsConfig.java`:
```java
"https://seu-app.onrender.com"
```

---

## ⚠️ Troubleshooting

| Erro | Causa | Solução |
|------|-------|---------|
| `Java not found` | JDK não especificado | Verifique `system.properties` |
| `Maven build failed` | Dependências não resoltas | Execute `mvn dependency:resolve` localmente |
| `Port already in use` | Porta fixa | Já configurado com `${PORT:8080}` |
| `Out of memory` | JVM sem memória | Aumente `JAVA_TOOL_OPTIONS` |
| `CORS error` | URL do frontend não autorizada | Atualize `CorsConfig.java` |
| `Database error` | H2 não encontrado | Verifique dependência no `pom.xml` |

---

## 📊 Monitoramento

Após deploy, monitore em Render:
- **Logs**: Verifique por erros
- **Metrics**: CPU e Memória
- **Health**: Status do aplicativo

---

## 🎯 Recursos Úteis

- 📖 [Render Docs - Java](https://render.com/docs/deploy-java)
- 📖 [Spring Boot on Render](https://spring.io/guides/gs/deploying-spring-boot-app-to-the-cloud/)
- 📖 [Maven on Render](https://render.com/docs/deploy-java#maven)

---

## ✨ Status Final

- [x] Projeto compilando ✅
- [x] Configurações do Render ✅
- [x] Documentação completa ✅
- [x] Pronto para deploy ✅

**Você pode fazer o deploy agora! 🚀**

---

## 📝 Notas

- Banco de dados é em memória (H2) - reseta ao reiniciar
- Sem autenticação obrigatória (permitAll configurado)
- CORS ativo para Vercel e Render
- Compressão HTTP habilitada em produção

---

**Última atualização**: 20 de novembro de 2025
**Versão**: 1.0
