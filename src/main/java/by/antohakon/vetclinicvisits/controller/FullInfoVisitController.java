package by.antohakon.vetclinicvisits.controller;

import by.antohakon.vetclinicvisits.dto.ClientVisitFullInfoDto;
import by.antohakon.vetclinicvisits.dto.VisitFullInfoDto;
import by.antohakon.vetclinicvisits.service.ClientVisitServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/visits/fullInfo")
public class FullInfoVisitController {

    private final ClientVisitServiceImpl clientVisitService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Page<ClientVisitFullInfoDto> getAllClientVisitsFullInfo(Pageable pageable) {
        return clientVisitService.getAllVisitFullInfo(pageable);
    }
}
