package carsharing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Car {

    private int id;
    private String name;
    private int companyId;
    private boolean isRented;

    public Car(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Car(int id, String name, int companyId, boolean isRented) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.isRented = isRented;
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public static void createTableCars() {
        String sql = "CREATE TABLE IF NOT EXISTS car " +
                "(id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(255) NOT NULL UNIQUE, " +
                "is_rented BOOLEAN, " +
                "company_id INT NOT NULL, " +
                "CONSTRAINT fk_company FOREIGN KEY (company_id) " +
                "REFERENCES company(id)" +
                ");";
        try {
            carsharing.DataBase.statement.executeUpdate(sql);
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("The table 'car' was created");
    }

    public static void createNewCar(String carName, String companyName) {
        int companyId = Company.getIdByCompanyName(companyName);
        String sql =
                "INSERT INTO " +
                "car (name, company_id, is_rented) " +
                "VALUES" +
                " ('" + carName + "', " + companyId + ", false);";
        try {
            carsharing.DataBase.statement.executeUpdate(sql);
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("The " + carName + " was added");
        System.out.println();
    }

    public static List<Car> getCompanyCars(String companyName) {
        List<Car> cars = new ArrayList<>();
        int companyId = Company.getIdByCompanyName(companyName);
        String sql = "SELECT * FROM car WHERE company_id = " + companyId + ";";
        try (ResultSet listOfCars = carsharing.DataBase.statement.executeQuery(sql)) {
            if (listOfCars.isBeforeFirst()) {
                while (listOfCars.next()) {
                    int id = listOfCars.getInt("id");
                    String name = listOfCars.getString("name");
                    boolean isRented = listOfCars.getBoolean("is_rented");
                    cars.add(new Car(id, name, companyId, isRented));
                }
            }
            return cars;
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return cars;
    }

    public static String getCarNameByIndex(String companyName, int index) {
        return getCompanyCars(companyName).get(index - 1).getName();
    }

    public static void printCompanyCars(String companyName) {
        List<Car> cars = getCompanyCars(companyName);
        if (!cars.isEmpty()) {
            System.out.println();
            System.out.println("carsharing.Car list:");
            cars.stream().forEach(car ->
                    System.out.println("" + (cars.indexOf(car) + 1) + ". " + car.getName()));
            System.out.println();
        } else {
            System.out.println("The car list is empty!");
            System.out.println();
        }
    }

    public static void printCompanyCarsForCustomer(String companyName) {
        List<Car> cars = getCompanyCars(companyName);
        if (!cars.isEmpty()) {
            System.out.println();
            System.out.println("Choose a car:");
            cars.stream()
                    .filter(car -> !car.isRented())
                    .forEach(car ->System.out.println(""
                            + (cars.indexOf(car) + 1)
                            + ". " + car.getName()));
            System.out.println("0. Back");
            System.out.println();
        } else {
            System.out.println("The car list is empty!");
            System.out.println();
        }
    }

    public static String getCarNameById(int carId) {
        String carName = "";
        String sql = "SELECT name FROM car WHERE id = " + carId + ";";
        try (ResultSet name = carsharing.DataBase.statement.executeQuery(sql)) {
            if (name.isBeforeFirst()) {
                while (name.next()) {
                    carName = name.getString("name");
                }
            }
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return carName;
    }

    public static int getIdByCarName(String carName) {
        int carId = 0;
        String sql = "SELECT id FROM car WHERE name = '" + carName + "';";
        try (ResultSet name = carsharing.DataBase.statement.executeQuery(sql)) {
            if (name.isBeforeFirst()) {
                name.next();
                carId = name.getInt("id");
            }
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return carId;
    }

    public static Car getCarByCarName(String carName) {
        String sql = "SELECT * FROM car WHERE name = '" + carName + "';";
        try (ResultSet car = carsharing.DataBase.statement.executeQuery(sql)) {
            if (car.isBeforeFirst()) {
                while (car.next()) {
                    int id = car.getInt("id");
                    String name = car.getString("name");
                    int companyId = car.getInt("company_id");
                    boolean isRented = car.getBoolean("is_rented");
                    return new Car(id, name, companyId, isRented);
                }
            }
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setIsRentedByCarName(String carName, boolean newValue) {
        String sql = "UPDATE CAR SET is_rented = " + newValue + " WHERE name = '" + carName + "';";
        try {
            carsharing.DataBase.statement.executeUpdate(sql);
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isRentedByCarName(String carName) {
        String sql = "SELECT is_rented FROM car WHERE name = '" + carName + "';";
        try (ResultSet isRented = carsharing.DataBase.statement.executeQuery(sql)) {
            if (isRented.isBeforeFirst()) {
                isRented.next();
                return isRented.getBoolean("is_rented");
            }
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
