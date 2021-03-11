package se.ifmo.blos.lab1.services;

import static java.lang.String.format;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ifmo.blos.lab1.domains.Car;
import se.ifmo.blos.lab1.domains.User;
import se.ifmo.blos.lab1.dtos.CarDto;
import se.ifmo.blos.lab1.exceptions.ResourceAlreadyExistsException;
import se.ifmo.blos.lab1.mappers.CarMapper;
import se.ifmo.blos.lab1.repositories.CarRepository;
import se.ifmo.blos.lab1.specifications.CarSpecifications;

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

  /*public Page<Car> getAllPublicEntities(
      final Specification<Car> specification, final Pageable pageable) {
    return carRepository.findAllPublic(specification, pageable);
  }

  public Page<Car> getAllPublicOrPersonalEntities(
      final Long ownerId, final Specification<Car> specification, final Pageable pageable) {
    return carRepository.findAllPublicOrPersonal(ownerId, specification, pageable);
  }

  public Page<CarDto> getAllPublicDtos(
      final Specification<Car> specification, final Pageable pageable) {
    return getAllPublicEntities(specification, pageable).map(carMapper::mapToDto);
  }

  public Page<CarDto> getAllPublicOrPersonalDtos(
      final Long ownerId, final Specification<Car> specification, final Pageable pageable) {
    return carRepository
        .findAllPublicOrPersonal(ownerId, specification, pageable)
        .map(carMapper::mapToDto);
  }*/

  @Transactional
  public CarDto createFromDtoWithOwner(final User owner, final CarDto carDto)
      throws ResourceAlreadyExistsException {
    if (isAlreadyExists(carDto)) {
      throw new ResourceAlreadyExistsException(
          format("%s with same identity already exists.", getResourceName()));
    }
    final Car car = carMapper.mapToPersistable(carDto);
    car.setOwner(owner);
    return carMapper.mapToDto(carRepository.save(car));
  }

  @Transactional(readOnly = true)
  public Page<CarDto> getAllDtosByOwner(
      final Long ownerId, final Specification<Car> specification, final Pageable pageable) {
    final Specification<Car> withOwnerSpecification =
        specification.and(CarSpecifications.withOwner(ownerId));
    return carRepository.findAll(withOwnerSpecification, pageable).map(carMapper::mapToDto);
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
