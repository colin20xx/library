package net.nco.sklad.common;

public enum CardStatus {
    NEW("NEW"),
    MODIFY("MODIFY"),
    EMPTY("EMPTY"),
    DELETED("DELETED");

    private final String value;

    CardStatus(String value) {
        this.value = value;
    }

    public String getValue() { return this.value; }
}
