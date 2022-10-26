package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Customer object model
 */
public class Customer {
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private int id;
    private String name;
    private String address;
    private String postal;
    private String phone;
    private String division;

    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Customer constructor
     *
     * @param id
     * @param name
     * @param address
     * @param postal
     * @param phone
     * @param division
     * @param country
     */

    public Customer(int id, String name, String address, String postal, String phone, String division, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.division = division;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

}
