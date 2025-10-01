# 🏥 VetClinic Management System API

## 📋 Обзор системы
Комплексная система управления ветеринарной клиникой, состоящая из 4 микросервисов для полного цикла работы с пациентами, 
врачами и визитами.

### 🔗 Быстрые ссылки
- **Docker Deployment**: `docker compose -f docker-compose-deploy.yaml up -d`

## 📚 Документация API
### 🎪 Микросервисы

| Сервис | Порт | Репозиторий | Swagger UI | Описание |
|--------|------|-------------|------------|----------|
| **Visits** | `8080` | [📦 VetClinicVisits](https://github.com/AntonP17/VetClinicVisits) | [🔗 OpenAPI](http://localhost:8080/swagger-ui/index.html) | Управление визитами |
| **Employees** | `8082` | [📦 vetClinicEmployes](https://github.com/AntonP17/vetClinicEmployes) | [🔗 OpenAPI](http://localhost:8082/swagger-ui/index.html) | Персонал клиники |
| **Clients** | `8081` | [📦 vetClinicCLients](https://github.com/AntonP17/vetClinicCLients) | [🔗 OpenAPI](http://localhost:8081/swagger-ui/index.html) | Клиенты и животные |
| **Analytics** | `8085` | [📦 vetClinicAnalitic](https://github.com/AntonP17/vetClinicAnalitic) | [🔗 OpenAPI](http://localhost:8085/swagger-ui/index.html) | Аналитика |
---

## 🚀 Сервисы системы

### 1. 📅 **VetClinicVisits** (`:8080`)
*Управление записями на визиты и расписанием*

#### 🔍 Получение данных
| Метод | Endpoint | Описание |
|-------|----------|----------|
| `GET` | `/api/visits` | Все визиты (UUID, пагинация, сортировка) |
| `GET` | `/api/visits/info` | Подробная история визитов |
| `GET` | `/api/visits/{visitId}` | Конкретный визит по ID |
| `GET` | `/api/visits/{visitId}/info` | Детальная информация о визите |
| `GET` | `/api/visits/byAnimal/{animalId}` | Визиты по ID животного |
| `GET` | `/api/visits/byAnimal?animalName=...` | Визиты по имени животного |
| `GET` | `/api/visits/byOwner/{ownerId}` | Визиты по ID владельца |
| `GET` | `/api/visits/byOwner?ownerFullName=...` | Визиты по ФИО владельца |
| `GET` | `/api/visits/byDoctor/{doctorId}` | Визиты по ID врача |
| `GET` | `/api/visits/byDoctor?doctorFullName=...` | Визиты по ФИО врача |

#### ➕ Создание визита
```http
POST /api/visits
Content-Type: application/json

{
    "ownerId": "4909a375-be78-4fa9-9146-9e45d393cf63",
    "doctorId": "99f616fb-32df-4734-8262-072447c03a88",
    "animalId": "e65f58fc-1ac6-4a79-8204-da3f05859b5f",
    "reasonRequest": "Хромает на одну лапу"
}
```

#### ✏️ Обновление визита
```http
PUT /api/visits/{visitId}
Content-Type: application/json

{
    "reasonRequest": "Обновленная причина визита"
}
```

#### 🗑️ Удаление визита
```http
DELETE /api/visits/{visitId}
```

---

### 2. 👨‍⚕️ **VetClinicEmployees** (`:8082`)
*Управление персоналом клиники*

#### 🔍 Получение сотрудников
| Метод | Endpoint | Описание |
|-------|----------|----------|
| `GET` | `/api/employees` | Все сотрудники (пагинация, сортировка) |
| `GET` | `/api/employees/{employeeId}` | Конкретный сотрудник по ID |

#### ➕ Создание сотрудника
```http
POST /api/employees
Content-Type: application/json

{
    "lastName": "Turman",
    "firstName": "Arnt", 
    "role": "ADMIN"
}
```

**Доступные роли:** `ADMIN`, `SURGEON`, `THERAPIST`, `ASSISTENT`

#### ✏️ Обновление сотрудника
```http
PUT /api/employees/{employeeId}
Content-Type: application/json

{
    "lastName": "Pokar",
    "firstName": "Anton",
    "role": "SURGEON"
}
```

#### 🗑️ Удаление сотрудника
```http
DELETE /api/employees/{employeeId}
```

---

### 3. 🐾 **VetClinicClients** (`:8081`)
*Управление клиентами и их питомцами*

#### 🔍 Получение данных
| Метод | Endpoint | Описание |
|-------|----------|----------|
| `GET` | `/api/animals` | Все животные |
| `GET` | `/api/animals/{animalId}` | Конкретное животное |
| `GET` | `/api/owners` | Все владельцы |
| `GET` | `/api/owners/{ownerId}` | Конкретный владелец |

#### ➕ Создание владельца
```http
POST /api/owners
Content-Type: application/json

{
    "firstName": "Kiska",
    "lastName": "Kaatopm"
}
```

#### ➕ Добавление животного
```http
POST /api/animals
Content-Type: application/json

{
    "animalOwnerUuid": "afd0f5a7-cea5-4380-a682-dcdd7c8a6c59",
    "animalType": "DOG",
    "animalName": "Eva"
}
```

**Доступные типы животных:** `DOG`, `CAT`, `BIRD`, `REPTILE`, `RODENT`, `AMPHIBIAN`

#### ✏️ Обновление данных
**Владелец:**
```http
PUT /api/owners/{ownerId}
Content-Type: application/json

{
    "firstName": "Anton",
    "lastName": "Pokr"
}
```

**Животное:**
```http
PUT /api/animals/{animalId}
Content-Type: application/json

{
    "animalType": "REPTILE",
    "animalName": "Name",
    "animalOwnerUuid": "bfa9db01-2139-45dd-8754-e9af8793faa1"
}
```

#### 🗑️ Удаление
```http
DELETE /api/animals/{animalId}
DELETE /api/owners/{ownerId}
```

---

### 4. 📊 **VetClinicAnalytics** (`:8085`)
*Аналитика и мониторинг записей*

#### 🔍 Получение аналитики
| Метод | Endpoint | Описание |
|-------|----------|----------|
| `GET` | `/api/status` | Все статусы записей |
| `GET` | `/api/status/{visitId}` | Статус конкретного визита |

---

## 🛠 Техническая информация

### 🐳 Запуск через Docker
```bash
docker compose -f docker-compose-deploy.yaml up -d
```

### 📊 Портирование сервисов
| Сервис | Порт | Назначение |
|--------|------|------------|
| Visits | 8080 | Управление визитами |
| Clients | 8081 | Клиенты и животные |
| Employees | 8082 | Персонал клиники |
| Analytics | 8085 | Аналитика и мониторинг |

### ⚙️ Особенности
- **Пагинация** и **сортировка** для списковых endpoints
- **UUID** идентификаторы для всех сущностей
- **Единый формат** запросов/ответов
- **Подробная документация** через Swagger UI

---

## 💡 Примеры использования

### 📝 Создание полной цепочки визита:
1. **Создать владельца** → `POST /api/owners`
2. **Добавить животное** → `POST /api/animals`
3. **Создать сотрудника** → `POST /api/employees`
4. **Записать на визит** → `POST /api/visits`

### 🔎 Поиск информации:
- Поиск всех визитов конкретного животного по имени
- Просмотр расписания врача
- История обращений владельца

Для полной документации с возможностью тестирования API используйте **Swagger UI**.