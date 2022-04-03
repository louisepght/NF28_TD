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


    // Property non renseignée
    Predicate<StringProperty> testProperty = prop -> prop.get() == null || prop.get().trim().equals("");

    /***********************************/
    public StringProperty lastNameProperty() {
        return lastName;
    }
    public final String getLastName(){
        return lastName.getValue();
    }
    public final void setLastName(String s){
        lastName.setValue(s);
    }
    /***********************************/
    public StringProperty firstNameProperty() {
        return firstName;
    }
    public final String getFirstName(){
        return firstName.getValue();
    }
    public final void setFirstName(String s){
        firstName.setValue(s);
    }
    /***********************************/
    public StringProperty genderProperty() {
        return gender;
    }
    public final String getGender(){
        return gender.getValue();
    }
    public final void setGender(String s){
        gender.setValue(s);
    }
    /***********************************/
    public StringProperty countryProperty() {
        return country;
    }
    public final String getCountry(){
        return country.getValue();
    }
    public final void setCountry(String s){
        country.setValue(s);
    }
    /***********************************/
    public StringProperty cityProperty() {
        return city;
    }
    public final String getCity(){
        return city.getValue();
    }
    public final void setCity(String s){
        city.setValue(s);
    }
    /***********************************/
    public BooleanProperty isContactValidProperty() {
        return contactChanged;
    }
    public BooleanProperty isValid(){
        return contactChanged;
    }
    public final void setValid(Boolean bool){
        contactChanged.setValue(bool);
    }
    /***********************************/


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

    public void copyContact(Contact contact){
        this.firstNameProperty().set(contact.firstNameProperty().getValue());
        this.lastNameProperty().set(contact.lastNameProperty().getValue());
        this.cityProperty().set(contact.cityProperty().getValue());
        this.countryProperty().set(contact.countryProperty().getValue());
        this.genderProperty().set(contact.genderProperty().getValue());
        this.isContactValidProperty().set(false);
    }

    public void updateContact(Contact contact){
        this.firstName.setValue(contact.firstName.getValue());
        this.lastName.setValue(contact.lastName.getValue());
        this.city.setValue(contact.city.getValue());
        this.gender.setValue(contact.gender.getValue());
        this.country.setValue(contact.country.getValue());
        this.contactChanged.setValue(false);
    }

    public void resetContact(){
        this.firstName.setValue("");
        this.lastName.setValue("");
        this.city.setValue("");
        this.gender.setValue("");
        this.country.setValue("");
        this.validationMessagesProperty().clear();
        this.contactChanged.setValue(false);
    }

    public boolean Equals(Contact contact){
        System.out.println("equals");
        boolean bool = (this.firstName.getValue() == contact.firstName.getValue() &&
                this.lastName.getValue() == contact.lastName.getValue());
        System.out.println(bool);
        return bool;
    }
}
