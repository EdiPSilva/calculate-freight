package br.com.java.calculatefreight.utils;

public enum StatusMessageEnum {
    OK("Ok");

    private String value;

    StatusMessageEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
