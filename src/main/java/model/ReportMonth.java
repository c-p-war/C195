package model;

/**
 * ReportMonth object model. Used in the Appoint by Month and Type report
 */
public class ReportMonth {
    private String type;
    private int count;
    private String month;

    /**
     * ReportMonth constructor. Used to generate the Appointments by Type and Month report.
     *
     * @param type
     * @param month
     * @param count
     */
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
