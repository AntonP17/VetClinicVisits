package by.antohakon.vetclinicvisits.repository;

import by.antohakon.vetclinicvisits.entity.ClientVisit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientVisitRepository extends JpaRepository<ClientVisit, Long> {

    ClientVisit findByVisitId(UUID visitId);
    Page<ClientVisit> findAllByAnimalId(UUID animalId, Pageable pageable);
    Page<ClientVisit> findAllByOwnerId(UUID clientId, Pageable pageable);
    Page<ClientVisit> findAllByDoctorId(UUID clientId, Pageable pageable);

}
