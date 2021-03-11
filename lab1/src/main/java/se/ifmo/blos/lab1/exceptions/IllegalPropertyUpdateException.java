package se.ifmo.blos.lab1.exceptions;

public class IllegalPropertyUpdateException extends Exception {

  private static final long serialVersionUID = 4502144543399649502L;

  public IllegalPropertyUpdateException() {
    super();
  }

  public IllegalPropertyUpdateException(final String message) {
    super(message);
  }
}
