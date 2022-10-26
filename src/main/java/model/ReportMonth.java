package model;

public class ReportMonth {
    private String type;
    private int count;
    private String month;


    public ReportMonth(String type, String month, int count) {
        this.type = type;
        this.month = month;
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMonth() {
        return month;
    }

    public void setmonth(String month) {
        this.month = month;
    }
}
