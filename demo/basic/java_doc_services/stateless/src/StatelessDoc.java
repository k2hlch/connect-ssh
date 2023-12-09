import  org.w3c.dom.*;

interface StatelessDoc 
{

     //method to display the element 
     public void displayElement(Element e) ;
 
     //method to process the input xml doc
     public Element processElement(Element e) ;
 
}
