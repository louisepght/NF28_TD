package com.example.nf28_td;

import com.example.nf28_td.Controller.ContactController;
import com.example.nf28_td.Controller.TD3Controller;
import com.example.nf28_td.Model.Constant;
import com.example.nf28_td.Model.Contact;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {
    public TD3Controller td3Controller;
    public ContactController contactController;
    @FXML
    public BorderPane TD3BorderPane;

    @Override
    public void start(Stage stage) throws IOException {
        td3Controller = new TD3Controller();
        contactController = new ContactController();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("TD3.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), Constant.WIDTH, Constant.HEIGHT);

        stage.setTitle("TD3");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}