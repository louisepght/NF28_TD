package com.example.nf28_td.Controller;

import com.example.nf28_td.Model.Contact;
import com.example.nf28_td.Model.Country;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;


public class ContactController {

    @FXML
    private TextField lastName, firstName, city;

    @FXML
    private ComboBox<String> country;

    @FXML
    private ToggleGroup sexeGroup;

    @FXML
    private RadioButton genderm;

    @FXML
    private RadioButton genderf;

    private Map<String, Control> controls;

    public Contact editingContact;

    MapChangeListener<String, String> validateFunction;

    public ContactController() {
        editingContact = new Contact();
        controls = new LinkedHashMap<>();
    }

    public void initialize() {
        makeBindings();
        addControls(editingContact);
    }

    private void makeBindings() {

        validateFunction = change -> {
            Control control = controls.get(change.getKey());
            if (change.wasRemoved()) {
                control.setStyle("-fx-border-color:  blue ;");
                control.setTooltip(null);
            } else if (change.wasAdded()) {
                control.setStyle("-fx-border-color: red ;");
                control.setTooltip(new Tooltip(change.getValueAdded()));
            }
        };
        // make bindings
        lastName.textProperty().bindBidirectional(editingContact.lastNameProperty());
        firstName.textProperty().bindBidirectional(editingContact.firstNameProperty());
        city.textProperty().bindBidirectional(editingContact.cityProperty());

        country.getSelectionModel().selectedItemProperty().addListener((observable, oldv, newv) -> {
            editingContact.countryProperty().setValue(newv);
        });
        editingContact.countryProperty().addListener((obj, o, n) -> {
            country.getSelectionModel().select(n);
        });

        country.setItems(FXCollections.observableList(Country.getCountryNames()));

        sexeGroup.selectedToggleProperty().addListener((observable, oldv, newv) -> {
            if (newv != null) {
                RadioButton bt = (RadioButton) newv;
                editingContact.genderProperty().set(bt.getText());
            }

        });

        editingContact.genderProperty().addListener((obj, o, n) -> {
            if (n == null) {
                return;
            }
            if (n.equals("M")) {
                sexeGroup.selectToggle(genderm);
            } else {
                sexeGroup.selectToggle(genderf);
            }
        });


        editingContact.validationMessagesProperty().addListener(validateFunction);

    }

    private void addControls(Contact contact) {
        controls.put(contact.lastNameProperty().getName(), lastName);
        controls.put(contact.firstNameProperty().getName(), firstName);
        controls.put(contact.cityProperty().getName(), city);
        controls.put(contact.countryProperty().getName(), country);
        controls.put(contact.genderProperty().getName(), genderf);
    }

    @FXML
    public void validate() {
        editingContact.validate();
    }
}