package com.example.nf28_td.Model;
import java.util.function.Predicate;
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

    public Contact() {
        this.lastName = new SimpleStringProperty(this, Property.NAME.name(), "");
        this.firstName = new SimpleStringProperty(this, Property.GIVEN_NAME.name(), "");
        this.gender = new SimpleStringProperty(this, Constant.SEX, "M");
        this.city = new SimpleStringProperty(this, Property.CITY.name(), "");
        this.country = new SimpleStringProperty(this, Property.COUNTRY.name(), "");
        validationMessages = FXCollections.observableHashMap();
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
        return validationMessages.isEmpty();
    }

    public ObservableMap<String, String> validationMessagesProperty() {
        return validationMessages;
    }

    @Override
    public String toString() {
        return lastNameProperty().get() + " " + firstNameProperty().get();
    }

}
