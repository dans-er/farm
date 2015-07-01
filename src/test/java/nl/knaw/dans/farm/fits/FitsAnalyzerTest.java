package nl.knaw.dans.farm.fits;

import static org.junit.Assert.*;

import java.io.IOException;

import nl.knaw.dans.farm.FileInformationPackage;
import nl.knaw.dans.farm.FileMetadata;

import org.junit.Test;

import de.schlichtherle.io.FileInputStream;

public class FitsAnalyzerTest extends AnalyzerTest
{
    
    @Test
    public void testProcess() throws Exception {
        FileInputStream fis = new FileInputStream("src/test/resources/test-files/DSC00323.jpg");
        FileInformationPackage fip = new FileInformationPackage("easy-file:123");
        fip.setInutStream(fis);
        
        FileMetadata fmd = new FileMetadata();
        fmd.setFilename("DSC00323.jpg");
        fip.setFileMetadata(fmd);
        
        FitsAnalyzer fana = new FitsAnalyzer();
        fana.process(fip);
        
        fip.close();
        
        boolean fisClosed = false;
        try
        {
            fis.read();
        }
        catch (IOException e)
        {
            fisClosed = true;
        }
        assertTrue(fisClosed);
    }

}
