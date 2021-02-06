
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Caiuzu_forum&metric=bugs)](https://sonarcloud.io/dashboard?id=Caiuzu_forum)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Caiuzu_forum&metric=code_smells)](https://sonarcloud.io/dashboard?id=Caiuzu_forum)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=Caiuzu_forum&metric=ncloc)](https://sonarcloud.io/dashboard?id=Caiuzu_forum)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Caiuzu_forum&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=Caiuzu_forum)

# Configurando [sonarcloud](https://sonarcloud.io):
###### Windows 10:

![img_1.png](img/img_1.png)

0 - gerar de forma manual e pegar o token (caminho para este projeto: https://sonarcloud.io/project/configuration?analysisMode=GitHubManual&id=Caiuzu_forum)

![img_2.png](img/img_2.png)

**1 - adicionar as linhas abaixo no pom.xml**

```properties
<properties>
<sonar.projectKey>Caiuzu_forum</sonar.projectKey>
<sonar.organization>caiuzu</sonar.organization>
<sonar.host.url>https://sonarcloud.io</sonar.host.url>
</properties>
```

**2 - seta a variavel de ambiente do sonar_token**

![img.png](img/img.png)

**3 - Setar JAVA_HOME como java11 com o comando abaixo:**

```
set JAVA_HOME=C:\Users\Lover\.jdks\adopt-openjdk-11.0.10
```

**4 - Rodar o mvn verify:** 

```
mvn verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.login=%SONAR_TOKEN%
```

![img_3.png](img/img_3.png)

**Obs.:** _Apartir desta data o SonarCloud considera o java8 depreciado, sendo assim, para projetos utilizando java8, devemos apenas rodar o comando como java11._