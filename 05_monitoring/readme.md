## AWS Serverless Workshop
### Logging and Monitoring  

To "survive" the highly distributed world of serverless applications, a well designed logging and monitoring strategy is obligatory.
  
#### Log Analyzer 

In a serverless world it is essential to be able to automatically react to abormalities and exceptions. These defects can be detected by observing the logs of the serverless application via a serverless function: a log analyzer

##### Step 1: create log analyzer lambda functions

Go to the 05\_monitoring folder and call ... 

    mvn clean package 
  
As a result of this maven operation you will find the generated deployment artifact inside the target folder. 

    ./target/monitoring-1.0-SNAPSHOT.jar

Use this artefact and create the following lambda function: 

* *YOUR_PREFIX\_log\_analyzer* 

##### Step 2: test log analyzer lambda functions

To test our log analyzer we can use the following predefined events that emulate CloudWatch log events: 

* *logEventCommon*: a JSON log event, representing a standard log entry
* *logEventError*: a JSON log event, representing an error log entry
* *logEventWarn*: a JSON log event, representing an error log entry
* *logEventMetric*: a JSON log event, representing a warning log entry

You will find JSON files having the same name and representing the above listed log events inside the main folder of the *05\_monitoring* project.

Create corresponding test events with the content of the JSON files and play around with the log analyzer lambda function. 

#### Log Export

Logs may get very, very big over time. To save money it makes sense to limit the retention period of a log and archive old log data from time to time. 

Go to the **CloudWatch** main page and take a look at the already existing logs. Try to change the retention policy for one of them. 

In addition take a look at the possible *actions* for the existing logs and try to export one of your logs to the S3 storage system. 

HINT: export log needs special policy
> S3 buckets need a special policy for storing log exports. You will find a template of this policy inside the JSON file **s3_policy.json**.
Insert the content of this policy - after replacing [S3-BUCKET-NAME] with a bucket name of your choice - into the 
bucket policy editor (bucket permission tab). See [Log Export to S3](https://docs.aws.amazon.com/AmazonCloudWatch/latest/logs/S3ExportTasks.htmll) 
for details.  

HINT: access archived log data
> If you want to access one of the archived logs just choose the action item **view all exports to Amazon S3**. 

