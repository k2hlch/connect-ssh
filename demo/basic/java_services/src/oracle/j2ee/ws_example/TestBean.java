package oracle.j2ee.ws_example;


public class TestBean implements java.io.Serializable
{
public int m_int;
public String m_string;

public TestBean()
    {}
    

public void setIntvalue(int i)
    {
        m_int = i;
        
    }
    
public int getIntvalue()
    {
        return m_int;
        
    }

public void setStringvalue(String s)
    {
        m_string = s;
    }
    
public String getStringvalue()
    {
        return m_string;
    }


public boolean equals (Object o)
    {
        TestBean beano = (TestBean)o;

        if( m_int == beano.getIntvalue() &&
            m_string.equals(beano.getStringvalue()))
            return true;
        else
            return false;
        
    }
    
    
}
