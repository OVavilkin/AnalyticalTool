/**
 * A library to analyze call centre records providing some information on average time spent for each category <br/>
 * by design AnalyticalToolForFiles and AnalyticalToolForStreams should be used to perform the task <br/>
 * Also there is App class that is runnable so library can be used as console tool: <br/>
 * <br/>
 * As a standalone application: <br/>
 * java -cp file.jar net.associal.anylyticaltool.App [inputFile [outputFile [errorFile]]] <br/>
 * <br/>
 * As console application, you can pipe input to: <br/>
 * cat inputFile | java -cp file.jar net.associal.analyticaltool.App [ > outputFile [ 2> errorFile ]] <br/>
 * <br/>
 * @author Alexey Vavilkin <br/>
 */
package net.associal.analyticaltool;