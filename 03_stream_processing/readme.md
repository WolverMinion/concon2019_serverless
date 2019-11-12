## AWS Serverless Workshop
### Event Streaming

"Event Streaming" is one of the main areas of Serverless Computing. 

This example demonstrates how to use a AWS Lambda function to read a Cloud based stream - e.g. sensor data - to detect unexpected values and anomalies. 

Therefore our lambda based Stream Processor *AnomalyDetector* will read data from a connected AWS Kinesis stream in real-time and interpret these data with the help of a given anomaly detection algorithm.   

HINT: What is Kinesis?
> For further information about Kinesis see [AWS Kinesis](https://aws.amazon.com/kinesis/) for more information and a deeper understanding.  


#### Programming a Lambda based Anomaly Detector

Our Anomaly Detector function will read data from a cloud based stream (AWS Kinesis). When new data occurs, 
the Anomaly Detector will read and interpret this data, with regard to possible anomalies. Any anomaly
will be written to a corresponding log. 

To start just go to 

    de.openknowledge.workshop.cloud.serverless.lambda.AnomalyDetector
    
and follow the embedded instruction. 

HINT: Look at TODOs 
> Taking a closer look at the **TODO** comments inside the AnomalyDetector class will enable you to fulfill this exercise.    

#### Bring the AnomalyDetector Detector to the Cloud 

As seen before during the HelloWorld example, we have serveral tasks to do to bring our AnomalyDetector Detector to the Cloud. 

* create a deployment artifact 
* create a Lambda Function in the AWS Cloud 
* test the Lambda Function

#### Create a deployment artifact

Once finished all the specified TODOs inside the AnomalyDetector class, we are able to build a deployment artifact (JAR) 
ncluding the AnomalyDetector class itself and all necessary dependencies. 

To do so, switch to the 03\_stream\_processing folder and call ... 

    mvn clean package 
  
As a result of this operation you will find the generated deployment artifact inside the target folder. 

    ./target/anomaly-detector-1.0-SNAPSHOT.jar
    
#### Create a Lambda Function in the AWS Cloud   

Login to your AWS Account and choose *Services -> Lambda* in the main menu. This will bring you to the Lambda main page. 
Click the *create function* button and follow the instruction on screen.

The description below will guide you through all necessary steps to create 
and configure your Lambda function in the AWS cloud: 

1. create function 
2. upload deployment artifact
3. link Lambda function handler method
4. specify a trigger for the lambda function

HINT: Further reading
> for a deeper understanding see also [Create a Simple Lambda Function](https://docs.aws.amazon.com/lambda/latest/dg/get-started-create-function.html).

##### Step 1: Create function 

During the first step of the function creation process we have to define a unique name for our Lambda function  and 
the requested runtime environment, which is Java 8 in our case. It is a best practice to use a prefix for the function 
name as an individual namespace. 
 
In addition we have to connect our Lambda function with an IAM Security Role 
(Identity & Access Management), to make sure that no unauthorized person/role is allowed to execute this function.    

Go to the  *"Author from the scratch"* section and fill the following fields

* Name: *myprefix*\_faultdetector (choose your own prefix!)
* Runtime: Java 8
* Role: IAM role to use for Lambda function execution 
   * *"Choose an existing role"* , if a role with Lambda execution rights already exists  
   * *"Create a custom role"* , if **no** role with Lambda execution rights exists
 
**IMPORTANT**: Chose the right IAM Role
> Make sure that the newly created or chosen role has the correct policies to be allowed to connect to Kinesis streams.  
Creating IAM roles is out of scope for this tutorial. 
See ["2.2. Create the Execution Role"](https://docs.aws.amazon.com/lambda/latest/dg/with-kinesis-example-create-iam-role.html) 
 from the AWS Kinesis Online Tutorial for detailed information.    

 
Press *Create function* button afterwards, which will lead you to the configuration page for the created AWS Lambda function. 

Until now we have created an AWS Lambda configuration in the Cloud but there is still no real code connected to it. 
To do so, we have to upload our deployment artifact and link our AnomalyDetector class to the configuration.

##### Step 2: Upload deployment artifact 

Go to section *"Function code"* and choose 

* Code entry type: *"Upload a Zip- or JAR-File"*

Click *Upload* button and choose the above mentioned deployment artifact.  

HINT: Size of deployment artifact 
> do not wonder about the size of the depoyment artifact which may be round about 10MB. Because the Java 8 based 
Lambda Runtime only includes the core Lamdba classes we have to provide all needed classes (Lambda events, Kinesis SDK) 
within our artifact.  

##### Step 3: Link AnomalyDetector handler method 

Next we have to link the Lambda handler method of our AnomalyDetector example to the current AWS Lambda configuration 
so that the AWS Lambda Runtime is knows what method to call.

The pattern we have to use is 

    [fullqualified className]::[methodName]
    
Go to section *"Function code"* again and fill in  
 
 * Handler: * de.openknowledge.workshop.cloud.serverless.lambda.AnomalyDetector::handleRequest*   


##### Step 4: Specify a trigger for the Lambda function

Great, we are almost done. Last but not least we have to connect the Kinesis stream with our Lambda function. 
This means we have to define the stream as a trigger for our function. 

HINT: How to set up a Kinesis stream with name of your choice ("sensor-data-stream" recommended)?
> Setting up a Kinesis stream is out of scope of this tutorial. You can find detailed information about creating a Kinesis stream at [Step 1: Create a Stream](https://docs.aws.amazon.com/streams/latest/dev/learning-kinesis-module-one-create-stream.html) inside the Amazon Kinesis Data Stream documentation. 

Chooes *Kinesis* from the *Add triggers* list inside the *Designer* section of the Lambda Configurator. A new box with an Kinesis icon will appear inside the *Designer*. In addition you will find a new section *Configure triggers* below the *Designer* section. 

Go to the *Configure triggers* section, choose one of your Kinesis streams and press the *add* button.

HINT: Which kinesis stream to choose?
> The tutorial uses a stream named "sensor-data-stream" by default. If you want to use a different stream you will have to chance the corresponding attribute inside the KinesisTestDataProducer class.  
 

**IMPORTANT**: Dont't forget to save 
> Do not forget to save the configuration changes. Just click the *save* button of the configuration pages main menu (upper right corner). 

Ok, let's test our Lambda function.

#### Test your Lambda Function 

Testing our newly created lambda function is quite easy. We just have to send some test data to the Kinesis stream with the help of a Kinesis Data Producer and see what happens.   

* go to KinesisTestDataProducer class in your workspace 
* run main method
* see what happens 

The KinesisTestDataProducer generates faked sensor data and sends it to a pre-configured Kinesis stream. Most of the data will be valid but some of the data shows some anomalies.   

If everything went right, you will find all anomaly data listed inside the Lambda function related log file. Goto [CloudWatch](https://console.aws.amazon.com/cloudwatch/), choose *Logs* in the main menu (on the left) and afterwards the corresponding Log Group of the *faultdetector* Lambda function. Choose the most current log stream and look for log entries indicating anomaly data. 

  
 
 
  