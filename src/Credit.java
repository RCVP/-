public class Credit {
    int id_credit;
    int id_cust;
    String name;
    String loanpurpose;
    String term;
    String firstpay;
    int sum;

    public Credit(int id_cust, String name, String loanpurpose, String term, String firstpay, int sum) {
        this.id_cust = id_cust;
        this.name = name;
        this.loanpurpose = loanpurpose;
        this.term = term;
        this.firstpay = firstpay;
        this.sum = sum;
    }

    public int getId_credit() {
        return id_credit;
    }

    public void setId_credit(int id_credit) {
        this.id_credit = id_credit;
    }

    public int getId_cust() {
        return id_cust;
    }

    public void setId_cust(int id_cust) {
        this.id_cust = id_cust;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoanpurpose() {
        return loanpurpose;
    }

    public void setLoanpurpose(String loanpurpose) {
        this.loanpurpose = loanpurpose;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getFirstpay() {
        return firstpay;
    }

    public void setFirstpay(String firstpay) {
        this.firstpay = firstpay;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
