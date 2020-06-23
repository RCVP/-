public class Entity {
    int id_cust;
    String companyname;
    String chief;
    String adress;
    String balance;
    int numberreference;

    public Entity(String companyname, String chief, String adress, String balance, int numberreference) {
        this.companyname = companyname;
        this.chief = chief;
        this.adress = adress;
        this.balance = balance;
        this.numberreference = numberreference;
    }

    public int getId_cust() {
        return id_cust;
    }

    public void setId_cust(int id_cust) {
        this.id_cust = id_cust;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getChief() {
        return chief;
    }

    public void setChief(String chief) {
        this.chief = chief;
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

    public int getNumberreference() {
        return numberreference;
    }

    public void setNumberreference(int numberreference) {
        this.numberreference = numberreference;
    }
}
