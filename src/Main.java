import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/bank?autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws SQLException{
        new Main().run();
    }
    private void run() throws SQLException{
        Database database = new Database(URL,USERNAME,PASSWORD);
        database.setConnection();
        menu(database);
        database.close();
    }
    private void menu(Database database) throws SQLException{
        System.out.println("1.Add");
        System.out.println("2.Employee and customer physical person");
        System.out.println("3.Employee and customer entity person");
        System.out.println("4.Physical person credit");
        System.out.println("5.Entity person credit");
        System.out.println("6.Physical person");
        System.out.println("7.Entity person");
        int k;
        do {
            k = in.nextInt();
            switch (k){
                case 1:
                    System.out.println("Add");
                    Menu(database);
                    menu(database);
                    break;
                case 2:
                    System.out.println("2.Employee and customer physical person");
                    ResultSet resultSet = database.employeeAndCustomerPhysicalPerson();
                    System.out.printf("%-20s%-40s%-40s%n", "Department", "Employee", "Customer");
                    while (resultSet.next()){
                        System.out.printf("%-20s%-40s%-40s%n", resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                    }
                    menu(database);
                    break;
                case 3:
                    System.out.println("3.Employee and customer entity person");
                    resultSet = database.employeeAndCustomerEntityPerson();
                    System.out.printf("%-20s%-40s%-40s%n", "Department", "Employee", "Customer");
                    while (resultSet.next()){
                        System.out.printf("%-20s%-40s%-40s%n", resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                    }
                    menu(database);
                    break;
                case 4:
                    System.out.println("4.Physical person credit");
                    resultSet = database.physicalPersonCredit();
                    System.out.printf("%-30s%-20s%-15s%-10s%n", "Customer", "Kind credit", "Term", "Sum");
                    while (resultSet.next()){
                        System.out.printf("%-30s%-20s%-15s%-10s%n", resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                                resultSet.getString(4));
                    }
                    menu(database);
                    break;
                case 5:
                    System.out.println("5.Entity person credit");
                    resultSet = database.entityPersonCredit();
                    System.out.printf("%-30s%-20s%-15s%-10s%n", "Customer", "Kind credit", "Term", "Sum");
                    while (resultSet.next()){
                        System.out.printf("%-30s%-20s%-15s%-10s%n", resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                                resultSet.getString(4));
                    }
                    menu(database);
                    break;
                case 6:
                    System.out.println("6.Physical person");
                    resultSet = database.physicalPerson();
                    System.out.printf("%-40s%-20s%-15s%-10s%-10s%n", "Full name", "Address", "Balance", "Passport", "Date of birth");
                    while (resultSet.next()){
                        System.out.printf("%-40s%-20s%-15s%-10s%-10s%n", resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                                resultSet.getString(4), resultSet.getString(5));
                    }
                    menu(database);
                    break;
                case 7:
                    System.out.println("7.Entity person");
                    resultSet = database.entityPerson();
                    System.out.printf("%-40s%-20s%-15s%-10s%-10s%n", "Company name", "Chief full name", "Address", "Balance", "Number reference");
                    while (resultSet.next()){
                        while (resultSet.next()){
                            System.out.printf("%-40s%-20s%-15s%-10s%-10s%n", resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                                    resultSet.getString(4), resultSet.getString(5));
                        }
                    }
                    menu(database);
                    break;
            }
        }while (k != 0);
    }
    private void Menu(Database database) throws SQLException{
        System.out.println("1.Add physical person");
        System.out.println("2.Add entity person");
        System.out.println("3.Add credit");
        int k;
        do {
            k = in.nextInt();
            switch (k){
                case 1:
                    System.out.println("1.Add physical person");
                    addPhy(database);
                    System.out.println("OK");
                    menu(database);
                    break;
                case 2:
                    System.out.println("2.Add entity person");
                    addEn(database);
                    System.out.println("OK");
                    menu(database);
                    break;
                case 3:
                    System.out.println("3.Add credit");
                    addCred(database);
                    System.out.println("OK");
                    menu(database);
                    break;
            }
        }while (k != 0);
    }
    private void addPhy(Database database)throws SQLException{
        System.out.print("");
        String m = in.nextLine();
        System.out.print("Full name:");
        String fname = in.nextLine();
        System.out.print("Adress:");
        String adress = in.nextLine();
        System.out.print("balance:");
        String balance = in.nextLine();
        System.out.print("Passport:");
        String pas = in.nextLine();
        System.out.print("Date of Birth (dd.mm.yyyy):");
        String db = in.nextLine();
        Physical physical = new Physical(fname,adress,balance,pas,db);
        database.addPhyDb(physical);
    }
    private void addEn(Database database)throws SQLException{
        System.out.print("");
        String m = in.nextLine();
        System.out.print("Company name:");
        String fname = in.nextLine();
        System.out.print("Full name chief:");
        String chief = in.nextLine();
        System.out.print("Adress:");
        String adress = in.nextLine();
        System.out.print("Balance:");
        String balance = in.nextLine();
        System.out.print("Number reference:");
        int db = in.nextInt();
        Entity entity = new Entity(fname,chief,adress,balance,db);
        database.addenDb(entity);
    }
    private void addCred(Database database)throws SQLException{
        System.out.print("Customer number:");
        int num = in.nextInt();
        System.out.print("");
        String m = in.nextLine();
        System.out.print("Name credit:");
        String nam = in.nextLine();
        System.out.print("Loan purpose:");
        String loan = in.nextLine();
        System.out.print("Term:");
        String term = in.nextLine();
        System.out.print("First pay:");
        String fpay = in.nextLine();
        System.out.print("Sum:");
        int sum = in.nextInt();
        Credit credit = new Credit(num,nam,loan,term,fpay,sum);
        database.addCrdb(credit);
    }
}
