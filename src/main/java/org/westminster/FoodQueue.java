package org.westminster;

public class FoodQueue {
    private int profit;
    private Customer[] queue;

    public FoodQueue(int limit) {
        this.queue = new Customer[limit];
        this.profit = 0;
    }

    public int getSize() {
        return this.queue.length;
    }

    public int getProfit() {
        return this.profit;
    }

    public Customer getElementAt(int index) {
        return this.queue[index];
    }

    public boolean hasEmptySlots() {
        for (int i = 0; i < this.getSize(); i++) {
            if (this.queue[i] == null) {
                return true;
            }
        }

        return false;
    }

    public void addToQueue(Customer customer) {
        for (int i = 0; i < this.getSize(); i++) {
            if (queue[i] == null)    {
                queue[i] = customer;
                return;
            }
        }
    }

    public void removeFromQueue(int index) {
        this.queue[index] = null;
        this.moveForward();
    }

    public void removeServed(int order) {
        this.profit += order * 650;
        this.removeFromQueue(0);
    }

    private void moveForward() {
        for (int i = 0; i < this.getSize() - 1; i++) {
            if (this.queue[i] == null && this.queue[i + 1] != null) {
                this.queue[i] = this.queue[i + 1];
                this.queue[i + 1] = null;
            }
        }
    }

    public String toString() {
        String outString = "";
        outString  = "Queue limit: " + this.getSize() + "\n";
        outString += "Queue profit: " + this.profit + "\n";
        outString += "Customers: " + "\n";

        for (Customer i : this.queue) {
            if (i != null) {
                outString += "\t" + i.toString() + "\n";
            }
        }

        return outString;
    }
}
