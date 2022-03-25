package com.example.nf28_td.Controller;

import com.example.nf28_td.ContactControl;
import com.example.nf28_td.HelloApplication;
import com.example.nf28_td.Model.Contact;
import com.example.nf28_td.Model.Group;
import com.example.nf28_td.Model.TD3Model;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.collections.FXCollections;

import java.util.LinkedHashMap;
import java.util.Map;

public class TD3Controller {
    public ContactController contactControllerTD;
    private ContactControl contactControl;
    public Contact editingContact;
    public Group newGroup;
    public TD3Model td3Model;
    public ListChangeListener<Group> listChangeListener;
    //private final Image groupIcon = new Image(getClass().getResourceAsStream("group.png"));

    @FXML
    private BorderPane TD3BorderPane;

    @FXML
    private TreeItem<Object> TD3TreeItem;

    @FXML
    private TreeView<Object> tree;


    public TD3Controller(){
        contactControl = new ContactControl();
        contactControllerTD = new ContactController();
        td3Model = new TD3Model();
    }

    public void initialize() {
        ContactControl cc = new ContactControl();
        contactControllerTD = cc.contactController;
        editingContact = cc.contactController.editingContact;
        try{
           TD3BorderPane.setCenter(cc.root);
        }catch (Exception e){
            throw e;
        }
        tree.setCellFactory(param -> new TextFieldTreeCellImpl());
        //écoute de la liste de groupes
        makeBindings();
    }

    public void ajouter(){
        //ajouter un groupe a la liste des groupes dans le model
        newGroup = new Group("Nouveau groupe");
        td3Model.addGroup(newGroup);
        //TreeItem newItem = new TreeItem("Nouveau groupe");
        //TD3TreeItem.getChildren().add(newItem);
    }

    public void remove(){

    }

    private void makeBindings(){
        td3Model.getGroups().addListener(new ListChangeListener<Group>() {
            @Override
            public void onChanged(Change<? extends Group> change) {
                TreeItem<Object> newItem = new TreeItem<Object>(newGroup);
                tree.getRoot().getChildren().add(newItem);
                //TD3TreeItem.getChildren().add(newItem);
            }
        });
    }

    private void addObservableList(Group group){

    }

    private static class TextFieldTreeCellImpl extends TreeCell<Object> {

        private TextField textField;

        public TextFieldTreeCellImpl() {
        }

        @Override
        public void startEdit() {
            super.startEdit();
            if (!(getTreeItem().getValue() instanceof Group)) {
                return;
            }

            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem() != null ? getItem().toString() : null);
            setGraphic(getTreeItem().getGraphic());
        }

        @Override
        public void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setOnKeyReleased(t -> {
                if (t.getCode() == KeyCode.ENTER) {
                    ((Group) getTreeItem().getValue()).nameProperty().set(textField.getText());
                    commitEdit(getTreeItem().getValue());
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            });
        }
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }


}

