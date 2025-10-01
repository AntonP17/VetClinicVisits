package by.antohakon.vetclinicvisits.repository;

import by.antohakon.vetclinicvisits.entity.ClientVisit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientVisitRepository extends JpaRepository<ClientVisit, Long> {

    ClientVisit findByVisitId(UUID visitId);
    Page<ClientVisit> findAllByAnimalId(UUID animalId, Pageable pageable);
    Page<ClientVisit> findAllByOwnerId(UUID clientId, Pageable pageable);
    Page<ClientVisit> findAllByDoctorId(UUID clientId, Pageable pageable);

    @Query("""
    SELECT c FROM ClientVisit c 
    WHERE (:visitId IS NULL OR c.visitId = :visitId)
    AND (:ownerId IS NULL OR c.ownerId = :ownerId)
    AND (:doctorId IS NULL OR c.doctorId = :doctorId)
    AND (:animalId IS NULL OR c.animalId = :animalId)
""")
    Page<ClientVisit> findAllByFilter(@Param("visitId") UUID visitId,
                                      @Param("ownerId") UUID ownerId,
                                      @Param("doctorId") UUID doctorId,
                                      @Param("animalId") UUID animalId,
                                      Pageable pageable);

}
