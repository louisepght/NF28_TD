package com.example.nf28_td.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Iterator;

public class TD3Model {
    private final ObservableList<Group> groups;
    private File file = new File("");

    public TD3Model(){
        groups = FXCollections.observableArrayList();
    }
    //listener de la liste des groupes déclenche l ajout deun treeitem de Group dans larbre

    public void addGroup(Group group){
        System.out.println("Group : ");
        System.out.println(group);
        groups.add(group);
        System.out.println("Groups : ");
        System.out.println(groups);
    }

    public ObservableList<Group> getGroups(){
        return groups;
    }
    public void setGroups(ObservableList<Group> groupList){
        this.groups.setAll(groupList);
    }

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

    /***********************JSON methods*****************************/
    public void save(File file){
        //JSONWorkscpace object creation
        JSONWorkspace jsonWorkspace = new JSONWorkspace();
        jsonWorkspace.setGroups(groups); //enregistrement de la liste des groupes à enregistrer dans le json
        try{
            System.out.println("td3Model.save");
            jsonWorkspace.save(file);
        }catch (IOException e){
            System.out.println("Error");
            System.out.println(e);
        }
    }

    //fait apparaitre les groups et contacts dans l'interface graphique
    public void load(File file){

        JSONWorkspace jsonWorkspace = new JSONWorkspace();


    }



}
