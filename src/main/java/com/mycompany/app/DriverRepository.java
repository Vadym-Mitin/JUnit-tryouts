package com.mycompany.app;

import java.util.Optional;

/**
 * Obtains drivers from database and stores them there.
 */
public interface DriverRepository {

    Optional<Driver> read(DriverId id);

    void write(Driver driver);
}
