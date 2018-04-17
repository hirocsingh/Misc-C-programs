
/**********************************************
Workshop #5
Course:JAC444 - 4th (WINTER)
Last Name:Singh
First Name:Avinash
ID:115408163
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature
File: BMICalculatorInterface.java
**********************************************/

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BMICalculatorInterface extends Remote {

        public String BMI_result(double height, double weight) throws RemoteException;

        public double height() throws RemoteException;

        public double weight() throws RemoteException;

}
