package cme.persistence;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Simple storage using a file.
 * @author ciaranbunting@gmail.com
 */
@ApplicationScoped
public class FileStorage implements Storage {

  /** Result property name. */
  public static final String RESULT_PROPERTY = "result";

  private final static Logger LOGGER = Logger.getLogger("FileStorage");

  @Inject
  @ConfigProperty(name = "storage.file", defaultValue = "storage.txt")
  String storageFile;

  @Inject
  @ConfigProperty(name = "storage.directory", defaultValue = "storage")
  File storageDirectory;

  @Inject
  @ConfigProperty(name = "storage.deliminator", defaultValue = ",")
  String deliminator;

  @Override
  public StorageItem get(StorageKey key) {
    String storageKey = (String)key.getKey();
    return createStorageItem(getItem(storageKey));
  }

  @Override
  public Collection<StorageItem> getAll() {
    try {
      Stream<String> lines = Files.lines(getStorageFile().toPath());
      return lines.map(this::createStorageItem).collect(Collectors.toList());
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "Error finding all storage items.", e);
    }
    return new ArrayList<>();
  }

  @Override
  public void create(StorageItem item) {
    String key = (String) item.getKey().getKey();
    Boolean value = (Boolean) item.getValue(RESULT_PROPERTY);

    try {
      Files.write(getStorageFile().toPath(), formatLine(key, value).getBytes(), StandardOpenOption.APPEND);
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, String.format("Error creating storage item %s", item), e);
    }
  }

  @Override
  public StorageItem createItem(StorageKey key, Object value) {
    return new FileStorageItem.Builder(key)
      .value("result", value)
      .build();
  }

  @Override
  public StorageKey createKey(Object key) {
    return new FileStorageKey((String) key);
  }

  /**
   * Returns the Storage File.
   * @return the Storage File.
   */
  private File getStorageFile() {
    return new File(storageDirectory, storageFile);
  }

  /**
   * Initial the File Storage when Application Scope is initialised by the CDI container.
   * @param init CDI context.
   */
  public void initialiseStorage(@Observes @Initialized(ApplicationScoped.class) Object init) {
    if (!storageDirectory.exists()) {
      LOGGER.warning(String.format("Storage directory %s does not exist, creating new directory.", storageDirectory));

      if (!storageDirectory.mkdirs()) {
        LOGGER.severe(String.format("Unable to create directory structure for file storage %s.", storageDirectory));
      }
    }
    File file = getStorageFile();
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        LOGGER.log(Level.SEVERE, String.format("Error creating storage file %s", storageFile), e);
      }
    }
  }

  /**
   * Return the Item for the key. May return null.
   * @param key the key.
   * @return the Item for the key.
   */
  private String getItem(String key) {
    try {
      Stream<String> lines = Files.lines(getStorageFile().toPath());
      Optional<String> item = lines.filter(line -> line.contains(key)).findFirst();
      lines.close();
      if (item.isPresent()) {
        return item.get();
      }
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, String.format("Error finding item for key %s", key), e);
    }

    return null;
  }

  /**
   * Returns the elements of the line by the deliminator.
   * @param line the line.
   * @return the elements of the line by the deliminator.
   */
  private String[] getElements(String line) {
    return line.split(deliminator);
  }

  /**
   * Creates a {@link StorageItem} from the raw file line.
   * @param item the line.
   * @return the storage item from the line.
   */
  private StorageItem createStorageItem(String item) {
    String[] lineElements = getElements(item);
    return createItem(createKey(lineElements[0]), Boolean.valueOf(lineElements[1]));
  }

  /**
   * Format the line for storage.
   * @param key the key.
   * @param value the value.
   * @return the formatted line for storage.
   */
  private String formatLine(String key, Boolean value) {
    StringBuffer buffer = new StringBuffer(key);
    buffer.append(deliminator);
    buffer.append(value);
    buffer.append("\n");
    return buffer.toString();
  }

}
