package com.example.nf28_td.Model;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.List;

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

}
