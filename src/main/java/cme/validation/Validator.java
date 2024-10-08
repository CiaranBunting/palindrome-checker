package cme.validation;

/**
 * Interface for Input Validators.
 */
public interface Validator {

  /**
   * Validate the param.
   * @param object the param.
   * @return true if the param is valid.
   */
  boolean validate(Object object);
}
