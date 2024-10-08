package cme.palindrome.app;

import cme.cache.AbstractCache;
import cme.cache.CacheQualifier;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Checker Service.
 * Caches previously checked words.
 * @author ciaranbunting@gmail.com
 */
@ApplicationScoped
public class CheckerService {

  @Inject
  @CacheQualifier
  AbstractCache<String, Boolean> cache;

  /**
   * Returns true if the value is a palindrome.
   * @param value the value to check.
   * @return true if the value is a palindrome.
   */
  public boolean check(String value) {
    Boolean item = cache.getItem(value);
    if (item != null) {
      return item;
    }

    boolean result = checkPalindrome(value);
    cache.setItem(value, result);

    return result;
  }

  /**
   * Returns true if the specified value is a palindrome.
   * @param value the value to check.
   * @return true if the specified value is a palindrome.
   */
  public boolean checkPalindrome(String value) {
    return value.equals(new StringBuilder(value.toLowerCase()).reverse().toString());
  }

}
