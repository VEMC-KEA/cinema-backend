package vemc.cinema.utils;

import vemc.cinema.dto.ReservationDto;
import vemc.cinema.dto.helperdto.ReservationTicketHelperDto;
import vemc.cinema.dto.helperdto.SeatHelperDto;

import java.util.List;

public class PriceCalculator {
    public static double calculateTotalPrice(ReservationDto reservationDto) {
        double totalPrice = 0.0;
        int ticketCount = reservationDto.getTickets().size();

        for (ReservationTicketHelperDto ticketDto : reservationDto.getTickets()) {
            double ticketPrice = calculateTicketPrice(ticketDto.getRow_letter(), ticketDto.getNumber(), reservationDto.getScreening().getHall().get(0).getNumber());

            Long movieId = reservationDto.getScreening().getMovie().getId();
            if (isFeatureFilm(movieId)) {
                ticketPrice += 10.0;
            }

            if (reservationDto.getScreening().is3d()) {
                ticketPrice += 5.0;
            }

            totalPrice += ticketPrice;
        }

        if (ticketCount > 10) {
            double groupDiscount = totalPrice * 0.07;
            totalPrice -= groupDiscount;
        }

        if (ticketCount <= 5) {
            totalPrice += 5.0;
        }

        return totalPrice;
    }

    private static boolean isFeatureFilm(Long movieId) {
        return movieId % 2 == 0;
    }

    private static double calculateTicketPrice(String rowLetter, int seatNumber, int hallNumber) {
        double basePrice = 10.0;

        if (rowLetter.equalsIgnoreCase("A") || rowLetter.equalsIgnoreCase("B")) {
            basePrice -= 2.0;
        } else if (seatNumber >= 12 && seatNumber <= 16) {
            basePrice += 2.0;
        }

        if (hallNumber >= 3) {
            basePrice += 5.0;
        }
        return basePrice;
    }
}
