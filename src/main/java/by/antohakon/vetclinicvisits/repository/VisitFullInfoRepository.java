package by.antohakon.vetclinicvisits.repository;

import by.antohakon.vetclinicvisits.dto.ClientVisitFullInfoDto;
import by.antohakon.vetclinicvisits.entity.ClientVisit;
import by.antohakon.vetclinicvisits.entity.VisitFullInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VisitFullInfoRepository  extends JpaRepository<VisitFullInfo,Long> {

    VisitFullInfo findByVisitId(UUID visitId);
    Page<ClientVisitFullInfoDto> findAllByAnimal(String animal, Pageable pageable);
    Page<ClientVisitFullInfoDto> findAllByOwner(String owner, Pageable pageable);
    Page<ClientVisitFullInfoDto> findAllByDoctor(String doctor, Pageable pageable);

}
