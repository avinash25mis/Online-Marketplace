package com.mayfair.baskets.repository;




import com.mayfair.baskets.model.Basket;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BasketRepository {


    public static Map<Long, Basket> CUSTOMER_BASKET_MAP = new ConcurrentHashMap<>();

    public Basket getActiveBasketByCutomerId(Long customerId){
       return CUSTOMER_BASKET_MAP.get(customerId);
    }


    public void updateBasket(Basket basket){
        CUSTOMER_BASKET_MAP.put(basket.getCustomerId(),basket);
    }




    public List<String> getProductList(Long customerId){
        if(CUSTOMER_BASKET_MAP.containsKey(customerId)) {
         return CUSTOMER_BASKET_MAP.get(customerId).getProductIdList();
        }else{
            return new ArrayList<String>();
        }
    }


}
