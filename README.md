# desafio-luiza

A documentação está [aqui](http://localhost:8080/v1/api/swagger-ui/index.html)

#### Para instalação, entre na pasta raiz do projeto e siga os passos abaixo:

##### Instalar as dependências e realizar testes: 
	.\mvnw package
##### Gerar versão. O profile já está configurado para "dev". Tirei os testes por já ter sido realizado na etapa anterior: 
	.\mvnw clean install -DskipTests
##### Construir a aplicação
	docker build  -t thiagopacheco/desafio-luiza .
##### Rodar aplicação	
	docker-compose up 	
