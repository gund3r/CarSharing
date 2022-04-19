package carsharing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Company {

    private int id;
    private String name;

    public Company(int id, String name) {
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

    public static void createTableCompanies() {
        String sql = "CREATE TABLE IF NOT EXISTS company " +
                "(id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(255) NOT NULL UNIQUE" +
                ");";
        try {
            carsharing.DataBase.statement.executeUpdate(sql);
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("The table 'company' was created");
    }

    public static void createNewCompany(String companyName) {
        String sql = "INSERT INTO company (name) VALUES ('" + companyName + "');";
        try {
            carsharing.DataBase.statement.executeUpdate(sql);
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("The " + companyName + " was created");
        System.out.println();
    }

    public static List<Company> getCompanyList() {
        List<Company> result = new ArrayList<>();
        try (ResultSet listOfCompanies = carsharing.DataBase.statement.executeQuery("SELECT * FROM company;")) {
            if (listOfCompanies.isBeforeFirst()) {
                while (listOfCompanies.next()) {
                    int id = listOfCompanies.getInt("id");
                    String name = listOfCompanies.getString("name");
                    Company company = new Company(id, name);
                    result.add(company);
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

    public static void printCompanyList() {
        List<Company> companyList = getCompanyList();
        if (!companyList.isEmpty()) {
            System.out.println("Choose the company:");
            companyList.stream().forEach(company ->
                    System.out.println("" + (companyList.indexOf(company) + 1) + ". " + company.getName()));
            System.out.println("0. Back");
        }
    }

    public static String getCompanyNameByIndex(int index) {
        List<Company> companies = getCompanyList();
        return companies.get(index - 1).getName();
    }

    public static int getIdByCompanyName(String companyName) {
        String sql = "SELECT COMPANY.ID FROM company WHERE COMPANY.NAME = '" + companyName + "';";
        try (ResultSet listOfCompanies = carsharing.DataBase.statement.executeQuery(sql)) {
            if (listOfCompanies.isBeforeFirst()) {
                listOfCompanies.next();
                return listOfCompanies.getInt("id");
            }
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getCompanyNameById(int companyId) {
        String companyName = "";
        String sql = "SELECT company.NAME FROM company WHERE COMPANY.id = " + companyId + ";";
        try (ResultSet listOfCompanies = carsharing.DataBase.statement.executeQuery(sql)) {
            if (listOfCompanies.isBeforeFirst()) {
                listOfCompanies.next();
                companyName = listOfCompanies.getString("name");
                return companyName;
            }
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return companyName;
    }

    public static boolean isCompanyListEmpty() {
        try (ResultSet listOfCompanies = carsharing.DataBase.statement.executeQuery("SELECT * FROM company;")) {
            if (listOfCompanies.isBeforeFirst()) {
                return false;
            }
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Company getCompanyByCompanyName(String companyName) {
        String sql = "SELECT * FROM company WHERE name = '" + companyName + "';";
        try (ResultSet company = carsharing.DataBase.statement.executeQuery(sql)) {
            if (company.isBeforeFirst()) {
                company.next();
                return new Company(company.getInt("id"), company.getString("name"));
            }
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
