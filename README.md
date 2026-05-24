# Cadastro Pessoas JSF PrimeFaces

Projeto web Java com Maven, Apache Tomcat 10.1+, Jakarta Faces, CDI via Weld Servlet e PrimeFaces 15.

## Requisitos

- Java 17 ou superior
- Maven 3.9+
- Apache Tomcat 10.1+

## Estrutura

```text
cadastro-pessoas-jsf/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── br/
        │       └── com/
        │           └── exemplo/
        │               ├── model/
        │               │   └── Pessoa.java
        │               └── view/
        │                   └── PessoaBean.java
        └── webapp/
            ├── index.xhtml
            └── WEB-INF/
                ├── beans.xml
                ├── faces-config.xml
                └── web.xml
```

## Gerar o WAR

```bash
mvn clean package
```

O arquivo gerado sera:

```text
target/cadastro-pessoas-jsf.war
```

## Deploy no Tomcat

Copie o WAR para a pasta `webapps` do Tomcat:

```bash
copy target\cadastro-pessoas-jsf.war C:\apache-tomcat-10.1.x\webapps\
```

No Linux/macOS:

```bash
cp target/cadastro-pessoas-jsf.war /opt/apache-tomcat-10.1.x/webapps/
```

Depois inicie o Tomcat:

```bash
C:\apache-tomcat-10.1.x\bin\startup.bat
```

No Linux/macOS:

```bash
/opt/apache-tomcat-10.1.x/bin/startup.sh
```

## Acessar

Abra no navegador:

```text
http://localhost:8080/cadastro-pessoas-jsf/
```

## Compatibilidade

Tomcat 10.1 usa Jakarta Servlet 6 e pacotes `jakarta.*`. Por isso este projeto usa Jakarta Faces 4, PrimeFaces 15 com classifier `jakarta` e nenhum import `javax.*`.

Como Tomcat nao e um servidor Jakarta EE completo, ele nao fornece CDI por padrao. O projeto inclui `weld-servlet-shaded` e `WEB-INF/beans.xml` para habilitar CDI no WAR.
