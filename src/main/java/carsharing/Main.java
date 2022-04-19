package carsharing;

import java.io.PrintStream;
import java.util.Scanner;

import static carsharing.Car.*;
import static carsharing.Company.*;
import static carsharing.Customer.*;
import static carsharing.DataBase.startDB;

public class Main {

    public static PrintStream out = System.out;
    public static final String FIRST_MENU = "1. Log in as a manager" + "\n" + "2. Log in as a customer" + "\n" +
                                            "3. Create a customer" + "\n" + "0. Exit";
    public static final String SECOND_MENU = "1. Company list" + "\n" + "2. Create a company" + "\n" + "0. Back";
    public static final String THIRD_MENU = "1. Car list" + "\n" + "2. Create a car" + "\n" + "0. Back";
    public static final String FOURTH_MENU = "1. Rent a car\n" + "2. Return a rented car\n" +
                                            "3. My rented car\n" + "0. Back";

    public static void mainMenu() {
        out.println(FIRST_MENU);
        Scanner scanner = new Scanner(System.in);
        boolean isWorks = true;
        while (isWorks) {
            switch(scanner.nextInt()) {
                case 0:
                    isWorks = false;
                    continue;
                case 1:
                    managerMenu();
                    break;
                case 2:
                    if (!isCustomersListEmpty()) {
                        printCustomersList();
                        int index = new Scanner(System.in).nextInt();
                        if (index != 0) {
                            customerMenu(getCustomerNameByIndex(index));
                        } else {
                            out.println(FIRST_MENU);
                            continue;
                        }
                    } else {
                        out.println("The customer list is empty!");
                        out.println(FIRST_MENU);
                        continue;
                    }
                    break;
                case 3:
                    out.println("Enter the customer name: ");
                    String customerName = new Scanner(System.in).nextLine();
                    createNewCustomer(customerName);
                    out.println(FIRST_MENU);
                    break;
                default:
                    break;
            }
        }
    }

    public static void managerMenu() {
        out.println(SECOND_MENU);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            switch(scanner.nextInt()) {
                case 0:
                    mainMenu();
                    break;
                case 1:
                    if (!isCompanyListEmpty()) {
                        printCompanyList();
                        int index = new Scanner(System.in).nextInt();
                        if (index != 0) {
                            companyMenu(getCompanyNameByIndex(index));
                        } else {
                            out.println(SECOND_MENU);
                            continue;
                        }
                    } else {
                        out.println("The company list is empty");
                    }
                    out.println(SECOND_MENU);
                    break;
                case 2:
                    out.println("Enter the company name: ");
                    String companyName = new Scanner(System.in).nextLine();
                    createNewCompany(companyName);
                    out.println(SECOND_MENU);
                    break;
                default:
                    break;
            }
        }
        mainMenu();
    }

    public static void companyMenu(String companyName) {
        Scanner scanner = new Scanner(System.in);
        out.println("'" + companyName + "' company");
        out.println(THIRD_MENU);
        while (scanner.hasNext()) {
            switch(scanner.nextInt()) {
                case 0:
                    managerMenu();
                    break;
                case 1:
                    printCompanyCars(companyName);
                    out.println(THIRD_MENU);
                    break;
                case 2:
                    out.println("Enter the car name: ");
                    String carName = new Scanner(System.in).nextLine();
                    createNewCar(carName, companyName);
                    out.println(THIRD_MENU);
                    break;
                default:
                    break;
            }
        }
        managerMenu();
    }

    public static void customerMenu(String customerName) {
        Scanner scanner = new Scanner(System.in);
        out.println(FOURTH_MENU);
        while (scanner.hasNext()) {
            switch(scanner.nextInt()) {
                case 0:
                    mainMenu();
                case 1:
                    takeCarForRent(customerName);
                    out.println(FOURTH_MENU);
                    continue;
                case 2:
                    returnRentedCar(customerName);
                    out.println(FOURTH_MENU);
                    continue;
                case 3:
                    showRentedCar(customerName);
                    out.println(FOURTH_MENU);
                    continue;
                default:
                    break;
            }
        }
        mainMenu();
    }

    public static void main(String[] args) {
        startDB();
        createTableCompanies();
        createTableCars();
        createTableCustomer();
        mainMenu();
    }

}