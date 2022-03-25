package com.example.nf28_td;

import com.example.nf28_td.Controller.ContactController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ContactControl {
    public Parent root;
    public ContactController contactController;


    public ContactControl(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ContactControl.fxml"));

        try{
            root = fxmlLoader.load();
            contactController = fxmlLoader.getController();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
