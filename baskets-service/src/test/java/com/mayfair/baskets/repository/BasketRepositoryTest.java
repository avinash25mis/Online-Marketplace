package com.mayfair.baskets.repository;

import com.mayfair.baskets.dto.request.UpdateBasketRequest;
import com.mayfair.baskets.model.Basket;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
/*
for testing  actual Repository
@DataJpaTest
*/
public class BasketRepositoryTest {

    @InjectMocks
    private BasketRepository repository;

    private static final Long CUSTOMER_ID = 5001l;
    private static final String PRODUCT_ID = "WF0123";

    @Test
    void getActiveBasketByCutomerId() {
        repository.getActiveBasketByCutomerId(5001l);
    }


    @Test
    void updateBasket() {
        Basket request= new Basket(CUSTOMER_ID,new ArrayList<>(Arrays.asList(PRODUCT_ID)));
        repository.updateBasket(request);
    }



    @Test
    void getProductList() {
        repository.getProductList(CUSTOMER_ID);
    }
}