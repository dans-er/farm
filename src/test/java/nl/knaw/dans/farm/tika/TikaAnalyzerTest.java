package nl.knaw.dans.farm.tika;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import nl.knaw.dans.farm.FileInformationPackage;
import nl.knaw.dans.farm.FileMetadata;
import nl.knaw.dans.farm.rdb.JPAUtil;

import org.junit.BeforeClass;
import org.junit.Test;

import de.schlichtherle.io.FileInputStream;

public class TikaAnalyzerTest
{
    
    @BeforeClass
    public static void beforeClass() {
        JPAUtil.setTestState(true);
    }
    
    @Test
    public void testProcess() throws Exception {
        //String filename = "farm.svg";
        //String filename = "01_HerkenningInhoudEASY.docx";
        String filename = "DSC00323.jpg";
        //String filename = "05 I Heard Her Call My Name.mp3";

        FileInputStream fis = new FileInputStream("src/test/resources/test-files/" + filename);
        
        FileInformationPackage fip = new FileInformationPackage("easy-file:125");
        fip.setInutStream(fis);
        
        FileMetadata fmd = new FileMetadata();
        fmd.setFilename(filename);
        fip.setFileMetadata(fmd);
        
        TikaAnalyzer tana = new TikaAnalyzer();
        tana.addHandler(new MetadataHandler());
        tana.process(fip);
        
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
