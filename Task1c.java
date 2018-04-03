/*
        Name: Avinash Singh
        Date: 23-March-2017
        File: Task1c.java
        
        Wprkshop 4

        Using Streams.

        JAC444
        Prof. Mahbob Ali.
*/


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Task1c {

        public static void main (String args[]){

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
                        "Jacob"
                );
        topNames2017
            .stream()
            .map(name->{return Character.toUpperCase(name.charAt(0))+name.substring(1);})
            .sorted()
            .forEach(System.out::println);
        }
}