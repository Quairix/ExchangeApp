#Test-project

Функции:  
+ Конвертация валюты
+ Показ статистики

# Запуск  

## Шаг 1 Запустить проект  
```gradle bootRun```

## Шаг 2 Отправлять необходимые запросы  

## API для работы с Postgresql 
1. POST запрос для добавления данных карты в формате JSON.  
http://localhost:8080/exchange

### JSON тело
```
{
  "count": 0,
  "currencyFrom": "string",
  "currencyTo": "string",
  "userId": 0
}
```

2. Статистика - возвращает общие данные в системе (количество операций, количество конвертаций определенной валюты в другую, пользователи с максимальной операцией более 10к и суммой операций более 100к)
http://localhost:8080/stats
http://localhost:8080/stats/Ratings
http://localhost:8080/stats/Max
http://localhost:8080/stats/Sum

## Доступ к Swagger-UI  (Веб документация API)
http://localhost:8080/swagger-ui.html  

## Тестирование с Postman
В папке postman-collections содержится необходимый набор запросов к серверу.

## Логгирование
Приложение ведет запись об операциях в файл logs/app.log