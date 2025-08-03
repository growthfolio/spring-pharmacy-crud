# 💊 Spring Pharmacy CRUD - Sistema de Gestão Farmacêutica

## 🎯 Objetivo de Aprendizado
Sistema CRUD desenvolvido para estudar **gestão de dados relacionais** e **Spring Boot fundamentals**. Implementa gerenciamento completo de farmácia com **produtos**, **categorias** e **relacionamentos JPA**, aplicando validações e boas práticas de desenvolvimento backend.

## 🛠️ Tecnologias Utilizadas
- **Framework:** Spring Boot, Spring Data JPA
- **Banco de dados:** MySQL
- **Build:** Maven
- **Validação:** Bean Validation (JSR-303)
- **Relacionamentos:** OneToMany, ManyToOne
- **Testes:** JUnit (planejado)

## 🚀 Demonstração
```json
// POST /categories - Criar categoria
{
  "nome": "Medicamentos",
  "descricao": "Produtos farmacêuticos para tratamento médico"
}

// POST /products - Criar produto
{
  "nome": "Paracetamol 500mg",
  "descricao": "Analgésico e antitérmico",
  "preco": 12.50,
  "quantidade": 100,
  "categoria": {
    "id": 1
  }
}

// GET /products - Listar produtos
[
  {
    "id": 1,
    "nome": "Paracetamol 500mg",
    "preco": 12.50,
    "quantidade": 100,
    "categoria": {
      "id": 1,
      "nome": "Medicamentos"
    }
  }
]
```

## 📁 Estrutura do Projeto
```
spring-pharmacy-crud/
├── src/main/java/
│   ├── controller/               # REST Controllers
│   │   ├── ProductController.java # Endpoints de produtos
│   │   └── CategoryController.java # Endpoints de categorias
│   ├── model/                    # Entidades JPA
│   │   ├── Product.java         # Entidade Produto
│   │   └── Category.java        # Entidade Categoria
│   ├── repository/               # Repositórios JPA
│   │   ├── ProductRepository.java
│   │   └── CategoryRepository.java
│   └── PharmacyApplication.java  # Classe principal
├── src/main/resources/
│   └── application.properties    # Configurações
├── pom.xml                       # Dependências Maven
└── target/                       # Arquivos compilados
```

## 💡 Principais Aprendizados

### 🗄️ Data Modeling
- **Entity relationships:** Relacionamentos bidirecionais
- **Foreign keys:** Chaves estrangeiras com JPA
- **Cascade operations:** Operações em cascata
- **Fetch strategies:** Lazy vs Eager loading
- **Data integrity:** Validações e constraints

### 🔄 CRUD Operations
- **Create:** Inserção de novos registros
- **Read:** Consultas simples e complexas
- **Update:** Atualização de dados existentes
- **Delete:** Remoção com verificação de integridade
- **Validation:** Validação de dados de entrada

### 🏗️ Spring Boot Architecture
- **Controller layer:** Endpoints REST bem estruturados
- **Repository layer:** Abstração de acesso a dados
- **Entity layer:** Mapeamento objeto-relacional
- **Configuration:** Configuração de banco e aplicação
- **Exception handling:** Tratamento de erros

## 🧠 Conceitos Técnicos Estudados

### 1. **Entity Mapping**
```java
@Entity
@Table(name = "tb_categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;
    
    @Size(max = 500, message = "Descrição não pode exceder 500 caracteres")
    private String descricao;
    
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("categoria")
    private List<Product> produtos = new ArrayList<>();
    
    // Constructors, getters, setters
}

@Entity
@Table(name = "tb_products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que zero")
    private BigDecimal preco;
    
    @Min(value = 0, message = "Quantidade não pode ser negativa")
    private Integer quantidade;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @JsonIgnoreProperties("produtos")
    private Category categoria;
}
```

### 2. **Repository Layer**
```java
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Query methods derivados do nome
    List<Product> findByNomeContainingIgnoreCase(String nome);
    
    List<Product> findByCategoriaId(Long categoriaId);
    
    List<Product> findByPrecoLessThan(BigDecimal preco);
    
    List<Product> findByQuantidadeGreaterThan(Integer quantidade);
    
    // Query personalizada
    @Query("SELECT p FROM Product p WHERE p.preco BETWEEN :min AND :max")
    List<Product> findByPrecoRange(@Param("min") BigDecimal min, 
                                  @Param("max") BigDecimal max);
}

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    List<Category> findByNomeContainingIgnoreCase(String nome);
    
    Optional<Category> findByNome(String nome);
}
```

### 3. **Controller Implementation**
```java
@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {
    
    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return productRepository.findById(id)
            .map(product -> ResponseEntity.ok().body(product))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(productRepository.save(product));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, 
                                        @Valid @RequestBody Product product) {
        return productRepository.findById(id)
            .map(existingProduct -> {
                product.setId(existingProduct.getId());
                return ResponseEntity.ok(productRepository.save(product));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return productRepository.findById(id)
            .map(product -> {
                productRepository.delete(product);
                return ResponseEntity.noContent().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
```

## 🚧 Desafios Enfrentados
1. **Relationship mapping:** Configuração correta de relacionamentos
2. **Circular references:** Evitar referências circulares no JSON
3. **Data validation:** Implementação de validações robustas
4. **Database configuration:** Configuração do MySQL
5. **Error handling:** Tratamento adequado de exceções

## 📚 Recursos Utilizados
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Data JPA Reference](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Bean Validation Specification](https://beanvalidation.org/2.0/spec/)
- [Generation Brasil Bootcamp](https://brazil.generation.org/) - Bootcamp onde o projeto foi desenvolvido

## 📈 Próximos Passos
- [ ] Implementar testes unitários e de integração
- [ ] Adicionar sistema de autenticação
- [ ] Criar sistema de estoque avançado
- [ ] Implementar relatórios de vendas
- [ ] Adicionar sistema de fornecedores
- [ ] Criar interface web com Thymeleaf

## 🔗 Projetos Relacionados
- [React Pharmacy Front](../react-pharmacy-front/) - Frontend da aplicação
- [Spring GameStore](../spring-gamestore/) - Sistema similar com jogos
- [Spring Bookstore Management](../spring-bookstore-management/) - Gestão de livros

---

**Desenvolvido por:** Felipe Macedo  
**Contato:** contato.dev.macedo@gmail.com  
**GitHub:** [FelipeMacedo](https://github.com/felipemacedo1)  
**LinkedIn:** [felipemacedo1](https://linkedin.com/in/felipemacedo1)

> 💡 **Reflexão:** Este projeto foi essencial para consolidar os fundamentos do Spring Boot e JPA. A implementação de relacionamentos entre entidades e validações proporcionou base sólida para projetos mais complexos.