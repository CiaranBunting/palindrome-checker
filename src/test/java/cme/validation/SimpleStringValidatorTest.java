package cme.validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link SimpleStringValidator}.
 * @author ciaranbunting@gmail.com
 */
public class SimpleStringValidatorTest {

  static SimpleStringValidator validator;

  @BeforeAll
  public static void setup() {
    validator = new SimpleStringValidator();
  }

  @Test
  public void shouldReturnTrueWhenValid() {
    assertTrue(validator.validate("hello"));
    assertTrue(validator.validate("world"));
  }

  @Test
  public void shouldReturnFalseWhenInputBlank() {
    assertFalse(validator.validate(null));
    assertFalse(validator.validate(""));
    assertFalse(validator.validate(" "));
  }

  @Test
  public void shouldReturnFalseWhenContainsNumber() {
    assertFalse(validator.validate("hel1o"));
    assertFalse(validator.validate("4ello"));
    assertFalse(validator.validate("Hell0"));
    assertFalse(validator.validate("4e1l0"));
  }

  @Test
  public void shouldReturnFalseWhenContainsSpaces() {
    assertFalse(validator.validate("He llo"));
    assertFalse(validator.validate("H e l l o"));
  }

}
