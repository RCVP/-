public class Physical {
    private int id_cust;
    private String fullname;
    private String adress;
    private String balance;
    private String passport;
    private String dateofbirth;

    public int getId_cust() {
        return id_cust;
    }

    public void setId_cust(int id_cust) {
        this.id_cust = id_cust;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;

    }

    public Physical(String fullname, String adress, String balance, String passport, String dateofbirth) {
        this.fullname = fullname;
        this.adress = adress;
        this.balance = balance;
        this.passport = passport;
        this.dateofbirth = dateofbirth;
    }
}
