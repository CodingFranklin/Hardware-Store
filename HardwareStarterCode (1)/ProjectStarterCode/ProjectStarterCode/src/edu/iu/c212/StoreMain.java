package edu.iu.c212;

import edu.iu.c212.programs.StaffScheduler;

import java.io.IOException;

public class StoreMain {
    /**
     * This will instantiate the Store object, which will handle everything about the store.
     * @param args
     */
    public static void main(String[] args) throws IOException {
        Store hardwareStore = new Store();
        StaffScheduler.scheduleStaff();
        hardwareStore.saveItemsFromFile();
        hardwareStore.saveStaffFromFile();
    }
}
