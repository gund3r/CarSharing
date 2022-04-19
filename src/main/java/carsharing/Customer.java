package carsharing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static carsharing.Car.*;
import static carsharing.Company.*;

public class Customer {

    private int id;
    private String name;
    private int rentedCarId;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRentedCarId() {
        return rentedCarId;
    }

    public void setRentedCarId(int rentedCarId) {
        this.rentedCarId = rentedCarId;
    }

    public static void createTableCustomer() {
        String sql = "CREATE TABLE IF NOT EXISTS customer " +
                "(id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(255) NOT NULL UNIQUE, " +
                "rented_car_id INT, " +
                "CONSTRAINT fk_car FOREIGN KEY (rented_car_id)" +
                "REFERENCES car(id)" +
                ");";
        try {
            DataBase.statement.executeUpdate(sql);
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("The table 'customer' was created");
    }

    public static void createNewCustomer(String customerName) {
        String sql = "INSERT INTO customer (name) VALUES ('" + customerName + "');";
        try {
            DataBase.statement.executeUpdate(sql);
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("The customer was added!");
        System.out.println();
    }

    public static List<Customer> getCustomersList() {
        String sql = "SELECT * FROM customer;";
        List<Customer> result = new ArrayList<>();
        try (ResultSet listOfCustomers = DataBase.statement.executeQuery(sql)) {
            if (listOfCustomers.isBeforeFirst()) {
                while (listOfCustomers.next()) {
                    int id = listOfCustomers.getInt("id");
                    String name = listOfCustomers.getString("name");
                    result.add(new Customer(id, name));
                }
                return result;
            }
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void printCustomersList() {
        List<Customer> customersList = getCustomersList();
        if (!customersList.isEmpty()) {
            System.out.println("carsharing.Customer list:");
            customersList.stream().forEach(customer ->
                    System.out.println("" + (customersList.indexOf(customer) + 1) + ". " + customer.getName()));
            System.out.println("0. Back");
        } else {
            System.out.println("The customer list is empty!");
        }
    }

    public static String getCustomerNameByIndex(int index) {
        return getCustomersList().get(index - 1).getName();
    }

    public static int getIdByCustomerName(String customerName) {
        String sql = "SELECT id FROM customer WHERE name = '" + customerName + "';";
        try (ResultSet id = DataBase.statement.executeQuery(sql)) {
            if (id.isBeforeFirst()) {
                id.next();
                return id.getInt("id");
            }
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getCustomerNameById(int customerId) {
        String customerName = "";
        String sql = "SELECT name FROM customer WHERE id = " + customerId + ";";
        try (ResultSet name = DataBase.statement.executeQuery(sql)) {
            if (name.isBeforeFirst()) {
                name.next();
                return name.getString("name");
            }
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return customerName;
    }

    public static boolean isCustomersListEmpty() {
        try (ResultSet customersList = DataBase.statement.executeQuery("SELECT * FROM customer;")) {
            if (customersList.isBeforeFirst()) {
                return false;
            }
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Customer getCustomerByName(String customerName) {
        String sql = "SELECT * FROM customer WHERE name = '" + customerName + "';";
        try (ResultSet customerSet = DataBase.statement.executeQuery(sql)) {
            if (customerSet.isBeforeFirst()) {
                customerSet.next();
                int id = customerSet.getInt("id");
                String name = customerSet.getString("name");
                int rentedCarId = customerSet.getInt("rentedCarId");
                return new Customer(id, name);
            }
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setRentedCarIdByCustomerName(Integer newValue, String customerName) {
        String sql = "UPDATE customer SET rented_car_id = " + newValue + " WHERE name = '" + customerName + "';";
        try {
            DataBase.statement.executeUpdate(sql);
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static int getRentedCarIdByCustomerName(String customerName) {
        String sql = "SELECT rented_car_id FROM customer WHERE name = '" + customerName + "';";
        try (ResultSet customerSet = DataBase.statement.executeQuery(sql)) {
            customerSet.next();
            int result = customerSet.getInt("rented_car_id");
            if (result != 0) {
                return customerSet.getInt("rented_car_id");
            }
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void showRentedCar(String customerName) {
        if (!isCustomerHaveRentedCar(customerName)) {
            System.out.println("You didn't rent a car!");
        } else {
            String carName = getCarNameById(getRentedCarIdByCustomerName(customerName));
            Car car = getCarByCarName(carName);
            String companyName = getCompanyNameById(car.getCompanyId());
            System.out.println("Your rented car:");
            System.out.println(carName);
            System.out.println("carsharing.Company:");
            System.out.println(companyName);
            System.out.println();
        }
    }

    public static void returnRentedCar(String customerName) {
        if (!isCustomerHaveRentedCar(customerName)) {
            System.out.println("You didn't rent a car!");
        } else {
            String carName = getCarNameById(getRentedCarIdByCustomerName(customerName));
            setIsRentedByCarName(carName, false);
            setRentedCarIdByCustomerName(null, customerName);
            System.out.println("You've returned a rented car!");
            System.out.println();
        }
    }

    public static void takeCarForRent(String customerName) {
        if (isCustomerHaveRentedCar(customerName)) {
            System.out.println("You've already rented a car!");
            System.out.println();
        } else {
            Scanner scanner = new Scanner(System.in);
            printCompanyList();
            int companyIndex = scanner.nextInt();
            if (companyIndex != 0) {
                String companyName = getCompanyNameByIndex(companyIndex);
                printCompanyCarsForCustomer(companyName);
                int carIndex = scanner.nextInt();
                if (carIndex != 0) {
                    String carName = getCarNameByIndex(companyName, carIndex);
                    Customer.setRentedCarIdByCustomerName(getIdByCarName(carName), customerName);
                    setIsRentedByCarName(carName, true);
                    System.out.println("You rented '" + carName + "'");
                    System.out.println();
                }
            }
        }
    }

    public static boolean isCustomerHaveRentedCar(String customerName) {
        String sql = "SELECT rented_car_id FROM customer WHERE name = '" + customerName + "';";
        try (ResultSet customerSet = DataBase.statement.executeQuery(sql)) {
            customerSet.next();
            if (customerSet.getInt("rented_car_id") == 0) {
                return false;
            }
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
