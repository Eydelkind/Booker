package utils;

import models.Booking;
import models.BookingDates;

public class BookingDataFactory {

    public static Booking createDefaultBooking() {
        return new Booking(
                "Jim",
                "Brown",
                111,
                true,
                new BookingDates("2026-03-22", "2026-03-25"),
                "Breakfast"
        );
    }

    public static Booking createUpdatedBooking() {
        return new Booking(
                "James",
                "Brown",
                222,
                false,
                new BookingDates("2026-04-01", "2026-04-10"),
                "Lunch"
        );
    }
}