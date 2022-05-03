package com.mayfair.baskets.service;


import com.mayfair.baskets.cache.BsAppCache;
import com.mayfair.baskets.constants.BsErrorCodes;
import com.mayfair.baskets.exceptions.BasketException;
import com.mayfair.baskets.exceptions.BsExceptionDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BasketValidationService {



    /*
     * In actual Environment these code will be executed via a distributed cache
     * or even can be  validated when we fetch actual product/customer record via http
     * RestTemplate/Webclient
     * */



    public void validateProductId(String productId){
        if(!BsAppCache.PRODUCT_CACHE_MAP.containsKey(productId)){
            log.error("Invalid Product ID {} ",productId);
            throw  new BasketException(new BsExceptionDetails(BsErrorCodes.INVALID_PRODUCT_ID.getCode(),BsErrorCodes.INVALID_PRODUCT_ID.getDescription()+productId));
        }
    }


    public void validateCustomerId(Long customerId){
        if(!BsAppCache.CUSTOMER_CACHE_MAP.containsKey(customerId)){
            log.error("Invalid Customer ID {} ",customerId);
            throw  new BasketException(new BsExceptionDetails(BsErrorCodes.INVALID_CUSTOMER_ID.getCode(),BsErrorCodes.INVALID_CUSTOMER_ID.getDescription()+customerId));
        }
    }

}
