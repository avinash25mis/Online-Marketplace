# General Acceptance Criteria

These acceptance criteria apply to each user story

## Basket Storage

- You can assume a single instance of the service and in-memory storage of baskets **Please avoid using a database**
- This implementation should be thread safe and efficient
- The service should ensure that a single customer has only one active basket at any given point in time

## Support Multiple Devices

- The solution you come up with should support customers using this feature from multiple devices (think phone and desktop simultaneously)
- If a customer adds/removes a product on their mobile device and then opens their browser, they should see the same basket and the newly added product should be there
