package com.example.nf28_td.Model;

public enum Property {
    NAME("nom obligatoire"),
    GIVEN_NAME("prénom obligatoire"),
    COUNTRY("pays obligatoire"), CITY("ville obligatoire");
    private Property(String tooltip) {
        this.tooltip = tooltip;
    }
    public String tooltip;

}
