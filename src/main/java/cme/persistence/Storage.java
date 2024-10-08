package cme.persistence;

import java.util.Collection;

/**
 * Storage Interface.
 * Update and delete methods omitted in the interests of brevity.
 * @author ciaranbunting@gmail.com
 */
public interface Storage {

  StorageItem get(StorageKey key);

  Collection<StorageItem> getAll();

  void create(StorageItem item);

  StorageItem createItem(StorageKey key, Object value);

  StorageKey createKey(Object key);
}
