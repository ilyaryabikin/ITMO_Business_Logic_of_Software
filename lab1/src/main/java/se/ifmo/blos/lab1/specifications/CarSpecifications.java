package se.ifmo.blos.lab1.specifications;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import se.ifmo.blos.lab1.domains.Brand_;
import se.ifmo.blos.lab1.domains.Car;
import se.ifmo.blos.lab1.domains.Car_;
import se.ifmo.blos.lab1.requests.CarRequestParameters;

@NoArgsConstructor(access = PRIVATE)
public class CarSpecifications {

  public static Specification<Car> withVin(final @Nullable String vin) {
    return ((root, query, criteriaBuilder) -> {
      if (vin == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(criteriaBuilder.lower(root.get(Car_.VIN)), vin.toLowerCase());
    });
  }

  public static Specification<Car> withBrandId(final @Nullable Long brandId) {
    return ((root, query, criteriaBuilder) -> {
      if (brandId == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get(Car_.VIN).get(Brand_.ID), brandId);
    });
  }

  public static Specification<Car> withModelName(final @Nullable String modelName) {
    return ((root, query, criteriaBuilder) -> {
      if (modelName == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(
          criteriaBuilder.lower(root.get(Car_.MODEL_NAME)), modelName.toLowerCase());
    });
  }

  public static Specification<Car> withYear(final @Nullable Integer year) {
    return ((root, query, criteriaBuilder) -> {
      if (year == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get(Car_.YEAR), year);
    });
  }

  public static Specification<Car> withCity(final @Nullable String city) {
    return ((root, query, criteriaBuilder) -> {
      if (city == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(criteriaBuilder.lower(root.get(Car_.CITY)), city.toLowerCase());
    });
  }

  public static Specification<Car> withPrice(final @Nullable Double price) {
    return ((root, query, criteriaBuilder) -> {
      if (price == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get(Car_.PRICE), price);
    });
  }

  public static Specification<Car> withMileage(final @Nullable Double mileage) {
    return ((root, query, criteriaBuilder) -> {
      if (mileage == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get(Car_.MILEAGE), mileage);
    });
  }

  public static Specification<Car> withGeneration(final @Nullable Integer generation) {
    return ((root, query, criteriaBuilder) -> {
      if (generation == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get(Car_.GENERATION), generation);
    });
  }

  public static Specification<Car> withColor(final @Nullable String color) {
    return ((root, query, criteriaBuilder) -> {
      if (color == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(
          criteriaBuilder.lower(root.get(Car_.COLOR)), color.toLowerCase());
    });
  }

  public static Specification<Car> fromRequest(
      final @Nullable CarRequestParameters carRequestParameters) {
    if (carRequestParameters == null) {
      return Specification.where(null);
    }
    return Specification.where(withVin(carRequestParameters.getVin()))
        .and(withBrandId(carRequestParameters.getBrandId()))
        .and(withModelName(carRequestParameters.getModelName()))
        .and(withYear(carRequestParameters.getYear()))
        .and(withCity(carRequestParameters.getCity()))
        .and(withPrice(carRequestParameters.getPrice()))
        .and(withMileage(carRequestParameters.getMileage()))
        .and(withGeneration(carRequestParameters.getGeneration()))
        .and(withColor(carRequestParameters.getColor()));
  }
}
