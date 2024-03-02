## MiniBank Application
### Использование
Для запуска приложения вам понадобится Docker и Docker Compose.

1. Установите Docker и Docker Compose, если они еще не установлены на вашем компьютере.
2. Склонируйте репозиторий на свой компьютер:

    ```shell
    git clone https://github.com/DmBalaev/MiniBank-Application.git
    ```  
   
3. Перейдите в директорию проекта:
   ```shell
    cd MiniBank-Application
   ```   

4. Соберите проект
   ```shell
   ./mvnw clean install
   ```

5. Соберите и запустите Docker контейнеры:

   ```shell
   docker-compose up --build
   ```
   
6. После запуска приложение будет доступно по адресу http://localhost:8080.

### Документация API
Документация API доступна по адресу: http://localhost:8080/swagger-ui.html

# Документация по API
## API аутентификации
### Вход в систему
* URL: `/api/auth/signin`
* Метод: `POST`
* Тело запроса:
    ```json
      {
      "login": "login",
      "password": "password"
      }
    ```
* Команда curl:
  ```bash
  curl -X POST http://localhost:8080/api/auth/signin -H "Content-Type: application/json" -d '{"login":"login","password":"password"}'
  ```
* Ответы:
  * `200 OK`: Аутентификация успешна. Возвращает токен JWT.
  * `401 Unauthorized`: Неправильное имя пользователя или пароль.
  
### Регистрация
* URL: `/api/auth/signup`
* Метод: `POST`
* Тело запроса:
   ```json
  {
   "login": "desired_username",
   "password": "desired_password",
   "amount": 100.00,
   "phone": "user_phone_number",
   "email": "user_email@example.com",
   "firstName": "user_first_name",
   "lastName": "user_last_name",
   "surname": "user_surname",
   "birthday": "yyyy-MM-dd"
  }
   ```
* Команда curl:
  ```bash
  curl -X POST http://localhost:8080/api/auth/signup -H "Content-Type: application/json" -d '{"login":"login","password":"password","amount":100.00,"phone":"89999999999r","email":"user@mail.com","firstName":"firstName","lastName":"lastName","surname":"surname","birthday":"1990-02-13"}'
  ```
* Ответы:
  * `200 OK`: Регистрация успешна. Возвращает сообщение об успешной регистрации.
  * `400 Bad Request`: Неверные параметры запроса.

##  API транзакций
### Создание транзакции
* URL: `/api/v1/transactional`
* Метод: `POST`
* Тело запроса:
  ```json
    {
    "recipientPhoneNumber": "phoneNumber",
    "amount": 100.00
    }
    ```
* Команда curl:  
  ```bash
  curl -X POST http://localhost:8080/api/v1/transactional \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -d '{"recipientPhoneNumber":"phoneNumber","amount":100.00}'
  ```
* Ответы:
  * `200 OK`: Транзакция успешна. Возвращает детали транзакции.
  * `400 Bad Request`: Неверные детали транзакции.

### Получение транзакций
  
* URL: `/api/transactional`
* Метод: `GET`
* Команда curl:
  ```bash
  curl -X GET http://localhost:8080/api/transactional -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
  ```
* Ответы:
  * `200 OK`: Возвращает список транзакций для аутентифицированного пользователя.
  * `401 Unauthorized`: Пользователь не аутентифицирован.

## API клиентов
### Получение клиентов по запросу

* URL: `/api/v1/clients/search`
* Метод: `GET`
* Описание: Получение пагинированного списка клиентов на основе указанных критериев поиска.
* Параметры запроса:
  * `pageNo` (необязательный, по умолчанию `1`): Номер страницы.
  * `pageSize` (необязательный, по умолчанию `10`): Размер страницы.
  * `sortBy` (необязательный, по умолчанию `id`): Поле для сортировки.
  * `sortDir` (необязательный, по умолчанию `asc`): Направление сортировки (`asc` - по возрастанию, `desc` - по убыванию).
  * `birthDate` (необязательный): Дата рождения.
  * `fullName` (необязательный): Полное имя.
* Команда curl
  ```bash
  curl -X GET http://localhost:8080/api/v1/clients/search \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -H "Content-Type: application/json"
  ```
* Ответы:
  * `200 OK`: Список клиентов успешно возвращен.

### Поиск клиента по номеру телефона

* URL: `/api/v1/clients/search/by-phone`
* Метод: `GET`
* Описание: Получение клиента по его номеру телефона.
* Параметры запроса:
  * `phone`: Номер телефона клиента.
* Команда curl
  ```bash
    curl -X GET http://localhost:8080/api/v1/clients/search/by-phone?phone=CLIENT_PHONE_NUMBER \
    -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
    -H "Content-Type: application/json"
  ```
* Ответы:
  * `200 OK`: Клиент успешно найден.
  * `404 Not Found`: Клиент не найден.
  * `400 Bad Request`: Неверный номер телефона.

### Поиск клиента по адресу электронной почты

* URL: `/api/v1/clients/search/by-email`
* Метод: `GET`
* Описание: Получение клиента по его адресу электронной почты.
* Параметры запроса:
  * `email`: Адрес электронной почты клиента.
* Команда curl
  ```bash
    curl -X GET http://localhost:8080/api/v1/clients/search/by-email?email=CLIENT_EMAIL \
    -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
    -H "Content-Type: application/json"
  ```
* Ответы:
  * `200 OK`: Клиент успешно найден.
    * `404 Not Found`: Клиент не найден.
    * `400 Bad Request`: Неверный адрес электронной почты.