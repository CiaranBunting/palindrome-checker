package cme.cache;

import cme.persistence.Storage;
import cme.persistence.StorageItem;
import cme.persistence.StorageKey;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.metrics.MetricRegistry;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static cme.persistence.FileStorage.RESULT_PROPERTY;

/**
 * Simple Map based in-memory cache.
 * This is a write-through cache so values set via the API
 * are written to persistence storage.
 * @author ciaranbunting@gmail.com
 */
@ApplicationScoped
@CacheQualifier
public class SimpleCache extends AbstractCache<String, Boolean> {

  static Map<String, Boolean> cache = new HashMap<>();

  @Inject
  Storage storage;

  /**
   * Inject the Metric Registry to programmatically invoke metrics API.
   */
  @Inject
  MetricRegistry metricRegistry;

  @Override
  public Boolean getItem(String key) {
    if (cache.get(key) == null) {
      metricRegistry.counter("cache.miss").inc();
      return null;
    }
    metricRegistry.counter("cache.hit").inc();
    return cache.get(key);
  }

  @Override
  public void setItem(String key, Boolean value) {
    cache.put(key, value);
    writeItem(key, value);
  }

  @Override
  public Boolean removeItem(String key) {
    // Not implemented as not required but cache pruning would require a way to remove items.
    return null;
  }

  /**
   * Initialise the Cache from Storage when Application Scope is initialised by the CDI container.
   * @param init the initial context.
   */
  public void initialiseCache(@Observes @Initialized(ApplicationScoped.class) Object init) {
    storage.getAll().forEach(item -> cache.put((String) item.getKey().getKey(), (Boolean) item.getValue(RESULT_PROPERTY)));
  }

  /**
   * Write the Item to Storage.
   * The Asynchronous annotation will create a separate thread to run
   * this method and monitor the returned completion stage.
   *
   * @param key the key of the item
   * @param value the value of the item.
   * @return a completion stage when the async write to storage is complete.
   */
  @Asynchronous
  protected CompletionStage<String> writeItem(String key, Boolean value) {
    StorageKey storageKey = storage.createKey(key);
    StorageItem item = storage.createItem(storageKey, value);
    storage.create(item);
    return CompletableFuture.completedFuture("Item written to storage");
  }

}
