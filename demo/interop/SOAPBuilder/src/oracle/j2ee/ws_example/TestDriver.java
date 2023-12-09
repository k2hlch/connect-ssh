package oracle.j2ee.ws_example;

import oracle.j2ee.ws_example.InteropTestProxy;
import oracle.j2ee.ws_example.SOAPStruct;
import java.lang.reflect.Array;
import java.util.Date;
import java.util.Calendar;
import java.util.Vector;
import java.math.BigDecimal;

import org.soapbuilder.EndpointInfo;
import org.soapbuilder.interopInfoSvcProxy;

public class TestDriver {
    private static String[] endpointName = { "Import1", "Import2", "Import3", "RpcEnc"};
    private int globalTotalCount = 0;
    private int globalFailureCount = 0;
    private int _totalCount = 0;
    private int _failureCount = 0;

    private InteropTestProxy _proxy;

    private boolean _verbose = false;
    private boolean _dynamic = false;
    Vector  usedArgs   = null ;

    public void setVerbose(boolean verbose) {
        _verbose = verbose;
    }

    public boolean isVerbose() {
        return _verbose;
    }

    public void setDynamic(boolean dynamic) {
        _dynamic = dynamic;
    }

    public boolean isDynamic() {
        return _dynamic;
    }

    private void resetCounter() {
        _totalCount = 0;
        _failureCount = 0;
    }
    
    /**
     * When testMode is true, we throw exceptions up to the caller
     * instead of recording them and continuing.
     */
    private boolean testMode = false;

    public TestDriver() {
        _proxy = new InteropTestProxy();
    }

    /**
     * Returns an int specifying the number of times that the flag was
     * specified on the command line.  Once this flag is looked for you
     * must save the result because if you call it again for the same
     * flag you'll get zero.
     */
    public int isFlagSet(String args[], char optChar) {
        int  value = 0 ;
        int  loop ;
        int  i ;

        for ( loop = 0 ; usedArgs != null && loop < usedArgs.size() ; loop++ ) {
            String arg = (String) usedArgs.elementAt(loop);
            if ( arg.charAt(0) != '-' ) continue ;
            for ( i = 0 ; i < arg.length() ; i++ )
                if ( arg.charAt(i) == optChar ) value++ ;
        }

        for ( loop = 0 ; loop < args.length ; loop++ ) {
            if ( args[loop] == null || args[loop].length() == 0 ) continue ;
            if ( args[loop].charAt(0) != '-' ) continue ;
            while (args[loop] != null && 
                   (i = args[loop].indexOf(optChar)) != -1) {
                args[loop] = args[loop].substring(0, i) + args[loop].substring(i+1) ;
                if ( args[loop].length() == 1 ) 
                    args[loop] = null ;
                value++ ;
                if ( usedArgs == null ) usedArgs = new Vector();
                usedArgs.add( "-" + optChar );
            }
        }
        return( value );
    }

    /**
     * Main entry point.  Tests a variety of echo methods and reports
     * on their results.
     *
     * -v indicates verbose mode. if not set output only one line per endpoint tested. 
     * -r indicates to run test agains remote endpoints.
     * -d indicates to run test agains the dynamic list of remote endpoints (only with -r).
     */
    public static void main(String[] args)
    {
        TestDriver testDriver = new TestDriver();
        boolean remote = testDriver.isFlagSet(args,'r') > 0;
        testDriver.setVerbose( testDriver.isFlagSet(args,'v') > 0);
        testDriver.setDynamic( testDriver.isFlagSet(args,'d') > 0);

        if (remote) {
            testDriver.executeRemoteOnly();
        } else {
            testDriver.executeLocalOnly();
        }
        testDriver.runSummary();
    }

    private void runSummary() {
        System.out.println( "Overall result : " 
          + (globalTotalCount - globalFailureCount) + "/" + globalTotalCount);
    }

    private void executeLocalOnly() {
        executeBaseTestSuite( _proxy, "Local Oracle 9iAS");
// you can remove the comment to test the SOAPBuilder Round 3 RPC endpoints.
/*
        _proxy._setSoapURL("http://localhost:8888/soapbuilder/r3/Import1");
        executeRound3GroupDTestSuite( _proxy, "Local Oracle 9iAS", 0);
        _proxy._setSoapURL("http://localhost:8888/soapbuilder/r3/Import2");
        executeRound3GroupDTestSuite( _proxy, "Local Oracle 9iAS", 1);
        _proxy._setSoapURL("http://localhost:8888/soapbuilder/r3/Import3");
        executeRound3GroupDTestSuite( _proxy, "Local Oracle 9iAS", 2);
        _proxy._setSoapURL("http://localhost:8888/soapbuilder/r3/InteropTestRpcEnc");
        executeRound3GroupDTestSuite( _proxy, "Local Oracle 9iAS", 3);
*/
    }

    private void executeRemoteOnly() {
        EndpointInfo[] endpoints = null;

        if (isDynamic()) {
            try {
                interopInfoSvcProxy test = new interopInfoSvcProxy();
                endpoints = test.GetEndpointInfo( "base");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else {
            EndpointInfo[] staticList = {
              new EndpointInfo("Apache Axis","http://nagoya.apache.org:5049/axis/services/echo",""),
              new EndpointInfo("Apache SOAP 2.2","http://nagoya.apache.org:5049/soap/servlet/rpcrouter",""),
              new EndpointInfo("Oracle 9iAS","http://ws-interop.oracle.com/soapbuilder/r2/InteropTest",""),
              new EndpointInfo("ASP.NET","http://www.mssoapinterop.org/asmx/simple.asmx",""),
              new EndpointInfo("GLUE","http://www.themindelectric.net:8005/glue/round2",""),
              new EndpointInfo("HP SOAP","http://soap.bluestone.com/hpws/soap/EchoService",""),
              new EndpointInfo("Sun JAX-RPC","http://soapinterop.java.sun.com:80/round2/base","")
            };
            endpoints = staticList;
        }
        for (int i = 0 ; i < endpoints.length ; i++) {
            _proxy._setSoapURL(endpoints[i].getEndpointURL());
            executeBaseTestSuite( _proxy, endpoints[i].getEndpointName());
        }
    }

    /**
     * Determine if two objects are equal.  Handles nulls and recursively
     * verifies arrays are equal.  Accepts dates within a tolerance of
     * 999 milliseconds.
     */
    protected boolean equals(Object obj1, Object obj2) {
        if (obj1 == null || obj2 == null) return (obj1 == obj2);
        if (obj1.equals(obj2)) return true;
        if (obj1 instanceof Calendar && obj2 instanceof Calendar) {
            if (Math.abs(((Calendar)obj1).getTime().getTime() - ((Calendar)obj2).getTime().getTime()) < 1000) {
                return true;
            }
        }
        if (obj1 instanceof Date && obj2 instanceof Date) {
            if (Math.abs(((Date)obj1).getTime() - ((Date)obj2).getTime()) < 1000) {
                return true;
            }
        }
        if (!obj2.getClass().isArray()) return false;
        if (!obj1.getClass().isArray()) return false;
        if (Array.getLength(obj1) != Array.getLength(obj2)) return false;
        for (int i=0; i<Array.getLength(obj1); i++)
            if (!equals(Array.get(obj1,i),Array.get(obj2,i))) return false;
        return true;
    }

    public void verifyCall(String method, Object sent, Object gotBack) {
        String message;
        if (this.equals(sent, gotBack)) {
            message = "OK";
            if (!isVerbose()) {
              return;
            }
        } else {
            _failureCount++;
            if (gotBack instanceof Exception) {
                message = "Exception: " + ((Exception)gotBack).getMessage();
            } else {
                message = "Fail:" + gotBack + " expected " + sent;
            }
        }
        // Line up the output
        String tab = "";
        int l = method.length();
        while (l < 25) {
            tab += " ";
            l++;
        }
        System.out.println( method + tab + " " + message);
    }

    private void testEchoString( String input) {
       _totalCount++;
       try { 
         String output = _proxy.echoString( input);
         verifyCall( "echoString", input, output);
       } catch (Exception ex) {
         verifyCall( "echoString", input, ex);
       }
    }

    private void testEchoStringArray( String[] input) {
       _totalCount++;
       try { 
           String[] output = _proxy.echoStringArray( input);
           verifyCall( "echoStringArray", input, output);
       } catch (Exception ex) {
           verifyCall( "echoStringArray", input, ex);
       }
    }

    private void testEchoInteger( Integer input) {
       _totalCount++;
       try { 
           Integer output = _proxy.echoInteger( input);
           verifyCall( "echoInteger", input, output);
       } catch (Exception ex) {
           verifyCall( "echoInteger", input, ex);
       }
    }

    private void testEchoIntegerArray( Integer[] input) {
       _totalCount++;
       try { 
           Integer[] output = _proxy.echoIntegerArray( input);
           verifyCall( "echoIntegerArray", input, output);
       } catch (Exception ex) {
           verifyCall( "echoIntegerArray", input, ex);
       }
    }


    private void testEchoFloat( Float input) {
       _totalCount++;
       try { 
           Float output = _proxy.echoFloat( input);
           verifyCall( "echoFloat", input, output);
       } catch (Exception ex) {
           verifyCall( "echoFloat", input, ex);
       }
    }

    private void testEchoFloatArray( Float[] input) {
       _totalCount++;
       try { 
           Float[] output = _proxy.echoFloatArray( input);
           verifyCall( "echoFloatArray", input, output);
       } catch (Exception ex) {
           verifyCall( "echoFloatArray", input, ex);
       }
    }

    private void testEchoStruct( SOAPStruct input) {
       _totalCount++;
       try { 
           SOAPStruct output = _proxy.echoStruct( input);
           verifyCall( "echoStruct", input, output);
       } catch (Exception ex) {
           verifyCall( "echoStruct", input, ex);
       }
    }

    private void testEchoStructArray( SOAPStruct[] input) {
       _totalCount++;
       try { 
           SOAPStruct[] output = _proxy.echoStructArray( input);
           verifyCall( "echoStructArray", input, output);
       } catch (Exception ex) {
           verifyCall( "echoStructArray", input, ex);
       }
    }

    private void testEchoDate( Date input) {
       _totalCount++;
       try { 
           Date output = _proxy.echoDate( input);
           verifyCall( "echoDate", input, output);
       } catch (Exception ex) {
           verifyCall( "echoDate", input, ex);
       }
    }

    private void testEchoDecimal( BigDecimal input) {
       _totalCount++;
       try { 
           BigDecimal output = _proxy.echoDecimal( input);
           verifyCall( "echoDecimal", input, output);
       } catch (Exception ex) {
           verifyCall( "echoDecimal", input, ex);
       }
    }

    private void testEchoBase64( Byte[] input) {
       _totalCount++;
       try { 
           Byte[] output = _proxy.echoBase64( input);
           verifyCall( "echoBase64", input, output);
       } catch (Exception ex) {
           verifyCall( "echoBase64", input, ex);
       }
    }

    private void testEchoBoolean( Boolean input) {
       _totalCount++;
       try { 
           Boolean output = _proxy.echoBoolean( input);
           verifyCall( "echoBoolean", input, output);
       } catch (Exception ex) {
           verifyCall( "echoBoolean", input, ex);
       }
    }

    private void executeBaseTestSuite(InteropTestProxy s, String platformName) {
        resetCounter();

        if (isVerbose()) 
            System.out.println( "Running against " + platformName + "...");

        _totalCount++;
        try {
            _proxy.echoVoid();
            verifyCall( "echoVoid", null, null);
        } catch (Exception ex) {
            verifyCall( "echoVoid", null, ex);
            System.out.println( "There is no point to continue for this endpoint if the ping behaviour fail");
            return;
        }
        testEchoString("Hello");
        testEchoString("Hello\nHello\tHello");
        testEchoString("");
        testEchoString("äöü&%&ß&<>\";\\");

        testEchoStringArray(new String[] {"abc", "def"});

        testEchoInteger(new Integer(Integer.MAX_VALUE));
        testEchoInteger(new Integer(Integer.MAX_VALUE+1));
        testEchoInteger(new Integer(Integer.MIN_VALUE));

        testEchoIntegerArray(new Integer[] {new Integer(3), new Integer(7)});

        testEchoFloat(new Float(0.0));
        testEchoFloat(new Float(Float.MAX_VALUE));
        testEchoFloat(new Float(Float.NEGATIVE_INFINITY));
        testEchoFloat(new Float(Float.POSITIVE_INFINITY));
        testEchoFloat(new Float(Float.MIN_VALUE));

        testEchoFloatArray(new Float[] {new Float(3.7F), new Float(7F)});

        testEchoStruct(new SOAPStruct("Hello", new Integer(0), new Float(3.3)));

        testEchoStructArray(
            new SOAPStruct[] {
                new SOAPStruct("one", new Integer(1), new Float(Float.MIN_VALUE)),
                new SOAPStruct("two", new Integer(2), new Float(Float.MAX_VALUE)), 
                new SOAPStruct("three", new Integer(3), new Float(3.3F))});

        testEchoDate(new Date());

        testEchoDecimal( new BigDecimal(String.valueOf(5.6)));
        //testEchoBase64("Base64".getBytes());
        testEchoBoolean(Boolean.TRUE);
        testEchoBoolean(Boolean.FALSE);

        globalTotalCount += _totalCount;
        globalFailureCount += _failureCount;

        System.out.println( platformName + " - " 
          + _failureCount + " failure for " + _totalCount + " test cases (" 
          + _proxy._getSoapURL() + ")");
    }

    private void executeRound3GroupDTestSuite(InteropTestProxy s, String platformName, int endpointID) {
        resetCounter();

        if (isVerbose()) 
            System.out.println( "Running Round III - Group D '" + endpointName[endpointID] + "' against " + platformName + "...");

        switch(endpointID) {
          case 0: // Import1
            testEchoString("Hello");
            testEchoString("Hello\nHello\tHello");
            testEchoString("");
            testEchoString("äöü&%&ß&<>\";\\");
            break;
          case 1: // Import2
            testEchoStruct(new SOAPStruct("Hello", new Integer(0), new Float(3.3)));
            break;
          case 2: // Import3
            testEchoStruct(new SOAPStruct("Hello", new Integer(0), new Float(3.3)));
            testEchoStructArray(
                new SOAPStruct[] {
                    new SOAPStruct("one", new Integer(1), new Float(Float.MIN_VALUE)),
                    new SOAPStruct("two", new Integer(2), new Float(Float.MAX_VALUE)), 
                    new SOAPStruct("three", new Integer(3), new Float(3.3F))});
            break;
          case 3: // InteropTestRpcEnc
            _totalCount++;
            try {
                _proxy.echoVoid();
                verifyCall( "echoVoid", null, null);
            } catch (Exception ex) {
                verifyCall( "echoVoid", null, ex);
                System.out.println( "There is no point to continue for this endpoint if the ping behaviour fail");
                return;
            }
            testEchoString("Hello");
            testEchoString("Hello\nHello\tHello");
            testEchoString("");
            testEchoString("äöü&%&ß&<>\";\\");
            testEchoStruct(new SOAPStruct("Hello", new Integer(0), new Float(3.3)));
            testEchoStructArray(
                new SOAPStruct[] {
                    new SOAPStruct("one", new Integer(1), new Float(Float.MIN_VALUE)),
                    new SOAPStruct("two", new Integer(2), new Float(Float.MAX_VALUE)), 
                    new SOAPStruct("three", new Integer(3), new Float(3.3F))});
            break;
        }

        globalTotalCount += _totalCount;
        globalFailureCount += _failureCount;

        System.out.println( platformName + " - " 
          + _failureCount + " failure for " + _totalCount + " test cases (" 
          + _proxy._getSoapURL() + ")");
    }
}