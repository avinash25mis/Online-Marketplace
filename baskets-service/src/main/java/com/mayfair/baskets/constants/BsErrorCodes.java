package com.mayfair.baskets.constants;

public enum BsErrorCodes {

    TEMPORARY_FAILURE("3000","Service temporary Unavailable"),
    INVALID_CUSTOMER_ID("3001","Customer ID Not Valid :"),
    INVALID_PRODUCT_ID("3002","Product ID Not Valid / Not Available :"),
    PRODUCT_NOT_FOUND_IN_BASKET("3003","Product Not present in the basket :"),
    DUPLICATE_PRODUCT_IN_BASKET("3004","Product Already present in the basket :");

    String code;
    String description;

    BsErrorCodes(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
