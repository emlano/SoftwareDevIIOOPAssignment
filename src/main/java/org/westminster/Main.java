package org.westminster;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserInterface ui = new UserInterface();

        ui.printMenu();

        while (true) {
            if (ui.getStock() == 10) {
                throwErr("StockLow");
                System.out.println();
            }

            System.out.println();
            System.out.print("( > ) Enter command: ");
            String command = scanner.nextLine().trim().toUpperCase();
            System.out.println();

            switch (command) {
                case "100", "VFQ" -> ui.printAllQueues();
                case "101", "VEQ" -> ui.printEmptyQueues();
                case "102", "ACQ" -> {
                    try {
                        System.out.print("    ( ? ) Enter customers' first name: ");
                        String firstName = scanner.nextLine().trim();

                        System.out.print("    ( ? ) Enter customers' last name: ");
                        String lastName = scanner.nextLine().trim();

                        System.out.print("    ( ? ) Enter customers' order: ");
                        int order = Integer.valueOf(scanner.nextLine());

                        if (firstName.equals("") || lastName.equals("")) {
                            throwErr("NameEmpty");
                            break;

                        } else if (firstName.equals("null") || lastName.equals("null")) {
                            throwErr("IllegalNull");
                            break;
                        
                        } else if (firstName.contains(",") || lastName.contains(",")) {
                            throwErr("IllegalComma");
                            break;
                        }

                        if (order > 50) {
                            throwErr("BadOrder");
                            break;
                        }

                        System.out.println();
                        Customer customer = new Customer(firstName, lastName, order);
                        ui.addCustomer(customer);
                    
                    } catch (NumberFormatException e) {
                        throwErr("InvalidInt");
                    }
                }

                case "103", "RCQ" -> {
                    try {
                        System.out.print("    ( ? ) Enter customers' queue: ");
                        int queue = Integer.valueOf(scanner.nextLine());
                        
                        System.out.print("    ( ? ) Enter customers' row: ");
                        int row = Integer.valueOf(scanner.nextLine());

                        if (queue > 3 || queue < 1) {
                            throwErr("UnfoundQueue");
                            break;
                        }

                        if (queue == 1 && (row < 1 || row > 2)) {
                            throwErr("UnfoundRow");
                            break;
                        
                        } else if (queue == 2 && (row < 1 || row > 3)) {
                            throwErr("UnfoundRow");
                            break;
                        
                        } else if (queue == 3 && (row < 1) || row > 5) {
                            throwErr("UnfoundRow");
                            break;
                        }

                        ui.removeFromQueue(queue - 1, row - 1);
                    
                    } catch (NumberFormatException e) {
                        throwErr("InvalidInt");
                    }
                }

                case "104", "PCQ" -> {
                    try {
                        System.out.print("    ( ? ) Enter customers' queue: ");
                        int queue = Integer.valueOf(scanner.nextLine());

                        if (queue < 1 || queue > 3) {
                            throwErr("UnfoundQueue");
                            break;
                        }

                        ui.removeServedCustomer(queue - 1);
                    
                    } catch (NumberFormatException e) {
                        throwErr("InvalidInt");
                    }
                }

                case "105", "VCS" -> { ui.viewSortedCustomers(); }
                case "106", "SPD" -> { ui.writeToFile(); }
                case "107", "LPD" -> { ui.readFromFile(); }
                case "108", "STK" -> { 
                    System.out.println("    ( $ ) Remaining burger stock: " + ui.getStock()); 
                }
                
                case "109", "AFS" -> {
                    System.out.print("    ( ? ) Enter amount to be added: ");
                    int burgers = Integer.valueOf(scanner.nextLine());

                    if (burgers < 0 || burgers > 50) {
                        throwErr("UnfoundBurger");
                        break;
                    
                    } else if (burgers + ui.getStock() > 50){
                        throwErr("ExceedsStock");
                        break;
                    }

                    ui.setStock(burgers);
                    System.out.println("    ( $ ) Successfully updated stock!");
                }

                case "110", "IFQ" -> {
                    System.out.print("    ( ? ) Enter queue: ");
                    int queue = Integer.valueOf(scanner.nextLine());

                    if (queue < 1 || queue > 3) {
                        throwErr("UnfoundQueue");
                        break;
                    }
                    System.out.println();
                    System.out.println("    ( $ ) Queue Profit: " + ui.getProfit(queue - 1) + " rs.");
                }

                case "111", "PMN" -> { ui.printMenu(); }

                case "999", "EXT" -> {
                    System.out.println("( $ ) Program exiting!");
                    System.out.println();
                    scanner.close();
                    return;
                }

                default -> {
                    System.out.println("( ! ) Error! No such command found!");
                }
            }
        }
    }

    public static void throwErr(String code) {
        switch (code) {
            case "StockLow" -> {
                System.out.println();
                System.out.println("( ! ) Warning! Burger stock Low!");
            }

            case "IllegalNull" -> {
                System.out.println();
                System.out.println("( ! ) Error! Illegal string 'null' in input!");
            }

            case "IllegalComma" -> {
                System.out.println();
                System.out.println("( ! ) Error! Illegal character ',' in input!");
            }

            case "BadOrder" -> {
                System.out.println();
                System.out.println("( ! ) Error! Customer order unsatisfiable!");
            }

            case "UnfoundRow" -> {
                System.out.println();
                System.out.println("( ! ) Error! Row input out of bounds!");
            }

            case "UnfoundQueue" -> {
                System.out.println();
                System.out.println("( ! ) Error! Queue input out of bounds!");
            }

            case "InvalidInt" -> {
                System.out.println();
                System.out.println("( ! ) Error! Input not an Integer! Number input required!");
            }

            case "ExceedsStock" -> {
                System.out.println();
                System.out.println("( ! ) Error! Given amount overflows stock limit! (Max: 50)");
            }

            case "UnfoundBurger" -> {
                System.out.println();
                System.out.println("( ! ) Error! Burger stock out of bounds! (0 - 50)");
            }

            case "NameEmpty" -> {
                System.out.println();
                System.out.println("( ! ) Error! One or more empty name input!");
            }
        }
    }
}