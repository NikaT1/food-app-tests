# language: ru

@allure.label.layer:db
@allure.label.owner:troynikova_veronika
@all
@db-tests
Функция: Добавление нового продукта в базу данных

  Предыстория:
    Допустим пользователь устанавливает соединение с базой данных

  @allure.label.jira:DB-1
    @critical
    @correct
  Структура сценария: Проверка добавления корректных данных в таблицу FOOD БД

  Проверка корректности добавления нового товара в таблицу FOOD
  базы данных тестового стенда посредством выполнения SQL запроса
    * пользователь находит самый большой id в таблице FOOD
    * пользователь выполняет запрос на добавление нового продукта в таблицу: id+1, "<название>", "<тип>" и <является экзотическим>
    * пользователь выполняет запрос на получение строки из таблицы с id+1 и проверяет содержимое: "<название>", "<тип>" и <является экзотическим>
    * пользователь удаляет строку из таблицы с id+1
    * пользователь закрывает соединение с базой данных

    Примеры:
      | название | тип   | является экзотическим |
      | Мандарин | Фрукт | true                  |
      | Огурец   | Овощ  | false                 |


  @allure.label.jira:DB-2
    @critical
    @fail
  Структура сценария: Проверка добавление некорректных данных в таблицу FOOD БД

  Проверка корректного сообщения об ошибке при попытке добавления
  нового товара в таблицу FOOD с повторяющимся ID
    * пользователь находит самый большой id в таблице FOOD
    * пользователь выполняет запрос на добавление нового продукта в таблицу: id+0, "<название>", "<тип>" и <является экзотическим>
    * пользователь проверяет, что появилось сообщение об ошибке "Unique index or primary key violation"
    * пользователь закрывает соединение с базой данных

    Примеры:
      | название | тип   | является экзотическим |
      | Мандарин | Фрукт | true                  |
      | Огурец   | Овощ  | false                 |