
/**********************************************
Workshop #5
Course:JAC444 - 4th (WINTER)
Last Name:Singh
First Name:Avinash
ID:115408163
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature
File: BMICalculatorImpl.java
**********************************************/

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BMICalculatorImpl implements BMICalculatorInterface {

        public double m_height = 0.0;
        public double m_weight = 0.0;

        public BMICalculatorImpl() throws RemoteException {}

        public String BMI_result(double height, double weight) throws RemoteException {
                String BMI_result = null;
                double BMI = 0.0;

                m_height = height;
                m_weight = weight;

                BMI = weight / (height * height);

                if (BMI < 18.5)
                        BMI_result = "Underweight";
                else if (BMI >= 18.5 && BMI <= 25.0)
                        BMI_result = "Normal";
                else if (BMI >= 25.0 && BMI < 30.0)
                        BMI_result = "Overweight";
                else
                        BMI_result = "Obese";

                return BMI_result;
        }

        public double height() throws RemoteException {
                return m_height;
        }

        public double weight() throws RemoteException {
                return m_weight;
        }

}
