# Distributed Minesweeper

## Main Concept


* In this project we used a standalone implementation of the Minesweeper Game developed by Sorin.
* This game aims to be a distributed version of the minesweeper game, for communications between players and server we used Remote Method Invocation(RMI) protocol.
* We used JavaFx and Java Swing for GUIs.

## Getting Started

### Prerequisites

* Python for server(SimpleHTTPServer).
* Postgres to store players' information. 

**Create database**
```
createdb -DMinesweeper -Upostgres
```

**Create table**
```
CREATE TABLE Players(id Serial PRIMARY KEY,username varchar(20),gameswon int,gameslost int,password text);
```

### Run the Server

1. Run the **runpython** script 
2. Run the **runregistry** script 
3. Run the **runserver** script 

### Run the Client

1. Run the **runclient** script 

## Demonstration

![client1](screenshots/client1.gif)

![client2](screenshots/client2.gif)

**Development Date:** June 2018

## Authors

* **Filipe Mesquita** - [filipemes](https://github.com/filipemes)

* **Pedro Costa** - [pedrooct](https://github.com/pedrooct)

* **Paulo Bento** - [paulobento10](https://github.com/paulobento10)




