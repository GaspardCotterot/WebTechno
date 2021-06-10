# WebTechno (Spring Boot)
---
###How to use/install

---
* #####Indiquer l'url de votre base de données
Dans le fichier *src/main/resources/application.properties*, rentrer les informations comme indiqué :
```
# Datasource
spring.datasource.url=jdbc:mysql://localhost:3306/nomDeLaBase?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=root
```

* #####Lancer l'application

Il suffit de lancer la fonction main de `src/main/java/Isep/webtechno/WebtechnoApplication.java`

Pour cela vous pouvez utiliser un IDE comme Intellij ou exécuter le fichier mvnw : `./mvnw spring-boot:run`
