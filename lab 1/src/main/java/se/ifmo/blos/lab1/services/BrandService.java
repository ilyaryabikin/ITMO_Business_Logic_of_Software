package se.ifmo.blos.lab1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ifmo.blos.lab1.domains.Brand;
import se.ifmo.blos.lab1.dtos.BrandDto;
import se.ifmo.blos.lab1.mappers.BrandMapper;
import se.ifmo.blos.lab1.repositories.BrandRepository;

@Service("brandService")
public class BrandService extends CommonService<Brand, Long, BrandDto> {

  private static final String RESOURCE_NAME = "Brand";

  private final BrandRepository brandRepository;
  private final BrandMapper brandMapper;

  @Autowired
  public BrandService(final BrandRepository brandRepository, final BrandMapper brandMapper) {
    super(brandRepository, brandMapper);
    this.brandRepository = brandRepository;
    this.brandMapper = brandMapper;
  }

  @Transactional(readOnly = true)
  public Page<Brand> getAllEntitiesWithName(final String name, final Pageable pageable) {
    return brandRepository.findAllByName(name, pageable);
  }

  @Transactional(readOnly = true)
  public Page<BrandDto> getAllDtosWithName(final String name, final Pageable pageable) {
    return getAllEntitiesWithName(name, pageable).map(brandMapper::mapToDto);
  }

  @Transactional(readOnly = true)
  @Override
  public boolean isAlreadyExists(final BrandDto dto) {
    if (dto.getName() == null) {
      return false;
    }
    return brandRepository.existsByName(dto.getName());
  }

  @Override
  protected String getResourceName() {
    return RESOURCE_NAME;
  }
}
