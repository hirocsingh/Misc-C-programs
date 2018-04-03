/*
        Name: Avinash Singh
        Date: 23-March-2017
        File: Task1a.java
        
        Wprkshop 4

        JAC444
        Prof. Mahbob Ali.
*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



public class Task1a{
        
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
                List<String> newList = new ArrayList<String>();
                topNames2017.forEach(name->newList.add(Character.toUpperCase(name.charAt(0))+name.substring(1)));
                newList.sort((s1,s2)->s1.compareTo(s2));
                newList.forEach(name->System.out.println(name));
        }

        
}