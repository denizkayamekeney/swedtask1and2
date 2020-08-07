package task1.dao;

import task1.dto.Vehicle;

public class VehicleHelper {
    static final String[] producers = {"PORSCHE", "BMW", "MERCEDES", "AUDI", "TOYOTA", "VOLVO",
            "SKODA", "VOLKSWAGEN", "FORD", "DACIA", "VAZ"};

    /**
     * It is creating a random vehicle.
     * */
    public static Vehicle createRandomVehicle(){
        return new Vehicle(
                randomPlateNumber(), //plate_number
                randomInterval(1990,2020), //
                randomInterval(5000,30000),//PURCHASE_PRISE,
                producers[randomInterval(0,9)], //producer
                randomInterval(0,100000), //milage
                randomInterval(0,70000)); // previous_indemnity
    }

    /**
     * It is creating a random plate number three English uppercase character
     * with three digit.
     * */
    static String randomPlateNumber(){
        StringBuilder plateNumber = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            char ch = (char) (Math.random() * 26 + 'A');
            plateNumber.append(ch);
        }
        for (int i = 0; i < 3; i++) {
            char digit1 = (char) (Math.random() * 10 + '0');
            plateNumber.append(digit1);
        }
        return plateNumber.toString();
    }

    /**
     * It returns a random value in a given interval
     * */

    static int randomInterval (int min, int max){
        return (int) (Math.random() * (max - min +1) + min);
    }
}
