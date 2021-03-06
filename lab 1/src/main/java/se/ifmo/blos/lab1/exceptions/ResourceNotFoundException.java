package se.ifmo.blos.lab1.exceptions;

public class ResourceNotFoundException extends Exception {

  private static final long serialVersionUID = 3839520715789180641L;

  public ResourceNotFoundException() {
    super();
  }

  public ResourceNotFoundException(final String message) {
    super(message);
  }
}
