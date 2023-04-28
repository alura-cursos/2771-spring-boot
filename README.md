
# Como Instalar 
## How to Install

```
sudo apt update 
sudo apt upgrade -y
sudo reboot
```
Sempre em uma VM nova eu atualizo todos os pacotes e reinicio, algumas imagens estao muito defasadas
* *Always upgrade all packages on a new VM as the images are often outdated.* *
```
sudo apt install maven openjdk-17-jre docker.io docker-compose
```
Instala o Maven, o OpenJDK17, o docker e o Docker compose
* *Install Maven, OpenJDK17, Docker and Docker compose* *
```
sudo groupadd docker
sudo usermod -aG docker $USER
```
Cria o grupo docker e adiciona o usuario pra não precisar ficar usando sudo como docker
* *creates the docker group and add it to use docker as non previleged (sudo)* *
> https://docs.docker.com/engine/install/linux-postinstall/
	
```
nano docker-compose.yaml
```
Aqui criamos o arquivo do compose que vai abrigar os bancos de dados sem precisar instalar o Mysql na maquina
* *Create the docker-compose file to run Mysql in a conteiner for the application * *

O conteudo abaixo cria o banco com o usuario vollmed, senha e senha do root e deve estar dentro do docker-compose.yaml
* *The  content below should be inside the docker-compose.yaml file* *
```
version: '3'
services:
  db:
    image: mysql:latest
    restart: always
    environment:
      MYSQL_USER: vollmed
      MYSQL_PASSWORD: p^IMJ2D-iWQ^Jf=
      MYSQL_DATABASE: vollmed_api
      MYSQL_ROOT_PASSWORD: ej573.CP26f:cK@
    ports:
      - "3306:3306"
    volumes:
      - /home/ubuntu/mysql_vollmed:/var/lib/mysql
	  
```
certifique-se de mudar no seu arquivo /src/main/resources/application.properties para os dados que vc usar, no meu caso fica assim, com os dados do banco acima
* *the data regarding user and paswword needs to be the same in your /src/main/resources/application.properties file* *
> spring.datasource.username=vollmed
> spring.datasource.password=p^IMJ2D-iWQ^Jf=

```
docker-compose up -d	  

```
Buscar a imagem e configura o conteiner de acordo com os dados acima 
* *It will pull the mysql image and configure the conteiner as wee need.* *

```
docker ps
```
vai listar os conteineres que vc tem, pra pegar o id do MYSQL
* *List your conteiners to get the mysql conteiner ID* *

```
docker exec -it meu_container /bin/bash
```
Aqui vc deve mudar o meu_container para o id que vc ve no seu conteiner
* *It will execute the bash inside the conteiner and "meu_conteiner" should be edited with your conteiner id* *

```
mysql -u root -p
```
no bash da imagem vc entra no mysql
* *inside the conteiner will access mysql cli* *

```
GRANT ALL PRIVILEGES ON *.* TO 'vollmed'@'%';
```
garante todos privilegios em todos os banco pro usuario que criamos
* *grant all privileges for vollmed user in all databases [for testing purposes only]* *

```
CREATE DATABASE vollmed_api_test;
```
cria o banco de teste necessario pra rodar sem erros
* *creates the test database in case test will be needed in the future* *

```
exit
```
sai do cli do mysql
* *exit Mysql CLI* *

```
exit
```
sai do bash do conteiner
* *exit the conteiner bash* *

```
git clone https://github.com/santos-adilio/DevOpsChallenge2023.git
```
Clona o projeto do meu repositório
* *Clone my repository project into the local machine* *

```
mvn clean package -DskipTests
```
Executa o projeto e gera o arquivo api-0.0.1-SNAPSHOT.jar dentro da pasta target
* *Build the project and generate the file api-0.0.1-SNAPSHOT.jar inside the target folder* *

```
java -jar -DDATASOURCE_URL=jdbc:mysql://localhost/vollmed_api -DDATASOURCE_USERNAME=vollmed -DDATASOURCE_PASSWORD=p^IMJ2D-iWQ^Jf= api-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=prod
```
executa a aplicação informando qual arquivo de configurações ele deveria usar. com os dados da conexão
* *run the application with the config and connection data* *



## 💻 Sobre o projeto

Voll.med é uma clínica médica fictícia que precisa de um aplicativo para gestão de consultas. O aplicativo deve possuir funcionalidades que permitam o cadastro de médicos e de pacientes, e também o agendamento e cancelamento de consultas.

Enquanto um time de desenvolvimento será responsável pelo aplicativo mobile, o nosso será responsável pelo desenvolvimento da API Rest desse projeto.

---

## ⚙️ Funcionalidades

- [x] CRUD de médicos;
- [x] CRUD de pacientes;
- [x] Agendamento de consultas;
- [x] Cancelamento de consultas.

---

## 🎨 Layout

O layout da aplicação mobile está disponível neste link: <a href="https://www.figma.com/file/N4CgpJqsg7gjbKuDmra3EV/Voll.med">Figma</a>

---

## 📄 Documentação

A documentação das funcionalidades da aplicação pode ser acessada neste link: <a href="https://trello.com/b/O0lGCsKb/api-voll-med">Trello</a>

---

## 🛠 Tecnologias

As seguintes tecnologias foram utilizadas no desenvolvimento da API Rest do projeto:

- **[Java 17](https://www.oracle.com/java)**
- **[Spring Boot 3](https://spring.io/projects/spring-boot)**
- **[Maven](https://maven.apache.org)**
- **[MySQL](https://www.mysql.com)**
- **[Hibernate](https://hibernate.org)**
- **[Flyway](https://flywaydb.org)**
- **[Lombok](https://projectlombok.org)**

---

## 📝 Licença

Projeto desenvolvido por [Alura](https://www.alura.com.br) e utilizado nos cursos de Spring Boot.

Instrutor: [Rodrigo Ferreira](https://cursos.alura.com.br/user/rodrigo-ferreira) 

---
