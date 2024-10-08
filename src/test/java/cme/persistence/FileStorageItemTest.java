package cme.persistence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for {@link FileStorageItem}
 * @author ciaranbunting@gmail.com
 */
public class FileStorageItemTest {

  @Test
  public void shouldCreateWithKey() {
    FileStorageKey key = new FileStorageKey("testKey");
    FileStorageItem item = new FileStorageItem.Builder(key).build();
    assertNotNull(item);
    assertEquals(key, item.getKey());
  }

  @Test
  public void shouldCreateWithValue() {
    FileStorageKey key = new FileStorageKey("testKey");
    FileStorageItem item = new FileStorageItem.Builder(key)
      .value("test", "value").build();
    assertNotNull(item);
    assertEquals(key, item.getKey());
    assertEquals("value", item.getValue("test"));
  }

  @Test
  public void shouldBeEqual() {
    FileStorageKey key = new FileStorageKey("testKey");
    FileStorageItem item1 = new FileStorageItem.Builder(key)
      .value("test", "value").build();
    FileStorageItem item2 = new FileStorageItem.Builder(key)
      .value("test", "value").build();
    assertNotNull(item1);
    assertNotNull(item2);
    assertEquals(item1, item2);
  }

  @Test
  public void shouldBeNotEqual() {
    FileStorageKey key1 = new FileStorageKey("testKey");
    FileStorageKey key2 = new FileStorageKey("testKey");
    FileStorageItem item1 = new FileStorageItem.Builder(key1)
      .value("test1", "value1").build();
    FileStorageItem item2 = new FileStorageItem.Builder(key2)
      .value("test2", "value2").build();
    assertNotNull(item1);
    assertNotNull(item2);
    assertNotEquals(item1, item2);
  }
}
