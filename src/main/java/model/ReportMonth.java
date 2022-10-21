package model;

public class ReportMonth {
    private int monthInt;
    private int count;
    private String monthString;

    public ReportMonth(int monthInt, String monthString, int count){
        this.monthInt = monthInt;
        this.monthString = monthString;
        this.count = count;
    }

    public int getMonthInt() {
        return monthInt;
    }

    public void setMonthInt(int monthInt) {
        this.monthInt = monthInt;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMonthString() {
        return monthString;
    }

    public void setMonthString(String monthString) {
        this.monthString = monthString;
    }
}
