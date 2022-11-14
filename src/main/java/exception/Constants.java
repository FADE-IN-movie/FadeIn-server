package exception;

public class Constants {

  public enum ExceptionClass {
    PRODUCT("Product"), USER("User"), CONTENT("Content");

    private String exceptionClass;

    ExceptionClass(String exceptionClass) { this.exceptionClass = exceptionClass; }

    public String getExceptionClass() { return exceptionClass; }

    @Override
    public String toString() { return getExceptionClass() + " Exception. ";}
  }
}
