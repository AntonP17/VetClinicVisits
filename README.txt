Добро пожаловать в API VetClinic

Swagger DOC - http://localhost:8080/swagger-ui/index.html#/

данный репозиторий предназначен для управления владельцами животных, животными, визитами к врачам, врачами,
аналитикой

Основные сервисы :

1) VetClinicVisits

запись владельца с животным к определенному врачу, управление визитами, просмотр назначений,
редактирование назначений, удаление визита

GET :

http://localhost:8080/api/visits - просмотр истории всех визитов ко всем врачам UUID (пагинация и сортировка)
http://localhost:8080/api/visits/info - подробный просмотр истории визитов (пагинация и сортировка)
http://localhost:8080/api/visits/{visitId} - просмотр конкретного визита(данные UUID)
http://localhost:8080/api/visits/{visitId}/info -просмотр подробного визита текстом
http://localhost:8080/api/visits/byAnimal/{animalId} - поиск всех визитов по конкретному UUID животного
http://localhost:8080/api/visits/byAnimal?animalName=Animal - поиск всех визитов по имени
http://localhost:8080/api/visits/byOwner/{ownerId} - поиск всех визитов по конкретному UUID владельца
http://localhost:8080/api/visits/byOwner?ownerFullName=Anton Pokrovsky - поиск всех визитов по полному имени владельца
http://localhost:8080/api/visits//byDoctor/{doctorId} - поиск всех визитов по конкретному UUID врача
http://localhost:8080/api/visits//byDoctor?doctorFullName=Anton Pokrovsky - поиск всех визитов по полному имени врача

POST :

http://localhost:8080/api/visits - создание визита

{
    "ownerId" : "4909a375-be78-4fa9-9146-9e45d393cf63",
    "doctorId" : "99f616fb-32df-4734-8262-072447c03a88",
    "animalId" : "e65f58fc-1ac6-4a79-8204-da3f05859b5f",
    "reasonRequest" : "хромает на одну лапу "
}

PUT :

http://localhost:8080/api/visits/{visitId} - обновление конкретного визита

{
    "reasonRequest" : "хромает на одну лапу"
}

DELETE :

http://localhost:8080/api/visits/{visitId} - удаление конкретного визита