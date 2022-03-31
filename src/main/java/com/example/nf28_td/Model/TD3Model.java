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
        System.out.println("Group : ");
        System.out.println(group);
        groups.add(group);
        System.out.println("Groups : ");
        System.out.println(groups);
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
            Group nextGroup = it.next();
            if(nextGroup.getName() == group.getName()) nextGroup.addContact(contactToAdd);
        }
        System.out.println(group.getListContact());
    }

}
