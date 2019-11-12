## AWS Serverless Workshop
### Data & File  Processing 

Data and file processing is one of the main areas of Serverless Computing. 

This example demonstrates how to use AWS Lambda functions to process/manipulate a file by creating a thumbnail from an original image. The Lambda function will be triggered by an event that arises whenever someone places a file (an image) in a pre-defined 
folder (a.k.a. *Bucket* ) of the cloud based AWS Storage System ( 
a.k.a. *S3* )

HINT: What is AWS S3?
> Think about AWS S3 as a kind of of cloud based filesystem. See also 
[AWS Simple Storage System](https://aws.amazon.com/s3/) for more information and a deeper understanding.  

#### Programing a Lambda based Thumbnail Generator

Our Thumbnail Generator function should be triggered automatically, if somebody - human or machine - puts an image to a user specific pre-defined image location of the S3 Cloud Storage system. 

After detecting a new image the Lambda function should 

* read the image from the storage system
* create a thumbnail representation of this image
* store the created thumbnail inside the S3 Cloud Storage system    

Go to the ThumbnailGenerator class source code 

    de.openknowledge.workshop.cloud.serverless.lambda.ThumbnailGenerator
    
and follow the embedded instruction. 

HINT: Look at TODOs 
> Taking a closer look at the **TODO** comments inside the ThumbnailGenerator class will enable you to fulfill this exercise.  

#### Bring the Thumbnail Generator to the Cloud 

As seen before during the first example, we have serveral tasks to do to bring 
our Thumbnail Generator to the Cloud. 

* create a deployment artifact 
* create a Lambda Function in the AWS Cloud 
* test the Lambda Function

#### Create a deployment artifact

Once finished all the specified TODOs inside the ThumbnailGenerator class, we are able to build a deployment artifact (JAR) including the ThumbnailGenerator class itself and all necessary dependencies. 

To do so, switch to the 02\_file\_processing folder and call ... 

    mvn clean package 
  
As a result of this operation you will find the generated deployment artifact inside the target folder. 

    ./target/thumbnail-generator-1.0-SNAPSHOT.jar
    
#### Create a Lambda Function in the AWS Cloud   

Log-in to your AWS Account and choose *Services -> Lambda* in the main menu. This will bring you to the Lambda main page. Click the *create function* button and follow the instruction on screen.

The description below will guide you through all necessary steps to create and configure your Lambda function in the AWS cloud: 

1. create function 
2. upload deployment artifact
3. link Lambda function handler method
4. specify a trigger for the lambda function

HINT: Further reading
> for a deeper understanding see also [Create a Simple Lambda Function](https://docs.aws.amazon.com/lambda/latest/dg/get-started-create-function.html).

##### Step 1: Create function 

During the first step of the function creation process we have to define a unique name for our Lambda function  and the requested runtime environment, which in our case is Java 8. It is a best practice to use a prefix for the function name as an individual namespace. 
 
In addition we have to connect our Lambda function with an IAM Security Role 
(Identity & Access Management), to make sure that no unauthorized person/role is allowed to execute this function.    

Go to the  *"Author from the scratch"* section and fill in the following fields

* Name: *myprefix*\_thumbnailgenerator (choose your own prefix!)
* Runtime: Java 8
* Role: IAM role to use for Lambda function execution 
   * *"Choose an existing role"* , if a role with Lambda execution rights already exists  
   * *"Create a custom role"* , if **no** role with Lambda execution rights exists
 
**IMPORTANT**: Chose the right IAM Role
> Make sure that the newly created or chosen role has the correct policies to be allowed to read and write to the S3 Storage system. Creating IAM roles is out of scope for this tutorial. See ["Lambda Permission Model"](https://docs.aws.amazon.com/lambda/latest/dg/intro-permission-model.html) for further information.   

HINT: Lambda Permissions
> There are several predefined lambda execution roles. While **AWSBasicLambdaExecutionRole** is the simplest role possible, allowing only access to the cloud based log sysetm, **AWSLambdaExecutionRole** in addition adds access rights to the S3 storage system.
 
Press *Create function* button afterwards, which will lead you to the configuration page for the created AWS Lambda function. 

Until now we have created an AWS Lambda configuration in the Cloud but there is still no real code connected to it. To do so, we have to upload our deployment artifact and link our ThumbnailGenerator class to the configuration.

##### Step 2: Upload deployment artifact 

Go to section *"Function code"* and choose 

* Code entry type: *"Upload a Zip- or JAR-File"*

Click *Upload* button and choose the above mentioned deployment artifact.  

HINT: Size of deployment artifact 
> do not wonder about the size of the depoyment artifact which may be round about 10MB. Because the Java 8 based Lambda Runtime only includes the core Lamdba classes we have to provide all needed classes (Lambda events, S3 SDK) within our artifact.  

##### Step 3: Link ThumbnailGenerator handler method 

Next we have to link the Lambda handler method of our ThumbnailGenerator example to the current AWS Lambda configuration so that the AWS Lambda Runtime knows what method to call.

The pattern we have to use is 

    [fullqualified className]::[methodName]
    
Go to section *"Function code"* again and fill in  
 
 * Handler: *de.openknowledge.workshop.cloud.serverless.lambda.ThumbnailGenerator::handleRequest*   


##### Step 4: Specify a trigger for the Lambda function

Great, we are almost done. Last but not least we have to connect an existing S3 bucket with our Lamnda function. 
This means we have to define the S3 bucket as a trigger for our function.   

HINT: How to create a S3 Bucket with name of your choice ("ok-ws-images" recommended)?
> Creating a S3 Bucket is out of scope of this tutorial. You can find detailed information about S3 Buckets handling at [How Do I Create an S3 Bucket?](https://docs.aws.amazon.com/AmazonS3/latest/user-guide/create-bucket.html) inside the Amazon S3 documentation.

Chooes *S3* from the *Add triggers* list inside the *Designer* section of the Lambda Configurator. A new box with an S3 icon will appear inside the *Designer*. In addition you will find a new section *Configure triggers* below the *Designer* section. 

Goto the *Configure triggers* section and fill in the fields: 

* Bucket: choose the s3 bucket to use
* Event Tyoe: choose *Object Created (All)* 
* Prefix: *images/*
* Suffix: leave empty 

HINT: Which bucket to choose?
> The tutorial uses a bucket named "ok-ws-images" by default. If you want to use a different bucket name you will have to chance the corresponding attribute inside the ThumbnailGenerator class.  

**IMPORTANT**: Dont't forget to save 
> Do not forget to save the configuration changes. Just click the *save* button of the configuration pages main menu (upper right corner). 

Ok, let's test our Lambda function.

#### Test your Lambda Function 

Testing our newly created lambda function is quite easy. We just have to put an image to the pre-definded S3 Storage location, which is the *images* folder inside our S3 workshop bucket: 

* open your [S3 Storage](https://s3.console.aws.amazon.com/s3/) system inside a browser of your choice
* login, if not done so far
* choose the workshop bucket
* choose the images folder inside the bucket
* upload an image

HINT: Use jpg or png only
> due to the fact that our ThumbnailGenerator is not very intelligent it is recommended only to use images of image type *jpg* or *png*. 

If everything went right, you will find a thumbnail version of the uploaded images inside the *thumbnails* folder next to the images folder.
 
 
  