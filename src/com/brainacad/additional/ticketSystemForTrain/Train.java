package com.brainacad.additional.ticketSystemForTrain;

import java.util.*;

public class Train {

    private String name;
    private String number;
    private Map<Integer, Car> cars = new LinkedHashMap<>(); // номера вагонов
    private List<String> route;

    public Train() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Map<Integer, Car> getCars() {
        return cars;
    }

    public void setCars(int countCar, int countSeats) {
        for (int i = 1; i <= countCar; i++) {
            cars.put(i, new Car(i, countSeats));
        }
    }

    public List<String> getRoute() {
        return route;
    }

    public void setRoute(List<String> route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "поезд № " + number + " - " + name;
    }

    public void printListTrainAndPassenger(int numberCar) {

        Car car = cars.get(numberCar);

        if (car == null) {
            System.out.println("В поезде " + name + " нет вагона с № " + numberCar);
            System.out.println("Проверьте корректность введенных данных и повторите еще раз!");
            return;
        }

        toString();
        System.out.println(car);

        Iterator<Map.Entry<Integer, Passenger>> entryIterator1 = car.getSeat().entrySet().iterator();
        while (entryIterator1.hasNext()) {
            Map.Entry<Integer, Passenger> entry1 = entryIterator1.next();
            System.out.println(entry1.getValue());
        }
    }

    public void printListStatusSeats(int numberCar) {

        Car car = cars.get(numberCar);

        if (car == null) {
            System.out.println("В поезде " + name + " нет вагона с № " + numberCar);
            System.out.println("Проверьте корректность введенных данных и повторите еще раз!");
            return;
        }
        car.printStatusSeats();
    }

    public void buyTicket(int numberCar, int numberSeat, Passenger passenger) {

        Car car = cars.get(numberCar);
        if (car == null) {
            System.out.println("В поезде " + name + " нет вагона с № " + numberCar);
            System.out.println("Проверьте корректность введенных данных и повторите еще раз!");
            return;
        }

        if (car.buyTicket(numberSeat, passenger)) {
            System.out.println("Билет куплен...");
        } else {
            System.out.println("Ошибка при покупке билета");
        }
        ;
    }
}
