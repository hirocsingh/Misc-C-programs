
/**********************************************
Workshop #5
Course:JAC444 - 4th (WINTER)
Last Name:Singh
First Name:Avinash
ID:115408163
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature
File: BMIClient.java
**********************************************/

import java.net.MalformedURLException;
//import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class BMIClient {

        private BMIClient() {}

        public static void main(String[] args) {

                try {
                        Registry registry = LocateRegistry.getRegistry(null);

                        BMICalculatorInterface stub = (BMICalculatorInterface) registry.lookup("BMICalculator");

                        double c_height = 0.0;
                        double c_weight = 0.0;
                        String result = null;

                        Scanner input = new Scanner(System.in);
                        System.out.print("\nWrite your height in meters (ex 1.80): ");
                        c_height = input.nextDouble();
                        System.out.print("\nWrite your weight in kilograms (ex: 70.5): ");
                        c_weight = input.nextDouble();

                        result = stub.BMI_result(c_height, c_weight);

                        System.out.println("\nWaiting for BMI from server…");

                        System.out.println("\nYour BMI is: " + result + "\n");

                } catch (Exception e) {
                        System.err.println("Client exception: " + e.toString());
                        e.printStackTrace();
                }
        }

        

}
