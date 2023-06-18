package org.westminster;

import java.util.ArrayList;

public class FoodQueue {
    private int limit;
    private int profit;
    private Customer[] queue;

    public FoodQueue(int limit) {
        this.limit = limit;
        this.queue = new Customer[limit];
        this.profit = 0;
    }

    public int getSize() {
        return this.limit;
    }

    public int getProfit() {
        return this.profit;
    }

    public Customer getElementAt(int index) {
        return this.queue[index];
    }

    public boolean hasEmptySlots() {
        for (int i = 0; i < this.limit; i++) {
            if (this.queue.get(i) == null) {
                return true;
            }
        }

        return false;
    }

    public void addToQueue(Customer customer) {
        for (int i = 0; i < this.queue.size(); i++) {
            if (queue.get(i) == null)    {
                queue.set(i, customer);
            }
        }
    }

    public void removeFromQueue(int index) {
        this.queue.remove(index);
        this.queue.add(null);
    }

    public void removeServed(int order) {
        this.profit += order * 650;
        this.removeFromQueue(0);
        this.queue.add(null);
    }

    public String toString() {
        String outString = "";
        outString  = "Queue limit: " + this.limit + "\n";
        outString += "Queue profit: " + this.profit + "\n";
        outString += "Customers: " + "\n";

        for (Customer i : this.queue) {
            outString += "\t" + i.toString();
        }

        return outString;
    }
}
