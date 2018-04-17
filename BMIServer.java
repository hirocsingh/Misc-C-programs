
/**********************************************
Workshop #5
Course:JAC444 - 4th (WINTER)
Last Name:Singh
First Name:Avinash
ID:115408163
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature
File: BMIServer.java
**********************************************/

//import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.Date;

public class BMIServer extends BMICalculatorImpl {

        
        public BMIServer() throws RemoteException {}
        

        public static void main(String[] args) throws RemoteException {
                try {
                        Date dNow = new Date();
                        BMICalculatorImpl cal = new BMICalculatorImpl();

                        BMICalculatorInterface stub = (BMICalculatorInterface) UnicastRemoteObject.exportObject(cal, 0);
                        Registry registry = LocateRegistry.getRegistry();

                        registry.bind("BMICalculator", stub);

                        System.out.println("\nBMIServer started at " + String.format("%tc", dNow));
                        
                        System.out.println("\nConnected to a client at started at " + String.format("%tc", dNow));

                        System.out.println("\nWaiting for height and weight from client:");

                        //System.out.println("\nHeight: " + stub.height);
                        //System.out.println("\nWeight: " + stub.weight);

                        System.out.println("\nCalculating BMI...");

                } catch (Exception e) {
                        System.err.println("Server exception: " + e.toString());
                        e.printStackTrace();
                }

                new BMIServer();
        }

}
