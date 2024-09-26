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
    private String customerID;
    private String name;

    public Customer( String customerID, String name){
        this.customerID = customerID;
        this.name = name;
    }

    public String getCustomerID(){
        return customerID;
    }
    public String getName(){
        return name;
    }
}

class Rental{
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days){
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar(){
        return car;
    }
    public Customer getCustomer(){
        return customer;
    }
    public int getDays(){
        return days;
    }

}

class CarRentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car){
        cars.add(car);
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer,int days){
        if(car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car,customer,days));
        }else{
            System.out.println("Car isn't available for rent\n");
        }
    }

    public void returnCar(Car car){
        Rental rentalToRemove = null;

        for( Rental rental :  rentals ){
            if(rental.getCar() == car ){
                rentalToRemove = rental;
                break;
            }
        }

        if(rentalToRemove != null){
            rentals.remove(rentalToRemove);
            car.returnCar();
            System.out.println("Car returned successfully\n");
        } else{
            System.out.println("Car wasn't rented");
        }
    }

    public void menu(){
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("### Car Rental System ####");
            System.out.println("1. Rent a car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.println("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if(choice == 1 ){
                System.out.println("\n ### Rent a Car ###\n");
                System.out.println("Enter your name: ");
                String customerName = scanner.nextLine();

                System.out.println("\n Available cars: \n");

                for(Car car : cars){
                    if(car.isAvailable()){
                        System.out.println(car.getCarID() + "--"+ car.getBrand() + "--"+ car.getModel());
                    }
                }

                System.out.println("\n Enter the car id you want to rent: ");
                String carId = scanner.nextLine();

                System.out.println("\nEnter the number of days you want to rent: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine();

                Customer newCustomer = new Customer("CUS"+(customers.size()+1),customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for(Car car : cars){
                    if(car.getCarID().equals(carId) && car.isAvailable()){
                        selectedCar = car;
                        break;
                    }
                }

                if(selectedCar != null){
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("Rental info:\n");
                    System.out.println("Customer ID: "+newCustomer.getCustomerID());
                    System.out.println("Customer name: "+newCustomer.getName());
                    System.out.println("Car: " +selectedCar.getBrand() + "--"+selectedCar.getModel() );
                    System.out.println("Rental Days "+ rentalDays  );
                    System.out.printf("Total Price: $%.2f\n",totalPrice  );

                    System.out.println("\nConfirm Rental: (Y/N) ");
                    String confirm = scanner.nextLine();
                    if(confirm.equalsIgnoreCase("Y")){
                        rentCar(selectedCar,newCustomer,rentalDays);
                        System.out.println("\n Car rented successfully \n");
                    }else{
                        System.out.println("\nRental cancelled \n");
                    }
                }else{
                    System.out.println("\n Invalid car selection or car not available for rent\n");
                }

            } else if (choice == 2) {
                System.out.println("\n ## Return a Car ==\n");
                System.out.println("Enter the car id you want to return :");
                String carId = scanner.nextLine();

                Car carToReturn = null;
                for(Car car : cars){
                    if(car.getCarID().equals(carId) && !car.isAvailable()){
                        carToReturn = car;
                        break;
                    }
                }

                if(carToReturn != null){
                    Customer customer = null;
                    for (Rental rental : rentals){
                        if(rental.getCar() == carToReturn){
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if(customer != null){
                        returnCar(carToReturn);
                        System.out.println("Car returned Successfully by "+ customer.getName());
                    }else {
                        System.out.println("Car was not returned or rental information is missing");

                    }
                }else{
                    System.out.println("Invalid Car id\n");
                }
            } else if (choice == 3) {
                break;
            }else{
                System.out.println("Invalid choice. Please enter a valid option. ");
            }
        }

        System.out.println("\n Thank you for using the car rental system \n");
    }
}

public class Main {
    public static void main(String[] args) {
        CarRentalSystem carRentalSystem = new CarRentalSystem();
        Car car1 = new Car("C001","Toyota","Prius",60);
        Car car2 = new Car("C002","Honda","Civic",70);
        carRentalSystem.addCar(car1);
        carRentalSystem.addCar(car2);
        carRentalSystem.menu();
    }
}