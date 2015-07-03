package nl.knaw.dans.farm.fits;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import nl.knaw.dans.farm.FileInformationPackage;
import nl.knaw.dans.farm.FileMetadata;
import nl.knaw.dans.farm.rdb.JPAUtil;
import nl.knaw.dans.fits.FitsWrap;

import org.junit.BeforeClass;
import org.junit.Test;

import de.schlichtherle.io.FileInputStream;

public class FitsAnalyzerTest
{
    
    @BeforeClass
    public static void beforeClass() {
        FitsWrap.setFitsHome("/Users/ecco/git/fits-api/fits-0.8.5");
        JPAUtil.setTestState(true);
    }
    
    @Test
    public void testProcess() throws Exception {
        //FileInputStream fis = new FileInputStream("src/test/resources/test-files/farm.doc");
        //FileInputStream fis = new FileInputStream("src/test/resources/test-files/DSC00323.jpg");
        FileInputStream fis = new FileInputStream("src/test/resources/test-files/05 I Heard Her Call My Name.mp3");
        
        FileInformationPackage fip = new FileInformationPackage("easy-file:125");
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
