package model;

/**
 * ReportCountry object model used in the Customers by Country report
 */
public class ReportCountry {
    private String country;
    private int count;

    /**
     * ReportCountry constructor. Used to generate the Customers by Country report
     *
     * @param country
     * @param count
     */
    public ReportCountry(String country, int count) {
        this.country = country;
        this.count = count;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
