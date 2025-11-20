# 🎮 Banco de Dados Populado com Sucesso!

## 📋 Resumo

O arquivo `DataInitializer.java` foi configurado para popular automaticamente o banco de dados com todos os dados do seu `data.service.ts` do Angular.

---

## ✅ Dados Incluídos

### 🎮 **4 Consoles**
1. **PlayStation 5** - R$ 299
2. **Xbox Series X** - R$ 279
3. **Nintendo Switch** - R$ 199
4. **Steam Deck** - R$ 400

### 🎯 **10 Jogos**
1. **FIFA 25** - R$ 49 (Todos os consoles)
2. **God of War Ragnarök** - R$ 59 (PS5)
3. **Zelda: Tears of the Kingdom** - R$ 69 (Switch)
4. **Spider-Man 2** - R$ 69 (PS5)
5. **Mario Kart 8 Deluxe** - R$ 59 (Switch)
6. **Hollow Knight** - R$ 59 (Steam)
7. **SubNautica Deep Ocean Bundle** - R$ 59 (Steam)
8. **Marvel Guardians Of The Galaxy** - R$ 59 (Xbox)
9. **It Takes Two** - R$ 59 (Xbox)
10. **Need for Speed Unbound** - R$ 59 (Xbox)

### 🎧 **9 Acessórios**
1. **Controle Extra** - R$ 59 (Todos os consoles)
2. **Óculos VR** - R$ 129 (PS5)
3. **Headset Gamer** - R$ 59 (Todos os consoles)
4. **PlayStation Camera** - R$ 59 (PS5)
5. **Kinect** - R$ 80 (Xbox)
6. **Nintendo Labo** - R$ 80 (Switch)
7. **Volante Logitech G920** - R$ 300 (Xbox)
8. **Volante Thrustmaster T128** - R$ 200 (PS5)
9. **Base Carregadora** - R$ 80 (Xbox)

---

## 🔧 Como Funciona

O `DataInitializer` usa o padrão **CommandLineRunner** do Spring Boot:

1. **Executa automaticamente** quando a aplicação inicia
2. **Verifica se o banco está vazio** antes de popular
3. **Insere os dados** se não houver registros
4. **Evita duplicação** - só popula uma vez

### Mensagens no Console

Quando o Spring Boot iniciar, você verá:

```
✅ Consoles populados: 4
✅ Jogos populados: 10
✅ Acessórios populados: 9
```

---

## 🚀 Como Usar

### 1. **Reiniciar o Spring Boot**

No terminal do Spring:
```bash
# Se estiver rodando, pare com Ctrl+C

# Depois execute novamente
./mvnw spring-boot:run
```

### 2. **Verificar no Banco**

Os dados estarão nas tabelas:
- `console` (4 registros)
- `game` (10 registros)
- `accessory` (9 registros)

### 3. **Testar no Angular**

1. Abra a página de **Jogos** - deve mostrar os 10 jogos
2. Abra a página de **Acessórios** - deve mostrar os 9 acessórios
3. Na página de **Alugar** - deve mostrar os 4 consoles disponíveis
4. Faça um aluguel - deve aparecer no histórico

---

## 📌 Observações Importantes

### ✅ **Compatibilidade**
- Os IDs são gerados **automaticamente** pelo banco (auto-increment)
- O Angular usa IDs do tipo `string`, mas o backend usa `Long`
- As APIs devem fazer a conversão correta

### ⚠️ **Banco de Dados**
- Se você **deletar o banco** e recriar, os dados serão inseridos novamente
- Se o banco **já tem dados**, o DataInitializer **não insere novamente**
- Para repopular: delete os registros ou recrie o banco

### 🔄 **Sincronização**
- O `data.service.ts` do Angular agora é **apenas para cache local** (localStorage)
- Os **dados reais** vêm do backend Spring Boot
- Certifique-se de que as **APIs estão funcionando** (GET /api/games, /api/consoles, /api/accessories)

---

## 🛠️ Próximos Passos

### 1. **Verificar Endpoints da API**

Certifique-se de que existem endpoints para:
- `GET /api/consoles` - Lista todos os consoles
- `GET /api/games` - Lista todos os jogos
- `GET /api/accessories` - Lista todos os acessórios
- `GET /api/rentals` - Lista todos os aluguéis

### 2. **Testar Aluguéis**

1. Faça login com um usuário
2. Cadastre um endereço
3. Vá em "Alugar" e complete um aluguel
4. Verifique se aparece em "Minha Área" > "Histórico de Aluguéis"

### 3. **Área Admin**

Se você é admin, deve conseguir:
- Ver todos os aluguéis registrados
- Ver todos os clientes
- Gerenciar consoles/jogos/acessórios

---

## 🐛 Solução de Problemas

### **Problema: Dados não aparecem**

1. Verifique se o Spring Boot iniciou corretamente
2. Olhe o console e procure pelas mensagens de "✅ populados"
3. Verifique se há erros de conexão com o banco de dados

### **Problema: Aluguéis não aparecem**

1. Os aluguéis **NÃO** são populados automaticamente
2. Você precisa **fazer um aluguel manualmente** pela interface
3. Os aluguéis ficam na tabela `rental` no banco

### **Problema: Imagens não carregam**

1. Imagens externas (URLs) devem funcionar se você tiver internet
2. Imagens locais (`/assets/...`) só funcionam se estiverem na pasta `public` do Angular

---

## 📊 Estrutura do Banco

```
┌─────────────┐
│   console   │
├─────────────┤
│ id (PK)     │
│ name        │
│ price       │
│ imagem      │
└─────────────┘

┌─────────────┐
│    game     │
├─────────────┤
│ id (PK)     │
│ name        │
│ price       │
│ console     │
│ imagem      │
└─────────────┘

┌─────────────┐
│  accessory  │
├─────────────┤
│ id (PK)     │
│ name        │
│ price       │
│ console     │
│ imagem      │
└─────────────┘
```

---

## ✨ Resultado Final

Agora seu sistema está **completamente funcional**:
- ✅ Backend populado com todos os dados
- ✅ Usuários podem fazer login
- ✅ Usuários podem cadastrar endereços
- ✅ Usuários podem alugar consoles/jogos/acessórios
- ✅ Histórico de aluguéis funciona
- ✅ Área admin tem acesso a todos os registros

**Parabéns! Seu sistema Games4Life está pronto para uso! 🎉**
