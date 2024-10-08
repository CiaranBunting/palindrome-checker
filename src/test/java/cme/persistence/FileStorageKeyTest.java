package cme.persistence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Tests for {@link FileStorageKey}.
 * @author ciaranbunting@gmail.com
 */
public class FileStorageKeyTest {

  @Test
  public void shouldReturnKey() {
    FileStorageKey key = new FileStorageKey("testKey");
    assertEquals("testKey", key.getKey());
  }

  @Test
  public void shouldBeEqual() {
    FileStorageKey key1 = new FileStorageKey("testKey");
    FileStorageKey key2 = new FileStorageKey("testKey");
    assertEquals(key1, key2);
  }

  @Test
  public void shouldNotBeEqual() {
    FileStorageKey key1 = new FileStorageKey("testKey1");
    FileStorageKey key2 = new FileStorageKey("testKey2");
    assertNotEquals(key1, key2);
  }
}
