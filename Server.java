
/**********************************************
Workshop #5
Course:JAC444 - 4th (WINTER)
Last Name:Singh
First Name:Avinash
ID:115408163
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature
File: Server.java
**********************************************/

import java.io.IOException;
import java.net.*;
import java.util.Vector;
import java.io.*;
import java.util.*;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.*;
import java.util.Scanner;



public class Server {
        static Vector<Socket> ClientSockets;
        int clientCount = 0;
        //int i = 0;
       

        Server() throws IOException {
                Date dNow = new Date();
                System.out.println("MultiThreadServer started at " + String.format("%tc", dNow));
                System.out.println();

                ServerSocket server = new ServerSocket(8000);
                ClientSockets = new Vector<Socket>();
                

                while (true) {
                        Socket client = server.accept();
                        AcceptClient acceptClient = new AcceptClient(client);
                        System.out.println("Connection from Socket " + "[addr = " + client.getLocalAddress() + ",port = "
                                        + client.getPort() + ",localport = " + client.getLocalPort() + "] at "
                                        + String.format("%tc", dNow));
                        System.out.println();
                        //System.out.println(clientCount);
                        
                }
                //server.close();
        }

        public static void main(String[] args) throws IOException {
                Server server = new Server();
        }

        class AcceptClient extends Thread {
                Socket ClientSocket;
                DataInputStream din;
                DataOutputStream dout;

                AcceptClient(Socket client) throws IOException {
                        ClientSocket = client;
                        din = new DataInputStream(ClientSocket.getInputStream());
                        dout = new DataOutputStream(ClientSocket.getOutputStream());

                        //String LoginName = din.readUTF();
                        //i = clientCount;
                        clientCount++;
                        ClientSockets.add(ClientSocket);
                        //System.out.println(ClientSockets.elementAt(i));
                        //System.out.println(ClientSockets.elementAt(1));

                        start();
                }

                public void run() {
                        
                                try {
                                        while (true) {
                                        String msgFromClient = din.readUTF();
                                        System.out.println(msgFromClient);
                                        for (int i = 0; i < ClientSockets.size(); i++) {
                                                Socket pSocket = (Socket) ClientSockets.elementAt(i);
                                                DataOutputStream pOut = new DataOutputStream(pSocket.getOutputStream());
                                                pOut.writeUTF(msgFromClient);
                                                pOut.flush();
                                        }
                                }
                                        
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        
                }
        }
}