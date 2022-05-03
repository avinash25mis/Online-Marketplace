package com.mayfair.baskets.cache;


import com.mayfair.baskets.dto.Customer;
import com.mayfair.baskets.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.singletonList;


/*
*  Mocking the cache
* In production we will use some distributed cache lke redis
* Using this in-memory made up cache just for demonstration
*
* */

@Component
public class BsAppCache {

    public static Map<String, Product> PRODUCT_CACHE_MAP= new ConcurrentHashMap<>();
    public static Map<Long, Customer> CUSTOMER_CACHE_MAP= new ConcurrentHashMap<>();
    public static Random random = new Random();
    public static List<String> productNameList = new ArrayList<>(Arrays.asList("Druid Corner Sofa","Trapp Dining Table","Mishler Standing Lamp"));
    public static List<String> offerList = new ArrayList<>(Arrays.asList("3for2","2for1","1for1"));
    public static List<Double> priceList = new ArrayList<>(Arrays.asList(549.99,149.99,36.49));





   @PostConstruct
   public void initializeCache(){
        getProductData();
        getCustomerData();

    }

    private void getCustomerData() {
        Customer c1 = Customer.builder().id(5001l).fullName("john doe").email("customer5001@gmail.com")
                .mobile("8931767676").address("45/5 D. H. Kolkata").build();
        Customer c2 = Customer.builder().id(5002l).fullName("Mr Spector").email("customer5002@gmail.com")
                .mobile("773454333").address("MG Road Delhi").build();
        Customer c3 = Customer.builder().id(5003l).fullName("Mr Bale").email("customer5003@gmail.com")
                .mobile("6631434343").address("33 Street Bandra Road Mumbai").build();
        CUSTOMER_CACHE_MAP.put(c1.getId(),c1);
        CUSTOMER_CACHE_MAP.put(c2.getId(),c2);
        CUSTOMER_CACHE_MAP.put(c3.getId(),c3);
    }

    private void getProductData() {
        Product forObject = restTemplate.getForObject("products-service/api/v1/products/" + "WF0123", Product.class);


        addProduct("WF0123");
        addProduct("WF01234");
        addProduct("WF012345");
    }

    public  static void addProduct(String productId) {
        Product product = Product.builder().id(productId).name(productNameList.get(random.nextInt(productNameList.size())))
                .price(new BigDecimal(priceList.get(random.nextInt(priceList.size())))).offers(singletonList(offerList.get(random.nextInt(productNameList.size())))).build();
        PRODUCT_CACHE_MAP.put(product.getId(),product);
    }

    public static void removeProduct(String productId) {
       PRODUCT_CACHE_MAP.remove(productId);
    }

}


