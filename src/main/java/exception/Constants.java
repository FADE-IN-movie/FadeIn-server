package exception;

public class Constants {

  public enum ExceptionClass {
    USER("User"), CONTENT("Content"), REVIEW("Review"), LIKE("like");

    private String exceptionClass;

    ExceptionClass(String exceptionClass) { this.exceptionClass = exceptionClass; }

    public String getExceptionClass() { return exceptionClass; }

    @Override
    public String toString() { return getExceptionClass() + " Exception. ";}
  }
}
