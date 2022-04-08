package com.example.nf28_td.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.List;
import java.util.stream.Collectors;

public class Group {

    private StringProperty name;
    private ObservableList<Contact> contacts = FXCollections.observableArrayList();

    /***********Constructors************************/
    public Group(String name){
        this.name = new SimpleStringProperty(this, Property.NAME.name(), name);
        //contacts = FXCollections.observableArrayList();
    }
    public Group(){}
    /***********Setters and getters************************/

    public StringProperty nameProperty(){
        return name;
    }

    public String getName(){
        return name.get();
    }

    public void setName(String s){
        name.setValue(s);
    }
    /******************************/

    public Contact getContact(){
        return contacts.get(1);
    }

    public void setContact(ObservableList<Contact> contactlist){
        contacts.setAll(contactlist);
    }


    /******************************/
    @JsonIgnore
    public ObservableList<Contact> getListContact(){
        return contacts;
    }

    public List<Contact> getSimpleListContact(){
        return this.contacts.stream().toList();
    }

    @JsonIgnore
    public void setListContact(ObservableList<Contact> contactObservableList){
        this.contacts = contactObservableList;
    }

    public void setListContactWithSimpleList(List<Contact> contctgroups){
        this.contacts.setAll(contctgroups);
    }
    /******************************/
    public void addContact(Contact contact){
        System.out.println("addContact");
        contacts.add(contact);
        System.out.println(contacts);
    }
    /*************surcharge*************************/
    public String toString(){ return name.getValue();}

}
