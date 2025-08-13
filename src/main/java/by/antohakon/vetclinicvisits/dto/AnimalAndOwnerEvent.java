package by.antohakon.vetclinicvisits.dto;

import lombok.Builder;

@Builder
public record AnimalAndOwnerEvent(String animalName, String fullName) {
}
