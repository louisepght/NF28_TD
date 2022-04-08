package com.example.nf28_td.Controller;

import com.example.nf28_td.ContactControl;
import com.example.nf28_td.HelloApplication;
import com.example.nf28_td.Model.Constant;
import com.example.nf28_td.Model.Contact;
import com.example.nf28_td.Model.Group;
import com.example.nf28_td.Model.TD3Model;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class TD3Controller {
    public ContactController contactControllerTD;
    private ContactControl contactControl;
    public Contact editingContact;
    private Contact newContact;
    private Contact originalContact;
    public Group newGroup;
    public TD3Model td3Model;
    public ListChangeListener<Group> listChangeListener;
    public TreeItem<Object> selectedItem;
    private Group groupToAddContact; //groupe dans lequel on va ajouter un contact
    //private final Image groupIcon = new Image(getClass().getResourceAsStream("group.png")); //Imput stream must not be null

    //private final Image groupIcon = new Image(getClass().getResourceAsStream(Constant.IMAGES + "group.png"), 16, 16, true, true);
    //private final Image contactIcon = new Image(getClass().getResourceAsStream(Constant.IMAGES + "contact.png"),16, 16, true, true);

    private int addOrModifyContact; // 0 = add, 1 = modify

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
        treeListener();
        grouplistListener();
        editContactListener();
        errorListener();
    }

    public void ajouter(){
        System.out.println("ajouter");
        if(tree.getSelectionModel().getSelectedItem() != null){
            if(tree.getSelectionModel().getSelectedItem().getValue() == tree.getRoot().getValue()){
                System.out.println("ajouter");
                newGroup = new Group("Nouveau groupe");
                td3Model.addGroup(newGroup);
                newGroup.getListContact().addListener(new ListChangeListener<Contact>() {
                    @Override
                    public void onChanged(ListChangeListener.Change<? extends Contact> c) {
                        //MyLogger.log("groupListListener() : Entrée");
                        //MyLogger.log("groupListListener() - Taille : " + modele.getObsGroupList().size());
                        while (c.next()) {
                            if (c.wasAdded()) {
                                for (Contact ct : c.getAddedSubList()) {
                                    System.out.println("listener sur le groupe");
                                    //treeAddChild(getGroupFromSelectedItem(), ct);
                                    TreeItem<Object> contactItem = new TreeItem<Object>(ct);
                                    getGroupFromSelectedItem().getChildren().add(contactItem);
                                }
                            }
                            if (c.wasRemoved()) {
                                for (Contact ct : c.getRemoved()) {
                                    // treeRemoveChild(treeView.getRoot(), g);
                                    //treeRemoveContact(getGroupFromSelectedItem(), ct);
                                    // treeView.refresh();
                                }
                            } /*
                             * if (c.wasUpdated()) { Group g = new Group(); treeUpdateGroup(g); }
                             */
                        }
                    }
                });
            }
        }
    }

    public void remove(){
    }

    private void makeBindings(){

        treeListener();
        grouplistListener();
        editContactListener();
    }

    public void treeListener(){
        tree.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) return;
            selectedItem = (TreeItem<Object>) newValue;
            Object g = selectedItem.getValue();
            System.out.println(g);
            if (g instanceof Contact){
                System.out.println("edition contact");
                addOrModifyContact = 1;// mode édition
                //mode édition cloner contact copier le contact
                //récupération du contact sélectionné
                Contact buf = (Contact) g;
                originalContact = buf.clone(); //clonage du contact original
                editingContact.copyContact(buf);
                contactControllerTD.setPaneVisibility(true);
            }else if(g instanceof Group){
                addOrModifyContact = 0;
                //réinitialisation des champs
                contactControllerTD.editingContact.resetContact();
                //addButton.setDisable(true);
                contactControllerTD.setPaneVisibility(true);
            }else{
                addOrModifyContact = 0;
                System.out.print("je suis dans la racine \n");
                //addButton.setDisable(false); // true : desactive, false: active
                contactControllerTD.setPaneVisibility(false);
            }
        });
    }

    public TreeItem<Object> getGroupFromSelectedItem(){
        TreeItem<Object> newTreeItem = new TreeItem<>();
        if(selectedItem.getValue() instanceof Group){
            newTreeItem = selectedItem;
        }else{
            newTreeItem = selectedItem.getParent();
        }
        return  newTreeItem;
    }

    public void editContactListener(){
        editingContact.isValid().addListener((obs,oldval,newval) -> {
            if(newval.booleanValue()){
                Contact contactval = editingContact.clone();
                //vérifier qu'on est dans le bon groupe
                for(Group grp : td3Model.getGroups()){
                    if(grp == (Group) getGroupFromSelectedItem().getValue()){
                        //mode ajout
                        if(addOrModifyContact != 1){
                            //ajout d'un contact
                            //System.out.println("ditcontactlistener");
                            grp.addContact(contactval);
                            //System.out.println(grp.getListContact().toString());
                        }else{
                            //edition d'un contact
                            for(Contact c : grp.getListContact()){
                                System.out.println(originalContact);
                                System.out.println(c);
                                if(c.Equals(originalContact)){

                                    c.updateContact(editingContact);
                                    tree.refresh();
                                }
                            }
                        }
                        }
                    /*
                    * edition
                        for(contact ct : grp.getcontacts(){
                        * if(ct.isEqual(originalcontact)){
                        * if(ct.isEqual(originalContact){ct.update(editingcontact)} //implementer update dans contact
                    * */

                }
                editingContact.reset();
                contactControllerTD.setPaneVisibility(false);
            }
        });


                /*
        editingContact.contactChanged.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                //ajouter le contact à l'arbre
                newContact = editingContact;
                System.out.println(selectedItem);
                TreeItem<Object> treenewcontact = new TreeItem<Object>(newContact);
                //int sizeofselecteditem = selectedItem.getChildren().size();
                //System.out.println(sizeofselecteditem-1);
                selectedItem.getChildren().add(treenewcontact);
            }
        });
        */
    }

    public void grouplistListener(){
        td3Model.getGroups().addListener(new ListChangeListener<Group>() {
            @Override
            public void onChanged(Change<? extends Group> change) {
                while(change.next()){
                    if(change.wasAdded()){
                        for(Group p : change.getAddedSubList()){
                            //ajouter le groupe au selected item
                            //parent.getChildren().add(new TreeItem<Object>(obj,new ImageView()))
                            TreeItem<Object> newItem = new TreeItem<Object>(p);
                            tree.getRoot().getChildren().add(newItem);
                            //p.getListContact().addListener(contactListener);
                        }
                    }
                    if(change.wasRemoved()){
                        for (Group p : change.getRemoved()){

                        }
                    }
                }
            }
        });
        //contactlistener -> change {
            //change.getelementadded()

        //}
        }



    public TreeItem<Object> getSelectedItem(){
        return selectedItem;
    }

    public void addContactToGroup(Contact contactToAdd){
        td3Model.addContactToGroup(contactToAdd, groupToAddContact);
    }

    public void importFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(new File("").getAbsolutePath()));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            td3Model.load(file);
        }
    }

    public void saveFileUnder(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(new File("").getAbsolutePath()));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            System.out.println("savefileunder");
            td3Model.save(file);
        }
    }

    public void saveFile(){
        System.out.println("saveFile");
        if (td3Model != null) {
            File file = new File("default.json");
            td3Model.save(file);
        }
    }

    public void ErrorHandler(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Problème");
        alert.setContentText(td3Model.getError().getValue());
        alert.showAndWait();
        td3Model.getError().set("");
    }

    public void errorListener(){
        td3Model.getError().addListener((obs,oldval,newval)-> {
            if(newval!=null){
                if(newval!=""){
                    ErrorHandler();
                }
            }
        });
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

