package com.comp.compmanager.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")

public class Admin {

    //Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private int id;

    @Column(name = "admin_firstname", length = 50, nullable = false)
    private String firstname;

    @Column(name = "admin_lastname", length = 50, nullable = false)
    private String lastname;

    @Column(name = "admin_address", length = 50, nullable = false)
    private String address;

    @Column(name = "admin_zip", length = 5, nullable = false)
    private int zipcode;

    @Column(name = "admin_city", length = 50, nullable = false)
    private String city;

    @Column(name = "admin_country", length = 50, nullable = false)
    private String country;

    @Column(name = "admin_email", length = 50, nullable = false)
    private String email;


    //Constructor
    public Admin() {}
    public Admin(String firstname, String lastname, String address, int zipcode, String city, String country, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
        this.country = country;
        this.email = email;
    }

    //Getters & setters
    public int getId() {
        return id;
    } public void setId(int id) {
        this.id = id; }

    public String getFirstname() {
        return firstname;
    } public void setFirstname(String firstname) {
        this.firstname = firstname; }

    public String getLastname() {
        return lastname;
    } public void setLastname(String lastname) {
        this.lastname = lastname; }

    public String getAddress() {
        return address;
    } public void setAddress(String address) {
        this.address = address; }

    public int getZipcode() {
        return zipcode;
    } public void setZipcode(int zipcode) {
        this.zipcode = zipcode; }

    public String getCity() {
        return city;
    } public void setCity(String city) {
        this.city = city; }

    public String getCountry() {
        return country;
    } public void setCountry(String country) {
        this.country = country; }

    public String getEmail() {
        return email;
    } public void setEmail(String email) {
        this.email = email; }
}
