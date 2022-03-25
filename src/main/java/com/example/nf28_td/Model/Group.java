package com.example.nf28_td.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.List;

public class Group {

    private StringProperty name;
    private final ObservableList<Contact> contacts;

    public Group(String name){
        this.name = new SimpleStringProperty(this, Property.NAME.name(), name);
        contacts = FXCollections.observableArrayList();
    }

    public String getName(){
        return name.getValue();
    }

    public Contact getContact(){
        return contacts.get(1);
    }

    public List<Contact> getListContact(){
        return contacts;
    }

    public StringProperty nameProperty(){
        return name;
    }

}
