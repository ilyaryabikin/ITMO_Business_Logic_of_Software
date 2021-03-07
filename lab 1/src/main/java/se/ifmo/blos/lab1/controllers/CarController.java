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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import se.ifmo.blos.lab1.dtos.CarDto;
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
    return carService.getAllByOwner(userId, CarSpecifications.fromRequest(carRequestParameters), pageable);
  }

  @PostMapping(path = "/cars", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("permitAll()")
  @ResponseStatus(CREATED)
  public CarDto createCar(final @RequestBody @Valid CarDto carDto)
      throws ResourceAlreadyExistsException {
    return carService.createFromDto(carDto);
  }

  @DeleteMapping(path = "/cars/{id}", produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("isAuthenticated()")
  @ResponseStatus(NO_CONTENT)
  public void removeCar(final @PathVariable UUID id) throws ResourceNotFoundException {
    carService.removeById(id);
  }
}
