package org.westminster;

public class Customer {
    private String firstName;
    private String secondName;
    private int burgerRequired;

    public Customer(String firstName, String secondName, int burgerRequired) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.burgerRequired = burgerRequired;
    }

    public int getOrder() {
        return this.burgerRequired;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getSecondName() {
        return this.secondName;
    }

    public String toString() {
        return this.firstName + "," + this.secondName + "," + this.burgerRequired;
    }
}
