package model.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public class FileManager {
    private static final String STORE_DIRECTORY = "./store";
    private static final String EXTENSION = ".ser";

    /**
     * Generate store directory if not exists
     */
    public static void init() {
        // Create store directory
        createDirectory("");
    }

    public static boolean checkExists(String path) {
        return !Files.exists(Path.of(STORE_DIRECTORY, path + EXTENSION));
    }

    /**
     * Deserialize content in the file contained in the store folder
     *
     * @param fileName Name of the file contained in the store folder
     * @param <T>      Type of the object to deserialize
     * @return Deserialized object or null if Object is not found / file not found
     */
    public static <T extends Serializable> T deserialize(String fileName) {
        String directory = STORE_DIRECTORY + File.separator + fileName + EXTENSION;

        try (FileInputStream fi = new FileInputStream(directory)) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(fi)) {
                return (T) objectInputStream.readObject();

            } catch (ClassNotFoundException | ClassCastException e) {
                throw new RuntimeException(e);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Serialize an Object and store it in a file
     *
     * @param obj      Object to serialize
     * @param fileName File to store the object in the store folder
     * @param <T>      Type of the object to serialize
     */
    public static <T> void serialize(T obj, String fileName) {
        String directory = STORE_DIRECTORY + File.separator + fileName + EXTENSION;

        try (FileOutputStream fw = new FileOutputStream(directory)) {
            ObjectOutputStream ob = new ObjectOutputStream(fw);
            ob.writeObject(obj);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a new directory in the store folder
     *
     * @param name name of the directory
     */
    public static void createDirectory(String name) {
        Path directory = Path.of(STORE_DIRECTORY, name);
        if (Files.exists(directory)) return;
        try {
            Files.createDirectory(directory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get latest modified file in a directory
     *
     * @param directoryPath path to the directory
     * @return path to latest modified file
     */
    public static String getLatestModifiedFile(String directoryPath) {
        Path path = Path.of(STORE_DIRECTORY, directoryPath);
        final Optional<Path> lastModifiedFileName;

        try (Stream<Path> files = Files.list(path)) {

            lastModifiedFileName = files.max(Comparator.comparingLong(p -> {
                try {
                    return Files.getLastModifiedTime(p).toMillis();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return lastModifiedFileName.map(String::valueOf).map(s -> s.substring(16, s.length() - 4)).orElse(null);

    }

    /**
     * Get file from resource folder
     *
     * @param path path to the file
     * @return path to the file in the resource directory
     */
    public static String getResource(String... path) {
        return Path.of("resources", path).toString();
    }
}

