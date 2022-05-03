package com.mayfair.baskets.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
public class BasketException extends RuntimeException{

    private BsExceptionDetails exceptionDetails;

    public BasketException(BsExceptionDetails exceptionDetails) {
        super(exceptionDetails.getErrorMessage());
        this.exceptionDetails=exceptionDetails;
    }


}
