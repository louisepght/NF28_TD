package com.example.nf28_td.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;

public class TD3Model {
    private final ObservableList<Group> groups;

    public TD3Model(){
        groups = FXCollections.observableArrayList();
    }

    //listener de la liste des groupes déclenche l ajout deun treeitem de Group dans larbre


    public void addGroup(Group group){
        groups.add(group);
    }

    public void addTreeView(){
    }

    public ObservableList<Group> getGroups(){
        return groups;
    }

    public Group getGroup(){
        return groups.get(groups.size()-1);
    }

    public void addContactToGroup(Contact contactToAdd, Group group){
        //trouver le groupe correspondant dans la observable list de groupe
        Iterator<Group> it = groups.iterator();
        while(it.hasNext()) {
            if(it.next().getName() == group.getName()) it.next().addContact(contactToAdd);
        }
    }

}
