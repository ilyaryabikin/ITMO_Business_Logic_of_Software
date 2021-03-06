package se.ifmo.blos.lab1.utils;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import lombok.Value;

@Value(staticConstructor = "of")
public class RestErrorResponse {

  @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSXXX", timezone = "UTC")
  Instant timestamp;

  int status;

  String error;

  String message;

  String requestUrl;
}
