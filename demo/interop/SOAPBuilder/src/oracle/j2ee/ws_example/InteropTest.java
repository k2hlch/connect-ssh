package oracle.j2ee.ws_example;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.soap.encoding.Hex;

/**
 * Test implementation of the Round II base interop service. The definition 
 * can be found at http://www.whitemesa.com/interop/proposal2.html .
 * 
 * note: the test assumes that the SOAPStruct class is defined in the same
 * package as the InteropTest class.
 * 
 * @author Eric Rajkovic <eric.rajkovic@oracle.com>
 */

public class InteropTest {

    /**
     * This method accepts a single string and echoes it back to the client.
     */
    public String echoString(String input) {
        return input;    
    }

    /**
     * This method accepts an array of strings and echoes it back to the client.
     */
    public String[] echoStringArray(String[] input) {
        return input;
    }
    
    /**
     * This method accepts an single integer and echoes it back to the client.
     */
    public Integer echoInteger(Integer input) {
        return input;
    }

    /**
     * This method accepts an array of integers and echoes it back to the 
     * client.
     */
    public Integer[] echoIntegerArray(Integer[] input) {
        return input;
    }

    /**
     * This method accepts a single float and echoes it back to the client.
     */
    public Float echoFloat(Float input) {
        return input;
    }

    /**
     * This method accepts an array of floats and echoes it back to the client.
     */
    public Float[] echoFloatArray(Float[] input) {
        return input;
    }

    /**
     * This method accepts a single structure and echoes it back to the 
     * client.  
     */
    public SOAPStruct echoStruct(SOAPStruct input) {
        return input;
    }

    /**
     * This method accepts an array of structures and echoes it back to the 
     * client.  The structure used is the same defined in the description of 
     * the "echoStruct" method.
     */
    public SOAPStruct[] echoStructArray(SOAPStruct[] input) {
        return input;
    }

    /**
     * This method exists to test the "void" return case.  It accepts no 
     * arguments, and returns no arguments.
     */
    public void echoVoid() {
    }

    /**
     * This methods accepts a binary object and echoes it back to the client.
     */
    public byte[] echoBase64(byte[] input) {
        return input;
    }

    /**
     * This methods accepts a hex object and echoes it back to the client.
     */
    public Hex echoHexBinary(Hex input) {
        return input;
    }

    /**
     * This method accepts a Date/Time and echoes it back to the client.
     */
    public Date echoDate(Date input) {
        return input;
    }

    /**
     * This method accepts a BigDecimal and echoes it back to the client.
     */
    public BigDecimal echoDecimal(BigDecimal input) {
        return input;
    }

    /**
     * This method accepts a boolean and echoes it back to the client.
     */
    public boolean echoBoolean(boolean input) {
        return input;
    }

}

