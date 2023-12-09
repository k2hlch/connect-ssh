This is a demo which illustrates a simple web service from an EJB
which echoes its input.

Note : substitute host:port by appropriate values .

step 0

  Create a directory named tmp in the stateless_ejb directory.  This
  will be used to store new files which the demo creates.

step 1

  Compile the java sources

  javac -classpath $ORACLE_HOME/j2ee/home/lib/ejb.jar:. HelloService*.java -d tmp

step 2

  Create an ejb jar file from the compiled classes and the supplied
  META-INF/ejb-jar.xml

  jar cf tmp/Hello.jar META-INF -C tmp demo

step 3

  Use the web service assembly tool to create an ear file containing
  the jar file from step 2.  The tool will add the war file and other
  necessary xml descriptors.

  java -jar $ORACLE_HOME/webservices/lib/WebServicesAssembler.jar -config assemble.xml

step 4

  Deploy the ear file

 - In an iAS environment , use the following command to deploy the ear file :

  %dcmctl deployApplication -file tmp/HelloService.ear -a demo_ejb_web_service

  (it will deploy the application to the 'home' instance ,refer to 'OC4J - Users Guide for more details on dcmctl)

 - For a standalone OC4J version , do the following:

  java -jar $ORACLE_HOME/j2ee/home/admin.jar ormi://localhost admin welcome \
    -deploy -file tmp/HelloService.ear -deploymentName demo_ejb_web_service

step 5

  To use OC4J's built-in web server, bind the web application to name

  java -jar $ORACLE_HOME/j2ee/home/admin.jar ormi://localhost admin welcome \
   -bindWebApp demo_ejb_web_service HelloService_web http-web-site /sejb_webservices

  Use you browser to open the url - http://<host>:<port>/sejb_webservices , click on the links of each exposed service .You will reach the  'service endpoint page' of that service , which will have  the links to  operations  exposed as well as other webservice 'artifacts'(wsdl,proxy etc).

  Try the individual operations and view the resulting SOAP messages .

step 6

  Download the jar containing the client proxy class to the tmp directory,
  naming it HelloClient_proxy.jar.  Download it from:

  http://$HOST:8888/sejb_webservices/HelloService?proxy_jar

step 7

  Compile and execute the client code using the build.xml ant script provided
  by typing in "ant". The first execution will take longer because the server
  side wrapper is built on the first call.

  This concludes example of client using static-proxy to invoke a webservice .

step 8 
   For client using  dynamic proxy invocation:

   compile and execute the client DynamicClient.java .Make sure that the classpath is set as in java_services
and dsv2.jar is present in the classpath .

   %javac DynamicClient.java -d tmp
   %java -classpath tmp/:$CLASSPATH DynamicClient "http://host:port/sejb_webservices/HelloService?WSDL" "Echo this sentence"

 you should see a result like :
  HELLO!! You just said :Echo this sentence


To cleanup, remove the tmp directory and undeploy the application

  java -jar $ORACLE_HOME/j2ee/home/admin.jar ormi://localhost admin welcome \
    -undeploy demo_ejb_web_service

NOTE :
For Win NT/2000 users ,
  use ';' instead of ':' between different classpath entries when setting classpath.
  environment variables are represented as %ORACLE_HOME% instead of $ORACLE_HOME .
