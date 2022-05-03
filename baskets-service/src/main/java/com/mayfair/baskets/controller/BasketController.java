package com.mayfair.baskets.controller;

import com.mayfair.baskets.constants.ApiConstants;
import com.mayfair.baskets.constants.BsConstants;
import com.mayfair.baskets.dto.request.UpdateBasketRequest;
import com.mayfair.baskets.dto.response.BsResponse;
import com.mayfair.baskets.exceptions.BsExceptionDetails;
import com.mayfair.baskets.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
//@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequestMapping(ApiConstants.BASKET_BASE_URL)
@Tag(name = "Basket Management Contoller", description = "APIs to update/display products in the basket of the Customer")
public class BasketController {

    private final BasketService basketService;

    @Autowired
    private RestTemplate restTemplate;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }



    @Operation(summary = "Adding product to the basket of a customer", description = "Available productIds : WF0123, WF01234, WF012345<br/>" +
                    "Available customer Ids : 5001, 5002, 5003" )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = BsResponse.class))),
    @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = BsExceptionDetails.class))),
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = BsExceptionDetails.class)))})

    @PostMapping
    public ResponseEntity<BsResponse> addProductToBasket(@NotNull @RequestBody @Valid UpdateBasketRequest request){
        log.info("processing product adition {}]", request);
        String message = basketService.addProductToBasket(request);
        return ResponseEntity.ok(new BsResponse(BsConstants.OK,message,null));
    }




    @Operation(summary = "Delete product from the basket of a customer",description = "Available productIds : WF0123, WF01234, WF012345<br/>" +
            "Available customer Ids : 5001, 5002, 5003")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = BsResponse.class))),
    @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = BsExceptionDetails.class))),
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = BsExceptionDetails.class)))})



    @DeleteMapping
    public ResponseEntity<BsResponse> removeProductFromBasket(@NotNull @RequestBody @Valid UpdateBasketRequest request){
        log.info("processing product deletion {}]", request);
        String message = basketService.removeProductFromBasket(request);
        return ResponseEntity.ok(new BsResponse(BsConstants.OK,message,null));
    }





    @Operation(summary = "Get All product Ids currently present in the Basket of the customer")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = BsResponse.class))),
    @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = BsExceptionDetails.class))),
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = BsExceptionDetails.class)))})

    @GetMapping(ApiConstants.CUSTOMER+"{customerId}")
    public ResponseEntity<BsResponse<List>> getProductIdList(@PathVariable Long customerId){
        log.info("fetching product Id  of {}]", customerId);
        List<String> productIdList=basketService.getProductIdList(customerId);
        return ResponseEntity.ok(new BsResponse(BsConstants.OK,BsConstants.PRODUCT_LIST,productIdList));
    }




}
