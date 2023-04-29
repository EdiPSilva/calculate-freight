package br.com.java.calculatefreight.configuration;

public enum MessageCodeEnum {

    INVALID_REQUEST_DEFAULT_MESSAGE("invalid.request.default.message"),
    INVALID_ID("invalid.id"),
    REGISTER_NOT_FOUND("register.not.found"),
    REGISTER_NOT_FOUND_BY_ID("register.not.found.by.id"),
    INVALID_DOCUMENT("invalid.document"),
    DOCUMENT_ALREADY_CADASTRE("document.already.cadastre"),
    REGISTER_ALREADY_CADASTRE("register.already.cadastre"),
    INVALID_STATE_ABBREVIATION("invalid.state.abbreviation"),
    COLUMN_NOT_FOUND("column.not.found"),
    INVALID_POST_CODE("invalid.postal.code"),
    INVALID_STATE("invalid.state"),
    STATE_NOT_FOUND("state.not.exists"),
    START_POSTAL_CODE_ALREADY_CADASTRE("start.postal.code.already.cadastre"),
    END_POSTAL_CODE_ALREADY_CADASTRE("end.postal.code.already.cadastre"),
    START_AND_END_POSTAL_CODE_EQUALS("start.and.end.postal.code.equals"),
    INVALID_CALCULATION_TYPE("invalid.calculation.type"),
    CALCULATION_TYPE_NOT_FOUND("calculation.type.not.found");

    private String value;

    MessageCodeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}