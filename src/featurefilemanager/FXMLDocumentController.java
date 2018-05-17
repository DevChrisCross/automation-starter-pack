/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package featurefilemanager;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.DirectoryChooser;

/**
 *
 * @author Chris
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML private TableView tableView = new TableView<>();
    private final ObservableList<Person> data =
        FXCollections.observableArrayList(
            new Person("A", "Z", "a@example.com"),
            new Person("B", "X", "b@example.com"),
            new Person("C", "W", "c@example.com"),
            new Person("D", "Y", "d@example.com"),
            new Person("E", "V", "e@example.com")
        );  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person,String>("firstName"));

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setCellValueFactory(
            new PropertyValueFactory<Person,String>("lastName")
        );

        TableColumn emailCol = new TableColumn("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
            new PropertyValueFactory<Person,String>("email")
        );
                                     
        tableView.setItems(data);
        firstNameCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
        tableView.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
    }    
    
    
    
    public static class Person {
        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;

        private Person(String fName, String lName, String email) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
        }

        public String getFirstName() {
            return firstName.get();
        }
        public void setFirstName(String fName) {
            firstName.set(fName);
        }
       
        public String getLastName() {
            return lastName.get();
        }
        public void setLastName(String fName) {
            lastName.set(fName);
        }
       
        public String getEmail() {
            return email.get();
        }
        public void setEmail(String fName) {
            email.set(fName);
        }
       
    }
}
