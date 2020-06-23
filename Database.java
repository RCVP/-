import java.sql.*;
import java.util.Scanner;

class Database {
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
    private String URL;
    private String USERNAME;
    private String PASSWORD;

    Database(String URL, String USERNAME, String PASSWORD) {
        this.URL = URL;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
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
    void setConnection() throws SQLException {
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
    void close() throws SQLException {
        st.close();
        pt.close();
        connection.close();
    }

    ResultSet employeeAndCustomerPhysicalPerson() throws SQLException{
        resultSet = st.executeQuery("SELECT emp.department, emp.fullname, phy.fullname FROM employee emp " +
                "CROSS JOIN cust cust ON cust.id_employee = emp.id_employee " +
                "CROSS JOIN physicalperson phy ON cust.id_cust = phy.id_cust");
        return resultSet;
    }
    ResultSet employeeAndCustomerEntityPerson() throws SQLException{
        resultSet = st.executeQuery("SELECT emp.department, emp.fullname, ent.companyname " +
                "FROM employee emp CROSS JOIN cust cust ON cust.id_employee = emp.id_employee " +
                "CROSS JOIN entity ent ON cust.id_cust = ent.id_cust");
        return resultSet;
    }
    ResultSet physicalPersonCredit() throws SQLException{
        resultSet = st.executeQuery("SELECT phy.fullname, cred.name, cred.term, cred.sum " +
                "FROM credit cred CROSS JOIN physicalperson phy ON " +
                "cred.id_cust = phy.id_cust");
        return resultSet;
    }
    ResultSet entityPersonCredit() throws SQLException{
        resultSet = st.executeQuery("SELECT ent.companyname, cred.name, cred.term, cred.sum " +
                "FROM credit cred CROSS JOIN entity ent ON " +
                "cred.id_cust = ent.id_cust");
        return resultSet;
    }
    ResultSet physicalPerson() throws SQLException{
        resultSet = st.executeQuery("SELECT phy.fullname, phy.adress, phy.balance, phy.pasport, phy.dateofbirth " +
                "FROM physicalperson phy");
        return resultSet;
    }
    ResultSet entityPerson() throws SQLException{
        resultSet = st.executeQuery("SELECT ent.companyname, ent.chief, ent.adress, ent.balance, ent.numberreference " +
                "FROM entity ent");
        return resultSet;
    }

    void addPhyDb(Physical physical)throws SQLException{
        pt = connection.prepareStatement("INSERT INTO physicalperson VALUE (?,?,?,?,?,?)");
        pt.setInt(1,physical.getId_cust());
        pt.setString(2,physical.getFullname());
        pt.setString(3,physical.getAdress());
        pt.setString(4,physical.getBalance());
        pt.setString(5,physical.getPassport());
        pt.setString(6,physical.getDateofbirth());
        pt.execute();
    }
    void addenDb(Entity entity)throws SQLException{
        pt = connection.prepareStatement("INSERT INTO entity VALUE (?,?,?,?,?,?)");
        pt.setInt(1,entity.getId_cust());
        pt.setString(2,entity.getCompanyname());
        pt.setString(3,entity.getChief());
        pt.setString(4,entity.getAdress());
        pt.setString(5,entity.getBalance());
        pt.setInt(6,entity.getNumberreference());
        pt.execute();
    }
    void addCrdb(Credit credit)throws SQLException{
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
}
