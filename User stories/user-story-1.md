# User Story 1 - Add Products To Basket

As a user, I would like to:
* Be able to add products to a basket by product ID.
* Be able to see a list of product IDs in my basket.

## Acceptance Criteria

* You have satisfied the user story above.
* Your solution meets our thread safety and storage requirements [described here](ac.md).

## General Tips

We have assembled a list of general tips which might help clarify any questions you might have for this user story in the 
following [page](tips.md).

## Future Steps - Architecture Design Discussion

During the later architectural interview, you can expect the following questions on this user story.

- What are the options for scaling the service to multiple instances and multiple datacenters?  We would like the customer's basket to be always available and have same state, regardless where they connect from.
- What are appropriate storage solutions for the data (whether NoSQL or relational databases)?
