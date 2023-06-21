package org.westminster;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserInterface ui = new UserInterface();

        //ui.printAllQueues();
        // ui.printEmptyQueues();
        Customer harry = new Customer("Harry", "Poter", 3);
        Customer Frodo = new Customer("Frodo", "Baggins", 4);
        Customer A = new Customer("A", "B", 10);
        Customer B = new Customer("B", "C", 5);
        Customer C = new Customer("C", "D", 2);
        Customer elf = new Customer("Elf", "Shelf", 20);
        ui.addCustomer(harry);
        ui.addCustomer(Frodo);
        ui.addCustomer(A);
        ui.addCustomer(B);
        ui.addCustomer(C);
        ui.addCustomer(elf);
        ui.viewSortedCustomers();
        ui.printAllQueues();
    }
}