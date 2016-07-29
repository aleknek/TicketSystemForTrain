package com.brainacad.additional.ticketSystemForTrain;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        Set<Train> trains = addTrains();

        Scanner sc = new Scanner(System.in);
        System.out.println("******* Добро пожаловать в систему покупки железнодорожных билетов (для выхода из программы команда 'Exit') *******");
        System.out.println("Справка о доступных командах, команда 'Help' или 'Помощь'.");

        while (sc.hasNext()) {

            String word = sc.nextLine();
            word = word.toLowerCase().trim();
            boolean exit = false;

            switch (word) {
                case "help":
                case "помощь":
                    printHelp();
                    break;
                case "exit":
                case "выход":
                    exit = true;
                    break;
                case "поезда все":
                case "trains all":
                    printListAllTrainAndAllPassenger(trains);
                    break;
                case "места вагон":
                case "seats car":
                    printSeatsInCar(sc, trains);
                    break;
                case "места поезд":
                case "seats train":
                    printSeatsInTrain(sc, trains);
                    break;
                case "пассажиры":
                case "passengers":
                    printListPassengers(sc, trains);
                    break;
                case "buy":
                case "купить":
                    exit = buyTicket(sc, trains, word);
                    break;
                case "маршрут":
                case "route":
                    printRoute(sc, trains, word);
                    break;
                case "поезда":
                case "trains":
                    printListAllTrain(trains);
                    break;
                default:
                    System.out.println("Введите новую команду (список команд - 'Help' или 'Помощь'): ");
                    continue;
            }

            if (exit) {
                break;
            }
        }
    }

    public static void printHelp() {
        System.out.println("Купить билет по номеру поезда, команды - 'Купить' или 'Buy'");
        System.out.println("Посмотреть список доступных поездов по маршруту, команды - 'Маршрут' или 'Route'");
        System.out.println("Посмотреть список всех поездов, команды - 'Поезда' или 'Trains'");
        System.out.println("Посмотреть наличие мест в вагоне, команда - 'Места вагон' или 'Seats car'");
        System.out.println("Посмотреть наличие мест в поезде, команда - 'Места поезд' или 'Seats train'");
        System.out.println("Посмотреть список пассажиров в вагоне, команда - 'Пассажиры' или 'Passengers'");
        System.out.println("Посмотреть список всех поездов и пассажиров, команда - 'Поезда все' или 'Trains all'");
        System.out.println("Выход из программы, команды - 'Выход' или 'Exit'");
    }

    public static void printSeatsInCar(Scanner sc, Set<Train> trains) {

        Train train = getTrainByNumber(sc, trains);
        System.out.println(train);

        if (train != null) {

            System.out.println("Введите номер вагона и нажмите 'Ввод': ");

            int numberCar = getNumberCarOrSeat(sc);
            if (numberCar == -1) {
                System.out.println("Некорректный номер вагона.");
            }
            train.printListStatusSeats(numberCar);
        }
    }

    public static void printSeatsInTrain(Scanner sc, Set<Train> trains) {

        Train train = getTrainByNumber(sc, trains);
        System.out.println(train);

        if (train != null) {

            Iterator<Map.Entry<Integer, Car>> itrCars = train.getCars().entrySet().iterator();
            while (itrCars.hasNext()) {
                Map.Entry<Integer, Car> entry = itrCars.next();
                int numberCar = entry.getKey();
                System.out.println("Вагон № " + numberCar);
                train.printListStatusSeats(numberCar);
            }
        }
    }

    public static void printListPassengers(Scanner sc, Set<Train> trains) {

        Train train = getTrainByNumber(sc, trains);
        System.out.println(train);

        if (train != null) {

            System.out.println("Введите номер вагона и нажмите 'Ввод': ");

            int numberCar = getNumberCarOrSeat(sc);
            if (numberCar == -1) {
                System.out.println("Некорректный номер вагона.");
            }
            train.printListTrainAndPassenger(numberCar);
        }
    }

    public static boolean buyTicket(Scanner sc, Set<Train> trains, String word) {

        Train train = getTrainByNumber(sc, trains);

        if (train != null) {

            System.out.println(train);

            while (true) {

                System.out.println("Введите номер вагона и нажмите 'Ввод': ");

                int numberCar = getNumberCarOrSeat(sc);
                if (numberCar == -1) {
                    if (word.equals("exit") || word.equals("выход")) {
                        return true;
                    }
                    System.out.println("Некорректный номер вагона. Проверьте правильность ввода и повторите еще раз");
                    continue;
                }

                System.out.println("Введите номер места и нажмите 'Ввод'. Место по умолчанию, выберите '0': ");

                int numberSeat = getNumberCarOrSeat(sc);
                if (numberSeat == -1) {
                    if (word.equals("exit") || word.equals("выход")) {
                        return true;
                    }
                    System.out.println("Некорректный номер места. Проверьте правильность ввода и повторите еще раз");
                    continue;
                }

                System.out.println("Введите имя и фамилию пассажира через пробел: ");
                sc.hasNext();
                word = sc.nextLine().trim();

                int index = word.indexOf(" ");
                String firstName = word.substring(0, index);
                String lastName = word.substring(index + 1);
                train.buyTicket(numberCar, numberSeat, new Passenger(firstName, lastName));
                return false;
            }

        } else {
            System.out.println("Поезд № " + word + " не найден. Посмотреть список поездов, команда 'Trains' или 'Поезда'. Выйти, команда - 'Exit'");
        }

        return false;
    }

    public static void printRoute(Scanner sc, Set<Train> trains, String word) {

        System.out.println("Введите станцию отправления и станцию прибытия, через пробел");

        sc.hasNext();
        word = sc.nextLine().toLowerCase().trim();

        int index = word.indexOf(" ");
        String stationFrom = word.substring(0, index);
        System.out.println(stationFrom);
        String stationTo = word.substring(index + 1);

        List<Train> trainList = getTrainByRoute(trains, stationFrom, stationTo);
        Iterator<Train> listIterator = trainList.iterator();

        while (listIterator.hasNext()) {
            System.out.println(listIterator.next());
        }
    }

    public static Set<Train> addTrains() {

        Set<Train> trains = new HashSet<>();

        //********************************* TRAIN 1 *****************************************
        List<String> route1 = new LinkedList<>();
        route1.add("киев");
        route1.add("нежин");
        route1.add("конотоп");
        route1.add("путивль");
        route1.add("ворожба");
        route1.add("сумы");

        Train train1 = new Train();
        train1.setNumber("780");
        train1.setName("киев-сумы (столичный экспрес)");
        train1.setCars(3, 12);
        train1.setRoute(route1);

        trains.add(train1);

        //********************************* TRAIN 2 *****************************************
        List<String> route2 = new LinkedList<>();//
        route2.add("киев");
        route2.add("бровары");
        route2.add("нежин");
        route2.add("бахмач");
        route2.add("конотоп");
        route2.add("путивль");
        route2.add("ворожба");
        route2.add("сумы");
        route2.add("смородино");
        route2.add("богодухов");
        route2.add("харьков");

        Train train2 = new Train();
        train2.setNumber("776");
        train2.setName("киев-харьков");
        train2.setCars(3, 12);
        train2.setRoute(route2);

        trains.add(train2);

        //********************************* TRAIN 3 *****************************************
        List<String> route3 = new LinkedList<>();//
        route3.add("Киев");
        route3.add("Фастов");
        route3.add("Казатин");
        route3.add("Винница");
        route3.add("Тернополь");
        route3.add("Львов");

        Train train3 = new Train();
        train3.setNumber("049");
        train3.setName("Киев-Львов (Галичина)");
        train3.setCars(3, 12);
        train3.setRoute(route3);

        trains.add(train3);

        //********************************* TRAIN 4 *****************************************
        List<String> route4 = new LinkedList<>();//
        route4.add("Дарница");
        route4.add("Киев");
        route4.add("Коростень");
        route4.add("Подзамче");
        route4.add("Львов");

        Train train4 = new Train();
        train4.setNumber("741");
        train4.setName("Киев-Львов (Интерсити+)");
        train4.setCars(3, 12);
        train4.setRoute(route4);

        trains.add(train4);

        //********************************* TRAIN 5 *****************************************
        List<String> route5 = new LinkedList<>();//
        route5.add("Киев");
        route5.add("Знаменка");
        route5.add("Александрия");
        route5.add("Пятихатки");
        route5.add("Запорожье");

        Train train5 = new Train();
        train5.setNumber("736");
        train5.setName("Киев-Запорожье (Интерсити+)");
        train5.setCars(3, 12);
        train5.setRoute(route5);

        trains.add(train5);

        //**************************************************************************
        return trains;

    }

    public static void printListAllTrain(Set<Train> trains) {
        Iterator<Train> itr = trains.iterator();
        while (itr.hasNext()) {
            Train train = itr.next();
            System.out.println(train);
        }
    }

    public static void printListAllTrainAndAllPassenger(Set<Train> trains) {

        Iterator<Train> itr = trains.iterator();

        while (itr.hasNext()) {

            Train train = itr.next();
            System.out.println(train);

            Map<Integer, Car> cars = train.getCars();
            Iterator<Map.Entry<Integer, Car>> itrCars = cars.entrySet().iterator();

            while (itrCars.hasNext()) {

                Map.Entry<Integer, Car> entry = itrCars.next();
                Car car = entry.getValue();
                System.out.println(car);

                Iterator<Map.Entry<Integer, Passenger>> entryIterator1 = car.getSeat().entrySet().iterator();
                while (entryIterator1.hasNext()) {
                    Map.Entry<Integer, Passenger> entry1 = entryIterator1.next();
                    System.out.println(entry1.getValue());
                }
            }
            System.out.println("************************************************");
        }
    }

    public static List<Train> getTrainByRoute(Set<Train> trains, String cityFrom, String cityTo) {

        List<Train> trainList = new ArrayList<>();
        Iterator<Train> itr = trains.iterator();

        while (itr.hasNext()) {

            Train train = itr.next();

            List<String> currentRoute = train.getRoute();
            int indexCityFrom = currentRoute.indexOf(cityFrom);
            int indexCityTo = currentRoute.lastIndexOf(cityTo);

            if (indexCityFrom != -1 && indexCityTo != -1
                    && indexCityFrom < indexCityTo) {
                trainList.add(train);
            }
        }

        return trainList;
    }

    public static Train findTrainByNumber(Set<Train> trains, String numberTrain) {

        Iterator<Train> itr = trains.iterator();

        while (itr.hasNext()) {
            Train train = itr.next();
            if (train.getNumber().equals(numberTrain)) {
                return train;
            }
        }
        return null;
    }

    public static int getNumberCarOrSeat(Scanner sc) {

        String numericString;

        sc.hasNext();
        numericString = sc.nextLine().toLowerCase().trim();

        Pattern patternNumeric = Pattern.compile("[0-9]{0,3}", Pattern.UNICODE_CASE);
        Matcher matcherNumeric = patternNumeric.matcher(numericString);

        if (matcherNumeric.matches()) {
            return Integer.parseInt(numericString);
        } else return -1;
    }

    public static Train getTrainByNumber(Scanner sc, Set trains) {
        System.out.println("Введите номер поезда и нажмите 'Ввод': ");
        sc.hasNext();
        return findTrainByNumber(trains, sc.nextLine().toLowerCase().trim());
    }
}
