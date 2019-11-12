## AWS Serverless Workshop
### API Gateway   

Lambda functions are only accessable from inside the AWS Cloud by default. If you want to access an lambda function from outside the cloud, e.g. from a web client, you have to implement a kind of backplane between non-cloud and cloud services via an [API Gatway](https://docs.aws.amazon.com/apigateway/latest/developerguide/welcome.html). 

To get to know the functioning of an API Gateway we will set up an example scenario where external clients can access our image gallery via lambda functions via RESTful calls. 

The corresponding URI will look like 

	.../images/{imageId}
	

#### PRECONDITION: Set up lambda functions and DynamoDB 

We will need several lambda funtions and a DynamoDB instance for this example. 

Lambda functions:  

* *api\_list\_images*: returns list of images (of owner) via `GET ../images?owner= ...` (`owner` optional)
* *api\_read\_image*: returns single image given by its id via `GET ../images/{imageId}`
* *api\_write\_image*: stores image via `POST ../images/`
* *api\_update\_image*: updates image via `PUT ../images/{imageId}`
* *api\_delete\_image*: deletes image via `DELETE ../images/{imageId}`

DynamoDB Instance

* *imageinfos*: DynamoDB table hosting our image information 

##### Step 1: create lambda functions

Go to the 04\_api\_gateway folder and call ... 

    mvn clean package 
  
As a result of this maven operation you will find the generated deployment artifact inside the target folder. 

    ./target/api-gateway-1.0-SNAPSHOT.jar

Use this artefact to create the above listed lambda functions. 

**IMPORTANT**: Chose the right IAM Role
> Make sure that the newly created or chosen role has the correct policies to be allowed to read and write to the DynamoDB service. Creating IAM roles is out of scope for this tutorial. See ["Lambda Permission Model"](https://docs.aws.amazon.com/lambda/latest/dg/intro-permission-model.html) for further information. 

##### Step 2: create DynamoDB 

To create and initialize the DynamoDB table with image information we use a little helper application.  

* go to ImageInfoTableClientBase class in your workspace 
    * take a look at the TODOs
* go to ImageInfoTableCreatorClient class in your workspace  
    * run main method
    * see what happens 
    
The ImageInfoTableCreatorClient generates a DynamoDB table named *imageinfo* and initializes it with some image information. 

HINT
> If you want to delete the created table for any reason just rund the ImageInfoTableDeletionClient.

Now, after all preparative work is done, we can start creating the API Gateway. 

#### Create and setup the API Gateway 

The description below will guide you through all necessary steps to create and configure an API Gateway for accessing your Lambda functions: 

1. Create API Gateway Endpoint
2. Create resoures and methods
3. Test the API

##### Step 1: Create API Gateway 

First of all we have to create an API Gateway endpoint.  

Login to your AWS Account and choose *Services -> API Gateway* in the main menu. This will bring you to the API Gateway main page. Click the *create gateway* button and follow the instruction on screen.

Select the *new API* selectbox and fill in the following values: 

* API name: *image-info-api*
* Description: RESTful API for image information 
* Endpoint Type: regional 

Press *Create API* button afterwards, which will lead you to the configuration page for the created API Gatwway.  

##### Step 2: Create resources and methods 

Lets start with a simple GET request for all images. 

Choose the action *create resource* and fill in the resource name *images*. The resource path should get the same name by default. Subsequent to this step choose the action *create method* for the just created resource and choose the *GET* method from the drop down box. 

Because we want to connect one of our lambda functions to this REST resource, we have to configure 

* integation type: Lambda Function
* Lambda Region: YOUR REGION 
* Lambda Function: gateway\_list\_images

Press *Save* button afterwards, which will bring up a permissions dialog. Confirm the permission request by pressing the *Ok* button. 

##### Step 3: Test the API  

To test the newly created API Gateway just press the *test* icon and the *test* button on the subsequent page. If everything went right you will see a list of image information. 

#### What's next? 

Congratulations! Your have just created an API Gatweway and hence enabled the rest of the world to access your image gallery lambda functions. Or to be honest at least one of them. 

But what is about the other functions. How to configure the API Gateway to support a path and / or query parameter, e.g. when calling 

* *GET ../images/{imageId}*
* *GET ../images?owner=mobileLarson*

And how to handle a request with additional payload, e.g. when calling 

* *POST ../images/* or 
* *PUT ../images/{imageId}*

HINT
> to path though query parameters, path parameters or request payload you have to declare a corresponding mapping template. 
See [API Gatway](https://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-mapping-template-reference.html) for details.  


HINT
> To path though query parameters, path parameters or request payload you have to declare a corresponding mapping template. 
See [API Gatway Mapping Template](https://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-mapping-template-reference.html) for details. 

Just play around a little bit with the test function of the API Gateway and the different mapping possibilities and try to understand the way the Gateway works. 

HINT
> Right now the lambda functions are only accessable via API Gateway test functionality. 
To get an unique URL for the API Gateway and the underlaying lambda functions we must publish the API Gateway in addition for a specific stage. 
