package com.example.nf28_td.Model;
import java.util.Iterator;
import java.util.function.Predicate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Contact {

    private final StringProperty lastName;
    private final StringProperty firstName;
    private final StringProperty city;
    private final StringProperty country;
    private final StringProperty gender;
    private final ObservableMap<String, String> validationMessages;
    private final BooleanProperty contactChanged;

    public Contact() {
        this.lastName = new SimpleStringProperty(this, Property.NAME.name(), "");
        this.firstName = new SimpleStringProperty(this, Property.GIVEN_NAME.name(), "");
        this.gender = new SimpleStringProperty(this, Constant.SEX, "M");
        this.city = new SimpleStringProperty(this, Property.CITY.name(), "");
        this.country = new SimpleStringProperty(this, Property.COUNTRY.name(), "");
        this.contactChanged = new SimpleBooleanProperty();
        validationMessages = FXCollections.observableHashMap();
    }

    public BooleanProperty isValid(){
        return contactChanged;
    }
    // Property non renseignée
    Predicate<StringProperty> testProperty = prop -> prop.get() == null || prop.get().trim().equals("");

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public StringProperty countryProperty() {
        return country;
    }

    public StringProperty cityProperty() {
        return city;
    }

    public String getGender() {
        return gender.get();
    }

    public void reset(){
        this.lastName.setValue("");
        this.firstName.setValue("");
        this.gender.setValue("");
        this.country.setValue("");
        this.city.setValue("");
        this.contactChanged.setValue(false);
    }
    /**
     * Validité d'une StringProperty : sprop
     */
    private void validate(StringProperty sprop) {
        if (testProperty.test(sprop))
            validationMessages.put(sprop.getName(), Property.valueOf(sprop.getName()).tooltip);
    }

    // Etudie la validité de chaque Property
    public boolean validate() {
        validationMessages.clear();
        validate(lastName);
        validate(firstName);
        validate(city);
        validate(country);
        contactChanged.setValue(validationMessages.isEmpty());
        return validationMessages.isEmpty();
    }

    public ObservableMap<String, String> validationMessagesProperty() {
        return validationMessages;
    }

    @Override
    public String toString() {
        return lastNameProperty().get() + " " + firstNameProperty().get();
    }


    public Contact clone(){
        Contact newContact = new Contact();
        newContact.firstNameProperty().set(this.firstNameProperty().getValue());
        newContact.lastNameProperty().set(this.lastNameProperty().getValue());
        newContact.cityProperty().set(this.cityProperty().getValue());
        newContact.countryProperty().set(this.countryProperty().getValue());
        newContact.genderProperty().set(this.genderProperty().get());
        return newContact;
    }

}
