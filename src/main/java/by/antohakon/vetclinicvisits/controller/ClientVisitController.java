package by.antohakon.vetclinicvisits.controller;

import by.antohakon.vetclinicvisits.dto.*;
import by.antohakon.vetclinicvisits.service.ClientVisitServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/visits")
public class ClientVisitController {

    private final ClientVisitServiceImpl clientVisitService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Page<ClientVisitDto> getAllClientVisits(Pageable pageable) {
        return clientVisitService.getAllVisits(pageable);
    }

    @GetMapping("/{visitId}")
    @ResponseStatus(value = HttpStatus.OK)
    public VisitInfoDto getClientVisitById(@PathVariable UUID visitId) {
        return clientVisitService.getVisitById(visitId);
    }

    @GetMapping("/{visitId}/info")
    @ResponseStatus(value = HttpStatus.OK)
    public VisitFullInfoDto getFullVisitInfoById(@PathVariable UUID visitId) {
       return clientVisitService.getFullVisitById(visitId);
    }

    @GetMapping("/byAnimalId/{animalId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Page<ClientVisitDto> getAllVisitsByAnimalId(@PathVariable UUID animalId, Pageable pageable) {
        return clientVisitService.getAllVisitsByAnimalId(animalId, pageable);
    }

    @GetMapping("/byOwnerId/{ownerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Page<ClientVisitDto> getAllVisitsByOwnerId(@PathVariable UUID ownerId, Pageable pageable) {
        return clientVisitService.getAllVisitsByOwnerId(ownerId, pageable);
    }

    @GetMapping("/byDoctorId/{doctorId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Page<ClientVisitDto> getAllVisitsByDoctorId(@PathVariable UUID doctorId, Pageable pageable) {
        return clientVisitService.getAllVisitsByDoctorId(doctorId, pageable);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public VisitInfoDto createClientVisit(@RequestBody CreateVisitDto createVisitDto) {
        return clientVisitService.createVisit(createVisitDto);
    }

    @PutMapping("/{visitId}")
    @ResponseStatus(value = HttpStatus.OK)
    public VisitInfoDto updateClientVisit(@PathVariable UUID visitId, @RequestBody UpdateVisitDto updateVisitDto) {
        return clientVisitService.updateVisitById(updateVisitDto, visitId);
    }

    @DeleteMapping("/{visitId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteClientVisit(@PathVariable UUID visitId) {
        clientVisitService.deleteVisit(visitId);
    }

}
