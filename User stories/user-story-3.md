# User Story 3 - Calculating a total value of a basket

## User Story

As a user, I would like to:
* Be able to get a list of all the products in my basket.
* Be able to get the total value of the basket.
* Take any valid promotions into account when calculating the total value of my basket.

## Acceptance Criteria

* You have satisfied the user story above.
* Your solution meets our thread safety and storage requirements [described here](ac.md).

## Extra Information

### Product Service Stub

We have provided a stub microservice that provides basic details about the product. You can integrate against if you wish, or you can solve this using mocking.

You can launch this service locally by running `com.wayfair.products.ProductsServiceMain` from products-service. It will run on port 8081 by default, and expose the API definition under [/swagger endpoint](http://localhost:8081/swagger).

On a high level, the product service has one endpoint: to get a product by id. It will return JSON containing product id, current price, and a list of names currently running promotions.

### Promotions Information

For simplicity, let's assume that there are only two possible promotions right now: "3for2" and "2for1".

* In "2for1" the customer can get 2 items for the price of 1.
  For example:
    - 1 product -> charged for 1
    - 2 products -> charged for 1
    - 3 products -> charged for 2
    - 4 products -> charged for 2, and so on.

* In "3for2" the customer can get 3 items for the price of 2.
  For example:
    - 1 product -> charged for 1
    - 2 products -> charged for 2
    - 3 products -> charged for 2
    - 4 products -> charged for 3
    - 5 products -> charged for 4
    - 6 products -> charged for 4, and so on.

#### Promotions Edge Case

In the event where a product has two promotion codes, it is okay to simply pick one for the purpose of calculation.

## General Tips

We have assembled a list of general tips which might help clarify any questions you might have for this user story in the
following [page](tips.md).

## Future Steps - Architecture Design Discussion

- Think about ways we can make this implementation less intense on the product service.
- How would this change if we used dynamic pricing (prices change at irregular, unpredictable increments, i.e., change every minute, or every week)?
