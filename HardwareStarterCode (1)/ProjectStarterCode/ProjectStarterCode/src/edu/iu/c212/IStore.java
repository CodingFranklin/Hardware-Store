package edu.iu.c212;

import edu.iu.c212.models.Item;
import edu.iu.c212.models.Staff;
import edu.iu.c212.utils.FileUtils;

import java.io.IOException;
import java.util.List;

public interface IStore {
    /**
     * This will call FileUtils.readInventoryFromFile that reads in all the items
     * from inventory.txt, and uses System.exit to exit the program if an exception
     * is thrown
     * ##
     * Each item will be its own line in the file, and will be of the form “<itemName>
     * <itemQuantity> <itemCost> <itemAisle>”. The inventory.txt file is
     * comma-delimited.
     * ##
     * @return
     */
    List<Item> getItemsFromFile();

    /**
     * This will call FileUtils.readStaffFromFile that reads in all the staff from
     * staff availability IN.txt, and use System.exit to exit the program if an
     * exception is thrown.
     * @return
     */
    List<Staff> getStaffFromFile();


    /**
     * This will call FileUtils.writeInventoryToFile that saves all the items from
     * inventory.txt, and uses System.exit to exit the program if an exception is
     * thrown.
     */
    void saveItemsFromFile();

    /**
     * This will call FileUtils.writeStaffToFile that saves all the staff to staff -
     * availability IN.txt, and use System.exit to exit the program if an exception is thrown
     */
    void saveStaffFromFile();
    void takeAction();


}
