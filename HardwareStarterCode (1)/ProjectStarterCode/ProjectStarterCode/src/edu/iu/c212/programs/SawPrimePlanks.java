package edu.iu.c212.programs;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SawPrimePlanks {
    private static boolean isPrime(int n){
        int bound = (int) Math.sqrt(n);
        for (int i=2; i<bound; i++){
            if(n % i==0){
                return false;
            }
        }
        return true;
    }


    /**
     * You will saw planks recursively into a prime number of planks
     * at each step until no more composite-number length planks remain.
     * @param plankLength - given plank's length
     * @return how many planks we will have after sawing
     */
    public static int sawPlank(int plankLength){
        List<Integer> primeNumbers = new ArrayList<>(Arrays.asList(2,3,5,7,11,13,15,17,19));
        if (isPrime(plankLength)){
            return 1;
        }
        for (int i = 0; i < primeNumbers.size(); i++) {
            if (plankLength % primeNumbers.get(i) == 0){
                return primeNumbers.get(i);
            }
        }
        return 1;
    }


    public static List<Integer> getPlankLengths(int longPlankLength){
        int length = longPlankLength;
        List<Integer> plankLengths = new ArrayList<>();
        int totalQty = 1;
        while (!isPrime(length)){
            int qty = sawPlank(length);
            length = length / qty;
            totalQty = totalQty * qty;
        }

        for (int i = 0; i < totalQty; i++) {
            plankLengths.add(length);
        }
        return plankLengths;
    }

    //195
    //for each 195: [65,65,65] totalQty: 3
    //for each 65: [13,13,13,13,13] totalQty: 3 * 5
    //return the list which has 15 int 13;
    //[13,13,13,13,13,13,13,13,13,13,13,13,13,13,13]

}
