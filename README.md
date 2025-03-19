# API de Gerenciamento de Veículos

Esta é uma API RESTful desenvolvida em Spring Boot para gerenciamento de veículos. A API permite a criação, leitura, atualização e exclusão (CRUD) de veículos, além de suportar diferentes tipos de veículos, como carros, motos e caminhões.

---
<br>

## Tecnologias Utilizadas

- **Spring Boot**: Framework principal para desenvolvimento da aplicação.
- **Spring Data JPA**: Anotações para o banco de dados.
- **H2 Database**: Banco de dados em memória para desenvolvimento e testes.
- **Swagger**: Documentação da API.
- **Lombok**: Para redução de boilerplate code.
- **JUnit e Mockito**: Para testes unitários e de integração.

---
<br>

## Endpoints da API

A API oferece os seguintes endpoints:

### Criar um Veículo

**POST** `/veiculos`

<details>
<summary>Requisição</summary>

```json
{
  "tipo": "Carro",
  "marca": "Toyota",
  "modelo": "Corolla",
  "anoFabricacao": 2023,
  "numeroPortas": 4
}
```

**Resposta**

```json
{
  "id": 1,
  "tipo": "Carro",
  "marca": "Toyota",
  "modelo": "Corolla",
  "anoFabricacao": 2023,
  "numeroPortas": 4
}
```
---

</details>

### Listar Todos os Veículos

**GET** `/veiculos`
  


<details>
<summary>Resposta</summary>

```json
[
  {
    "id": 1,
    "tipo": "Carro",
    "marca": "Toyota",
    "modelo": "Corolla",
    "anoFabricacao": 2023,
    "numeroPortas": 4
  },
  {
    "id": 2,
    "tipo": "Moto",
    "marca": "Honda",
    "modelo": "CB 500",
    "anoFabricacao": 2022,
    "temPartidaEletrica": true
  }
]
```

---

</details>

### Buscar Veículo por ID

**GET** `/veiculos/{id}`

<details>
<summary>Resposta</summary>

```json
{
  "id": 1,
  "tipo": "Carro",
  "marca": "Toyota",
  "modelo": "Corolla",
  "anoFabricacao": 2023,
  "numeroPortas": 4
}
```

---

</details>

### Atualizar Veículo

**PUT** `/veiculos/{id}`

<details>
<summary>Requisição</summary>

```json
{
  "id": 1,
  "tipo": "Carro",
  "marca": "Ford",
  "modelo": "Focus",
  "anoFabricacao": 2021,
  "numeroPortas": 4
}
```

**Resposta:**

```json
{
  "id": 1,
  "tipo": "Carro",
  "marca": "Ford",
  "modelo": "Focus",
  "anoFabricacao": 2021,
  "numeroPortas": 4
}
```

---
</details>

### Excluir Veículo

**DELETE** `/veiculos/{id}`

**Resposta:** `204 No Content`
<br><br><br>

## Testes

A aplicação inclui testes para garantir a confiabilidade das funcionalidades implementadas. Os testes são organizados para validar os serviços, controladores e exceções. 

Os testes podem ser executados utilizando o Maven ou diretamente em uma IDE compatível com JUnit. Para rodar os testes via terminal, utilize:

```sh
mvn test
```

### Testes de Unidade
Os testes de unidade garantem que os métodos individuais funcionam conforme esperado. 
Eles estão localizados no diretório `src/test/java/com/dbserver/veiculo` e incluem testes para:

- **Carregar contexto da aplicação**
- **Controladores**: validação de requisições e retornos esperados.
- **Serviços**: verificação de regras de negócio e manipulação de entidades.
- **Exceções**: tratamento adequado de erros e mensagens personalizadas.

Exemplo de teste no `VeiculoApplicationTests.java`:

<details>
<summary>Código</summary>
  
```java
@SpringBootTest
class VeiculoApplicationTests {

    @Test
    void contextLoads() {
    }
}
```
</details>

Exemplo de teste de controlador no `VeiculoControllerTest.java`:

<details>
<summary>Código</summary>
  
```java
@WebMvcTest(VeiculoController.class)
class VeiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VeiculoService veiculoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarVeiculo() throws Exception {
        CarroDTO dto = new CarroDTO();
        dto.setTipo("Carro");
        dto.setMarca("Toyota");
        dto.setModelo("Corolla");
        dto.setNumeroPortas(4);
        dto.setAnoFabricacao(2005);

        when(veiculoService.save(any())).thenReturn(dto);

        mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }
```

---
</details>

## DTOs

Os **DTOs (Data Transfer Objects)** são utilizados para transportar dados entre a API e a camada de persistência, garantindo encapsulamento e segurança. Eles estão localizados no pacote `com.dbserver.veiculo.dto`.

Principais DTOs:

- `CarroDTO.java`: Representa a estrutura de um carro.
- `MotoDTO.java`: Representa a estrutura de uma moto.
- `CaminhaoDTO.java`: Representa a estrutura de um caminhão.
- `VeiculoDTO.java`: Classe base abstrata para todos os veículos.

Cada DTO contém métodos `fromEntity` e `toEntity`, permitindo a conversão entre DTOs e entidades do banco de dados.

Exemplo de um DTO (`CarroDTO.java`):

<details>
<summary>Código</summary>
  
```java
@Data
@EqualsAndHashCode(callSuper = true)
public class CarroDTO extends VeiculoDTO {
    
    @NotNull(message = "O campo 'numeroPortas' não pode ser nulo.")
    private Integer numeroPortas;

    public static CarroDTO fromEntity(Carro carro) {
        CarroDTO carroDTO = new CarroDTO();
        carroDTO.setId(carro.getId());
        carroDTO.setTipo(carro.getTipo());
        carroDTO.setMarca(carro.getMarca());
        carroDTO.setModelo(carro.getModelo());
        carroDTO.setAnoFabricacao(carro.getAnoFabricacao());
        carroDTO.setNumeroPortas(carro.getNumeroPortas());
        return carroDTO;
    }

    @Override
    public Carro toEntity() {
        Carro carro = new Carro();
        carro.setId(this.getId());
        carro.setTipo(this.getTipo());
        carro.setMarca(this.getMarca());
        carro.setModelo(this.getModelo());
        carro.setAnoFabricacao(this.getAnoFabricacao());
        carro.setNumeroPortas(this.getNumeroPortas());
        return carro;
    }
}
```

---
</details>

## Exceptions

O tratamento de exceções é feito por meio de classes personalizadas e um **Global Exception Handler**, garantindo mensagens amigáveis para o usuário e retornos HTTP adequados. 

### Exceções personalizadas
- `VeiculoNotFoundException.java`: Exceção para veículos não encontrados.

<details>
<summary>Código</summary>

```java
public class VeiculoNotFoundException extends RuntimeException {
    public VeiculoNotFoundException(Long id) {
        super("Veículo com ID " + id + " não encontrado.");
    }
}
```
</details>

### Global Exception Handler
O `GlobalExceptionHandler` captura e trata exceções, retornando respostas estruturadas conforme a falha.

<details>
<summary>Código</summary>

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(VeiculoNotFoundException.class)
    public ResponseEntity<String> handleVeiculoNotFoundException(VeiculoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
```

</details>

Com isso, a API retorna mensagens de erro claras para o usuário quando uma requisição falha devido a dados inválidos ou entidades não encontradas.

---

Isso conclui a explicação sobre os testes, DTOs e exceções na API Veículo.



## Como Executar o Projeto

1. Clone este repositório:

   ```sh
   git clone https://github.com/joaoespdev/Veiculo-CRUD-DB.git
   ```

2. Acesse o diretório do projeto:

   ```sh
   cd veiculo
   ```

3. Compile e execute a aplicação com o Maven:

   ```sh
   mvn spring-boot:run
   ```

4. API disponível em `http://localhost:8080/veiculos`.

   ```sh
   Ou acessando o banco de dados H2:
   
   http://localhost:8080/h2-console
   usuário: sa
   senha:
   ```
   
5. Interação com a API:

   ```sh
   Faça requisições em: http://localhost:8080/swagger-ui.html
   ```

