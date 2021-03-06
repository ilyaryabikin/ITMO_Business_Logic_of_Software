package se.ifmo.blos.lab1.dtos;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.ifmo.blos.lab1.domains.constraints.CurrentYearOrBefore;
import se.ifmo.blos.lab1.domains.enums.DriveUnitType;
import se.ifmo.blos.lab1.domains.enums.FuelType;
import se.ifmo.blos.lab1.domains.enums.GearboxType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDto implements Dto {

  private static final long serialVersionUID = -2810471298730123798L;

  @JsonProperty(access = READ_ONLY)
  @JsonFormat(shape = STRING)
  private UUID id;

  @Size(min = 17, max = 17)
  private String vin;

  private Long brandId;

  @Size(max = 128)
  private String modelName;

  @CurrentYearOrBefore
  private Integer year;

  @Size(max = 128)
  private String city;

  @DecimalMin("1")
  private Double price;

  @DecimalMin("0")
  private Double mileage;

  @Min(1)
  private Integer generation;

  private DriveUnitType driveUnitType;

  private FuelType fuelType;

  private GearboxType gearboxType;

  @Size(max = 128)
  private String color;

  @Size(min = 16, max = 8192)
  private String additionalInfo;
}
