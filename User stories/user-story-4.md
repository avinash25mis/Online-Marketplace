# User Story 4 - Enable checkout 

## User Story

As a user, I would like to:
* Be able to check out my basket, and submit a payment to the Payment service.

## Acceptance Criteria

* You have satisfied the user story above.
* Your solution meets our thread safety and storage requirements [described here](ac.md).

## Extra Information

### Payment Service

You can launch this service locally by running `com.wayfair.payments.PaymentsServiceMain` from payments-service. It will run on port 8082 by default, and expose the API definition under [/swagger endpoint](http://localhost:8082/swagger). 

You can also run this via docker with the following commands:

```bash
docker-compose build payments
docker-compose up payments
```

The payment service expects a basket id and total payment value, and will process the payment for the basket.

It immediately returns "payment id", and the operation will proceed asynchronously. Your service can simply remove the basket when payment id is returned. However, it should NOT remove the basket if payment service returns an error. An error should be returned from your endpoint, and the service should keep the basket and allow the customer to try again later.

## General Tips

We have assembled a list of general tips which might help clarify any questions you might have for this user story in the
following [page](tips.md).

## Future Steps - Architecture Design Discussion

During the later architectural interview, you can expect the following questions on this user story.

- What should we do with old baskets once we've checked out?
- How could we make the process of checking out less dependent on the payments service responding correctly?
- What would be the appropriate storage solution for the data - both for baskets that are still "in progress" and those that were successfully checked out (for the customer to be able to see their purchases history)?
