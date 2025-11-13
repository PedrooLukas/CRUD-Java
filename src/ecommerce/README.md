# E-Commerce System - CRUD Completo em Java

Sistema completo de e-commerce desenvolvido em Java aplicando todos os princípios da Programação Orientada a Objetos.

## 📋 Funcionalidades

- ✅ CRUD completo de Produtos (Físicos e Digitais)
- ✅ CRUD completo de Usuários (Clientes e Administradores)
- ✅ CRUD completo de Pedidos
- ✅ Sistema de estoque para produtos físicos
- ✅ Cálculo automático de frete
- ✅ Sistema de descontos (porcentagem e valor fixo)
- ✅ Gerenciamento de status de pedidos
- ✅ Validações completas
- ✅ Interface de menu interativa


## 📁 Estrutura do Projeto

```
ecommerce/
├── model/
│   ├── User.java (abstrata)
│   ├── Customer.java
│   ├── Admin.java
│   ├── Product.java (abstrata)
│   ├── PhysicalProduct.java
│   ├── DigitalProduct.java
│   ├── Order.java
│   └── OrderItem.java
├── repository/
│   ├── Repository.java (interface genérica)
│   ├── ProductRepository.java (interface)
│   ├── UserRepository.java (interface)
│   ├── OrderRepository.java (interface)
│   └── impl/
│       ├── ProductRepositoryImpl.java
│       ├── UserRepositoryImpl.java
│       └── OrderRepositoryImpl.java
├── service/
│   ├── ProductService.java
│   ├── UserService.java
│   └── OrderService.java
├── util/
│   ├── ValidationUtil.java
│   └── FormatUtil.java
└── ECommerceApplication.java (Main)
```


## 📊 Dados de Exemplo

O sistema já vem com dados inicializados:

**Usuários:**
- Cliente: João Silva (joao@email.com)
- Cliente: Maria Santos (maria@email.com)
- Admin: Admin (admin@ecommerce.com)

**Produtos:**
- Notebook Dell - R$ 3.500,00 (10 unidades)
- Mouse Logitech - R$ 250,00 (50 unidades)
- E-book Java Programming - R$ 49,90 (digital)
- Curso Web Development - R$ 199,90 (digital)

## 🔧 Boas Práticas Aplicadas

1. **Clean Code**: Nomes descritivos e código legível
2. **SOLID Principles**:
   - Single Responsibility: Cada classe tem uma responsabilidade única
   - Open/Closed: Aberto para extensão, fechado para modificação
   - Liskov Substitution: Subclasses podem substituir suas classes base
   - Interface Segregation: Interfaces específicas
   - Dependency Inversion: Dependência de abstrações, não implementações
3. **Design Patterns**:
   - Repository Pattern para acesso a dados
   - Service Layer para lógica de negócio
4. **Validações**: Classe utilitária para validações centralizadas
5. **Formatação**: Classe utilitária para formatação de dados
6. **Tratamento de Erros**: Exceções apropriadas e mensagens claras
7. **Streams e Lambda**: Uso de API funcional do Java
8. **Optional**: Evita NullPointerException

## 📝 Funcionalidades Detalhadas

### Gestão de Produtos
- Criar produtos físicos e digitais
- Listar todos os produtos
- Buscar por ID
- Atualizar informações
- Deletar produtos
- Atualizar estoque
- Filtrar por categoria
- Filtrar por faixa de preço

### Gestão de Usuários
- Criar clientes e administradores
- Listar todos os usuários
- Buscar por ID ou email
- Atualizar informações
- Deletar usuários
- Ativar/Desativar usuários
- Autenticação

### Gestão de Pedidos
- Criar pedidos
- Adicionar itens ao pedido
- Aplicar descontos
- Atualizar status (Pendente → Confirmado → Processando → Enviado → Entregue)
- Cancelar pedidos (com devolução de estoque)
- Visualizar histórico de pedidos
- Calcular receita total

## 🎓 Abordagem e conceitos do Java usados:

- ✅ Classes e Objetos
- ✅ Herança e Classes Abstratas
- ✅ Polimorfismo
- ✅ Encapsulamento
- ✅ Sobrecarga de Métodos
- ✅ Interfaces
- ✅ Composição
- ✅ Generics (`Repository<T>`)
- ✅ Collections (List, Map, Set)
- ✅ Streams e Lambda
- ✅ Optional
- ✅ Enums (`OrderStatus`)
- ✅ BigDecimal para valores monetários
- ✅ LocalDateTime para datas
- ✅ Exception Handling
