package se.ifmo.blos.lab1.controllers;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.Instant;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import se.ifmo.blos.lab1.utils.RestErrorResponse;
import se.ifmo.blos.lab1.exceptions.ResourceAlreadyExistsException;
import se.ifmo.blos.lab1.exceptions.ResourceNotFoundException;

@org.springframework.web.bind.annotation.RestControllerAdvice
@Slf4j
public class RestControllerAdvice {

  @ExceptionHandler(ResourceAlreadyExistsException.class)
  @ResponseStatus(CONFLICT)
  public RestErrorResponse handleResourceAlreadyExistsException(
      final ResourceAlreadyExistsException exception, final HttpServletRequest httpServletRequest) {
    log.warn("Handled ResourceAlreadyExistsException with message {}", exception.getMessage());
    return responseWithStatus(CONFLICT, exception, httpServletRequest);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(NOT_FOUND)
  public RestErrorResponse handleResourceNotFoundException(
      final ResourceNotFoundException exception, final HttpServletRequest httpServletRequest) {
    log.warn("Handled ResourceNotFoundException with message {}", exception.getMessage());
    return responseWithStatus(NOT_FOUND, exception, httpServletRequest);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public RestErrorResponse handleMethodArgumentNotValidException(
      final MethodArgumentNotValidException exception,
      final HttpServletRequest httpServletRequest) {
    log.warn("Handled MethodArgumentNotValidException with message {}", exception.getMessage());
    return responseWithStatus(BAD_REQUEST, exception, httpServletRequest);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(BAD_REQUEST)
  public RestErrorResponse handleHttpMessageNotReadableException(
      final HttpMessageNotReadableException exception,
      final HttpServletRequest httpServletRequest) {
    log.warn("Handled HttpMessageNotReadableException with message {}", exception.getMessage());
    return responseWithStatus(BAD_REQUEST, exception, httpServletRequest);
  }

  private RestErrorResponse responseWithStatus(
      final HttpStatus status,
      final Exception exception,
      final HttpServletRequest httpServletRequest) {
    return RestErrorResponse.of(
        Instant.now(),
        status.value(),
        status.getReasonPhrase(),
        exception.getMessage(),
        httpServletRequest.getRequestURL().toString());
  }
}
