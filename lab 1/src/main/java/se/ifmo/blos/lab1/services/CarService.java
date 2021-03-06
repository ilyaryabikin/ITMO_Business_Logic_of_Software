package se.ifmo.blos.lab1.services;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
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
