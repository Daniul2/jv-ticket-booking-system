package mate.academy;

import java.util.concurrent.Semaphore;

public class TicketBookingSystem {
    private final Semaphore seatSemaphore;

    public TicketBookingSystem(int totalSeats) {
        // Initialize the semaphore with the total number of available seats.
        // Each permit represents one seat.
        this.seatSemaphore = new Semaphore(totalSeats);
    }

    public BookingResult attemptBooking(String user) {
        // We use tryAcquire() instead of acquire().
        // acquire() would block the thread and wait until a seat is free.
        // tryAcquire() returns false immediately if no permits are available,
        // which is the desired behavior for a "Sold Out" scenario.
        boolean acquired = seatSemaphore.tryAcquire();

        if (acquired) {
            // If acquired, a permit is consumed (seat count decremented internally)
            return new BookingResult(user, true, "Booking successful.");
        } else {
            // If not acquired, it means 0 permits were left
            return new BookingResult(user, false, "No seats available.");
        }
    }
}
