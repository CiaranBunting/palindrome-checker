package cme.persistence;

import java.util.Objects;

/**
 * File Storage Key implementation.
 * @author ciaranbunting@gmail.com
 */
public class FileStorageKey implements StorageKey {

  /** The key.*/
  String key;

  public FileStorageKey(String key) {
    this.key = key;
  }

  /**
   * Returns the file storage key.
   * @return the file storage key.
   */
  public String getKey() {
    return key;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FileStorageKey that = (FileStorageKey) o;
    return Objects.equals(getKey(), that.getKey());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getKey());
  }

  @Override
  public String toString() {
    return "FileStorageKey{" +
      "key='" + key + '\'' +
      '}';
  }
}
