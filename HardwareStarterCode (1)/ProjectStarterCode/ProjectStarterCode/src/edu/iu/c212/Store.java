package edu.iu.c212;

import edu.iu.c212.models.Item;
import edu.iu.c212.models.Staff;
import edu.iu.c212.programs.SawPrimePlanks;
import edu.iu.c212.utils.FileUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Store implements IStore {
    private final List<Item> inventory;
    private final List<Staff> employees;
    private final List<String> commands;

    public Store(){
        inventory = getItemsFromFile();
        employees = getStaffFromFile();
        try {
            commands = FileUtils.readCommandsFromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        takeAction();
    }
    @Override
    public List<Item> getItemsFromFile() {
        List<Item> items = null;
        try {
            items = FileUtils.readInventoryFromFile();
        }
        catch (IOException e) {
            System.exit(0);
        }
        return items;
    }

    @Override
    public List<Staff> getStaffFromFile() {
        List<Staff> staffs = null;
        try {
            staffs = FileUtils.readStaffFromFile();
        }
        catch (IOException e) {
            System.exit(0);
        }
        return staffs;
    }

    @Override
    public void saveItemsFromFile() {
        try {
            FileUtils.writeInventoryToFile(inventory);
        }
        catch (IOException e) {
            System.exit(0);
        }
    }

    @Override
    public void saveStaffFromFile() {
        try {
            FileUtils.writeStaffToFile(employees);
        }
        catch (IOException e) {
            System.exit(0);
        }
    }

    /**
     * This loads in the inventory and staff information, reads from the input file and takes
     * the correct actions, then finally asks the user to hit Enter to end the program when
     * they’re finished. See the rest of the document for possible actions and how they’re
     * formatted in the input file.
     */
    @Override
    public void takeAction() {
        String output = "";
        //commands here
        for (String command : this.commands){
            /**
             * ADD ’<itemName>’ <itemCost> <itemQuantity> <itemAisle>
             */
            String[] parts = command.split(" ");
            if (parts[0].equals("ADD")){
                int start = 0;
                int end = 0;
                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].substring(0, 1).equals("'")){
                        start = i;
                    }
                    if (parts[i].substring(parts[i].length()-1).equals("'")){
                        end = i;
                    }
                }
                String a = parts[start].substring(1) + " ";
                String b = parts[end].substring(0,parts[end].length() - 1);
                String middle = "";
                for (int i = start + 1; i < end; i++) {
                    middle += parts[i] + " ";
                }
                String name = a + middle + b;
                if (start == end){
                    name = parts[start].substring(1,parts[start].length() - 1);
                }



                double cost = Double.parseDouble(parts[end + 1]);
                int qty = Integer.parseInt(parts[end + 2]);
                int aisle = Integer.parseInt(parts[end + 3]);
                this.inventory.add(new Item(name,cost,qty,aisle));
                output += name + " was added to inventory" + "\n";
            }
            /**
             * COST ’<itemName>’
             */
            else if (parts[0].equals("COST")){
                boolean contains = false;
                double price = 0;
                int start = 0;
                int end = 0;
                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].substring(0, 1).equals("'")){
                        start = i;
                    }
                    if (parts[i].substring(parts[i].length()-1).equals("'")){
                        end = i;
                    }
                }
                String a = parts[start].substring(1) + " ";
                String b = parts[end].substring(0,parts[end].length() - 1);
                String middle = "";
                for (int i = start + 1; i < end; i++) {
                    middle += parts[i] + " ";
                }
                String name = a + middle + b;
                if (start == end){
                    name = parts[start].substring(1,parts[start].length() - 1);
                }

                for (Item item : inventory){
                    if (item.getName().equals(name)){
                        contains = true;
                        price = item.getPrice();
                    }
                }
                if (contains && price != 0){
                    output += name + ": $" + price + "\n";
                }
                else {
                    output += "ERROR: " + name + " cannot be found" + "\n";
                }
            }
            /**
             * EXIT
             */
            else if (parts[0].equals("EXIT")){
                output += "Thank you for visiting High's Hardware and Gardening!" + "\n";
                System.out.println("Press enter to continue...");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();
                scanner.close();
            }
            /**
             * FIND ’<itemName>’
             */
            else if (parts[0].equals("FIND")){
                int start = 0;
                int end = 0;
                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].substring(0, 1).equals("'")){
                        start = i;
                    }
                    if (parts[i].substring(parts[i].length()-1).equals("'")){
                        end = i;
                    }
                }
                String a = parts[start].substring(1) + " ";
                String b = parts[end].substring(0,parts[end].length() - 1);
                String middle = "";
                for (int i = start + 1; i < end; i++) {
                    middle += parts[i] + " ";
                }
                String name = a + middle + b;
                if (start == end){
                    name = parts[start].substring(1,parts[start].length() - 1);
                }


                String itemName = "";
                int qty = 0;
                int aisle = 0;

                for (Item item : inventory){
                    if (item.getName().equals(name)){
                        itemName = item.getName();
                        qty = item.getQuantity();
                        aisle = item.getAisleNum();
                    }
                }
                if (itemName.isEmpty()){
                    output += "ERROR: " + name + " cannot be found" + "\n";
                }
                else {
                    output += qty + " " + itemName + " are available in " + aisle + "\n";
                }
            }
            /**
             * FIRE ’<staffName>’
             */
            else if (parts[0].equals("FIRE")) {
                int start = 0;
                int end = 0;
                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].substring(0, 1).equals("'")){
                        start = i;
                    }
                    if (parts[i].substring(parts[i].length()-1).equals("'")){
                        end = i;
                    }
                }
                String a = parts[start].substring(1) + " ";
                String b = parts[end].substring(0,parts[end].length() - 1);
                String middle = "";
                for (int i = start + 1; i < end; i++) {
                    middle += parts[i] + " ";
                }
                String name = a + middle + b;
                if (start == end){
                    name = parts[start].substring(1,parts[start].length() - 1);
                }


                boolean contain = false;

                Iterator<Staff> iterator = employees.iterator();

                while (iterator.hasNext()){
                    Staff s = iterator.next();
                    if (s.getFullName().equals(name)){
                        contain = true;
                        iterator.remove();
                    }
                }

                if (!contain){
                    output += "ERROR: " + name + " cannot be found" + "\n";
                }
                else {
                    output += name + " was fired" + "\n";
                }
            }
            /**
             * HIRE ’<staffName>’ <age> <role> <availability>
             */
            else if (parts[0].equals("HIRE")){
                int start = 0;
                int end = 0;
                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].substring(0, 1).equals("'")){
                        start = i;
                    }
                    if (parts[i].substring(parts[i].length()-1).equals("'")){
                        end = i;
                    }
                }
                String a = parts[start].substring(1) + " ";
                String b = parts[end].substring(0,parts[end].length() - 1);
                String middle = "";
                for (int i = start + 1; i < end; i++) {
                    middle += parts[i] + " ";
                }
                String name = a + middle + b;
                if (start == end){
                    name = parts[start].substring(1,parts[start].length() - 1);
                }

                int age = Integer.parseInt(parts[end + 1]);
                String role = "";
                if (parts[end + 2].equals("M")){
                    role = "Manager";
                }
                else if (parts[end + 2].equals("C")){
                    role = "Cashier";
                }
                else if (parts[end + 2].equals("G")){
                    role = "Gardening Expert";
                }
                String av = parts[end + 3];

                this.employees.add(new Staff(name,age,role,av));
                output += name + " has been hired as a " + role + "\n";
            }
            /**
             * PROMOTE ’<staffName>’ <role>
             */
            else if (parts[0].equals("PROMOTE")){
                int start = 0;
                int end = 0;
                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].substring(0, 1).equals("'")){
                        start = i;
                    }
                    if (parts[i].substring(parts[i].length()-1).equals("'")){
                        end = i;
                    }
                }
                String a = parts[start].substring(1) + " ";
                String b = parts[end].substring(0,parts[end].length() - 1);
                String middle = "";
                for (int i = start + 1; i < end; i++) {
                    middle += parts[i] + " ";
                }
                String name = a + middle + b;
                if (start == end){
                    name = parts[start].substring(1,parts[start].length() - 1);
                }


                String role = "";
                if (parts[end + 1].equals("M")){
                    role = "Manager";
                }
                else if (parts[end + 1].equals("C")){
                    role = "Cashier";
                }
                else if (parts[end + 1].equals("G")){
                    role = "Gardening Expert";
                }

                for (int i = 0; i < employees.size(); i++) {
                    if (name.equals(employees.get(i).getFullName())){
                        Staff promoted = new Staff(name,employees.get(i).getAge(),role,employees.get(i).getAvailability());
                        employees.set(i,promoted);
                    }
                }
                output += name + " was promoted to " + role  + "\n";
            }
            /**
             * SAW
             */
            else if (parts[0].equals("SAW")){
                for (int i = 0; i < inventory.size(); i++) {
                    if (inventory.get(i).getName().contains("Plank-")){
                        int length = Integer.parseInt(inventory.get(i).getName().substring(6));
                        List<Integer> plankLengths = SawPrimePlanks.getPlankLengths(length);
                        length = plankLengths.getFirst();
                        int qty = plankLengths.size();
                        int aisle = inventory.get(i).getAisleNum();
                        double price = Math.pow(length,2);
                        String name = "Plank-" + length;
                        Item sawed = new Item(name,price,qty,aisle);
                        inventory.set(i,sawed);
                    }
                }
                output += "Planks sawed" + "\n";
            }
            /**
             * SCHEDULE
             */
            else if (parts[0].equals("SCHEDULE")){
                //some specific implementation in programs/StaffScheduler
                output += "Schedule created" + "\n";
            }
            /**
             * SELL ’<itemName>’ <quantity>
             */
            else if (parts[0].equals("SELL")){
                int start = 0;
                int end = 0;
                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].substring(0, 1).equals("'")){
                        start = i;
                    }
                    if (parts[i].substring(parts[i].length()-1).equals("'")){
                        end = i;
                    }
                }
                String a = parts[start].substring(1) + " ";
                String b = parts[end].substring(0,parts[end].length() - 1);
                String middle = "";
                for (int i = start + 1; i < end; i++) {
                    middle += parts[i] + " ";
                }
                String name = a + middle + b;
                if (start == end){
                    name = parts[start].substring(1,parts[start].length() - 1);
                }

                int qty = Integer.parseInt(parts[end + 1]);

                boolean contain = false;
                boolean enough = false;

                for (int i = 0; i < inventory.size(); i++) {
                    if (name.equals(inventory.get(i).getName())){
                        contain = true;
                        if (inventory.get(i).getQuantity() > qty){
                            enough = true;
                            Item afterSell = new Item(
                                    name,
                                    inventory.get(i).getPrice(),
                                    inventory.get(i).getQuantity() - qty,
                                    inventory.get(i).getAisleNum());
                            inventory.set(i,afterSell);
                        }
                    }
                }

                if (contain && enough){
                    output += qty + " " + name + " was sold" + "\n";
                }
                else {
                    output += "ERROR: " + name + " could not be sold" + "\n";
                }
            }
            /**
             * QUANTITY ‘<itemName>’
             */
            else if (parts[0].equals("QUANTITY")){
                boolean contains = false;
                int qty = 0;
                int start = 0;
                int end = 0;
                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].substring(0, 1).equals("'")){
                        start = i;
                    }
                    if (parts[i].substring(parts[i].length()-1).equals("'")){
                        end = i;
                    }
                }
                String a = parts[start].substring(1) + " ";
                String b = parts[end].substring(0,parts[end].length() - 1);
                String middle = "";
                for (int i = start + 1; i < end; i++) {
                    middle += parts[i] + " ";
                }
                String name = a + middle + b;
                if (start == end){
                    name = parts[start].substring(1,parts[start].length() - 1);
                }

                for (Item item : inventory){
                    if (item.getName().equals(name)){
                        contains = true;
                        qty = item.getQuantity();
                    }
                }
                if (contains && qty != 0){
                    output += qty + "\n";
                }
                else {
                    output += "ERROR: " + name + " cannot be found" + "\n";
                }
            }
        }

        FileUtils.writeLineToOutputFile(output.substring(0,output.length() - 1));
    }

}
