package com.mayfair.baskets.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Basket {
    private Long customerId;
    private List<String> productIdList;
}
