package com.mycompany.app;

import java.util.HashMap;
import java.util.Optional;

/**
 * Repository of drivers that work in-memory.
 */
public class InMemoryDriverRepository implements DriverRepository {

    private static HashMap<DriverId, Driver> storage;

    static {
        storage = new HashMap<>();
    }

    public Optional<Driver> read(DriverId id) {
        // return Optional
        return Optional.ofNullable(storage.get(id));
    }

    public void write(Driver driver) {
        storage.put(driver.getId(), driver);
    }
}
