package com.mycompany.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static com.mycompany.app.DriverId.create;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DisplayName("PaymentCalculator should")
class PaymentCalculatorTest {

    private static final int HUNDRED_KM = 100;
    private static final int FIFTY_KM = 50;
    private static final double COEFFICIENT = 1.5;
    private static final long DRIVER_ID = 1L;

    @Test
    @DisplayName("calculate payment for a single ride")
    void calculatePaymentForSingleRide() {
        Driver driver = new Driver();
        Ride ride = new Ride(HUNDRED_KM);
        DriverId driverId = create(DRIVER_ID);
        driver.setId(driverId);
        driver.setCoefficient(COEFFICIENT);

        InMemoryDriverRepository repository = new InMemoryDriverRepository();
        repository.write(driver);

        PaymentCalculator calculator = new PaymentCalculator(repository);
        Payment payment = calculator.calculate(ride, driverId);

        assertNotNull(payment);
        assertNotNull(payment.getDateTime());
        assertEquals(COEFFICIENT * HUNDRED_KM, payment.getMoney());
        assertEquals(DRIVER_ID, driverId.getValue());

    }

    @Test
    @DisplayName("calculate payment for multiple rides")
    void calculatePaymentForMultipleRides() {
        Ride ride1 = new Ride(HUNDRED_KM);
        Ride ride2 = new Ride(FIFTY_KM);
        DriverId driverId = create(DRIVER_ID);
        Driver driver = new Driver();
        driver.setId(driverId);
        driver.setCoefficient(COEFFICIENT);

        InMemoryDriverRepository repository = new InMemoryDriverRepository();
        repository.write(driver);
        HashSet<Ride> rides = new HashSet<>();
        rides.add(ride1);
        rides.add(ride2);

        PaymentCalculator calculator = new PaymentCalculator(repository);
        Payment payment = calculator.calculate(rides, driverId);

        assertNotNull(payment);
        assertNotNull(payment.getDateTime());
        assertEquals(COEFFICIENT * (HUNDRED_KM + FIFTY_KM),
                payment.getMoney());
        assertEquals(DRIVER_ID, driverId.getValue());

    }

    @Test
    @DisplayName("calculate payment for multiple rides and varying distance")
    void calculatePaymentForMultipleRidesAndVaryingDistance() {
        DriverId driverId = create(DRIVER_ID);
        Ride ride1 = new Ride(HUNDRED_KM);
        Ride ride2 = new Ride(FIFTY_KM);

        Driver driver = new Driver();
        driver.setId(driverId);
        driver.setCoefficient(COEFFICIENT);

        InMemoryDriverRepository repository = new InMemoryDriverRepository();
        repository.write(driver);

        HashSet<Ride> rides = new HashSet<>();
        rides.add(ride1);
        rides.add(ride2);

        ride1.setDistance(FIFTY_KM);
        ride2.setDistance(HUNDRED_KM);

        PaymentCalculator calculator = new PaymentCalculator(repository);
        Payment payment = calculator.calculate(rides, driverId);

        assertNotNull(payment);
        assertNotNull(payment.getDateTime());
        assertEquals(COEFFICIENT * (HUNDRED_KM + FIFTY_KM),
                payment.getMoney());
        assertEquals(DRIVER_ID, driverId.getValue());

    }

//    private InMemoryDriverRepository mockInMemoryDriverRepository(DriverId driverId) {
//        Driver driver = new Driver();
//        driver.setId(driverId);
//        driver.setCoefficient(COEFFICIENT);
//
//        InMemoryDriverRepository mock = mock(InMemoryDriverRepository.class);
//
//        when(mock.read(any())).thenReturn(Optional.ofNullable(driver));
//        return mock;
//    }
}
