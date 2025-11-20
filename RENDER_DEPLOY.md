# 🚀 Deploy no Render - Games4Life Backend

## ✅ Configurações Realizadas para Render

Este projeto foi configurado com os seguintes ajustes para funcionar no **Render**:

### 1. **Dependências Adicionadas**
- ✅ `h2database` - Banco de dados em memória
- ✅ `spring-boot-actuator` - Health checks
- ✅ `spring-security-test` - Testes de segurança
- ✅ Lombok corrigido com `<optional>true</optional>`

### 2. **Arquivos de Configuração**
- ✅ `system.properties` - Especifica Java 21
- ✅ `Procfile` - Define como rodar a aplicação
- ✅ `render.yaml` - Configuração específica do Render
- ✅ `.gitignore` - Ignorar arquivos desnecessários
- ✅ `application-prod.properties` - Configuração de produção

### 3. **Melhorias de Segurança**
- ✅ SecurityConfig atualizado para permitir health checks
- ✅ H2 console desabilitado em produção
- ✅ CORS configurado para Vercel e Render

---

## 🔄 Passos para Deploy no Render

### **Passo 1: Preparar o Repositório Git**

```bash
# Comitar as mudanças
git add .
git commit -m "Configurar projeto para deploy no Render"
git push origin main
```

### **Passo 2: Criar um Serviço Web no Render**

1. Acesse [render.com](https://render.com)
2. Clique em **"New +"** → **"Web Service"**
3. Selecione **"Connect GitHub Repository"**
4. Escolha seu repositório `G4L-Spring`
5. Configure:
   - **Name**: `games4life-backend`
   - **Runtime**: `Java`
   - **Build Command**: `mvn clean package -DskipTests`
   - **Start Command**: `java -Dspring.profiles.active=prod -Dserver.port=$PORT -jar target/games4life-backend-0.0.1-SNAPSHOT.jar`

### **Passo 3: Configurar Variáveis de Ambiente**

No Render, adicione em **Environment**:
```
PORT=8080
JAVA_TOOL_OPTIONS=-Xmx512m -Xms256m
```

### **Passo 4: Deploy**

1. Clique em **"Deploy"**
2. Aguarde a build (cerca de 5-10 minutos)
3. Verifique os logs para erros

---

## 🧪 Testes Após Deploy

### **1. Verificar Health Check**

```bash
curl https://SEU-APP.onrender.com/actuator/health
```

Resposta esperada:
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "H2"
      }
    }
  }
}
```

### **2. Testar Endpoints**

```bash
# Listar consoles
curl https://SEU-APP.onrender.com/api/consoles

# Listar jogos
curl https://SEU-APP.onrender.com/api/games

# Listar acessórios
curl https://SEU-APP.onrender.com/api/accessories
```

### **3. Verificar CORS**

Acesse do seu frontend Angular:
```javascript
fetch('https://SEU-APP.onrender.com/api/consoles')
  .then(r => r.json())
  .then(data => console.log(data))
```

---

## ⚠️ Possíveis Erros e Soluções

### **Erro: `Maven not found`**
**Solução**: Use o `mvnw` do repositório
```bash
./mvnw clean package -DskipTests
```

### **Erro: `Port already in use`**
**Solução**: Já está configurado com `${PORT:8080}`

### **Erro: `Out of Memory`**
**Solução**: Aumentar limite de memória
```
JAVA_TOOL_OPTIONS=-Xmx1024m -Xms512m
```

### **Erro: `H2 database connection failed`**
**Solução**: Banco em memória será criado automaticamente

### **CORS Errors no Frontend**
**Solução**: Adicione sua URL do frontend em `CorsConfig.java`:
```java
cfg.setAllowedOrigins(List.of(
    "https://seu-frontend.vercel.app",
    "https://g4l-spring.onrender.com"
));
```

---

## 📊 Monitoramento no Render

1. Acesse o dashboard do seu serviço
2. Veja logs em **"Logs"**
3. Monitore uso de CPU/Memória em **"Metrics"**
4. Configure alerts se necessário

---

## 🔗 URLs Importantes

- **Backend**: `https://SEU-APP.onrender.com`
- **Health Check**: `https://SEU-APP.onrender.com/actuator/health`
- **API Base**: `https://SEU-APP.onrender.com/api`

---

## 📝 Próximos Passos

- [ ] Fazer deploy no Render
- [ ] Testar endpoints no Postman
- [ ] Conectar frontend Angular
- [ ] Validar CORS
- [ ] Testar autenticação

---

## 🆘 Suporte

Se encontrar problemas:
1. Verifique os logs do Render
2. Confirme que o `pom.xml` está correto
3. Teste localmente com `./mvnw spring-boot:run`
4. Valide com `curl` antes de testar no frontend

**Happy Deploying! 🚀**
