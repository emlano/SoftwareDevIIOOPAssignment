package org.westminster;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

        System.out.println("( ! ) Warning! All queues full! Attempting to Add to waiting list!");

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
            
            } else {
                System.out.println("( ! ) Error! Not enough burgers to fulfill customer's order!");
            }
        
        } else {
            System.out.println("( ! ) Error! No customers in queue!");
        }
    }

    public void viewSortedCustomers() {
        ArrayList<String> customerList = this.getAllCustomerNames();


        for (int j = 0; j < customerList.size(); j++) {
            boolean swapped = false;
            for (int i = 1; i < customerList.size(); i++) {
                if (customerList.get(i).compareTo(customerList.get(i - 1)) < 0) {
                    String temp = customerList.get(i - 1);
                    customerList.set(i - 1, customerList.get(i));
                    customerList.set(i, temp);
                    swapped = true;
                }

                if (!swapped) {
                    break;
                }
            }
        }

        System.out.println("( $ ) Displaying customer names sorted alphabetically: ");

        for (String i : customerList) {
            System.out.println(i);
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

            writer.close();
        
        } catch (IOException e) {
            System.out.println("( !!! ) Fatal error occured! :");
            System.out.println(e.getMessage());
        }
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
}