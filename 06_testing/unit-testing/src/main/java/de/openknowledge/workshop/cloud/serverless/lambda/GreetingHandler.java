package de.openknowledge.workshop.cloud.serverless.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import de.openknowledge.workshop.cloud.serverless.model.GreetingRequest;
import de.openknowledge.workshop.cloud.serverless.model.GreetingResponse;

public class GreetingHandler implements RequestHandler<GreetingRequest, GreetingResponse> {


    private static String DEFAULT_GREET = "Hello! I would really like to know, who you are!";

    /**
     * Creates a greeting response with the help of a given <code>GreetingRequest</code>
     * representing first name and last name.
     *
     * @param name    greeting request representing first name and last name
     * @param context aws lambda context
     * @return greeting response wrapping the created greeting
     */
    public GreetingResponse handleRequest(GreetingRequest name, Context context) {

        LambdaLogger logger = context.getLogger();

        // Print info from the context object
        logger.log(String.format("Function name: %s", context.getFunctionName()));
        logger.log(String.format("Function version: %s", context.getFunctionVersion()));
        logger.log(String.format("AWS request ID: %s", context.getAwsRequestId()));

        String firstName = name.getFirstName();
        String lastName = name.getLastName();

        logger.log(String.format("firstName: %s", firstName));
        logger.log(String.format("lastName: %s", lastName));

        String greeting = DEFAULT_GREET;

        if (firstName != null && lastName != null) {
            greeting = String.format("Hello, %s %s! i am pleased to meet you.", firstName, lastName);
        }

        logger.log(String.format("greeting: %s", greeting));

        return new GreetingResponse(greeting);
    }
}