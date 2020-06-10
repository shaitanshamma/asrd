[![CircleCI](https://circleci.com/gh/sportarty/asrd.svg?style=svg)](https://circleci.com/gh/sportarty/asrd)


# asrd
Автоматизированная система учета документации/приборов.


Для запуска проекта необходимо добавить переменные окружения среды
Чтобы добавить данные переменные к проекту в IntelliJ IDEA необходимо выполнить следующие действия
Run->Edit Configurations...-> В левом столбце окна должно быть выбрано AsrdApplication->
-> Далее в правой части окна должна быть выбрана вкладка Configuration ->
-> В выпадающем окне Environment в строке Environment Variables нажать на правый значок далее добавить 
следующие поля:
1) dburl со значением в формате jdbc:mysql://[host]:[port]/[DBname]?useSSL=false&characterEncoding=UTF-8
2) dbuser
3) dbpass
4) dbname
5) mailuser
6) mailpass


Так как flyway не будет использоваться на продакшн серверах то для его корректной работы необходимо иметь
отдельный файл настроек flyway.conf с жестко заданными настройками конкретно для Вашей БД.
Для запуска проекта в директорию /src/main/resources необходимо добавить файл flyway.conf
следующего содержимого:

flyway.url=jdbc:mysql://[host]:[port]/[DBname]?useSSL=false&characterEncoding=UTF-8
flyway.schemas=[DBname]
flyway.user=[user]
flyway.password=[password]

[] Заменить значениями для сервера с БД

Логин и пароль стоящие по добавляемые в БД сразу при создании admin и 100 соответственно
