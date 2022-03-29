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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.collections.FXCollections;
import javafx.util.Callback;

import java.util.LinkedHashMap;
import java.util.Map;

public class TD3Controller {
    public ContactController contactControllerTD;
    private ContactControl contactControl;
    public Contact editingContact;
    public Group newGroup;
    public TD3Model td3Model;
    public ListChangeListener<Group> listChangeListener;
    public TreeItem<Object> selectedItem;
    private Group groupToAddContact; //groupe dans lequel on va ajouter un contact
    //private final Image groupIcon = new Image(getClass().getResourceAsStream("group.png")); //Imput stream must not be null

    @FXML
    private BorderPane TD3BorderPane;

    @FXML
    private Button addButton;

    //@FXML
    private TreeItem<Object> TD3TreeItem;

    //@FXML
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
        TreeItem<Object> TD3TreeItem = new TreeItem<Object>("fiche de contacts");
        tree = new TreeView<Object>(TD3TreeItem);
        //TD3BorderPane.getChildren().add(tree);
        try{
            contactControllerTD.setPaneVisibility(false);
            //TD3TreeItem = new TreeItem<Object>("Fiche de contacts");
            TD3TreeItem.setValue("Fiche de contacts");
            TD3BorderPane.setCenter(cc.root);
            TD3BorderPane.setLeft(tree);

        }catch (Exception e){
            throw e;
        }

        // rename group
        tree.setCellFactory(param -> new TextFieldTreeCellImpl());
        tree.setEditable(true);
        tree.setCellFactory(new Callback<TreeView<Object>,TreeCell<Object>>(){
            @Override
            public TreeCell<Object> call(TreeView<Object> p) {
                return new TextFieldTreeCellImpl();
            }
        });

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
        tree.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            System.out.println("dans selected item tree");
            if (newValue == null)
                return;
            this.selectedItem = (TreeItem<Object>) newValue;
            System.out.print(selectedItem.getValue());
            Object g = selectedItem.getValue();
            if (g instanceof Contact){
                addButton.setDisable(true); // true : desactive, false: active
                //ajout d'un contact
                System.out.print("je suis dans une instance de contact");

                groupToAddContact = (Group) selectedItem.getParent().getValue(); // si un contact est selectionne alors on prend le groupe parent
                groupToAddContact.getListContact().addListener(new ListChangeListener<Contact>() {

                    @Override
                    public void onChanged(Change<? extends Contact> change) {
                        TreeItem<Object> newContact = new TreeItem<Object>(change);
                        selectedItem.getChildren().add(newContact);
                    }
                });
            }
                       else if (g instanceof Group){ //ajout d un contact

                addButton.setDisable(true); // true : desactive, false: active

                System.out.print("je suis dans une instance de groupe et jajoute un contact");

                groupToAddContact = (Group) g; //si l objet selectionne est de type groupe, alors on reste sur groupe

                //rendre visible le contactcontrol
                contactControllerTD.setPaneVisibility(true);


                groupToAddContact.getListContact().addListener(new ListChangeListener<Contact>() {

                    @Override
                    public void onChanged(Change<? extends Contact> change) {
                        TreeItem<Object> newContact = new TreeItem<Object>(change);
                        selectedItem.getChildren().add(newContact);
                    }
                });


            }else {
                addButton.setDisable(false); // true : desactive, false: active
                System.out.print("je suis dans la racine et je cree un groupe ");
                contactControllerTD.setPaneVisibility(false);
                td3Model.getGroups().addListener(new ListChangeListener<Group>() {
                    @Override
                    public void onChanged(Change<? extends Group> change) {
                        TreeItem<Object> newItem = new TreeItem<Object>(newGroup);
                        tree.getRoot().getChildren().add(newItem);
                    }
                });
            }
        });
    }

    public TreeItem<Object> getSelectedItem(){
        return selectedItem;
    }

    public void addContactToGroup(Contact contactToAdd){
        td3Model.addContactToGroup(contactToAdd, groupToAddContact);
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

