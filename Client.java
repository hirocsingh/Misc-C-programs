
/**********************************************
Workshop #5
Course:JAC444 - 4th (WINTER)
Last Name:Singh
First Name:Avinash
ID:115408163
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature
File: Client.java
**********************************************/

import java.net.Socket;
import java.util.Scanner;
import java.io.*;
import java.net.*;


public class Client implements Runnable{

        Socket socketConnection;
        DataOutputStream outToServer; 
        DataInputStream din;
        
        
        Client() throws UnknownHostException, IOException {

                socketConnection = new Socket("127.0.0.1", 8000);
                outToServer = new DataOutputStream(socketConnection.getOutputStream());
                din = new DataInputStream(socketConnection.getInputStream());
                
                
                Thread thread;
                thread = new Thread(this);
                thread.start();

                BufferedReader br = null;
                String ClientName = null;
                Scanner input = new Scanner(System.in);
                String SQL = "";
                try {
                        System.out.print("Enter you name: ");
                        ClientName = input.next();
                        ClientName += ": ";
                        //QUERY PASSING

                        br = new BufferedReader(new InputStreamReader(System.in));
                        while (!SQL.equalsIgnoreCase("exit")) {
                                System.out.println();
                                System.out.print(ClientName);

                                SQL = br.readLine();
                                //SQL = input.next();
                                outToServer.writeUTF(ClientName + SQL);
                                //outToServer.flush();
                                //System.out.println(din.readUTF());

                        }

                } catch (Exception e) {
                        System.out.println(e);
                }
        }

        public static void main(String[] arg) throws UnknownHostException, IOException {
                Client client = new Client();
        }

        
        public void run() {
                while (true) {
                        try {
                                System.out.println("\n" + din.readUTF());
                                
                        } catch (IOException e) {
                                e.printStackTrace();
                        }

                }
        }
}