
/*
        Name: Avinash Singh
        Date: 23-March-2017
        File: Task1b.java
        
        Wprkshop 4

        Using Method Reference.

        JAC444
        Prof. Mahbob Ali.
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



public class Task1b {

        

        public static void main(String args[]) {
                List<String> topNames2017 = Arrays.asList(
                        "Amelia", 
                        "Olivia", 
                        "emily", 
                        "Isla", 
                        "Ava", 
                        "oliver", 
                        "Jack",
                        "Charlie", 
                        "harry", 
                        "Jacob");

        
                List<String> newList = new ArrayList<String>();
                topNames2017.forEach(name -> newList.add(Character.toUpperCase(name.charAt(0)) + name.substring(1)));
                newList.sort((a, b) -> a.compareTo(b));
                newList.forEach(System.out::println);
        }
}




