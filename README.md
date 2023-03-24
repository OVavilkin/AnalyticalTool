# AnalyticalTool

A library to analyze call centre records providing some information on average time spent for each category
by design AnalyticalToolForFiles and AnalyticalToolForStreams should be used to perform the task
Also there is App class that is runnable so library can be used as console tool:
As a standalone application:
 java -cp file.jar net.associal.anylyticaltool.App [inputFile [outputFile [errorFile]]]
 
As console application, you can pipe input to:
 cat inputFile | java -cp file.jar net.associal.analyticaltool.App [ > outputFile [ 2> errorFile ]]
 
To build the project use
  mvn package
  
For details on the API please refer to https://github.com/OVavilkin/AnalyticalTool/blob/main/target/site/apidocs/index.html
