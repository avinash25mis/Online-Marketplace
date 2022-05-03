package com.mayfair.baskets.controller;

import com.mayfair.baskets.constants.ApiConstants;
import com.mayfair.baskets.constants.BsConstants;
import com.mayfair.baskets.constants.BsErrorCodes;
import com.mayfair.baskets.dto.request.UpdateBasketRequest;
import com.mayfair.baskets.exceptions.BasketException;
import com.mayfair.baskets.exceptions.BsExceptionDetails;
import com.mayfair.baskets.service.BasketService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BasketController.class)
public class BasketControllerTest {

    private final static String SAMPLE_REQUEST = "{\"customerId\": \"5001\",\"productId\": \"WF0123\"}";
    private static final Long CUSTOMER_ID = 5001l;
    private static final String PRODUCT_ID = "WF0123";

    @MockBean
    private BasketService basketService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Add product To Basket Test")
    void addProductToBasket() throws Exception{
        UpdateBasketRequest request= new UpdateBasketRequest(CUSTOMER_ID,PRODUCT_ID);
        when(basketService.addProductToBasket(request)).thenReturn(BsConstants.PRODUCT_ADDED);
        mockMvc.perform(post(ApiConstants.BASKET_BASE_URL).contentType(APPLICATION_JSON).content(SAMPLE_REQUEST))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json("{\"status\":\"OK\",\"message\":\"Product Added to Basket\"}"));
    }


    @Test
    @DisplayName("Add product To Basket With Invalid CustomerId")
    void addProductToBasket_invalidCustomerID() throws Exception{
        when(basketService.addProductToBasket(any())).thenThrow(new BasketException(new BsExceptionDetails(BsErrorCodes.INVALID_CUSTOMER_ID.getCode(),BsErrorCodes.INVALID_CUSTOMER_ID.getDescription())));
        mockMvc.perform(post(ApiConstants.BASKET_BASE_URL).contentType(APPLICATION_JSON).content(SAMPLE_REQUEST))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errorCode\":\"3001\"," + "\"errorMessage\":\"Customer ID Not Valid :\"}"));
    }


    @Test
    @DisplayName("Add product To Basket With Invalid CustomerId")
    void addProductToBasket_invalidProductId() throws Exception{
        when(basketService.addProductToBasket(any())).thenThrow(new BasketException(new BsExceptionDetails(BsErrorCodes.INVALID_PRODUCT_ID.getCode(),BsErrorCodes.INVALID_PRODUCT_ID.getDescription())));
        mockMvc.perform(post(ApiConstants.BASKET_BASE_URL).contentType(APPLICATION_JSON).content(SAMPLE_REQUEST))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errorCode\":\"3002\"," + "\"errorMessage\":\"Product ID Not Valid / Not Available :\"}"));
    }


    @Test
    @DisplayName("Remove product To Basket Test")
    void removeProductFromBasket() throws Exception {
        UpdateBasketRequest request= new UpdateBasketRequest(CUSTOMER_ID,PRODUCT_ID);
        when(basketService.removeProductFromBasket(request)).thenReturn(BsConstants.PRODUCT_DELETED);
        mockMvc.perform(delete(ApiConstants.BASKET_BASE_URL).contentType(APPLICATION_JSON).content(SAMPLE_REQUEST))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json("{\"status\":\"OK\",\"message\":\"Product Removed from Basket\"}"));
    }



    @Test
    @DisplayName("Remove product from Basket With Invalid CustomerId")
    void removeProductFromBasket_invalidCustomerID() throws Exception{
        when(basketService.removeProductFromBasket(any())).thenThrow(new BasketException(new BsExceptionDetails(BsErrorCodes.INVALID_CUSTOMER_ID.getCode(),BsErrorCodes.INVALID_CUSTOMER_ID.getDescription())));
        mockMvc.perform(delete(ApiConstants.BASKET_BASE_URL).contentType(APPLICATION_JSON).content(SAMPLE_REQUEST))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errorCode\":\"3001\"," + "\"errorMessage\":\"Customer ID Not Valid :\"}"));
    }






    @Test
    @DisplayName("Get Product List from Basket")
    void getProductIdList() throws Exception {
        when(basketService.getProductIdList(CUSTOMER_ID)).thenReturn(new ArrayList<String>( Arrays.asList(PRODUCT_ID)));
        mockMvc.perform(get(ApiConstants.BASKET_BASE_URL+ApiConstants.CUSTOMER + CUSTOMER_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"status\":\"OK\",\"message\":\"Product List from Customer's Basket\",\"result\": [\"WF0123\"] }"));
    }


    @Test
    @DisplayName("Get Product List from Basket if Blank")
    void getProductIdList_ifBlank() throws Exception {
        when(basketService.getProductIdList(CUSTOMER_ID)).thenReturn(new ArrayList<String>());
        mockMvc.perform(get(ApiConstants.BASKET_BASE_URL+ApiConstants.CUSTOMER + CUSTOMER_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"status\":\"OK\",\"message\":\"Product List from Customer's Basket\",\"result\": [ ] }"));
    }
}