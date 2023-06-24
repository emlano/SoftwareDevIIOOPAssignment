package org.westminster;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class UserInterface {
    private WaitingList waitingList;
    private FoodQueue queueOne, queueTwo, queueThree;
    private int burgerStock;
    
    public UserInterface() {
        this.waitingList = new WaitingList();
        this.queueOne = new FoodQueue(2);
        this.queueTwo = new FoodQueue(3);
        this.queueThree = new FoodQueue(5);
        this.burgerStock = 0;
    }

    public void printAllQueues() {
        FoodQueue[] allQueues = this.getAllQueues();
        System.out.println();

        System.out.println("      *****************");
        System.out.println("      *   Cashiers    *");
        System.out.println("      *****************");

        for (int i = 0; i < 5; i++) {
            System.out.print("        ");

            for (int j = 0; j < 3; j++) {
                if (i < allQueues[j].getSize()) {
                    if (this.customerInPosition(allQueues[j], i)) {
                        System.out.print("O");
                    
                    } else {
                        System.out.print("X");
                    }
                
                } else {
                    System.out.print(" ");
                }

                System.out.print("     ");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("O - Occupied, X - Not Occupied");
        System.out.println();
    }

    public void printEmptyQueues() {
        FoodQueue[] allQueues = this.getAllQueues();
        System.out.println();

        System.out.println("     *******************");
        System.out.println("     *  Empty Cashiers *");
        System.out.println("     *******************");

        for (int i = 0; i < 5; i++) {
            System.out.print("        ");

            for (int j = 0; j < 3; j++) {
                if (i < allQueues[j].getSize() && allQueues[j].hasEmptySlots()) {
                    if (this.customerInPosition(allQueues[j], i)) {
                        System.out.print("O");
                    
                    } else {
                        System.out.print("X");
                    }
                
                } else {
                    System.out.print(" ");
                }

                System.out.print("     ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("O - Occupied, X - Not Occupied");
        System.out.println();
    }

    public void addCustomer(Customer customer) {
        FoodQueue[] allQueues = this.getAllQueues();

        for (FoodQueue i : allQueues) {
            if (i.hasEmptySlots()) {
                i.addToQueue(customer);
                System.out.println("( $ ) Successfully added to queue!");
                return;
            }
        }

        System.out.println("( ! ) Warning! All queues full! Attempting to add to waiting list!");

        if (!this.waitingList.isFull()) {
            this.waitingList.addToList(customer);
            System.out.println("( $ ) Successfully added to waiting list!");
            return;
        }

        System.out.println("( ! ) Error! Unable to add customer! Waiting list full!");
    }

    public void removeFromQueue(int queue, int row) {
        FoodQueue[] allQueues = this.getAllQueues();

        if (this.customerInPosition(allQueues[queue], row)) {
            allQueues[queue].removeFromQueue(row);
            this.getWaitingListCustomer(queue);
        
        } else {
            System.out.println("( ! ) Error! No customer at position!");
        }
    }

    public void removeServedCustomer(int queue) {
        FoodQueue[] allQueues = this.getAllQueues();

        if (this.customerInPosition(allQueues[queue], 0)) {
            int customerOrder = allQueues[queue].getElementAt(0).getOrder();
            
            if (this.burgerStock > customerOrder) {
                allQueues[queue].removeServed(customerOrder);
                this.burgerStock -= customerOrder;
                this.getWaitingListCustomer(queue);
            
            } else {
                System.out.println("( ! ) Error! Not enough burgers to fulfill customer's order!");
            }
        
        } else {
            System.out.println("( ! ) Error! No customers in queue!");
        }
    }

    public void viewSortedCustomers() {
        ArrayList<String> customerList = this.getAllCustomerNames();


        for (int i = 0; i < customerList.size(); i++) {
            for (int j = 1; j < customerList.size(); j++) {
                if (customerList.get(j).compareTo(customerList.get(j - 1)) < 0) {
                    String temp = customerList.get(j - 1);
                    customerList.set(j - 1, customerList.get(j));
                    customerList.set(j, temp);
                }
            }
        }

        System.out.println("    ( $ ) Displaying customer names sorted alphabetically: ");
        System.out.println();

        for (String i : customerList) {
            System.out.println("        " + i);
        }
    }

    public void writeToFile() {
        try {
            File file = new File("data.txt");
            FileWriter writer = new FileWriter(file);
            FoodQueue[] allQueues = this.getAllQueues();

            writer.write(this.burgerStock + "\n");
            
            for (FoodQueue i : allQueues) {
                writer.write(i.getProfit() + "\n");
            }
            
            for (FoodQueue i : allQueues) {
                for (int j = 0; j < i.getSize(); j++) {
                    if (i.getElementAt(j) != null) {
                        writer.write(i.getElementAt(j).toString() + "\n");
                    }
                }
            }

            for (int i = 0; i < this.waitingList.getSize(); i++) {
                if (this.waitingList.getElementAt(i) != null) {
                    writer.write(this.waitingList.getElementAt(i).toString() + "\n");
                }
            }

            System.out.println("    ( $ ) Session saved to file!");
            writer.close();
        
        } catch (IOException e) {
            System.out.println("( !!! ) Fatal error occurred! :");
            System.out.println(e.getMessage());
        }
    }

    public void readFromFile() {
        FoodQueue[] allQueues = this.getAllQueues();

        if (this.loadedDataFound(allQueues)) {
            System.out.println("( ! ) Caution! Program storing data! File read stopped to prevent data loss!");
            return;
        }

        try {
            File file = new File("data.txt");
            Scanner fileScan = new Scanner(file);

            this.burgerStock = Integer.valueOf(fileScan.nextLine());
            this.queueOne.setProfit(Integer.valueOf(fileScan.nextLine()));
            this.queueTwo.setProfit(Integer.valueOf(fileScan.nextLine()));
            this.queueThree.setProfit(Integer.valueOf(fileScan.nextLine()));

            while (fileScan.hasNextLine()) {
                boolean added = false;
                String[] data = fileScan.nextLine().split(",");
                int order = Integer.valueOf(data[2]);

                Customer customer = new Customer(data[0], data[1], order);

                for (FoodQueue i : allQueues) {
                    if (i.hasEmptySlots()) {
                        i.addToQueue(customer);
                        added = true;
                        break;
                    }
                }

                if (!added && !this.waitingList.isFull()) {
                    this.waitingList.addToList(customer);
                }
            }

            System.out.println("    ( $ ) Session restored from file!");
            fileScan.close();

        } catch (FileNotFoundException e) {
            System.out.println("( ! ) Error! Saved data not found! Please save first!");

        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("( ! ) Error! Unable to read some values! Possibly corrupted file!");
        }
    }

    public void printMenu() {
        System.out.println();
        System.out.println(" [#]=========================================[#]");
        System.out.println(" [#] Foodies Fave Customer Management System [#]");
        System.out.println(" [#]            Version 0.2.0                [#]");
        System.out.println(" [#]=========================================[#]");
        System.out.println();
        System.out.println("    100 or VFQ -- View all queues");
        System.out.println("    101 or VEQ -- View all empty queues");
        System.out.println("    102 or ACQ -- Add customer to queue");
        System.out.println("    103 or RCQ -- Remove customer from queue");
        System.out.println("    104 or PCQ -- Remove a served customer");
        System.out.println("    105 or VCS -- View customers sorted alphabetically");
        System.out.println("    106 or SPD -- Store program data in file");
        System.out.println("    107 or LPD -- Load program data from file");
        System.out.println("    108 or STK -- View remaining burger stock");
        System.out.println("    109 or AFS -- Add burgers to Stock");
        System.out.println("    110 or IFQ -- Display income of a queue");
        System.out.println("    111 or PMN -- Print this menu");
        System.out.println("    999 or EXT -- Exit program");
        System.out.println();
    }

    public int getStock() {
        return this.burgerStock;
    }

    public void setStock(int stock) {
        this.burgerStock += stock;
    }

    public int getProfit(int queue) {
        FoodQueue[] allQueues = this.getAllQueues();

        return allQueues[queue].getProfit();
    }

    private ArrayList<String> getAllCustomerNames() {
        ArrayList<String> customerList = new ArrayList<>();
        FoodQueue[] allQueues = this.getAllQueues();

        for (FoodQueue i : allQueues) {
            for (int j = 0; j < i.getSize(); j++) {
                if (i.getElementAt(j) != null) {
                    customerList.add(i.getElementAt(j).getFirstName() + " " + i.getElementAt(j).getSecondName());
                }
            }
        }

        return customerList;
    }

    private boolean customerInPosition(FoodQueue queue, int index) {
        return (queue.getElementAt(index) != null);
    }

    private FoodQueue[] getAllQueues() {
        FoodQueue[] allQueues = new FoodQueue[3];

        allQueues[0] = this.queueOne;
        allQueues[1] = this.queueTwo;
        allQueues[2] = this.queueThree;

        return allQueues;
    }

    private void getWaitingListCustomer(int queue) {
        FoodQueue[] allQueues = this.getAllQueues();

        if (!this.waitingList.isEmpty()) {
            for (int i = 0; i < allQueues[queue].getSize(); i++) {
                if (allQueues[queue].getElementAt(i) == null) {
                    allQueues[queue].addToQueue(this.waitingList.nextCustomer());
                    System.out.println("    ( $ ) Note: Waiting list customer added to queue!");
                    return;
                }
            }
        }
    }

    private boolean loadedDataFound(FoodQueue[] allQueues) {
        if (this.burgerStock != 0) {
            return true;
        }

        for (int i = 0; i < 3; i++) {
            if (allQueues[i].getElementAt(0) != null) {
                return true;

            } else if (allQueues[i].getProfit() != 0) {
                return true;
            }
        }

        return false;
    }
}