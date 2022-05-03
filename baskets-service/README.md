## Wayfair Java Spring Take Home Assignment

#### baskets-service has been developed which runs on port 8083 by default

### Assignments Picked
User Story 1 - Adding products to the basket
User Story 2 - Removing products from the basket



### Assumptions

#### 1. Mocked Data 
(Since we are not using Db we assume appilcation has following Data to start and test with)

#### 2. Avaulable Product ID 
WF0123, WF01234, WF012345

#### 3. Avaulable Customer ID
 5001, 5002, 5003
 
#### 4. We also assume a Customer service runnning so from where we will get available valid Customer Ids & which may also be stored  in a distributed cache 
 
 
### APIs
 
#### 1. Add product to Basket

 curl -X POST http://localhost:8083/api/v1/baskets  -H "Content-Type: application/json" -d '{"customerId":"5001","productId": "WF0123"}' 
 
also available at (http://localhost:8083/swagger)

#### 2. List of product IDs in a basket.

curl -X GET http://localhost:8083/api/v1/baskets/customer/5001  -H "Content-Type: application/json" 

also available at (http://localhost:8083/swagger)


#### 3. Remove product from Basket

curl -X DELETE http://localhost:8083/api/v1/baskets  -H "Content-Type: application/json" -d '{"customerId":"5001","productId": "WF0123"}' 

also available at (http://localhost:8083/swagger)



