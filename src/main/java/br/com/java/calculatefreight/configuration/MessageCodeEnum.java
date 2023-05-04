package br.com.java.calculatefreight.configuration;

public enum MessageCodeEnum {

    INVALID_REQUEST_DEFAULT_MESSAGE("invalid.request.default.message"),
    INVALID_ID("invalid.id"),
    REGISTER_NOT_FOUND("register.not.found"),
    INVALID_DOCUMENT("invalid.document"),
    INVALID_POST_CODE("invalid.postal.code"),
    INVALID_STATE("invalid.state"),
    STATE_NOT_FOUND("state.not.exists"),
    INVALID_CALCULATION_TYPE("invalid.calculation.type"),
    CALCULATION_TYPE_NOT_FOUND("calculation.type.not.found"),
    SHIPPING_COMPANY_INACTIVE("shipping.company.inactive");

    private String value;

    MessageCodeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}