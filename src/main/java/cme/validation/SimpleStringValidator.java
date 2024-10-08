package cme.validation;

import javax.enterprise.context.ApplicationScoped;

/**
 * Simple String Validator.
 * Validates the String doesn't contain spaces or numbers.
 * @author ciaranbunting@gmail.com
 */
@ApplicationScoped
public class SimpleStringValidator implements Validator {

  @Override
  public boolean validate(Object object) {
    return object instanceof String && !isBlank((String) object) &&
      !containsNumber((String) object) && !containsSpaces((String) object);
  }

  /**
   * Returns true if the specified string is null or empty.
   * @param input the string to test.
   * @return true if the specified string is null or empty.
   */
  protected boolean isBlank(String input) {
    return input == null || input.trim().equals("");
  }

  /**
   * Returns true if the input string contains a number/digit.
   * @param input the input string to test.
   * @return true if the input string contains a number/digit.
   */
  protected boolean containsNumber(String input) {
    return input.matches(".*\\d.*");
  }

  /**
   * Returns true if the input string contains 1 or more spaces.
   * @param input the input string to test.
   * @return true if the input string contains 1 or more spaces.
   */
  protected boolean containsSpaces(String input) {
    return input.matches(".*\\s.*");
  }
}
