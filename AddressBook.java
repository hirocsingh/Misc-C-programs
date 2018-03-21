/*
        Author: Avinash Singh
        Date: 19 - March - 2018
        JAC444

        WORKSHOP 3 - Address Book.
        Using Random Access File and JavaFx in Java.
*/


import javafx.application.Application;
import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.RandomAccessFile;



import java.io.IOException;
import javafx.event.*;


public class AddressBook extends Application {

        // Variables for Internal process.
        private String Name;
        private String Street;
        private String City;
        private String State;
        private String Zip;
        private static long FilePointer = 0;
        private static long tempFilePointer = 0;
        private static long tempFilePointer2 = 0;
        private static long internalPointer = (long) 7 + (long) 4 + (long) 22 + (long) 34 + (long) 35;
        boolean NameUpdated = false;
        boolean StreetUpdated = false;
        boolean StateUpdated = false;
        boolean CityUpdated = false;
        boolean ZipUpdated = false;
        //private int ContactCount = 0;

        //creating labels.
        Text Name_label = new Text("Name");
        Text Street_label = new Text("Street");
        Text City_label = new Text("City");
        Text State_label = new Text("State");
        Text Zip_label = new Text("Zip");

        //Creating Text Fileds.        
        TextField Name_Field = new TextField();
        TextField Street_Field = new TextField();
        TextField City_Field = new TextField();
        TextField State_Field = new TextField();
        TextField Zip_Field = new TextField();
        

        //Creating Buttons 
        Button Add = new Button("Add");
        Button First = new Button("First");
        Button Next = new Button("Next");
        Button Previous = new Button("Previous");
        Button Last = new Button("Last");
        Button Update = new Button("Update");
        Button Clear = new Button("Clear");

        // Alert message IDs.
        final private int NAME_SIZE_ALERT = 1;
        final private int STREET_SIZE_ALERT = 2;
        final private int CITY_SIZE_ALERT = 3;
        final private int STATE_SIZE_ALERT = 4;
        final private int ZIP_SIZE_ALERT = 5;
        final private int CLEAR_BUTTON = 6;

        static long getFilePointer() {
                return FilePointer;
        }

        static void setFilePointer(long pointer) {
                FilePointer = pointer;
        }

        @Override
        public void start(Stage stage) {

                Name_Field.setEditable(true);
                Street_Field.setEditable(true);
                City_Field.setEditable(true);
                State_Field.setEditable(true);
                Zip_Field.setEditable(true);
                
                State_Field.setPrefWidth((long)3);

                //Creating a Grid Pane 
                GridPane gridPane = new GridPane();

                //Setting size for the pane 
                gridPane.setMinSize(330, 270);

                //Setting the padding  
                gridPane.setPadding(new Insets(5, 5, 5, 5));

                //Setting the vertical and horizontal gaps between the columns 
                gridPane.setVgap(2);
                gridPane.setHgap(2);

                //Setting the Grid alignment 
                gridPane.setAlignment(Pos.TOP_LEFT);

                //Arranging all the nodes in the grid
                // Fields and Labels.
                gridPane.add(Name_label, 0, 0); 
                gridPane.add(Name_Field, 1, 0);

                gridPane.add(Street_label, 0, 1);
                gridPane.add(Street_Field, 1, 1);

                gridPane.add(City_label, 0, 2);
                gridPane.add(City_Field, 1, 2);

                gridPane.add(State_label, 0, 3);
                gridPane.add(State_Field, 1, 3);

                gridPane.add(Zip_label, 0, 4);
                gridPane.add(Zip_Field, 1, 4);
                // Buttons.
                gridPane.add(Add, 1, 5);
                gridPane.add(First, 2, 0);
                gridPane.add(Next, 3, 1);
                gridPane.add(Previous, 2, 1);
                gridPane.add(Last, 3, 0);
                gridPane.add(Update, 1, 6);
                gridPane.add(Clear, 2, 5);
               

                //Styling nodes (Buttons).
                Add.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
                First.setStyle("-fx-background-color: green; -fx-text-fill: black;");
                Next.setStyle("-fx-background-color: green; -fx-text-fill: black;");
                Last.setStyle("-fx-background-color: green; -fx-text-fill: black;");
                Previous.setStyle("-fx-background-color: green; -fx-text-fill: black;");
                Update.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
                Clear.setStyle("-fx-background-color: red; -fx-text-fill: black;");

                Name_Field.setStyle("-fx-font: normal bold 20px 'serif' ");
                Street_Field.setStyle("-fx-font: normal bold 20px 'serif' ");
                City_Field.setStyle("-fx-font: normal bold 20px 'serif' ");
                State_Field.setStyle("-fx-font: normal bold 20px 'serif' ");
                Zip_Field.setStyle("-fx-font: normal bold 20px 'serif' ");

                gridPane.setStyle("-fx-background-color: BEIGE;");

                //Creating a scene object 
                Scene scene = new Scene(gridPane);

                //Setting title to the Stage 
                stage.setTitle("Address Book");

                //Adding scene to the stage 
                stage.setScene(scene);

                //Displaying the contents of the stage 
                stage.show();


                EventHandler<ActionEvent> AddbuttonHandler = new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                                
                                boolean cleanInput = verifyUserInput();

                                while(!cleanInput)
                                        cleanInput = verifyUserInput();

                                if(cleanInput){
                                        if(event.getSource() == Add)
                                                AddContact();
                                        else if (event.getSource() == First)
                                                FirstContactInRecord();
                                        else if (event.getSource() == Last)
                                                LastContactInRecord();
                                        else if (event.getSource() == Previous)
                                                PreviousContactInRecord();
                                        else if (event.getSource() == Update)
                                                UpdateContactInRecord();
                                        else if (event.getSource() == Next)
                                                NextContactInRecord();
                                }

                                if (event.getSource() == Clear)
                                        ClearEntry(CLEAR_BUTTON);
                                        
                                event.consume();
                        }
                };

                Name_Field.focusedProperty().addListener((arg0, oldValue, newValue) -> {
                        if (!newValue) { // when focus lost
                                if (Name_Field.getText().length() > 32) {
                                        alertMessages(NAME_SIZE_ALERT);
                                        ClearEntry(NAME_SIZE_ALERT);
                                        Name_Field.requestFocus();
                                }
                        }
                });
                Street_Field.focusedProperty().addListener((arg0, oldValue, newValue) -> {
                        if (!newValue) { // when focus lost
                                if (Street_Field.getText().length() > 32) {
                                        alertMessages(STREET_SIZE_ALERT);
                                        ClearEntry(STREET_SIZE_ALERT);
                                        Street_Field.requestFocus();
                                }
                        }
                });
                City_Field.focusedProperty().addListener((arg0, oldValue, newValue) -> {
                        if (!newValue) { // when focus lost
                                if (City_Field.getText().length() > 20) {
                                        alertMessages(CITY_SIZE_ALERT);
                                        ClearEntry(CITY_SIZE_ALERT);
                                        City_Field.requestFocus();
                                }
                        }
                });
                State_Field.focusedProperty().addListener((arg0, oldValue, newValue) -> {
                        if (!newValue) { // when focus lost
                                if (State_Field.getText().length() > 2) {
                                        alertMessages(STATE_SIZE_ALERT);
                                        ClearEntry(STATE_SIZE_ALERT);
                                        State_Field.requestFocus();
                                }
                        }
                });
                Zip_Field.focusedProperty().addListener((arg0, oldValue, newValue) -> {
                        if (!newValue) { // when focus lost
                                if (Zip_Field.getText().length() > 5) {
                                        alertMessages(ZIP_SIZE_ALERT);
                                        ClearEntry(ZIP_SIZE_ALERT);
                                        Zip_Field.requestFocus();
                                }
                        }
                });

                Name_Field.textProperty().addListener((observable, oldValue, newValue) -> {
                        System.out.println("Name changed from " + oldValue + " to " + newValue);
                        NameUpdated = true;
                });

                Street_Field.textProperty().addListener((observable, oldValue, newValue) -> {
                        System.out.println("Street changed from " + oldValue + " to " + newValue);
                        StreetUpdated = true;
                });

                State_Field.textProperty().addListener((observable, oldValue, newValue) -> {
                        System.out.println("State changed from " + oldValue + " to " + newValue);
                        StateUpdated = true;
                });

                City_Field.textProperty().addListener((observable, oldValue, newValue) -> {
                        System.out.println("City changed from " + oldValue + " to " + newValue);
                        CityUpdated = true;
                });

                Zip_Field.textProperty().addListener((observable, oldValue, newValue) -> {
                        System.out.println("Zip changed from " + oldValue + " to " + newValue);
                        ZipUpdated = true;
                });

                Add.setOnAction(AddbuttonHandler);
                First.setOnAction(AddbuttonHandler);
                Next.setOnAction(AddbuttonHandler);
                Previous.setOnAction(AddbuttonHandler);
                Last.setOnAction(AddbuttonHandler);
                Update.setOnAction(AddbuttonHandler);
                Clear.setOnAction(AddbuttonHandler);
                
                
        }

        private void AddContact() {
                try (RandomAccessFile raFile = new RandomAccessFile("Address.dat", "rw")) {

                        long tempPointer = getFilePointer();
                        
                        raFile.seek(tempPointer);
                        //raFile.write("\n".getBytes());
                        raFile.write(Name.getBytes()); //add the Name
                        raFile.seek(raFile.getFilePointer() + 1); //set pointer to Name's length + 1  characters
                        raFile.write("\n".getBytes());
                        raFile.write(Street.getBytes());
                        raFile.seek(raFile.getFilePointer() + 1);
                        raFile.write("\n".getBytes());
                        raFile.write(City.getBytes());
                        raFile.seek(raFile.getFilePointer() + 1);
                        raFile.write("\n".getBytes());
                        raFile.write(State.getBytes());
                        raFile.seek(raFile.getFilePointer() + 1);
                        raFile.write("\n".getBytes());
                        raFile.write(Zip.getBytes());
                        raFile.seek(raFile.getFilePointer() + 1);
                        raFile.write("\n".getBytes());
                        raFile.write("\n".getBytes());

                        tempPointer = raFile.getFilePointer();
                        setFilePointer(tempPointer);

                        System.out.println(Name + "\n" + Street + "\n" + City + "\n" + State + "\n" + Zip);
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Contact Added");
                        alert.setHeaderText("SUCCESS");
                        alert.setContentText("1 Contact added succesfully !!!");

                        alert.showAndWait();

                } catch (IOException x) {
                        System.out.println("I/O Exception: " + x);
                } finally {
                        Name_Field.clear();
                        Street_Field.clear();
                        City_Field.clear();
                        State_Field.clear();
                        Zip_Field.clear();

                        Name = "";
                        Street = "";
                        City = "";
                        State = "";
                        Zip = "";
                }
        }

        private void FirstContactInRecord() {
                try (RandomAccessFile raFile = new RandomAccessFile("Address.dat", "r")) {
                        long temp = 0;
                        //temp = header.length();
                        raFile.seek(temp);
                        Name_Field.setText(raFile.readLine());
                        Street_Field.setText(raFile.readLine());
                        City_Field.setText(raFile.readLine());
                        State_Field.setText(raFile.readLine());
                        Zip_Field.setText(raFile.readLine());
                        tempFilePointer = temp + internalPointer;
                        
                        
                        //temp = 0;
                } catch (IOException x) {
                        System.out.println("I/O Exception: " + x);
                }

        }

        private void LastContactInRecord() {
                try (RandomAccessFile raFile = new RandomAccessFile("Address.dat", "r")) {
                        long temp = getFilePointer();

                        System.out.println(temp);
                        temp = temp - internalPointer;
                        System.out.println(temp);
                        raFile.seek(temp);
                        Name_Field.setText(raFile.readLine());
                        Street_Field.setText(raFile.readLine());
                        City_Field.setText(raFile.readLine());
                        State_Field.setText(raFile.readLine());
                        Zip_Field.setText(raFile.readLine());
                        //temp = 0;
                } catch (IOException x) {
                        System.out.println("I/O Exception: " + x);
                }
        }

        private void PreviousContactInRecord() {
                try (RandomAccessFile raFile = new RandomAccessFile("Address.dat", "r")) {
                        long tempPointer = 0;
                        tempPointer = tempFilePointer2 - internalPointer ;
                        System.out.println(tempPointer + "BEGINING");
                        raFile.seek(tempPointer);
                        Name_Field.setText(raFile.readLine());
                        Street_Field.setText(raFile.readLine());
                        City_Field.setText(raFile.readLine());
                        State_Field.setText(raFile.readLine());
                        Zip_Field.setText(raFile.readLine());
                        //tempPointer = tempPointer - (long) 7 - (long) 4 - (long) 22 - (long) 34 - (long) 35;
                        tempFilePointer2 = tempPointer;
                        System.out.println(tempPointer + "LEAVING");

                        if(tempPointer == 0){
                                tempPointer = getFilePointer();
                                System.out.println(tempPointer + "RESTART");
                                tempFilePointer2 = tempPointer;
                        }

                } catch (IOException x) {
                        System.out.println("I/O Exception: " + x);
                }
        }

        private void NextContactInRecord() {
                try (RandomAccessFile raFile = new RandomAccessFile("Address.dat", "r")) {
                        long tempPointer = 0;
                        tempPointer = tempFilePointer ;
                        System.out.println(tempPointer + "BEGINING");
                        raFile.seek(tempPointer);
                        Name_Field.setText(raFile.readLine());
                        Street_Field.setText(raFile.readLine());
                        City_Field.setText(raFile.readLine());
                        State_Field.setText(raFile.readLine());
                        Zip_Field.setText(raFile.readLine());
                        tempPointer = tempPointer + internalPointer;
                        tempFilePointer = tempPointer;
                        tempFilePointer2 = tempPointer - internalPointer;
                        System.out.println(tempPointer + "LEAVING");
                        System.out.println(tempFilePointer2);

                        if (tempPointer == getFilePointer()) {
                                tempPointer = 0;
                                System.out.println(tempPointer + "RESTART");
                                tempFilePointer = tempPointer;
                        }
                        
                } catch (IOException x) {
                        System.out.println("I/O Exception: " + x);
                }
        }

        private void UpdateContactInRecord() {
                long tempointer = 0;
                if(NameUpdated){
                        tempointer = tempFilePointer2;
                        System.out.println(tempointer + "CURRENT");
                        try (RandomAccessFile raFile = new RandomAccessFile("Address.dat", "rw")) {
                                raFile.seek(tempointer);
                                Name = Name_Field.getText();
                                Name = padString(Name, 32);
                                raFile.write(Name.getBytes());

                        } catch (IOException x) {
                                System.out.println("I/O Exception: " + x);
                        }

                }

                if (StreetUpdated) {
                        tempointer = tempFilePointer2 + 34;
                        System.out.println(tempointer + "CURRENT");
                        try (RandomAccessFile raFile = new RandomAccessFile("Address.dat", "rw")) {
                                raFile.seek(tempointer);
                                Street = Street_Field.getText();
                                Street = padString(Street, 32);
                                raFile.write(Street.getBytes());

                        } catch (IOException x) {
                                System.out.println("I/O Exception: " + x);
                        }

                }

                if (StateUpdated) {
                        tempointer = tempFilePointer2 + 34 + 34 + 22;
                        System.out.println(tempointer + "CURRENT");
                        try (RandomAccessFile raFile = new RandomAccessFile("Address.dat", "rw")) {
                                raFile.seek(tempointer);
                                State = State_Field.getText();
                                State = padString(State, 2);
                                raFile.write(State.getBytes());

                        } catch (IOException x) {
                                System.out.println("I/O Exception: " + x);
                        }

                }

                if (CityUpdated) {
                        tempointer = tempFilePointer2 + 34 + 34 ;
                        System.out.println(tempointer + "CURRENT");
                        try (RandomAccessFile raFile = new RandomAccessFile("Address.dat", "rw")) {
                                raFile.seek(tempointer);
                                City = City_Field.getText();
                                City = padString(City, 20);
                                raFile.write(City.getBytes());

                        } catch (IOException x) {
                                System.out.println("I/O Exception: " + x);
                        }

                }

                if (ZipUpdated) {
                        tempointer = tempFilePointer2 + 34 + 34 + 22 + 4;
                        System.out.println(tempointer + "CURRENT");
                        try (RandomAccessFile raFile = new RandomAccessFile("Address.dat", "rw")) {
                                raFile.seek(tempointer);
                                Zip= Zip_Field.getText();
                                Zip = padString(Zip, 5);
                                raFile.write(Zip.getBytes());

                        } catch (IOException x) {
                                System.out.println("I/O Exception: " + x);
                        }

                }
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Contact Updated");
                alert.setHeaderText("SUCCESS");
                alert.setContentText("Contact updated succesfully !!!");

        }

        private void ClearEntry(int callerID) {
                if(callerID == NAME_SIZE_ALERT){
                        Name_Field.clear();
                }
                else if (callerID == STREET_SIZE_ALERT) {
                        Street_Field.clear();
                }
                else if (callerID == CITY_SIZE_ALERT) {
                        City_Field.clear();
                }
                else if(callerID == STATE_SIZE_ALERT){
                        State_Field.clear();
                }
                else if (callerID == ZIP_SIZE_ALERT) {
                        Zip_Field.clear();
                }
                else{
                        Name_Field.clear();
                        Street_Field.clear();
                        City_Field.clear();
                        State_Field.clear();
                        Zip_Field.clear();

                        Name = "";
                        Street = "";
                        City = "";
                        State = "";
                        Zip = "";
                }
        }

        public boolean verifyUserInput(){

                int caller_ID;
                boolean verifiedInput = false;

                if (Name_Field.getText().length() > 32) {
                        verifiedInput = false;
                        caller_ID = 1;
                        alertMessages(caller_ID);
                        Name_Field.clear();

                }
                else if(Street_Field.getText().length() > 32){
                        verifiedInput = false;
                        caller_ID = 2;
                        alertMessages(caller_ID);
                        Street_Field.clear();

                }

                else if (City_Field.getText().length() > 20){
                        verifiedInput = false;
                        caller_ID = 3;
                        alertMessages(caller_ID);
                        City_Field.clear();

                }
                else if(State_Field.getText().length() > 2){
                        verifiedInput = false;
                        caller_ID = 4;
                        alertMessages(caller_ID);
                        State_Field.clear();

                }
                else if(Zip_Field.getText().length() > 6) {
                        verifiedInput = false;
                        caller_ID = 5;
                        alertMessages(caller_ID);
                        Zip_Field.clear();
                }
                else if(Name_Field.getText() == null ||
                        Street_Field.getText() == null ||
                        City_Field.getText() == null ||
                        State_Field.getText() == null ||
                        Zip_Field.getText() == null){
                                verifiedInput = false;
                                caller_ID = 0;
                                alertMessages(0);
                } else {
                        verifiedInput = true;
                        Name = Name_Field.getText();
                        Name = padString(Name,32);
                        Street = Street_Field.getText();
                        Street = padString(Street, 32);
                        City = City_Field.getText();
                        City = padString(City, 20);
                        State = State_Field.getText();
                        State = padString(State, 2);
                        Zip = Zip_Field.getText();
                        Zip = padString(Zip, 5);
                       

                }

                return verifiedInput;
        }

        public void alertMessages(int id){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("WARNING");
                switch(id){
                        case 1: alert.setHeaderText("Name Too Long");
                                alert.setContentText("Name should be at max 32 characters long");
                                alert.showAndWait();
                                break;

                        case 2: alert.setHeaderText("Street Address Too Long");
                                alert.setContentText("Street Address should be at max 32 characters long");
                                alert.showAndWait();
                                break;

                        case 3: alert.setHeaderText("City Name Too Long");
                                alert.setContentText("City Name should be at max 20 characters long");
                                alert.showAndWait();
                                break;

                        case 4: alert.setHeaderText("State Abbreviation Too Long");
                                alert.setContentText("State Abbreviation should be at max 2 characters long");
                                alert.showAndWait();
                                break;

                        case 5: alert.setHeaderText("Zip Code Too Long");
                                alert.setContentText("Zip Code should be at max 5 characters long");
                                alert.showAndWait();
                                break;

                        default:alert.setHeaderText("No Input");
                                alert.setContentText("User hasn't provided any input");
                                alert.showAndWait();
                                break;
                }
                
                
                      
        }

        public static String padString(String word, int length) {
                String newWord = word;
                for (int count = word.length(); count < length; count++) {
                        newWord = newWord + " ";
                }
                return newWord;
        }

        public static void main(String args[]) {
                try (RandomAccessFile raFile = new RandomAccessFile("Address.dat", "rw")) {
                        System.out.println(getFilePointer());
                        setFilePointer(raFile.length());
                        System.out.println(getFilePointer());
                } catch (IOException x) {
                        System.out.println("I/O Exception: " + x);
                }
                launch(args); 
        }
         
}