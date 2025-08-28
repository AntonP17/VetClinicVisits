--liquibase formatted sql
--changeset antoxakon:4  -- Формат: author:id
--comment: create index on client_visit , full_visit

create index if not exists client_visit_uuid_index
    on full_info_visit(visit_id);

create index if not exists owner_visit_uuid_index
    on full_info_visit(owner);

create index if not exists doctor_visit_uuid_index
    on full_info_visit(doctor);

create index if not exists animal_visit_uuid_index
    on full_info_visit(animal);

create index if not exists ownerId_doctor_uuid_index
    on full_info_visit(doctor, owner);

create index if not exists animal_owner_uuid_index
    on full_info_visit(owner, animal);