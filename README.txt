TRIKAS-CORP
===========

Projeto web Java tradicional usando Java 17, Maven, Jakarta JSF, PrimeFaces,
Apache Tomcat 10.1 e PostgreSQL.


1. Tecnologias utilizadas
-------------------------

- Java 17 ou superior
- Maven
- Apache Tomcat 10.1+
- Jakarta Faces / JSF
- PrimeFaces 15
- PostgreSQL
- JDBC PostgreSQL
- SLF4J + Logback
- BCrypt para hash de senha
- IntelliJ IDEA como IDE recomendada


2. Versoes utilizadas neste projeto
-----------------------------------

- Java: 17
- Maven: 3.9.16 no ambiente local
- Apache Tomcat: 10.1.55 no ambiente local
- PostgreSQL: 13.21 no ambiente local
- Jakarta Faces: 4.0.11
- PrimeFaces: 15.0.0 com classifier jakarta
- PostgreSQL JDBC Driver: 42.7.11
- Weld Servlet: 5.1.2.Final
- Hibernate Validator: 8.0.1.Final
- SLF4J: 2.0.16
- Logback: 1.5.16
- BCrypt: 0.10.2


3. Links oficiais para download
-------------------------------

Java 17:
- Eclipse Temurin: https://adoptium.net/temurin/releases/?version=17
- Microsoft OpenJDK: https://learn.microsoft.com/en-us/java/openjdk/download
- Oracle Java: https://www.oracle.com/java/technologies/downloads/

Maven:
- Download: https://maven.apache.org/download.cgi
- Instalacao: https://maven.apache.org/install.html

Apache Tomcat 10:
- Download: https://tomcat.apache.org/download-10.cgi

PostgreSQL:
- Download geral: https://www.postgresql.org/download/
- Windows: https://www.postgresql.org/download/windows/

PrimeFaces:
- Site oficial: https://www.primefaces.org/
- Documentacao: https://primefaces.github.io/primefaces/

Jakarta EE:
- Site oficial: https://jakarta.ee/


4. Requisitos minimos
---------------------

- Windows 10 ou Windows 11
- Java JDK 17 instalado
- Maven configurado no PATH
- PostgreSQL instalado e em execucao
- Apache Tomcat 10.1 ou superior
- Porta 8080 livre para Tomcat
- Porta 5432 livre para PostgreSQL
- IntelliJ IDEA Community ou Ultimate


5. Configuracao do Java
-----------------------

Instale um JDK 17.

Exemplo de caminho no Windows:

C:\Program Files\Java\zulu17.60.17-ca-jdk17.0.16-win_x64

Configure a variavel de ambiente JAVA_HOME apontando para a pasta do JDK.

Exemplo:

JAVA_HOME=C:\Program Files\Java\zulu17.60.17-ca-jdk17.0.16-win_x64

Adicione ao PATH:

%JAVA_HOME%\bin

Para validar:

java -version
javac -version


6. Configuracao do Maven
------------------------

Instale o Maven e configure a variavel MAVEN_HOME.

Exemplo:

MAVEN_HOME=C:\Program Files\Apache\Maven\apache-maven-3.9.16

Adicione ao PATH:

%MAVEN_HOME%\bin

Para validar:

mvn -version

Caso o comando mvn nao seja reconhecido, use o caminho completo:

& "C:\Program Files\Apache\Maven\apache-maven-3.9.16\bin\mvn.cmd" -version


7. Configuracao do PostgreSQL
-----------------------------

Porta padrao utilizada:

5432

Banco utilizado pelo projeto:

trikas_db

Usuario utilizado pelo projeto:

user_trikas

Observacao:
Nao versionar senha real no GitHub. Para ambiente local, use senha de
desenvolvimento. Em ambiente real, usar variavel de ambiente, secret manager
ou arquivo externo nao versionado.


8. Criacao da database e usuario
--------------------------------

Entrar no psql como usuario postgres:

& "C:\Program Files\PostgreSQL\13\bin\psql.exe" -U postgres

Comandos SQL:

CREATE USER user_trikas WITH PASSWORD 'sua_senha_local';
CREATE DATABASE trikas_db OWNER user_trikas;
GRANT ALL PRIVILEGES ON DATABASE trikas_db TO user_trikas;

Conectar no banco:

\c trikas_db

Permissoes no schema public:

GRANT USAGE ON SCHEMA public TO user_trikas;
GRANT CREATE ON SCHEMA public TO user_trikas;


9. Tabela usuario
-----------------

Tabela usada no login:

public.usuario

Campos esperados pelo codigo atual:

- nome
- matricula
- email
- senha_hash
- cargo
- perfil

Exemplo de criacao:

CREATE TABLE public.usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    matricula VARCHAR(30) NOT NULL UNIQUE,
    email VARCHAR(160),
    senha_hash VARCHAR(100),
    cargo VARCHAR(80),
    perfil VARCHAR(50)
);

Indice recomendado para login:

CREATE INDEX idx_usuario_matricula
ON public.usuario (matricula);

Permissao para o usuario da aplicacao:

GRANT SELECT, INSERT, UPDATE, DELETE
ON TABLE public.usuario
TO user_trikas;

GRANT USAGE, SELECT, UPDATE
ON ALL SEQUENCES IN SCHEMA public
TO user_trikas;


10. Como executar scripts SQL
-----------------------------

Pelo pgAdmin:

1. Abrir pgAdmin.
2. Conectar no servidor PostgreSQL.
3. Selecionar a database trikas_db.
4. Abrir Query Tool.
5. Colar o SQL.
6. Clicar em Execute.

Pelo psql:

& "C:\Program Files\PostgreSQL\13\bin\psql.exe" -U user_trikas -d trikas_db -h localhost -p 5432

Depois executar os comandos SQL manualmente.


11. Como gerar o WAR
--------------------

Na raiz do projeto, onde esta o pom.xml:

mvn package

Ou usando o caminho completo do Maven:

& "C:\Program Files\Apache\Maven\apache-maven-3.9.16\bin\mvn.cmd" package

O WAR sera gerado em:

target\cadastro-pessoas-jsf.war


12. Como rodar no Tomcat
------------------------

Opcao 1: Deploy manual

1. Gerar o WAR com Maven.
2. Copiar target\cadastro-pessoas-jsf.war para a pasta webapps do Tomcat.
3. Subir o Tomcat pelo startup.bat.
4. Acessar no navegador:

http://localhost:8080/cadastro-pessoas-jsf/

Opcao 2: IntelliJ com Smart Tomcat

1. Instalar o plugin Smart Tomcat.
2. Criar uma configuracao Smart Tomcat.
3. Apontar Tomcat Server para a pasta raiz do Tomcat, nao para bin.
4. Configurar Context Path:

/cadastro-pessoas-jsf

5. Configurar Document Base para:

target\cadastro-pessoas-jsf

6. Rodar mvn package antes de iniciar ou configurar o IntelliJ para executar
   Maven package antes do Run.


13. Como acessar a aplicacao
----------------------------

URL local:

http://localhost:8080/cadastro-pessoas-jsf/

Tela atual:

Login com matricula e senha.

Comportamento atual:

- Se a matricula nao existir, o sistema exibe erro.
- Se a matricula existir e senha_hash estiver vazio, o sistema cadastra a senha
  digitada como hash BCrypt.
- Se senha_hash ja existir, o sistema valida a senha digitada contra o hash.


14. Dependencias principais
---------------------------

As dependencias ficam no pom.xml.

Principais:

- jakarta.servlet-api
- jakarta.faces
- primefaces com classifier jakarta
- weld-servlet-shaded
- hibernate-validator
- postgresql JDBC driver
- slf4j-api
- logback-classic
- bcrypt


15. Estrutura do projeto
------------------------

src
  main
    java
      br
        com
          trikascrm
            database
              ConexaoPostgres.java
              Repository.java
            model
              UsuarioVO.java
            security
              SenhaUtil.java
            service
              Service.java
            view
              LoginMbean.java
    webapp
      index.xhtml
      WEB-INF
        beans.xml
        faces-config.xml
        web.xml

target
  cadastro-pessoas-jsf.war


16. Comandos importantes
------------------------

Validar Java:

java -version

Validar Maven:

mvn -version

Gerar WAR:

mvn package

Limpar e gerar WAR:

mvn clean package

Conectar no PostgreSQL:

& "C:\Program Files\PostgreSQL\13\bin\psql.exe" -U user_trikas -d trikas_db -h localhost -p 5432

Ver estrutura da tabela:

\d public.usuario

Ver indices:

SELECT indexname, indexdef
FROM pg_indexes
WHERE schemaname = 'public'
  AND tablename = 'usuario';


17. Observacoes importantes de compatibilidade
----------------------------------------------

- Tomcat 10.1 usa Jakarta Servlet 6.
- Por isso o projeto deve usar pacotes jakarta.*, nao javax.*.
- PrimeFaces 15 deve ser usado com classifier jakarta.
- JSF no Tomcat precisa ser empacotado junto com a aplicacao, pois Tomcat nao e
  um servidor Jakarta EE completo.
- CDI no Tomcat tambem precisa de dependencia propria, neste projeto usando
  Weld Servlet.
- O driver JDBC do PostgreSQL precisa estar dentro do WAR em WEB-INF/lib.
- Senhas de usuario devem ser armazenadas como hash, nao como texto puro.


18. Git e GitHub
----------------

Nome sugerido do repositorio:

trikas-corp

Arquivos que nao devem subir:

- target/
- .idea/
- .smarttomcat/
- arquivos .log
- arquivos .env
- arquivos .war gerados

Fluxo recomendado:

git init
git status
git add .
git commit -m "Primeiro commit do projeto trikas-corp"
git branch -M main
git remote add origin URL_DO_REPOSITORIO
git push -u origin main

Nao fazer push antes de conferir o git status e confirmar que nenhum arquivo
sensivel sera enviado.
