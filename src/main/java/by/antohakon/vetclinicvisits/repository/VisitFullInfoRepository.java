package by.antohakon.vetclinicvisits.repository;

import by.antohakon.vetclinicvisits.entity.VisitFullInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VisitFullInfoRepository  extends JpaRepository<VisitFullInfo,Long> {

    VisitFullInfo findByVisitId(UUID visitId);

}
