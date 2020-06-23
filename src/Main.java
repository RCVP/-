import java.sql.*;
import java.util.Scanner;
public class Main {
    private static final String SQL_Cus = "CREATE TABLE cust " +
            "(id_cust int PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
            "id_employee int NOT NULL);";
    private static final String SQL_Cred = "CREATE TABLE credit " +
            "(id_credit int PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
            "id_cust int NOT NULL, " +
            "name VARCHAR(15) NOT NULL, " +
            "loanpurpose VARCHAR(50) NOT NULL, " +
            "term VARCHAR(10) NOT NULL, " +
            "firstpay VARCHAR(20) NOT NULL, " +
            "sum INT NULL);";
    private static final String SQL_Phy = "CREATE TABLE physicalperson " +
            "(id_cust INT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
            "fullname VARCHAR(80) NOT NULL, " +
            "adress VARCHAR(50) NOT NULL, " +
            "balance VARCHAR(10) NOT NULL, " +
            "pasport VARCHAR(10) NOT NULL, " +
            "dateofbirth VARCHAR(10) NOT NULL);";
    private static final String SQL_Ent = "CREATE TABLE entity" +
            "(id_cust INT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
            "companyname VARCHAR(40) NOT NULL," +
            "chief VARCHAR(80) NOT NULL," +
            "adress VARCHAR(50) NOT NULL," +
            "balance VARCHAR(10) NOT NULL, " +
            "numberreference int);";
    private static final String SQL_Emp = "CREATE TABLE employee" +
            "(id_employee INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
            "fullname VARCHAR(80) NOT NULL," +
            "department VARCHAR(30) NOT NULL);";
    private static final String SQL_Fam = "CREATE TABLE family" +
            "(id_person INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
            "id_cust INT NOT NULL," +
            "fullname VARCHAR(80) NOT NULL," +
            "statut_person INT NOT NULL);";
    private static final String SQL_Kind = "CREATE TABLE kindcredit" +
            "(name VARCHAR(15) PRIMARY KEY NOT NULL," +
            "percent VARCHAR(5) NOT NULL," +
            "interestondebt VARCHAR(5) NOT NULL);";
    private static final String SQL_Paid = "CREATE TABLE paidof" +
            "(number_account INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
            "number_sch VARCHAR(10) NOT NULL," +
            "sum_paid int," +
            "date VARCHAR(10) NOT NULL);";
    private static final String SQL_sched = "CREATE TABLE schedule" +
            "(number_sch VARCHAR (10) NOT NULL PRIMARY KEY," +
            "id_credit int," +
            "sum int," +
            "month_pay VARCHAR (10) NOT NULL);";
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement pt;
    private Statement st;
    private Scanner in = new Scanner(System.in);

    private static final String URL = "jdbc:mysql://localhost:3306/bank?autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) throws SQLException{
        new Main().run();
    }
    private void run() throws SQLException{
        setConnection();
        String[][] cust = new String[100][];
        String[][] physical = new String[100][];
        String[][] entity = new String[100][];
        String[][] employee = new String[100][];
        String[][] credit = new String[100][];
        String[][] kindcred = new String[100][];
        String[][] family = new String[100][];
        String[][] sched = new String[100][];
        String[][] paidof = new String[100][];
        putInToEnt(entity);
        putInToEm(employee);
        putInToPhy(physical);
        putInToCust(cust);
        putInToKind(kindcred);
        putInToCred(credit);
        putInToFam(family);
        putInToSch(sched);
        putInToPaid(paidof);
        menu();
        st.close();
        pt.close();
        connection.close();
    }
    private void setConnection() throws SQLException{
        if (open()) {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            st = connection.createStatement();
            DatabaseMetaData metadata = connection.getMetaData();
            resultSet = metadata.getTables(null, null, "credit", null);
            if (resultSet.next()) {
            } else {
                st.executeUpdate(SQL_Cus);
                st.executeUpdate(SQL_Cred);
                st.executeUpdate(SQL_Phy);
                st.executeUpdate(SQL_Ent);
                st.executeUpdate(SQL_Emp);
                st.executeUpdate(SQL_Fam);
                st.executeUpdate(SQL_Kind);
                st.executeUpdate(SQL_Paid);
                st.executeUpdate(SQL_sched);
                st.executeUpdate("ALTER TABLE cust ADD FOREIGN KEY (id_employee) " +
                        "REFERENCES employee (id_employee);");
                st.executeUpdate("ALTER TABLE credit ADD FOREIGN KEY (id_cust) " +
                        "REFERENCES cust (id_cust);");
                st.executeUpdate("ALTER TABLE credit ADD FOREIGN KEY (name) " +
                        "REFERENCES kindcredit (name);");
                st.executeUpdate("ALTER TABLE schedule ADD FOREIGN KEY (id_credit) " +
                        "REFERENCES credit (id_credit);");
                st.executeUpdate("ALTER TABLE paidof ADD FOREIGN KEY (number_sch) " +
                        "REFERENCES schedule (number_sch);");
                st.executeUpdate("ALTER TABLE family ADD FOREIGN KEY (id_cust) " +
                        "REFERENCES physicalperson (id_cust);");
            }
        }
    }
    private void menu() throws SQLException{
        System.out.println("1.Add");
        System.out.println("2.Employee and customer physical person");
        System.out.println("3.Employee and customer entity person");
        System.out.println("4.Physical person credit");
        System.out.println("5.Entity person credit");
        System.out.println("6.Physical person");
        System.out.println("7.Entity person");
        System.out.println("8.Monthly payments");
        int k;
        do {
            k = in.nextInt();
            switch (k){
                case 1:
                    System.out.println("Add");
                    Menu();
                    menu();
                    break;
                case 2:
                    System.out.println("2.Employee and customer physical person");
                    resultSet = st.executeQuery("SELECT emp.department, emp.fullname, phy.fullname FROM employee emp " +
                            "CROSS JOIN cust cust ON cust.id_employee = emp.id_employee " +
                            "CROSS JOIN physicalperson phy ON cust.id_cust = phy.id_cust");
                    System.out.printf("%-20s%-40s%-40s%n", "Department", "Employee", "Customer");
                    while (resultSet.next()){
                            System.out.printf("%-20s%-40s%-40s%n", resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));
                    }
                    menu();
                    break;
                case 3:
                    System.out.println("3.Employee and customer entity person");
                    resultSet = st.executeQuery("SELECT emp.department, emp.fullname, ent.companyname " +
                            "FROM employee emp CROSS JOIN cust cust ON cust.id_employee = emp.id_employee " +
                            "CROSS JOIN entity ent ON cust.id_cust = ent.id_cust");
                    System.out.printf("%-20s%-40s%-40s%n", "Department", "Employee", "Customer");
                    while (resultSet.next()){
                            System.out.printf("%-20s%-40s%-40s%n", resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));
                    }
                    menu();
                    break;
                case 4:
                    System.out.println("4.Physical person credit");
                    resultSet = st.executeQuery("SELECT phy.fullname, cred.name, cred.term, cred.sum " +
                            "FROM credit cred CROSS JOIN physicalperson phy ON " +
                            "cred.id_cust = phy.id_cust");
                    System.out.printf("%-30s%-20s%-15s%-10s%n", "Customer", "Kind credit", "Term", "Sum");
                    while (resultSet.next()){
                            System.out.printf("%-30s%-20s%-15s%-10s%n", resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
                                    resultSet.getString(4));
                    }
                    menu();
                    break;
                case 5:
                    System.out.println("5.Entity person credit");
                    resultSet = st.executeQuery("SELECT ent.companyname, cred.name, cred.term, cred.sum " +
                            "FROM credit cred CROSS JOIN entity ent ON " +
                            "cred.id_cust = ent.id_cust");
                    System.out.printf("%-30s%-20s%-15s%-10s%n", "Customer", "Kind credit", "Term", "Sum");
                    while (resultSet.next()){
                            System.out.printf("%-30s%-20s%-15s%-10s%n", resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
                                    resultSet.getString(4));
                    }
                    menu();
                    break;
                case 6:
                    System.out.println("6.Physical person");
                    resultSet = st.executeQuery("SELECT phy.fullname, phy.adress, phy.balance, phy.pasport, phy.dateofbirth " +
                            "FROM physicalperson phy");
                    System.out.printf("%-40s%-20s%-15s%-10s%-10s%n", "Full name", "Address", "Balance", "Passport", "Date of birth");
                    while (resultSet.next()){
                            System.out.printf("%-40s%-20s%-15s%-10s%-10s%n", resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
                                    resultSet.getString(4),resultSet.getString(5));
                    }
                    menu();
                    break;
                case 7:
                    System.out.println("7.Entity person");
                    resultSet = st.executeQuery("SELECT ent.companyname, ent.chief, ent.adress, ent.balance, ent.numberreference " +
                            "FROM entity ent");
                    System.out.printf("%-40s%-20s%-15s%-10s%-10s%n", "Company name", "Chief full name", "Address", "Balance", "Number reference");
                    while (resultSet.next()){
                        while (resultSet.next()){
                            System.out.printf("%-40s%-20s%-15s%-10s%-10s%n", resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
                                    resultSet.getString(4),resultSet.getString(5));
                        }
                    }
                    menu();
                    break;
                case 8:
                    System.out.println("8.Monthly payments: April");
                    resultSet = st.executeQuery("SELECT phy.fullname, paid.sum_paid," +
                            "FROM physicalperson phy CROSS JOIN credit cred ON cred.id_cust = phy.id_cust" +
                            " CROSS JOIN schedule sch ON sch.id_credit = cred.id_credit" +
                            " CROSS JOIN paidof paid ON paid.number_sch = sch.number_sch AND paid.date = 'april'");
                    System.out.printf("%-40s%-40s%n", "Customer", "Paid");
                    while (resultSet.next()){
                        System.out.printf("%-40s%-40s%n", resultSet.getString(1), resultSet.getString(2));
                    }
                    menu();
                    break;
            }
        }while (k != 0);
    }
    private void Menu() throws SQLException{
        System.out.println("1.Add physical person");
        System.out.println("2.Add entity person");
        System.out.println("3.Add credit");
        int k;
        do {
            k = in.nextInt();
            switch (k){
                case 1:
                    System.out.println("1.Add physical person");
                    addPhy();
                    System.out.println("OK");
                    menu();
                    break;
                case 2:
                    System.out.println("2.Add entity person");
                    addEn();
                    System.out.println("OK");
                    menu();
                    break;
                case 3:
                    System.out.println("3.Add credit");
                    addCred();
                    System.out.println("OK");
                    menu();
                    break;
            }
        }while (k != 0);
    }
    private void putInToCust(String[][] cust)throws SQLException{
        pt = connection.prepareStatement("INSERT INTO cust VALUE(?,?)");
        for (String[] p : cust){
            if(p!=null){
                pt.setInt(1,Integer.parseInt(p[0]));
                pt.setInt(2,Integer.parseInt(p[1]));
                pt.execute();
            }
        }
    }
    private void addPhy()throws SQLException{
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
        addPhyDb(physical);
    }
    private void addPhyDb(Physical physical)throws SQLException{
        pt = connection.prepareStatement("INSERT INTO physicalperson VALUE (?,?,?,?,?,?)");
        pt.setInt(1,physical.getId_cust());
        pt.setString(2,physical.getFullname());
        pt.setString(3,physical.getAdress());
        pt.setString(4,physical.getBalance());
        pt.setString(5,physical.getPassport());
        pt.setString(6,physical.getDateofbirth());
        pt.execute();
    }
    private void putInToPhy(String[][] phy)throws SQLException{
        pt = connection.prepareStatement("INSERT INTO physicalperson VALUE(?,?,?,?,?,?)");
        for (String[] p : phy){
            if(p!=null){
                pt.setInt(1,Integer.parseInt(p[0]));
                pt.setString(2,p[1]);
                pt.setString(3,p[2]);
                pt.setString(4,p[3]);
                pt.setString(5,p[4]);
                pt.setString(6,p[5]);
                pt.execute();
            }
        }
    }
    private void addEn()throws SQLException{
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
        addenDb(entity);
    }
    private void addenDb(Entity entity)throws SQLException{
        pt = connection.prepareStatement("INSERT INTO entity VALUE (?,?,?,?,?,?)");
        pt.setInt(1,entity.getId_cust());
        pt.setString(2,entity.getCompanyname());
        pt.setString(3,entity.getChief());
        pt.setString(4,entity.getAdress());
        pt.setString(5,entity.getBalance());
        pt.setInt(6,entity.getNumberreference());
        pt.execute();
    }
    private void putInToEnt(String[][] ent)throws SQLException{
        pt = connection.prepareStatement("INSERT INTO entity VALUE(?,?,?,?,?,?)");
        for (String[] p : ent){
            if(p!=null){
                pt.setInt(1,Integer.parseInt(p[0]));
                pt.setString(2,p[1]);
                pt.setString(3,p[2]);
                pt.setString(4,p[3]);
                pt.setString(5,p[4]);
                pt.setInt(6,Integer.parseInt(p[5]));
                pt.execute();
            }
        }
    }
    private void addCred()throws SQLException{
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
        addCrdb(credit);
    }
    private void addCrdb(Credit credit)throws SQLException{
        pt = connection.prepareStatement("INSERT INTO credit VALUE (?,?,?,?,?,?,?)");
        pt.setInt(1,credit.getId_credit());
        pt.setInt(2,credit.getId_cust());
        pt.setString(3,credit.getName());
        pt.setString(4,credit.getLoanpurpose());
        pt.setString(5,credit.getTerm());
        pt.setString(6,credit.getFirstpay());
        pt.setInt(7,credit.getSum());
        pt.execute();
    }
    private void putInToCred(String[][] cred)throws SQLException{
        pt = connection.prepareStatement("INSERT INTO credit VALUE(?,?,?,?,?,?,?)");
        for (String[] p : cred){
            if(p!=null){
                pt.setInt(1,Integer.parseInt(p[0]));
                pt.setInt(2,Integer.parseInt(p[1]));
                pt.setString(3,p[2]);
                pt.setString(4,p[3]);
                pt.setString(5,p[4]);
                pt.setString(6,p[5]);
                pt.setInt(7,Integer.parseInt(p[6]));
                pt.execute();
            }
        }
    }
    private void putInToEm(String[][] empl)throws SQLException{
        pt = connection.prepareStatement("INSERT INTO employee VALUE(?,?,?)");
        for (String[] p : empl){
            if(p!=null){
                pt.setInt(1,Integer.parseInt(p[0]));
                pt.setString(2,p[1]);
                pt.setString(3,p[2]);
                pt.execute();
            }
        }
    }
    private void putInToFam(String[][] fam)throws SQLException{
        pt = connection.prepareStatement("INSERT INTO family VALUE(?,?,?,?)");
        for (String[] p : fam){
            if(p!=null){
                pt.setInt(1,Integer.parseInt(p[0]));
                pt.setInt(2,Integer.parseInt(p[1]));
                pt.setString(3,p[2]);
                pt.setString(4,p[3]);
                pt.execute();
            }
        }
    }
    private void putInToKind(String[][] kind)throws SQLException{
        pt = connection.prepareStatement("INSERT INTO kindcredit VALUE(?,?,?)");
        for (String[] p : kind){
            if(p!=null){
                pt.setString(1,p[0]);
                pt.setString(2,p[1]);
                pt.setString(3,p[2]);
                pt.execute();
            }
        }
    }
    private void putInToSch(String[][] sch)throws SQLException{
        pt = connection.prepareStatement("INSERT INTO schedule VALUE(?,?,?,?)");
        for (String[] p : sch){
            if(p!=null){
                pt.setString(1,p[0]);
                pt.setInt(2,Integer.parseInt(p[1]));
                pt.setInt(3,Integer.parseInt(p[2]));
                pt.setString(4,p[3]);
                pt.execute();
            }
        }
    }
    private void putInToPaid(String[][] paid)throws SQLException{
        pt = connection.prepareStatement("INSERT INTO schedule VALUE(?,?,?,?)");
        for (String[] p : paid){
            if(p!=null){
                pt.setInt(1,Integer.parseInt(p[0]));
                pt.setString(2,p[1]);
                pt.setInt(3,Integer.parseInt(p[2]));
                pt.setString(4,p[3]);
                pt.execute();
            }
        }
    }
    private boolean open(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            System.out.println("Connection succesfull!");
            return true;
        }
        catch(Exception ex){
            System.out.println("Connection failed...");
            System.out.println(ex);
            return false;
        }
    }
}
