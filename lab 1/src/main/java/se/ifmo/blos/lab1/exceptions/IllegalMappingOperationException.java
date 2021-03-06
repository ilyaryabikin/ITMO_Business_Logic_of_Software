package se.ifmo.blos.lab1.exceptions;

public class IllegalMappingOperationException extends RuntimeException {

  private static final long serialVersionUID = 4502144543399649502L;

  public IllegalMappingOperationException() {
    super();
  }

  public IllegalMappingOperationException(final String message) {
    super(message);
  }
}
