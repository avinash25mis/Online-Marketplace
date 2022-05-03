package com.mayfair.baskets.service;

import com.mayfair.baskets.constants.BsConstants;
import com.mayfair.baskets.constants.BsErrorCodes;
import com.mayfair.baskets.dto.request.UpdateBasketRequest;
import com.mayfair.baskets.exceptions.BasketException;
import com.mayfair.baskets.exceptions.BsExceptionDetails;
import com.mayfair.baskets.model.Basket;
import com.mayfair.baskets.repository.BasketRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class BasketServiceTest {

    @InjectMocks
    private BasketService basketService;

    @Mock
    private BasketRepository basketRepository;

    @Mock
    private BasketValidationService basketValidationService;


    private static final Long CUSTOMER_ID = 5001l;
    private static final Long INVALID_CUSTOMER_ID = 000l;
    private static final String PRODUCT_ID = "WF0123";
    private static final String INVALID_PRODUCT_ID = "ANY_PRODUCT_ID";

    @Test
    void addProductToBasket() {
        UpdateBasketRequest request= new UpdateBasketRequest(CUSTOMER_ID,PRODUCT_ID);
        Basket basket= new Basket(CUSTOMER_ID,new ArrayList<>());
        Mockito.doNothing().when(basketValidationService).validateProductId(request.getProductId());
        Mockito.doNothing().when(basketValidationService).validateCustomerId(request.getCustomerId());
        Mockito.when(basketRepository.getActiveBasketByCutomerId(CUSTOMER_ID)).thenReturn(basket);
        Mockito.doNothing().when(basketRepository).updateBasket(basket);
        assertThat(basketService.addProductToBasket(request)).isEqualTo(BsConstants.PRODUCT_ADDED);
    }






    @Test
    void addProductToBasket_invalidProduct() {
        UpdateBasketRequest request= new UpdateBasketRequest(CUSTOMER_ID,INVALID_PRODUCT_ID);
        Mockito.doThrow(new BasketException(new BsExceptionDetails(BsErrorCodes.INVALID_PRODUCT_ID.getCode(),BsErrorCodes.INVALID_PRODUCT_ID.getDescription()+INVALID_PRODUCT_ID)))
                .when(basketValidationService).validateProductId(INVALID_PRODUCT_ID);
        assertThatThrownBy(() -> basketService.addProductToBasket(request)).isInstanceOf(BasketException.class)
                .hasMessage(BsErrorCodes.INVALID_PRODUCT_ID.getDescription()+INVALID_PRODUCT_ID);
    }

    @Test
    void addProductToBasket_invalidCustomer() {
        UpdateBasketRequest request= new UpdateBasketRequest(INVALID_CUSTOMER_ID,PRODUCT_ID);
        Mockito.doThrow(new BasketException(new BsExceptionDetails(BsErrorCodes.INVALID_CUSTOMER_ID.getCode(),BsErrorCodes.INVALID_CUSTOMER_ID.getDescription()+INVALID_CUSTOMER_ID)))
                .when(basketValidationService).validateCustomerId(INVALID_CUSTOMER_ID);
        assertThatThrownBy(() -> basketService.addProductToBasket(request)).isInstanceOf(BasketException.class)
                .hasMessage(BsErrorCodes.INVALID_CUSTOMER_ID.getDescription()+0000l);
    }

    @Test
    void removeProductFromBasket() {
        UpdateBasketRequest request= new UpdateBasketRequest(CUSTOMER_ID,PRODUCT_ID);
        Basket basket= new Basket(CUSTOMER_ID,new ArrayList<>(Arrays.asList(PRODUCT_ID)));
        Mockito.doNothing().when(basketValidationService).validateCustomerId(request.getCustomerId());
        Mockito.when(basketRepository.getActiveBasketByCutomerId(CUSTOMER_ID)).thenReturn(basket);
        Mockito.doNothing().when(basketRepository).updateBasket(basket);
        assertThat(basketService.removeProductFromBasket(request)).isEqualTo(BsConstants.PRODUCT_DELETED);
    }


    @Test
    void removeProductFromBasket_productNotInBasket() {
        UpdateBasketRequest request= new UpdateBasketRequest(CUSTOMER_ID,PRODUCT_ID);
        Basket basket= new Basket(CUSTOMER_ID,new ArrayList<>());
        Mockito.doNothing().when(basketValidationService).validateCustomerId(request.getCustomerId());
        Mockito.when(basketRepository.getActiveBasketByCutomerId(CUSTOMER_ID)).thenReturn(basket);
        assertThatThrownBy(() -> basketService.removeProductFromBasket(request)).isInstanceOf(BasketException.class)
                .hasMessage(BsErrorCodes.PRODUCT_NOT_FOUND_IN_BASKET.getDescription()+PRODUCT_ID);
    }

    @Test
    void removeProductFromBasket_emptyBasket() {
        UpdateBasketRequest request= new UpdateBasketRequest(CUSTOMER_ID,PRODUCT_ID);
        Basket basket= new Basket(null,null);
        Mockito.doNothing().when(basketValidationService).validateCustomerId(request.getCustomerId());
        Mockito.when(basketRepository.getActiveBasketByCutomerId(CUSTOMER_ID)).thenReturn(basket);
        assertThatThrownBy(() -> basketService.removeProductFromBasket(request)).isInstanceOf(BasketException.class)
                .hasMessage(BsErrorCodes.PRODUCT_NOT_FOUND_IN_BASKET.getDescription()+PRODUCT_ID);
    }





    @Test
    void removeProductFromBasket_invalidCustomer() {
        UpdateBasketRequest request= new UpdateBasketRequest(INVALID_CUSTOMER_ID,PRODUCT_ID);
        Mockito.doThrow(new BasketException(new BsExceptionDetails(BsErrorCodes.INVALID_CUSTOMER_ID.getCode(),BsErrorCodes.INVALID_CUSTOMER_ID.getDescription()+INVALID_CUSTOMER_ID)))
                .when(basketValidationService).validateCustomerId(INVALID_CUSTOMER_ID);
        assertThatThrownBy(() -> basketService.removeProductFromBasket(request)).isInstanceOf(BasketException.class)
                .hasMessage(BsErrorCodes.INVALID_CUSTOMER_ID.getDescription()+INVALID_CUSTOMER_ID);
    }




    @Test
    void getProductIdList() {
        Mockito.when(basketRepository.getProductList(CUSTOMER_ID)).thenReturn(new ArrayList<>(Arrays.asList(PRODUCT_ID)));
        assertThat(basketService.getProductIdList(CUSTOMER_ID).size()).isEqualTo(1);
    }


    @Test
    void getProductIdList_emptyList() {
        Mockito.when(basketRepository.getProductList(CUSTOMER_ID)).thenReturn(new ArrayList<>());
        assertThat(basketService.getProductIdList(CUSTOMER_ID).size()).isEqualTo(0);
    }
}