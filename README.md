# asrd
Автоматизированная система учета документации/приборов



Для запуска проекта в директорию /src/main/resources необходимо добавить файл flyway.conf
следующего содержимого:

flyway.url=jdbc:mysql://[host]:[port]/[DBname]?useSSL=false&characterEncoding=UTF-8
flyway.schemas=[DBname]
flyway.user=[user]
flyway.password=[password]

Скорректировать файл application.properties в следующих строках
spring.datasource.url = jdbc:mysql://[host]:[port]/[DBname]?useSSL=false&characterEncoding=UTF-8

spring.datasource.username=[user]
spring.datasource.password=[password]



[] Заменить значениями для сервера с БД
