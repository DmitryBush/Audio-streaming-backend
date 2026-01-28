# Audio Streaming Backend

***

Микросервисное бэкенд приложение для поточного REST-стриминга треков.

Цель данного проекта предоставить пользователям возможность создания своего собственного сервера 
с безграничным наполнением медиатеки и возможность получения доступа из любой точки мира.

## Основные функции
- Поддержка разнообразных типов треков: Flac, MP3, M4A, WAV, WMA, Ogg, Vorbis
- Поддержка автоматического парсинга метаданных треков
- JWT аутентификация
- Поддержка плейлистов
- Поиск через Elasticsearch

## Как работает приложение
Приложение запускается с помощью `docker compose` и доступно через API Gateway на порту 8675.

Загрузка треков возможна по URI `/api/v1/uploads/uploadSong` с body запроса типа `form-data` с ключом `file`.
Приложение автоматически парсит данные трека (исполнитель, альбом, жанр) и создает свою базу метаданных.
Также приложение поддерживает сохранения обложек альбома, которые могут быть доступны по пути `/api/v1/albums/{id}/artwork`.

Данное приложение поддерживает аутентификацию и авторизацию, через JWT аутентификацию совместно с `csrf` защитой. 
Благодаря системе пользователей, поддерживается система плейлистов для разных пользователей системы.

В приложении есть система поиска метаданных(трек, альбом, жанр), плейлистов по названию основанная на Elasticsearch.
Данные в es поступают через CDC `Debezium` с `Apache Kafka`.

## Архитектура приложения
![Архитектура бэкенд приложения](/docs/Audio-streaming.png)
1. Streaming service - Основной сервис отвечающий за загрузку треков, 
парсинг метаданных и стриминг треков через range запросы
2. User service - Сервис работающий с данными пользователя для аутентификации и авторизации. 
Также сервис управляет плейлистами пользователей
3. Search service - Сервис для поиска треков, альбомов, артистов и жанров по различным критериям. 
В качестве хранилища используется Elasticsearch с CDC Debezium
4. Registry service - Сервис для регистрации всех запущенных микросервисах
5. API Gateway - Единая точка доступа для маршрутизации запросов
## Технологический стек
### Backend:

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Web MVC
- Spring Security
- Spring Cloud
- PostgreSQL
- Redis
- Minio (S3-совместимое объектное хранилище)
- Elasticsearch
- Debezium
- Apache Kafka

### Инфраструктура:
- Docker
- Docker compose

## Запуск приложения
Для запуска приложения необходимо установить docker

### 1. Сборка необходимых образов
Для запуска docker контейнеров, необходимо скомпилировать и собрать микросервисы.

Для этого запустите следующие команды из корня проекта:
```bash
    # Сборка Streaming service
    docker build -t sound-flow-streaming-service -f StreamingService/Dockerfile .
    # Сборка User service
    docker build -t sound-flow-user-service -f UserService/Dockerfile .
    # Сборка Search service
    docker build -t sound-flow-search-service -f SearchService/Dockerfile .
    # Сборка Registry service
    docker build -t sound-flow-registry-service -f EurekaRegistryService/Dockerfile EurekaRegistryService
    # Сборка Api gateway service
    docker build -t sound-flow-gateway-service -f ApiGateway/Dockerfile ApiGateway
```
### 2. Заполните .env файл
Данное приложение использует 'секреты', распространяемые через переменные окружения. 
Для их задания используется `.env` файл.

Пример `.env` файла:
```dotenv
    # Стандартные переменные, которые не требуется изменять
    POSTGRES_MULTIPLE_DATABASES=metadata_db,user_db,playlist_db
    POSTGRES_PLAYLIST_JDBC_URL=jdbc:postgresql://postgres:5432/playlist_db
    POSTGRES_USER_JDBC_URL=jdbc:postgresql://postgres:5432/user_db
    POSTGRES_STREAMING_JDBC_URL=jdbc:postgresql://postgres:5432/metadata_db
    POSTGRES_STREAMING_NAME=metadata_db
    POSTGRES_PLAYLIST_NAME=playlist_db
    REDIS_HOST=redis
    REDIS_PORT=6379
    OBJECT_STORAGE_URL=http://minio:9000
    ELASTICSEARCH_URL=elasticsearch:9200
    KAFKA_CLUSTER_ID=5OI4MgfwRM-t8DB4T8ZOZw
    KAFKA_HOSTNAME=kafka-broker-reindex
    KAFKA_REINDEX_BOOTSTRAP_SERVERS=kafka-broker-reindex:9096
    DEBEZIUM_URL=http://debezium-reindex:8083
    # Переменные с `чуствительной` информацией, замените на другие значения:
    POSTGRES_USERNAME=example
    POSTGRES_PASSWORD=example
    OBJECT_STORAGE_ACCESS_KEY=example
    OBJECT_STORAGE_SECRET_KEY=example
    # Секретный ключ подписи должен быть не менее 512 байт в base64
    SECRET_SIGNING_KEY=c3VwZXItbWVnYS11bHRyYS1wcm8tc2VjcmV0LXlvdS1ldmVuLWdldC1pdC1zaWduaW5nLWtleQ==
    ELASTICSEARCH_USERNAME=example
    ELASTICSEARCH_PASSWORD=example
```
### 3. Запуск сервера
Для запуска сервера необходимо ввести следующую команду из корня проекта
```bash 
    docker compose up -d
```
## Лицензия
Этот проект распространяется под лицензией GNU General Public License v3.0. 
Подробнее см. в файле [LICENSE](LICENSE).