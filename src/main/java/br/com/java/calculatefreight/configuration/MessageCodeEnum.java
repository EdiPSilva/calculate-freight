package br.com.java.calculatefreight.configuration;

public enum MessageCodeEnum {

    INVALID_REQUEST_DEFAULT_MESSAGE("invalid.request.default.message"),
    INVALID_ID("invalid.id"),
    REGISTER_NOT_FOUND("register.not.found"),
    INVALID_DOCUMENT("invalid.document"),
    INVALID_POST_CODE("invalid.postal.code"),
    SHIPPING_COMPANY_INACTIVE("shipping.company.inactive"),
    ORDER_NUMBER_ALREADY_EXISTS("order.number.already.exists");

    private String value;

    MessageCodeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}