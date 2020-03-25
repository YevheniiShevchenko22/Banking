package transactionhistory;

public class History {

    private String name;
    private String amount;
    private String generetic;
    private String date;
    private String time;

    public History(String name, String amount, String generetic, String date, String time) {
        this.name = name;
        this.amount = amount;
        this.generetic = generetic;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getGeneretic() {
        return generetic;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setGeneretic(String generetic) {
        this.generetic = generetic;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
