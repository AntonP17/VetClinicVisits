package by.antohakon.vetclinicvisits.controller;

import by.antohakon.vetclinicvisits.dto.*;
import by.antohakon.vetclinicvisits.service.ClientVisitServiceImpl;
import by.antohakon.vetclinicvisits.service.VisitFullInfoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/visits")
public class ClientVisitController {

    private final ClientVisitServiceImpl clientVisitService;
    private final VisitFullInfoServiceImpl visitFullInfoService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Page<ClientVisitDto> getAllClientVisits(@PageableDefault(size = 5) Pageable pageable) {
        return clientVisitService.getAllVisits(pageable);
    }

    @GetMapping("/info")
    @ResponseStatus(value = HttpStatus.OK)
    public Page<ClientVisitFullInfoDto> getAllClientVisitsFullInfo(Pageable pageable) {
        return visitFullInfoService.getAllVisitFullInfo(pageable);
    }

    @GetMapping("/{visitId}")
    @ResponseStatus(value = HttpStatus.OK)
    public VisitInfoDto getClientVisitById(@PathVariable UUID visitId) {
        return clientVisitService.getVisitById(visitId);
    }

    @GetMapping("/{visitId}/info")
    @ResponseStatus(value = HttpStatus.OK)
    public VisitFullInfoDto getFullVisitInfoById(@PathVariable UUID visitId) {
       return visitFullInfoService.getFullVisitById(visitId);
    }

    @GetMapping("/byAnimal/{animalId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Page<ClientVisitDto> getAllVisitsByAnimalId(@PathVariable UUID animalId, Pageable pageable) {
        return clientVisitService.getAllVisitsByAnimalId(animalId, pageable);
    }

    @GetMapping("/byAnimal")
    @ResponseStatus(value = HttpStatus.OK)
    public Page<ClientVisitFullInfoDto> getAllVisitsByAnimalName(@RequestParam String animalName, Pageable pageable) {
        return visitFullInfoService.getAllVisitsByAnimalName(animalName,pageable);
    }

    @GetMapping("/byOwner/{ownerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Page<ClientVisitDto> getAllVisitsByOwnerId(@PathVariable UUID ownerId, Pageable pageable) {
        return clientVisitService.getAllVisitsByOwnerId(ownerId, pageable);
    }

    @GetMapping("/byOwner")
    @ResponseStatus(value = HttpStatus.OK)
    public Page<ClientVisitFullInfoDto> getAllVisitsByOwnerName(@RequestParam String ownerFullName, Pageable pageable) {
        return visitFullInfoService.getAllVisitsByOwnerFullName(ownerFullName,pageable);
    }

    @GetMapping("/byDoctor/{doctorId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Page<ClientVisitDto> getAllVisitsByDoctorId(@PathVariable UUID doctorId, Pageable pageable) {
        return clientVisitService.getAllVisitsByDoctorId(doctorId, pageable);
    }

    @GetMapping("/byDoctor")
    @ResponseStatus(value = HttpStatus.OK)
    public Page<ClientVisitFullInfoDto> getAllVisitsByDoctorName(@RequestParam String doctorFullName, Pageable pageable) {
        return visitFullInfoService.getAllVisitsByDoctorFullName(doctorFullName,pageable);
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
