package se.ifmo.blos.lab1.exceptions;

public class ResourceAlreadyExistsException extends Exception {

  private static final long serialVersionUID = 4958441245498610922L;

  public ResourceAlreadyExistsException() {
    super();
  }

  public ResourceAlreadyExistsException(final String message) {
    super(message);
  }
}
