package com.example.nf28_td.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONWorkspace {
    private ObservableList<Group> jsonGroupsList = FXCollections.observableArrayList();

    public JSONWorkspace(){}

    @JsonIgnore
    public ObservableList<Group> getJsonGroupsList(){
        return jsonGroupsList;
    }

    @JsonIgnore
    public void setJsonGroupsList(ObservableList<Group> groupList){
        jsonGroupsList = groupList;
    }

    public List<Group> getJsonGroupsListAsSimplelist(){
        return jsonGroupsList.stream().toList();
    }

    //sauvegarde l'objet workspace dans un fichier
    public void save(File f) throws IOException {
        System.out.println(jsonGroupsList);
        System.out.println(f);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(f,jsonGroupsList);
        System.out.println("jsonworkspaces.save");
    }



    //assigner les groupes à partir d'une liste observable
    public void setGroups(ObservableList<Group> grps){
        System.out.println(grps);
        jsonGroupsList = grps;
    }

    //groupes lus à partir d'un fichier
    /*
    public ObservableList<Group> fromFile(File file) throws Exception{
        ObservableList<Group> groups = FXCollections.observableArrayList();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JSONWorkspace jsonWorkspace = null;
            InputStream inputStream = new FileInputStream(file);
            TypeReference<ObservableList<Group>> typeReference = new TypeReference<ObservableList<Group>>(){};
            jsonWorkspace = mapper.readValue(new FileReader(file), JSONWorkspace.class);
            groups = jsonGroupsList.stream().toList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return groups;
    }
    */

    public ObservableList<Group> fromFile(File file) throws Exception{
        System.out.println("fromFile");
        ObservableList<Group> groups = FXCollections.observableArrayList();
        ObjectMapper objectMapper = new ObjectMapper();
        JSONWorkspace jsonWorkspace = null;
        jsonWorkspace = objectMapper.readValue(new FileReader(file),JSONWorkspace.class);
        groups = jsonWorkspace.getJsonGroupsList();
        return groups;
        }

}
