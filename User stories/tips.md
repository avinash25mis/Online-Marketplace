# Tips

## Interacting with Products

We expect the solution to interact with products only by using their ID. All other information like name, price, and any other information are expected to come from external services. You can choose whether you want to use our included docker services, or to mock the service responses.

## Security

Please ignore security/authentication or session management in your solution. In any situations where you'd normally authenticate/authorize the user and read their credentials, simply pass in customerId in the API calls as one of the parameters. Yes, of course you'd never do this in a real-world application.
We can discuss the authentication and authorization requirements during the architecture/design stage.

## Lombok

The services we provide as examples use [project Lombok](https://projectlombok.org/). If you have not used it before but would like to use it for this submission, there are two things you'll need when working with these projects in Intellij:
* Make sure Lombok plugin is installed in Intellij
* Enable annotations processing on the project (you should get a pop-up prompting you to enable it after getting the plugin).

More details, and instructions for Eclipse, can be found [here](https://www.baeldung.com/lombok-ide), or on official Lombok website.

You can also read useful [introduction to Lombok](https://www.baeldung.com/intro-to-project-lombok).
