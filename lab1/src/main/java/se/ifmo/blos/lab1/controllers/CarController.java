package se.ifmo.blos.lab1.controllers;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import se.ifmo.blos.lab1.domains.User;
import se.ifmo.blos.lab1.dtos.CarDto;
import se.ifmo.blos.lab1.exceptions.IllegalPropertyUpdateException;
import se.ifmo.blos.lab1.exceptions.ResourceAlreadyExistsException;
import se.ifmo.blos.lab1.exceptions.ResourceNotFoundException;
import se.ifmo.blos.lab1.requests.CarRequestParameters;
import se.ifmo.blos.lab1.services.CarService;
import se.ifmo.blos.lab1.specifications.CarSpecifications;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CarController {

  private final CarService carService;

  @GetMapping(path = "/cars", produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("permitAll()")
  public Page<CarDto> getCars(
      final CarRequestParameters carRequestParameters, final Pageable pageable) {
    return carService.getAllDtos(CarSpecifications.fromRequest(carRequestParameters), pageable);
  }

  @GetMapping(path = "/cars/{id}", produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("permitAll()")
  public CarDto getCarById(final @PathVariable UUID id) throws ResourceNotFoundException {
    return carService.getDtoById(id);
  }

  @GetMapping(path = "/users/{userId}/cars", produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("isAuthenticated() && authentication.principal.id == #userId")
  public Page<CarDto> getCarByOwner(
      final @PathVariable Long userId,
      final CarRequestParameters carRequestParameters,
      final Pageable pageable) {
    return carService.getAllDtosByOwner(
        userId, CarSpecifications.fromRequest(carRequestParameters), pageable);
  }

  @PostMapping(
      path = "users/{userId}/cars",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("isAuthenticated() && authentication.principal.id == #userId")
  @ResponseStatus(CREATED)
  public CarDto createCar(final @PathVariable Long userId, final @RequestBody @Valid CarDto carDto)
      throws ResourceAlreadyExistsException {
    final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return carService.createFromDtoWithOwner(user, carDto);
  }

  @PatchMapping(
      path = "users/{userId}/cars/{carId}",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("isAuthenticated() && authentication.principal.id == #userId")
  public CarDto updateCar(
      final @PathVariable Long userId,
      final @PathVariable UUID carId,
      final @RequestBody @Valid CarDto carDto)
      throws ResourceNotFoundException, IllegalPropertyUpdateException {
    return carService.updateFromDto(carDto, carId);
  }

  @DeleteMapping(path = "users/{userId}/cars/{carId}", produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("isAuthenticated() && authentication.principal.id == #userId")
  @ResponseStatus(NO_CONTENT)
  public void removeCar(final @PathVariable Long userId, final @PathVariable UUID carId)
      throws ResourceNotFoundException {
    carService.removeById(carId);
  }
}
