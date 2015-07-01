package nl.knaw.dans.farm.fits;

import static org.junit.Assert.*;

import org.junit.Test;

public class FitsMediaTypeTest
{
    
    @Test
    public void testEquals() throws Exception {
        FitsMediatype fmt1 = new FitsMediatype("name", "version", null);
        fmt1.setIdentifier("easy-file:123");
        FitsMediatype fmt2 = new FitsMediatype("name", "version", null);
        fmt2.setIdentifier("easy-file:123");
        assertEquals(fmt1, fmt2);
        
        fmt1.setToolName("noname");
        assertNotEquals(fmt1, fmt2);
    }

}
