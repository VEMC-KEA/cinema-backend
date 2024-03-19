package vemc.cinema.utils;

import vemc.cinema.entity.Reservation;
import vemc.cinema.entity.Screening;
import vemc.cinema.entity.Seat;
import vemc.cinema.entity.Ticket;


public class PriceCalculator {

    /**
     * Calculates the total price of a reservation.
     * @param reservation the reservation to calculate the price for
     * @return the total price of the reservation
     */
    public static double calculateTotalPrice(Reservation reservation) {
        double totalPrice = 0.0;
        int ticketCount = reservation.getTickets().size();
        double reservationFee = reservation.getScreening().getCinema().getReservationFee();
        double groupDiscountRate = reservation.getScreening().getCinema().getGroupDiscount() / 100.0;

        for (Ticket ticket : reservation.getTickets()) {
            totalPrice += ticket.getPrice();
        }

        if (ticketCount <= 5) {
            totalPrice += reservationFee;
        }

        if (ticketCount > 10) {
            totalPrice -= totalPrice * groupDiscountRate;
        }

        return totalPrice;
    }

    /**
     * Calculates the price of a ticket based on the screening and seat.
     *
     * @param ticket    the ticket to calculate the price for
     * @param screening the screening the ticket is for
     * @param seat      the seat the ticket is for
     */
     public static void calculatePrice(Ticket ticket, Screening screening, Seat seat) {
        double basePrice = screening.getCinema().getMovieBasePrice();

        if (screening.is3d()) {
            basePrice += 2.0;
        }
        if (screening.getMovie().getRunTime() > 170) {
            basePrice += 3.0;
        }

        var rowLetter = seat.getRowLetter();
        if(rowLetter.equals("A") || rowLetter.equals("B")){
            basePrice -= 1.0;
        } else if(rowLetter.equals("Y") || rowLetter.equals("Z")) {
            basePrice += 1.0;
        }

        ticket.setPrice(basePrice);
     }
}


