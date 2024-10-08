package cme.cache;

/**
 * Abstract Cache providing some common functionality and Cache API.
 * @author ciaranbunting@gmail.com
 */
public abstract class AbstractCache<K, V> {

  public abstract V getItem(K key);

  public abstract void setItem(K key, V value);

  public abstract V removeItem(K key);
}
