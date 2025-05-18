package edu.iu.c212.utils;

import edu.iu.c212.models.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class FileUtils {
    private static File inputFile = new File("ProjectStarterCode/ProjectStarterCode/src/edu/iu/c212/resources/input.txt");
    private static File outputFile = new File("ProjectStarterCode/ProjectStarterCode/src/edu/iu/c212/resources/output.txt");
    private static File inventoryFile = new File("ProjectStarterCode/ProjectStarterCode/src/edu/iu/c212/resources/inventory.txt");
    private static File staffFile = new File("ProjectStarterCode/ProjectStarterCode/src/edu/iu/c212/resources/staff.txt");
    private static File staffAvailabilityFile = new File("ProjectStarterCode/ProjectStarterCode/src/edu/iu/c212/resources/staff_availability_IN.txt");
    private static File shiftSchedulesFile = new File("ProjectStarterCode/ProjectStarterCode/src/edu/iu/c212/resources/shift_schedules_IN.txt");
    private static File storeScheduleFile = new File("ProjectStarterCode/ProjectStarterCode/src/edu/iu/c212/resources/store_schedule_OUT.txt");

    /**
     * reads in all the items from inventory.txt
     * @return list of items
     * @throws IOException
     */
    public static List<Item> readInventoryFromFile() throws IOException {
        Scanner in = new Scanner(inventoryFile);
        List<Item> items = new ArrayList<>();
        while (in.hasNextLine()){
            String line = in.nextLine();
            String[] itemData = line.split(",");
            String name = itemData[0].substring(1,itemData[0].length() - 1);
            double price = Double.parseDouble(itemData[1]);
            int qty = Integer.parseInt(itemData[2]);
            int aisle = Integer.parseInt(itemData[3]);
            Item item = new Item(name,price,qty,aisle);
            items.add(item);
        }
        in.close();
        return items;
    }

    /**
     * reads in all the staff from staff availability IN.txt
     * @return List of staffs
     * @throws IOException
     */
    public static List<Staff> readStaffFromFile() throws IOException {
        Scanner in = new Scanner(staffAvailabilityFile);
        List<Staff> staffs = new ArrayList<>();
        while (in.hasNextLine()){
            String line = in.nextLine();
            String[] information = line.split(" ");
            String fullName = information[0] + " " + information[1];
            int age = Integer.parseInt(information[2]);
            String roleLetter = information[3];
            String role = "";
            if (roleLetter.equals("M")){
                role = "Manager";
            }
            else if (roleLetter.equals("C")){
                role = "Cashier";
            }
            else if (roleLetter.equals("G")){
                role = "Gardening Expert";
            }
            String av = information[4];
            Staff staff = new Staff(fullName,age,role,av);
            staffs.add(staff);
        }
        in.close();
        return staffs;
    }

    /**
     * saves all the items from inventory.txt into a new file called inventory_OUT.txt
     * @param items a list of items
     * @throws FileNotFoundException
     */
    public static void writeInventoryToFile(List<Item> items) throws FileNotFoundException {
        String res = "";
        for (Item item : items) {
            res += "'" + item.getName() + "',"
                    + item.getPrice() + ","
                    + item.getQuantity() + ","
                    + item.getAisleNum() + "\n";
        }
        res = res.substring(0,res.length() - 1);
        PrintWriter out = new PrintWriter(inventoryFile);
        out.print(res);
        out.close();
    }

    /**
     * saves all the staff to staff_availability_IN.txt
     * @param employees list of staffs
     * @throws FileNotFoundException
     */
    public static void writeStaffToFile(List<Staff> employees) throws FileNotFoundException {
        String res = "";
        for (Staff staff : employees) {
            if (staff.getRole().equals("Manager")){
                res += staff.getFullName() + " "
                        + staff.getAge() + " "
                        + "M" + " "
                        + staff.getAvailability() + "\n";
            }
            else if (staff.getRole().equals("Cashier")){
                res += staff.getFullName() + " "
                        + staff.getAge() + " "
                        + "C" + " "
                        + staff.getAvailability() + "\n";
            }
            else if (staff.getRole().equals("Gardening Expert")){
                res += staff.getFullName() + " "
                        + staff.getAge() + " "
                        + "G" + " "
                        + staff.getAvailability() + "\n";
            }
        }
        res = res.substring(0,res.length() - 1);
        PrintWriter out = new PrintWriter(staffFile);
        out.print(res);
        out.close();

        PrintWriter out1 = new PrintWriter(staffAvailabilityFile);
        out1.print(res);
        out1.close();
    }

    public static List<String> readCommandsFromFile() throws IOException {
        List<String> commands = new ArrayList<>();
        Scanner in = new Scanner(inputFile);
        while (in.hasNextLine()){
            String command = in.nextLine();
            commands.add(command);
        }
        in.close();
        return commands;
    }

    public static List<String> readShiftSchedulesFromFile() throws IOException{
        Scanner in = new Scanner(shiftSchedulesFile);
        List<String> workingHours = new ArrayList<>();
        while (in.hasNextLine()){
            String line = in.nextLine();
            workingHours.add(line);
        }
        in.close();
        return workingHours;
    }

    public static void writeStoreScheduleToFile(List<String> lines) {
        try {
            PrintWriter out = new PrintWriter(storeScheduleFile);
            String res = "";
            for (String s : lines){
                res += s;
            }
            out.print(res);
            out.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeLineToOutputFile(String line) {
        try {
            PrintWriter out = new PrintWriter(outputFile);
            out.print(line);
            out.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
