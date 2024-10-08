package cme.cache;

import cme.persistence.FileStorage;
import cme.persistence.Storage;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link SimpleCache}.
 * @author ciaranbunting@gmail.com
 */
public class SimpleCacheTest extends Mockito {

  Storage mockStorage;
  SimpleCache cache;
  MetricRegistry metricRegistry;
  Counter counter;

  @BeforeEach
  public void setup() {
    mockStorage = mock(FileStorage.class);
    metricRegistry = mock(MetricRegistry.class);
    counter = mock(Counter.class);
    when(metricRegistry.counter(anyString())).thenReturn(counter);
    cache = new SimpleCache();
    cache.storage = mockStorage;
    cache.metricRegistry = metricRegistry;
  }

  @Test
  public void shouldReturnItemIfCached() {
    cache.setItem("testKey", true);
    assertTrue(cache.getItem("testKey"));
    verify(metricRegistry).counter("cache.hit");
  }

  @Test
  public void shouldReturnNullIfNotCached() {
    assertNull(cache.getItem("someTestKey"));
    verify(metricRegistry).counter("cache.miss");
  }

  @Test
  public void shouldWriteItemToStorage() {
    cache.setItem("testKey", true);
    verify(mockStorage).create(any());
    assertTrue(cache.getItem("testKey"));
  }
}
