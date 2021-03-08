package se.ifmo.blos.lab1.services;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ifmo.blos.lab1.domains.Car;
import se.ifmo.blos.lab1.dtos.CarDto;
import se.ifmo.blos.lab1.mappers.CarMapper;
import se.ifmo.blos.lab1.repositories.CarRepository;

@Service("carService")
public class CarService extends CommonService<Car, UUID, CarDto> {

  private static final String RESOURCE_NAME = "Car";

  private final CarRepository carRepository;
  private final CarMapper carMapper;

  @Autowired
  public CarService(final CarRepository carRepository, final CarMapper carMapper) {
    super(carRepository, carMapper);
    this.carRepository = carRepository;
    this.carMapper = carMapper;
  }

  public Page<CarDto> getAllByOwner(
      final Long ownerId, final Specification<Car> specification, final Pageable pageable) {
    return carRepository
        .findAllByOwnerId(ownerId, specification, pageable)
        .map(carMapper::mapToDto);
  }

  @Override
  protected String getResourceName() {
    return RESOURCE_NAME;
  }

  @Transactional(readOnly = true)
  @Override
  public boolean isAlreadyExists(final CarDto dto) {
    if (dto.getVin() == null) {
      return false;
    }
    return carRepository.existsByVin(dto.getVin());
  }
}
