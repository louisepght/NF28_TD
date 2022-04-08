package com.example.nf28_td.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TD3Model {
    private ObservableList<Group> groups;
    private File file = new File("");
    private StringProperty error = new SimpleStringProperty();

    public TD3Model(){
        groups = FXCollections.observableArrayList();
    }

    /***********Getters and setters*************/

    public StringProperty getError(){
        return this.error;
    }

    public void setError(String message){
        this.error.set(message);
    }

    @JsonIgnore
    public ObservableList<Group> getGroups(){
        return groups;
    }
    @JsonIgnore
    public void setGroups(ObservableList<Group> groupList){

        groups = groupList;
    }

    /******************************************/

    public Group getGroup(){
        return groups.get(groups.size()-1);
    }

    public void addContactToGroup(Contact contactToAdd, Group group){
        //trouver le groupe correspondant dans la observable list de groupe
        Iterator<Group> it = groups.iterator();
        while(it.hasNext()) {
            Group nextGroup = it.next();
            if(nextGroup.getName() == group.getName()) nextGroup.addContact(contactToAdd);
        }
        System.out.println(group.getListContact());
    }

    public void addGroup(Group group){
        System.out.println("Group : ");
        System.out.println(group);
        groups.add(group);
        System.out.println("Groups : ");
        System.out.println(groups);
    }

    /***********************JSON methods*****************************/
    public void save(File file){
        //JSONWorkscpace object creation
        JSONWorkspace jsonWorkspace = new JSONWorkspace();
        jsonWorkspace.setGroups(groups); //enregistrement de la liste des groupes à enregistrer dans le json
        try{
            System.out.println("td3Model.save");
            jsonWorkspace.save(file);
        }catch (IOException e){
            this.setError("Erreur de sauvegarde");
        }
    }

    //fait apparaitre les groups et contacts dans l'interface graphique
    public void load(File file){
        JSONWorkspace jsonWorkspace = new JSONWorkspace();
        try{
            //récupération de la liste de groupe dans le fichier JSON
            //boucler sur les groupe pour avvoir les contacrq
            //ajouter les groupes à la liste des groupes
            //ajouter les informations des contacts dans les contacts
            ObservableList<Group> groupListFromFile = jsonWorkspace.fromFile(file);
            Iterator<Group> groupIterator = groupListFromFile.iterator();
            while(groupIterator.hasNext()){
                Group currentgroup = groupIterator.next();
                //ajout du groupe dans la liste des groupes du modèle
                addGroup(currentgroup);
                //itération sur la liste des contacts du groupe
                Iterator<Contact> contactIterator = currentgroup.getListContact().iterator();
                while(contactIterator.hasNext()){
                    //ajout des contacts du groupe en cours
                    currentgroup.addContact(contactIterator.next());
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
