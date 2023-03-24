# AnalyticalTool

A library to analyze call centre records providing some information on average time spent for each category <br>
by design AnalyticalToolForFiles and AnalyticalToolForStreams should be used to perform the task <br>
 <br>
Also the App class is runnable so library can be used as a console tool: <br>
 <br>
 <br>
As a standalone application: <br>
 <br>
 java -cp file.jar net.associal.anylyticaltool.App [inputFile [outputFile [errorFile]]] <br>
  <br>
  <br>
As console application, for example you can pipe input from cat: <br>
 <br>
 cat inputFile | java -cp file.jar net.associal.analyticaltool.App [ > outputFile [ 2> errorFile ]] <br>
  <br>
  <br>
To build the project use <br>
 <br>
  mvn package <br>
   <br>
   <br>
For details on the API please refer to <a href="https://htmlpreview.github.io/?https://github.com/OVavilkin/AnalyticalTool/blob/main/target/site/apidocs/index.html">API Documentation</a> <br>
