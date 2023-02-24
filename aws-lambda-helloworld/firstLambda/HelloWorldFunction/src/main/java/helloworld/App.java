package helloworld;

/**
 * Handler for requests to Lambda function.
 */
public class App {

    public String hello(String name) {
    	return "Lambda Functions are super easy and awesome!!" + name;
    }
}
