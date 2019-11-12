## AWS Serverless Workshop
### Preparation   

In the context of this workshop we will need access to diverse AWS  Services (e.g. S3, Kinesis, ...).

For simplicity reason we have encapsulated the AWS Service Client creation (e.g. S3Client. KinesisClient, ...) inside 
this module, named aws-account. All other modules will reference this module as a maven dependency. 

#### Adjust the region settings

To optimize your serverless experiences you should adjust the region setting of the AWS client creator class.   

Go to the AwsClientProvider class source code 

    de.openknowledge.workshop.cloud.serverless.infrastructure.AwsClientProvider
    
and follow the embedded instruction. 

HINT: Look at TODOs 
> Taking a closer look at the **TODO** comments inside the AwsClientProvider class will enable you to fulfill this exercise. 

To be able to choose the right region see also: 
[AWS Regionen und Availability Zones] (https://docs.aws.amazon.com/de_de/AWSEC2/latest/UserGuide/using-regions-availability-zones.html)
 

#### Compile and install to local maven repository 
 
Call the following mvn command inside the workshops main directory before starting with the exercises to build and install ```aws-account``` in your local maven repository: 
 
    mvn clean install

HINT:       
> Due to the fact, that the module ```aws-account``` is references by all other exercises you should call mvn clean install **before** starting with the exercises. 
 