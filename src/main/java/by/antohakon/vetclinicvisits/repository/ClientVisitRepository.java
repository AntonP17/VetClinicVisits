package by.antohakon.vetclinicvisits.repository;

import by.antohakon.vetclinicvisits.entity.ClientVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientVisitRepository extends JpaRepository<ClientVisit, Long> {
}
