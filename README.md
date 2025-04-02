# API de Transações - Itaú Unibanco Desafio de Programação

API REST que recebe Transações e retorna Estatísticas sob essas transações.

## Autor

Marcelo de Andrade Silva Filho  
[marceloandradesilvafilho@gmail.com](mailto:marceloandradesilvafilho@gmail.com)

## Pré-requisitos

- **JDK 21 ou superior:** Certifique-se de que o JDK está instalado (verifique com `java -version`).
- **Maven:** Build do projeto com o Maven
- **Git:** Para clonar o repositório.

## Como Construir a Aplicação

### Usando Maven

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/marceloasfilho/desafio-itau-vaga-99-junior.git
   cd 'SeuRepositorio'
   ```
2. **Construa o projeto:**

```bash
    mvn clean install
```
# Como Executar a Aplicação?

Após construir o projeto, você pode executá-lo de duas maneiras:

## 1. Executando o JAR Gerado
Se o build gerar um arquivo JAR (por exemplo, SeuProjeto.jar), execute:
```bash
    java -jar target/'SeuProjeto'.jar    # para Maven
```
## 2. Executando via IDE
- Importe o projeto na sua IDE (por exemplo, IntelliJ IDEA).
- Localize a classe principal (geralmente anotada com @SpringBootApplication).
- Execute a aplicação diretamente pela IDE.

# Exemplos de Uso
Teste a API com o Postman ou qualquer outro cliente HTTP o seguindo endpoint abaixo

```bash
    GET http://localhost:8080/healthcheck
```
Exemplo de resposta esperada:

```json
{
  "status": "UP",
  "timestamp": "2025-04-02T12:32:41.634460800Z"
}
```