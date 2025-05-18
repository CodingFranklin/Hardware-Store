package edu.iu.c212.programs;

import edu.iu.c212.models.Staff;
import edu.iu.c212.utils.FileUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class StaffScheduler {
    public static Comparator<Staff> staffComparator1 = new Comparator<Staff>() {
        @Override
        public int compare(Staff o1, Staff o2) {
            return o1.getAvailability().split("\\.").length
                    - o2.getAvailability().split("\\.").length;
        }
    };
    public static Comparator<Staff> staffComparator2 = new Comparator<Staff>() {
        @Override
        public int compare(Staff o1, Staff o2) {
            return o2.getAvailability().split("\\.").length
                    - o1.getAvailability().split("\\.").length;
        }
    };

    public static Comparator<String> lastNameComparator = new Comparator<String>() {
        @Override
        public int compare(String name1, String name2) {
            String[] parts1 = name1.split(" ");
            String[] parts2 = name2.split(" ");

            String lastName1 = parts1[parts1.length - 1];
            String lastName2 = parts2[parts2.length - 1];

            return Character.compare(lastName1.charAt(0), lastName2.charAt(0));
        }
    };

    private static int howManyAlready(List<List<String>> weekdays, String name){
        int count = 0;
        for (int i = 0; i < weekdays.size(); i++) {
            for (int j = 0; j < weekdays.get(i).size(); j++) {
                if (weekdays.get(i).get(j).equals(name)){
                    count++;
                }
            }
        }
        return count;
    }



    /**
     * This program reads in staff availability and shifts of the week. It will create
     * schedules such that no one employee is working every day while others are working
     * 0.It will do this by calculating the total hours from shifts of the week input file
     * and evenly distributing the hours for days worked (as best as possible) to the staff
     * availability list.
     */
    public static void scheduleStaff() throws IOException {
        List<Staff> staffs = FileUtils.readStaffFromFile();
        List<String> shiftSchedule = FileUtils.readShiftSchedulesFromFile();

        List<String> Monday = new ArrayList<>();
        List<String> Tuesday = new ArrayList<>();
        List<String> Wednesday = new ArrayList<>();
        List<String> Thursday = new ArrayList<>();
        List<String> Friday = new ArrayList<>();
        List<String> Saturday = new ArrayList<>();
        List<String> Sunday = new ArrayList<>();

        List<List<String>> weekdays = new ArrayList<>();
        weekdays.add(Monday);
        weekdays.add(Tuesday);
        weekdays.add(Wednesday);
        weekdays.add(Thursday);
        weekdays.add(Friday);
        weekdays.add(Saturday);
        weekdays.add(Sunday);

        //find the smallest number of days in the staff's availability
        int leastDays = 100;
        for (Staff staff : staffs){
            if (leastDays > staff.getAvailability().split("\\.").length){
                leastDays = staff.getAvailability().split("\\.").length;
            }
        }
        leastDays--;


        PriorityQueue<Staff> smallToBig = new PriorityQueue<>(staffComparator1);
        smallToBig.addAll(staffs);
        while (!smallToBig.isEmpty()){
            Staff staff = smallToBig.poll();
            String[] days = staff.getAvailability().split("\\.");
            if (days.length == leastDays){
                for (String day : days) {
                    if (day.equals("M")) {
                        Monday.add(staff.getFullName());
                    }
                    if (day.equals("T")) {
                        Tuesday.add(staff.getFullName());
                    }
                    if (day.equals("W")) {
                        Wednesday.add(staff.getFullName());
                    }
                    if (day.equals("TR")) {
                        Thursday.add(staff.getFullName());
                    }
                    if (day.equals("F")) {
                        Friday.add(staff.getFullName());
                    }
                    if (day.equals("SAT")) {
                        Saturday.add(staff.getFullName());
                    }
                    if (day.equals("SUN")) {
                        Sunday.add(staff.getFullName());
                    }
                }
            }
            else {
                //find the day that has the least number of staffs
                int least = 100;
                for (int i = 0; i < leastDays; i++) {
                    if (least > Monday.size()){
                        least = Monday.size();
                    }
                    else if (least > Tuesday.size()){
                        least = Tuesday.size();
                    }
                    else if (least > Wednesday.size()){
                        least = Wednesday.size();
                    }
                    else if (least > Thursday.size()){
                        least = Thursday.size();
                    }
                    else if (least > Friday.size()){
                        least = Friday.size();
                    }
                    else if (least > Saturday.size()){
                        least = Saturday.size();
                    }
                    else if (least > Sunday.size()){
                        least = Sunday.size();
                    }
                    for (String day : days) {
                        int howManyAlready = howManyAlready(weekdays, staff.getFullName());

                        if (day.equals("M") && Monday.size() == least && Monday.size() < leastDays + 1) {
                            if (howManyAlready < leastDays + 1) {
                                Monday.add(staff.getFullName());
                            }
                        }
                        if (day.equals("T") && Tuesday.size() == least && Tuesday.size() < leastDays + 1) {
                            if (howManyAlready < leastDays + 1) {
                                Tuesday.add(staff.getFullName());
                            }
                        }
                        if (day.equals("W") && Wednesday.size() == least && Wednesday.size() < leastDays + 1) {
                            if (howManyAlready < leastDays + 1) {
                                Wednesday.add(staff.getFullName());
                            }
                        }
                        if (day.equals("TR") && Thursday.size() == least && Thursday.size() < leastDays + 1) {
                            if (howManyAlready < leastDays + 1) {
                                Thursday.add(staff.getFullName());
                            }
                        }
                        if (day.equals("F") && Friday.size() == least && Friday.size() < leastDays + 1) {
                            if (howManyAlready < leastDays + 1) {
                                Friday.add(staff.getFullName());
                            }
                        }
                        if (day.equals("SAT") && Saturday.size() == least && Saturday.size() < leastDays + 1) {
                            if (howManyAlready < leastDays + 1) {
                                Saturday.add(staff.getFullName());
                            }
                        }
                        if (day.equals("SUN") && Sunday.size() == least && Sunday.size() < leastDays + 1) {
                            if (howManyAlready < leastDays + 1) {
                                Sunday.add(staff.getFullName());
                            }
                        }
                    }
                }
            }
        }

        int largest = 0;
        for (Staff staff : staffs) {
            if (howManyAlready(weekdays, staff.getFullName()) > largest) {
            largest = howManyAlready(weekdays, staff.getFullName());
            }
        }

        for (int i = 0; i < weekdays.size(); i++) {
            for (int j = 0; j < weekdays.get(i).size(); j++) {
                if (howManyAlready(weekdays, weekdays.get(i).get(j)) == largest){
                    weekdays.get(i).remove(j);
                }
            }
        }

        PriorityQueue<Staff> bigToSmall = new PriorityQueue<>(staffComparator2);
        bigToSmall.addAll(staffs);
        while (!bigToSmall.isEmpty()){
            Staff staff = bigToSmall.poll();
            String[] days = staff.getAvailability().split("\\.");
            if (days.length == leastDays){
                for (String day : days) {
                    if (day.equals("M")) {
                        Monday.add(staff.getFullName());
                    }
                    if (day.equals("T")) {
                        Tuesday.add(staff.getFullName());
                    }
                    if (day.equals("W")) {
                        Wednesday.add(staff.getFullName());
                    }
                    if (day.equals("TR")) {
                        Thursday.add(staff.getFullName());
                    }
                    if (day.equals("F")) {
                        Friday.add(staff.getFullName());
                    }
                    if (day.equals("SAT")) {
                        Saturday.add(staff.getFullName());
                    }
                    if (day.equals("SUN")) {
                        Sunday.add(staff.getFullName());
                    }
                }
            }
            else {
                //find the day that has the least number of staffs
                int least = 100;
                for (int i = 0; i < leastDays; i++) {
                    if (least > Monday.size()){
                        least = Monday.size();
                    }
                    else if (least > Tuesday.size()){
                        least = Tuesday.size();
                    }
                    else if (least > Wednesday.size()){
                        least = Wednesday.size();
                    }
                    else if (least > Thursday.size()){
                        least = Thursday.size();
                    }
                    else if (least > Friday.size()){
                        least = Friday.size();
                    }
                    else if (least > Saturday.size()){
                        least = Saturday.size();
                    }
                    else if (least > Sunday.size()){
                        least = Sunday.size();
                    }
                    for (String day : days) {
                        int howManyAlready = howManyAlready(weekdays, staff.getFullName());

                        if (day.equals("M") && Monday.size() == least && Monday.size() < leastDays + 1) {
                            if (howManyAlready < leastDays + 1) {
                                Monday.add(staff.getFullName());
                            }
                        }
                        if (day.equals("T") && Tuesday.size() == least && Tuesday.size() < leastDays + 1) {
                            if (howManyAlready < leastDays + 1) {
                                Tuesday.add(staff.getFullName());
                            }
                        }
                        if (day.equals("W") && Wednesday.size() == least && Wednesday.size() < leastDays + 1) {
                            if (howManyAlready < leastDays + 1) {
                                Wednesday.add(staff.getFullName());
                            }
                        }
                        if (day.equals("TR") && Thursday.size() == least && Thursday.size() < leastDays + 1) {
                            if (howManyAlready < leastDays + 1) {
                                Thursday.add(staff.getFullName());
                            }
                        }
                        if (day.equals("F") && Friday.size() == least && Friday.size() < leastDays + 1) {
                            if (howManyAlready < leastDays + 1) {
                                Friday.add(staff.getFullName());
                            }
                        }
                        if (day.equals("SAT") && Saturday.size() == least && Saturday.size() < leastDays + 1) {
                            if (howManyAlready < leastDays + 1) {
                                Saturday.add(staff.getFullName());
                            }
                        }
                        if (day.equals("SUN") && Sunday.size() == least && Sunday.size() < leastDays + 1) {
                            if (howManyAlready < leastDays + 1) {
                                Sunday.add(staff.getFullName());
                            }
                        }
                    }
                }
            }
        }

        //sort by alphabetical order
        for (int i = 0; i < weekdays.size(); i++) {
            weekdays.get(i).sort(lastNameComparator);
        }



        String M = "M ";
        for (int i = 0; i < Monday.size(); i++) {
            M += "(" + Monday.get(i) + ") ";
        }
        String T = "T ";
        for (int i = 0; i < Tuesday.size(); i++) {
            T += "(" + Tuesday.get(i) + ") ";
        }
        String W = "W ";
        for (int i = 0; i < Wednesday.size(); i++) {
            W += "(" + Wednesday.get(i) + ") ";
        }
        String TR = "TR ";
        for (int i = 0; i < Thursday.size(); i++) {
            TR += "(" + Thursday.get(i) + ") ";
        }
        String F = "F ";
        for (int i = 0; i < Friday.size(); i++) {
            F += "(" + Friday.get(i) + ") ";
        }
        String SAT = "SAT ";
        for (int i = 0; i < Saturday.size(); i++) {
            SAT += "(" + Saturday.get(i) + ") ";
        }
        String SUN = "SUN ";
        for (int i = 0; i < Sunday.size(); i++) {
            SUN += "(" + Sunday.get(i) + ") ";
        }


        //*********************************
        //missing the creating date!!!!!!!!!!!!!!!
        //example : Created on 4/12/2024 at 1727
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        int day = currentDate.getDayOfMonth();
        String hour = currentTime.getHour() + "";
        String minute = currentTime.getMinute() + "";
        if (hour.length() == 1){
            hour = "0" + hour;
        }
        if (minute.length() == 1){
            minute = "0" + minute;
        }
        String time = "Created on " + month + "/" + day + "/" + year
                + " at " + hour + minute;

        List<String> lines = new ArrayList<>();
        lines.add(time + "\n");
        lines.add(M + "\n");
        lines.add(T + "\n");
        lines.add(W + "\n");
        lines.add(TR + "\n");
        lines.add(F + "\n");
        lines.add(SAT + "\n");
        lines.add(SUN);


//        String res = time + "\n" + M + "\n" + T + "\n" + W + "\n" + TR + "\n" + F + "\n" + SAT + "\n" + SUN;
        FileUtils.writeStoreScheduleToFile(lines);

    }

}
