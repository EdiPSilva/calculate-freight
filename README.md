# Calculate Shipping
Este projeto é uma API RestFull que possui o objetivo de realizar o cálculo de frete para as cotações de frete e pedidos.

|Técnologia| Versão             |
|----------|--------------------|
|Java      | `17`               |
|Spring    | `2.7.1`            |
|Postgres  | `15.2-1.pgdg110+1` |

### Executar local
Para conseguir executar a aplicação no seu ambiente local, é necessário ter o Java 17 instalado e configurado e também o docker.

Esta aplicação têm depência da aplicação **[Data Calculate Shipping](https://github.com/EdiPSilva/data-calculate-freight)** que na raiz da mesma esta configurado o container do banco e as migration de atualiza.

Dessa forma caso já tenha realizado o cone e configurado a mesma, basta rodar o comando do docker compose para subir a base de dados.
``` 
docker-compose up -d
```
Após o banco postgres subir corretamente configure a IDE de sua preferência para executar a classe principal.
```
src.main.java.br.com.java.calculatefreight.CalculateFreightApplication
```
A aplicação roda na porta `8081`, então para acessar a documentação do **Swagger** basta utilizar da url
```
http://localhost:8081/swagger-ui.html
```