package model;


/**
 * Appointment object model
 */
public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private String start;
    private String end;
    private int customerId;
    private int userId;
    private String contactName;

    /**
     * Appointment constructor
     *
     * @param id
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerId
     * @param userId
     * @param contactName
     */
    public Appointment(int id, String title, String description, String location, String type, String start, String end, int customerId, int userId, String contactName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactName = contactName;
    }

    /**
     * Gets appointment ID
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Sets appointment ID
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets appointment title
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets appointment title
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets appointment description
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets appointment description
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets appointment location
     *
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets appointment location
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets appointment type
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Sets appointment type
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets appointment start date/time
     *
     * @return
     */
    public String getStart() {
        return start;
    }

    /**
     * Sets appointment start date/time
     *
     * @param start
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * Gets appointment end date/time
     *
     * @return
     */
    public String getEnd() {
        return end;
    }

    /**
     * Sets appointment end date/time
     *
     * @param end
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * Gets appointment customer ID
     *
     * @return
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets appointment customer ID
     *
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets appointment User ID
     *
     * @return
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets appointment User ID
     *
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets appointment Contact Name
     *
     * @return
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets appointment Contact Name
     *
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

}
