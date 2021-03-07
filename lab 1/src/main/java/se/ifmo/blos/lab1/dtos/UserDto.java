package se.ifmo.blos.lab1.dtos;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Dto {

  private static final long serialVersionUID = 8870747020716633775L;

  @JsonProperty(access = READ_ONLY)
  private String email;

  @JsonProperty(access = READ_ONLY)
  private String phoneNumber;
}
