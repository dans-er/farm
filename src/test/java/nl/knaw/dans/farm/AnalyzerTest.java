package nl.knaw.dans.farm;

import nl.knaw.dans.fits.FitsWrap;

import org.junit.BeforeClass;
import org.junit.Test;

public class AnalyzerTest
{
    @BeforeClass
    public static void beforeClass() {
        FitsWrap.setFitsHome("/Users/ecco/git/fits-api/fits-0.8.5");
    }
    
    @Test
    public void testMultipleInputstream() throws Exception {
        
    }

}
