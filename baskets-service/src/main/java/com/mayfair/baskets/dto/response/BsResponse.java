package com.mayfair.baskets.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BsResponse<T> {
    String status;
    String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T result;


}