import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carID;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carID,String brand,String model,double basePricePerDay){
        this.carID = carID;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarID(){
        return  carID;
    }
    public String getBrand(){
        return brand;
    }
    public String getModel(){
        return model;
    }
    public boolean isAvailable(){
        return isAvailable;
    }
    public double calculatePrice(int rentalDays){
        return basePricePerDay*rentalDays;
    }
    public void rent(){
        isAvailable = false;
    }
    public void returnCar(){
        isAvailable = true;
    }
}

class Customer{

}

class Rental{

}

class CarRentalSystem{

}