package com.retarus.fax.http;

public class Credentials {
    private final String username;
    private final String password;
    private final String customerNumber;

    public Credentials(String username, String password, String customerNumber) {
        this.username = username;
        this.password = password;
        this.customerNumber = customerNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }
}
