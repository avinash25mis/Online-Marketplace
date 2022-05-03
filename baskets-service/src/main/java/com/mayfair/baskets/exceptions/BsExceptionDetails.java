package com.mayfair.baskets.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class BsExceptionDetails {

    private final String errorCode;
    private final String errorMessage;
}
