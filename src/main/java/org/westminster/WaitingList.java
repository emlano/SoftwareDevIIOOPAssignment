package org.westminster;

public class WaitingList {
    private int front, rear, size;
    private Customer[] array;

    public WaitingList() {
        this.size = 5;
        this.array = new Customer[size];
        this.front = -1 ;
        this.rear = -1;
    }

    public boolean isFull() {
        if (this.front == 0 && this.rear == size - 1) {
            return true;
        }

        if (this.front == this.rear + 1) {
            return true;
        }

        return false;
    }

    public boolean isEmpty() {
        return this.front == -1;
    }

    public void addToList(Customer customer) {
        if (this.isFull()) {
            System.out.println("Error! Waiting list full!");
            return;
        }

        if (this.front == -1) {
            this.front = 0;
        }

        this.rear = (this.rear + 1) % this.size;
        array[rear] = customer;
    }

    public Customer nextCustomer() {
        if (this.isEmpty()) {
            return null;
        }

        Customer customer = this.array[front];
        this.array[front] = null;

        if (this.front == this.rear) {
            this.front = -1;
            this.rear = -1;
        
        } else {
            this.front = (this.front + 1) % this.size;
        }

        return customer;
    }
}
