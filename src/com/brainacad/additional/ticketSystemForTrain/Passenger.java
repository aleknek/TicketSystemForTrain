package com.brainacad.additional.ticketSystemForTrain;

public class Passenger {

    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        return "Пассажир: "+lastName+" "+firstName;
    }

    public Passenger(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
