import org.w3c.dom.*;
import oracle.xml.parser.v2.*;
import java.io.*;

public class StatelessDocImpl implements StatelessDoc
{

  public StatelessDocImpl()
  {

  }

  // Display the Element that was sent
  public void displayElement(Element e)
  {
    System.out.println("StatelessDocImpl::displaying the element ");
    try{ 
      ((XMLElement)e).print(System.out);
    }catch(IOException ioe){
        throw new RuntimeException(ioe.getMessage());
    }
  }

 
 //method to process the input xml doc
  public Element processElement(Element e) 
  {
    Element processedEl;
     try{
         processedEl  =applyXSLtoXML(e);         
     }catch(Exception ex){
        throw new RuntimeException(ex.getMessage());       
     }
      return processedEl;
  }

 /**
 * This Method Transforms an XML Document into another using the provided
 * Style Sheet: converter.xsl.  Note : This Method makes use of XSL Transformation
 * capabilities of Oracle XML Parser Version 2.0
 **/
 private Element applyXSLtoXML(Element e)
  throws Exception
  {     

    // Instantiate a new XSL Processor.
    XSLProcessor l_processor = new XSLProcessor();

    // Instantiate a stylesheet
    XSLStylesheet l_xsl = l_processor.newXSLStylesheet(this.getClass().getClassLoader().getResourceAsStream("converter.xsl"));
      
    // Enable any warnings that may occur
    l_processor.showWarnings(true);

    // Set Error Stream
    l_processor.setErrorStream(System.err);
    
    // Process XSL
    // Note that parameters passed are XSLStyleSheet and input XMLElement
    DocumentFragment l_result = l_processor.processXSL(l_xsl,(XMLElement)e);

    return (Element)l_result.getFirstChild();
 }
}

