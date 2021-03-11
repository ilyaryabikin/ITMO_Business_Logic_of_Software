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
import se.ifmo.blos.lab1.dtos.RestErrorResponse;
import se.ifmo.blos.lab1.exceptions.IllegalPropertyUpdateException;
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
    return responseWithStatus(CONFLICT, exception.getMessage(), httpServletRequest);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(NOT_FOUND)
  public RestErrorResponse handleResourceNotFoundException(
      final ResourceNotFoundException exception, final HttpServletRequest httpServletRequest) {
    log.warn("Handled ResourceNotFoundException with message {}", exception.getMessage());
    return responseWithStatus(NOT_FOUND, exception.getMessage(), httpServletRequest);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public RestErrorResponse handleMethodArgumentNotValidException(
      final MethodArgumentNotValidException exception,
      final HttpServletRequest httpServletRequest) {
    final var errorMessageBuilder = new StringBuilder("Failed field validation with messages: ");
    for (final var fieldError : exception.getBindingResult().getFieldErrors()) {
      errorMessageBuilder
          .append("wrong value for field ")
          .append(fieldError.getField())
          .append(": ")
          .append(fieldError.getDefaultMessage())
          .append(", ");
    }
    errorMessageBuilder.delete(errorMessageBuilder.lastIndexOf(", "), errorMessageBuilder.length());

    final String errorMessage = errorMessageBuilder.toString();
    log.warn("Handled MethodArgumentNotValidException with message {}", errorMessage);
    return responseWithStatus(BAD_REQUEST, errorMessage, httpServletRequest);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(BAD_REQUEST)
  public RestErrorResponse handleHttpMessageNotReadableException(
      final HttpMessageNotReadableException exception,
      final HttpServletRequest httpServletRequest) {
    log.warn("Handled HttpMessageNotReadableException with message {}", exception.getMessage());
    return responseWithStatus(BAD_REQUEST, exception.getMessage(), httpServletRequest);
  }

  @ExceptionHandler(IllegalPropertyUpdateException.class)
  @ResponseStatus(BAD_REQUEST)
  public RestErrorResponse handleHttpMessageNotReadableException(
      final IllegalPropertyUpdateException exception, final HttpServletRequest httpServletRequest) {
    log.warn("Handled IllegalPropertyUpdateException with message {}", exception.getMessage());
    return responseWithStatus(BAD_REQUEST, exception.getMessage(), httpServletRequest);
  }

  private RestErrorResponse responseWithStatus(
      final HttpStatus status,
      final String errorMessage,
      final HttpServletRequest httpServletRequest) {
    return RestErrorResponse.of(
        Instant.now(),
        status.value(),
        status.getReasonPhrase(),
        errorMessage,
        httpServletRequest.getRequestURL().toString());
  }
}
