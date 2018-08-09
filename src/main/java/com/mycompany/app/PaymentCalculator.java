package com.mycompany.app;

import java.util.Optional;
import java.util.Set;

import static java.time.ZonedDateTime.now;

/**
 * Calculates how much money and when we pay to drivers.
 *
 * @author Vladyslav Lubenskyi
 */
public class PaymentCalculator {

    final private InMemoryDriverRepository driverRepository;

    public PaymentCalculator(InMemoryDriverRepository repository) {
        this.driverRepository = repository;
    }

    /**
     * Calculates payment for a single ride for the given driver.
     */
    public Payment calculate(Ride ride, DriverId id) {
        Optional<Driver> driver = driverRepository.read(id);
        double sum;
        if (driver.isPresent()) {
            sum = calculateMoney(ride, driver.get());
        } else sum = 0;
        return new Payment((int) sum, now());
    }

    /**
     * Calculates payment for multiple rides at once for the given.
     */
    public Payment calculate(Set<Ride> rides, DriverId id) {
        Optional<Driver> driver = driverRepository.read(id);
        double sum = 0;
        for (Ride ride : rides) {
            if (driver.isPresent()) {
                sum += calculateMoney(ride, driver.get());
            }
//            sum += calculateMoney(ride, driver.get());
        }
        return new Payment((int) sum, now());
    }

    private double calculateMoney(Ride ride, Driver driver) {
        return ride.getDistance() * driver.getCoefficient();
    }
}
