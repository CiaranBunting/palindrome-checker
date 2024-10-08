package cme.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * File Storage Item.
 * Uses Builder pattern to create the item but allows updating values.
 * @author ciaranbunting@gmail.com
 */
public class FileStorageItem implements StorageItem {

  Map<String, Object> values;

  StorageKey key;

  private FileStorageItem(Builder builder) {
    this.key = builder.key;
    this.values = builder.values;
  }

  @Override
  public StorageKey getKey() {
    return key;
  }

  @Override
  public Object getValue(String property) {
    return values.get(property);
  }

  @Override
  public void setValue(String property, Object value) {
    values.put(property, value);
  }

  /**
   * Static class to construct the FileStorageItem.
   */
  public static class Builder {
    private StorageKey key;
    private Map<String, Object> values;

    /**
     * Constructor.
     * @param key the storage key.
     */
    public Builder(StorageKey key) {
      this.key = key;
      values = new HashMap<>();
    }

    /**
     * Set the property value.
     * @param property the property.
     * @param value the value.
     */
    public Builder value(String property, Object value) {
      values.put(property, value);
      return this;
    }

    /**
     * Builds the new FileStorageItem.
     * @return the new FileStorageItem.
     */
    public FileStorageItem build() {
      return new FileStorageItem(this);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FileStorageItem that = (FileStorageItem) o;
    return Objects.equals(values, that.values) && getKey().equals(that.getKey());
  }

  @Override
  public int hashCode() {
    return Objects.hash(values, getKey());
  }

  @Override
  public String toString() {
    return "FileStorageItem{" +
      "key=" + key +
      ", values=" + values +
      '}';
  }
}
