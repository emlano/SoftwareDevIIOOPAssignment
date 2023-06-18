package org.westminster;

import java.util.Scanner;
import java.util.ArrayList;

public class UserInterface {
    private WaitingList waitingList;
    private FoodQueue queueOne, queueTwo, queueThree;
    private Scanner scanner;
    
    public UserInterface(Scanner scanner) {
        this.waitingList = new WaitingList();
        this.queueOne = new FoodQueue(2);
        this.queueTwo = new FoodQueue(3);
        this.queueThree = new FoodQueue(5);
        this.scanner = scanner;
    }

    public void printAllQueues() {
        ArrayList<FoodQueue> allQueues = getAllQueues();

        System.out.println("\t*****************");
        System.out.println("\t*   Cashiers    *");
        System.out.println("\t*****************");

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                if (allQueues.get(j).getSize() > i) {
                    if (customerInPosition(allQueues.get(j), i)) {
                        System.out.print("\tO");
                    
                    } else {
                        System.out.print("\tX");
                    }
                
                } else {
                    System.out.print("\t ");
                }
            }
            System.out.println();
        }
    }

    public void printEmptyQueues() {
        FoodQueue[] allQueues = getAllQueues();

        System.out.println("\t*******************");
        System.out.println("\t* Empty Cashiers  *");
        System.out.println("\t*******************");

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                if (allQueues.get(j).getSize() > i && allQueues.get(j).hasEmptySlots()) {
                    if (customerInPosition(allQueues.get(j), i)) {
                        System.out.print("\t O");
                    
                    } else {
                        System.out.print("\t X");
                    }
                
                } else {
                    System.out.print("\t  ");
                }
            }
            System.out.println();
        }
    }

    public void addCustomer(Customer customer) {
        if (queueOne.hasEmptySlots()) {
            queueOne.addToQueue(customer);
        } else if (queueTwo.hasEmptySlots()) {
            queueTwo.addToQueue(customer);
        } else if (queueThree.hasEmptySlots()) {
            queueThree.addToQueue(customer);
        } else {}
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