package se.ifmo.blos.lab1.services;

import static java.lang.String.format;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import se.ifmo.blos.lab1.dtos.Dto;
import se.ifmo.blos.lab1.exceptions.IllegalPropertyUpdateException;
import se.ifmo.blos.lab1.exceptions.ResourceAlreadyExistsException;
import se.ifmo.blos.lab1.exceptions.ResourceNotFoundException;
import se.ifmo.blos.lab1.mappers.Mapper;
import se.ifmo.blos.lab1.repositories.PersistableRepository;

@Transactional(
    rollbackFor = {ResourceNotFoundException.class, ResourceAlreadyExistsException.class})
public abstract class CommonService<T extends Persistable<ID>, ID, D extends Dto> {

  private final PersistableRepository<T, ID> repository;
  private final Mapper<T, D> mapper;

  protected CommonService(final PersistableRepository<T, ID> repository, Mapper<T, D> mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Transactional
  public D createFromDto(final D dto) throws ResourceAlreadyExistsException {
    if (isAlreadyExists(dto)) {
      throw new ResourceAlreadyExistsException(
          format("%s with same identity already exists.", getResourceName()));
    }
    final T toPersist = mapper.mapToPersistable(dto);
    final T persisted = repository.save(toPersist);
    return mapper.mapToDto(persisted);
  }

  @Transactional(readOnly = true)
  public Page<T> getAllEntities(final Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Transactional(readOnly = true)
  public Page<T> getAllEntities(final Specification<T> specification, final Pageable pageable) {
    return repository.findAll(specification, pageable);
  }

  @Transactional(readOnly = true)
  public Page<D> getAllDtos(final Pageable pageable) {
    return getAllEntities(pageable).map(mapper::mapToDto);
  }

  @Transactional(readOnly = true)
  public Page<D> getAllDtos(final Specification<T> specification, final Pageable pageable) {
    return getAllEntities(specification, pageable).map(mapper::mapToDto);
  }

  @Transactional(readOnly = true)
  public T getEntityById(final ID id) throws ResourceNotFoundException {
    return repository
        .findById(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    format("%s with id %s was not found.", getResourceName(), id)));
  }

  @Transactional(readOnly = true)
  public D getDtoById(final ID id) throws ResourceNotFoundException {
    return mapper.mapToDto(getEntityById(id));
  }

  @Transactional
  public D updateFromDto(final D dto, final ID id)
      throws ResourceNotFoundException, IllegalPropertyUpdateException {
    final T persistable = getEntityById(id);
    mapper.updateFromDto(dto, persistable);
    return mapper.mapToDto(persistable);
  }

  @Transactional
  public void removeById(final ID id) throws ResourceNotFoundException {
    final T persistable = getEntityById(id);
    repository.delete(persistable);
  }

  @Transactional(readOnly = true)
  public abstract boolean isAlreadyExists(final D dto);

  protected abstract String getResourceName();
}
