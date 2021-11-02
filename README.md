# desafio-luiza

A documentação está [aqui](http://localhost:8080/v1/api/swagger-ui/index.html)

Para instalação, entre na pasta raiz do projeto e siga os passos abaixo:

1- instalar as dependências e realizar testes: 
	.\mvnw package
2- Gerar versão. O profile já está configurado para "dev". Tirei os testes por já ter sido realizado na etapa anterior: 
	.\mvnw clean install -DskipTests
3- Construir a aplicação
	docker build  -t thiagopacheco/desafio-luiza .
4- Rodar aplicação	
	docker-compose up 	
