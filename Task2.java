/*
        Name: Avinash Singh
        Date: 23-March-2017
        File: Task2.java
        
        Wprkshop 4

        JAC444
        Prof. Mahbob Ali.
*/


import java.lang.*;
import java.io.*;
import java.util.Scanner;
import java.util.regex.*;

public class Task2 {

        public static void main(String[] args) {

                String name;
                String gender;
                String year;
                String choice = "Y";

                while (!choice.equalsIgnoreCase("N")) {
                        Scanner input = new Scanner(System.in);

                       
                        do {
                                System.out.print("Enter the year: ");
                                while (!input.hasNextInt()) {
                                        System.out.print("ERROR: Year must be an integer!\nEnter the year: ");
                                        input.next();
                                }
                                year = input.next();
                                if (Integer.parseInt(year) < 2001 || Integer.parseInt(year) > 2010) {
                                        System.out.print("ERROR: Year must be from 2001 to 2010!\n");
                                }
                        } while (Integer.parseInt(year) < 2001 || Integer.parseInt(year) > 2010);

                        do {
                                System.out.print("Enter the gender: ");
                                gender = input.next();
                                if (!Pattern.matches("[mMfF]{1}", gender)) {
                                        System.out.println("ERROR: Only M or F accepted!");
                                }
                        } while (!Pattern.matches("[mMfF]{1}", gender));

                        do {
                                System.out.print("Enter the name: ");
                                name = input.next();
                                if (!Pattern.matches("[a-zA-Z]*", name)) {
                                        System.out.println("ERROR: Invalid name");
                                }
                        } while (!Pattern.matches("[a-zA-Z]*", name));
                        
                        int rank = 0;
                
                        try{
                                // Open the file that is the first 
                                // command line parameter
                                FileInputStream fstream = new FileInputStream("babynameranking" + year + ".txt");
                                // Get the object of DataInputStream
                                DataInputStream in = new DataInputStream(fstream);
                                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                                String strLine;
                                while ((strLine = br.readLine()) != null)   {
                                        String[] temp = strLine.split(" ");
                                        if (gender.equalsIgnoreCase("M") && temp[1].contains(name))
                                                rank = Integer.parseInt(temp[0]);
                                        else if (gender.equalsIgnoreCase("F") && temp[3].contains(name))
                                                rank = Integer.parseInt(temp[0]);

                                }
                                if (rank == 0) {
                                        System.out.println("The name " + name + " is not ranked in year " + year);
                                } else {
                                        System.out.println(name + " is ranked #" + rank + " in year " + year);
                                }
                                //Close the input stream
                                in.close();
                        } catch (Exception e){//Catch exception if any
                                System.err.println("Error: " + e.getMessage());
                        }
 
                        do {
                                System.out.print("Enter another inquiry? (Y/N) ");
                                choice = input.next();
                                if (!Pattern.matches("[nNyY]{1}", choice)) {
                                        System.out.println("ERROR: Only Y or N accepted!");
                                }
                        } while (!Pattern.matches("[nNyY]{1}", choice));

                        //input.close();
                }
        }
}