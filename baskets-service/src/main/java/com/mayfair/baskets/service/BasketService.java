package com.mayfair.baskets.service;

import com.mayfair.baskets.cache.BsAppCache;
import com.mayfair.baskets.constants.BsConstants;
import com.mayfair.baskets.constants.BsErrorCodes;
import com.mayfair.baskets.dto.request.UpdateBasketRequest;
import com.mayfair.baskets.exceptions.BasketException;
import com.mayfair.baskets.exceptions.BsExceptionDetails;
import com.mayfair.baskets.model.Basket;
import com.mayfair.baskets.repository.BasketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class BasketService {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private BasketValidationService basketValidationService;


  /*
  such that the isolation level is visble to other threads
   @Transactional*/
    public String addProductToBasket(UpdateBasketRequest request){
        basketValidationService.validateCustomerId(request.getCustomerId());
        basketValidationService.validateProductId(request.getProductId());
        log.info("valid product and customer Ids {} ",request);
        Basket basket = basketRepository.getActiveBasketByCutomerId(request.getCustomerId());
        if(basket==null){
            basket = new Basket(request.getCustomerId(),new ArrayList<String>( Arrays.asList( request.getProductId())));
        }else{
            List<String> productIdList = basket.getProductIdList();
            if(productIdList==null){
                productIdList = new ArrayList<>();
            }else{
                if(productIdList.contains(request.getProductId())){
                    log.error("product already present in the basket  {} ",request.getProductId());
                    throw  new BasketException(new BsExceptionDetails(BsErrorCodes.DUPLICATE_PRODUCT_IN_BASKET.getCode(),BsErrorCodes.DUPLICATE_PRODUCT_IN_BASKET.getDescription()+request.getProductId()));
                }
            }
            productIdList.add(request.getProductId());
        }
        basketRepository.updateBasket(basket);
        BsAppCache.removeProduct(request.getProductId());

        return BsConstants.PRODUCT_ADDED;
    }



    /*
   so  that the isolation level is visble to other threads
    @Transactional*/
    public String removeProductFromBasket(UpdateBasketRequest request){
        basketValidationService.validateCustomerId(request.getCustomerId());
        log.info("valid product and customer Ids {} ",request);
        boolean productNotFound=false;
        Basket basket = basketRepository.getActiveBasketByCutomerId(request.getCustomerId());
        if(basket == null){
            log.info("Empty Basket for customer  {} ",request.getCustomerId());
            productNotFound=true;
        }else{
            List<String> productIdList = basket.getProductIdList();
            if(productIdList == null){
                productNotFound=true;
            }else{
              if(!productIdList.remove(request.getProductId())){
                  log.error("Basket does not have the product  {} ",request.getProductId());
                  productNotFound=true;
              }
            }
        }
        if(productNotFound){
            throw  new BasketException(new BsExceptionDetails(BsErrorCodes.PRODUCT_NOT_FOUND_IN_BASKET.getCode(),BsErrorCodes.PRODUCT_NOT_FOUND_IN_BASKET.getDescription()+request.getProductId()));
        }
        basketRepository.updateBasket(basket);
        BsAppCache.addProduct(request.getProductId());

        return BsConstants.PRODUCT_DELETED;
    }



    public List<String> getProductIdList(Long customerId) {
        basketValidationService.validateCustomerId(customerId);
        return basketRepository.getProductList(customerId);
    }




}
