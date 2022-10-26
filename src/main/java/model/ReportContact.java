package model;

/**
 * ReportContact object model. Used in the Schedule by Contacts report
 */
public class ReportContact {
    private int id;
    private String title;
    private String type;
    private String description;
    private String start;
    private String end;
    private int customerId;

    /**
     * Report Contact constructor, used to generate the Appointments by Contact report
     *
     * @param id
     * @param title
     * @param type
     * @param description
     * @param start
     * @param end
     * @param customerId
     */
    public ReportContact(int id, String title, String type, String description, String start, String end, int customerId) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.description = description;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

}
