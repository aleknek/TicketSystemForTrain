package com.brainacad.additional.ticketSystemForTrain;

import java.util.HashMap;

public class Car {

    private int carNumber;
    private int countSeats;
    private HashMap<Integer, Passenger> seat = new HashMap<>();

    public Car(int carNumber, int countSeats) {
        this.carNumber = carNumber;
        this.countSeats = countSeats;
    }

    @Override
    public String toString() {
        return "вагон № " + carNumber + ", кол-во мест в вагоне: " + countSeats + ", кол-во свободных мест: " + getCountFreeSeats();
    }

    public HashMap<Integer, Passenger> getSeat() {
        return seat;
    }

    public boolean buyTicket(int numberSeat, Passenger passenger) {

        if (numberSeat == 0) {
            for (int i = 1; i < countSeats; i++)
                if (seat.containsKey(i) == false) {
                    numberSeat = i;
                    break;
                }
        }

        if (seat.containsKey(numberSeat)) {
            System.out.println("Место № " + numberSeat + " в вагоне " + carNumber + " занято. Выберите другое место");
            return false;
        } else {
            seat.put(numberSeat, passenger);
            return true;
        }

    }

    public int getCountFreeSeats() {
        if (seat == null) {
            return countSeats;
        } else return (countSeats - seat.size());
    }

    public void printStatusSeats() {

        boolean isFullSeat = false;
        System.out.print("Свободные места: ");
        for (int i = 1; i <= countSeats; i++) {
            if (seat.containsKey(i) == false) {
                System.out.print(i + " ");
            } else {
                isFullSeat = true;
            }
        }
        System.out.println();

        if (isFullSeat) {
            System.out.print("Занятые места: ");
            for (int i = 1; i <= countSeats; i++) {
                if (seat.containsKey(i) == true) {
                    System.out.print(i + " ");
                }
            }
            System.out.println();
        }
    }
}
