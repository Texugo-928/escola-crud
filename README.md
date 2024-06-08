## Diagrama de Classes

```mermaid
classDiagram
    class Aluno {
        -nome: String
        -data_nascimento: String
        -endereco: List<Endereco>
        -serie: String
        -segmento: String
        -nome_pai: String
        -nome_mae: String
    }

    class Endereco {
        -tipo: String
        -rua: String
        -numero: String
        -cep: String
        -complemento: String
    }

    Aluno "1" *-- "N" Endereco
 ```
