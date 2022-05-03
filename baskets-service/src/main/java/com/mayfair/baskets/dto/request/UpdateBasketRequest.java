package com.mayfair.baskets.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBasketRequest implements Serializable {

    @NonNull
    private Long customerId;
    @NonNull
    private String productId;
}
