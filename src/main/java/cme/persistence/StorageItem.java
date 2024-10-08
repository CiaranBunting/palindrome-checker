package cme.persistence;

/**
 * Storage Item Interface.
 * @author ciaranbunting@gmail.com
 */
public interface StorageItem {

  StorageKey getKey();

  Object getValue(String property);

  void setValue(String property, Object value);
}
