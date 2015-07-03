package nl.knaw.dans.farm.fits;

import java.io.File;
import java.util.List;

import nl.knaw.dans.fits.FitsWrap;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.harvard.hul.ois.fits.FitsMetadataElement;
import edu.harvard.hul.ois.fits.FitsOutput;

public class FitsTest
{
    
    @BeforeClass
    public static void beforeClass() {
        FitsWrap.setFitsHome("/Users/ecco/git/fits-api/fits-0.8.5");
    }
    
    @Test
    public void testConflict() throws Exception {
        //File file = new File("src/test/resources/test-files/DSC00323.jpg");
        //File file = new File("src/test/resources/test-files/farm.doc");
        File file = new File("src/test/resources/test-files/05 I Heard Her Call My Name.mp3");
        //File file = new File("src/test/resources/test-files/01_HerkenningInhoudEASY.docx");
        FitsOutput fop = FitsWrap.instance().process(file);
        //fop.output(System.err);
        
//        List<FitsIdentity> identities = fop.getIdentities();
//        FitsIdentity identity = identities.get(0);
//        
//        List<ExternalIdentifier> exids = identity.getExternalIdentifiers();
//        System.err.println(exids.size());
//        for (ExternalIdentifier exid : exids) {
//            System.err.println(exid.getToolInfo().getName());
//            System.err.println(exid.getToolInfo().getVersion());
//            System.err.println(exid.getValue());
//        }
        
        //List<FitsMetadataElement> elements = fop.getFileInfoElements();
        
        List<FitsMetadataElement> elements = fop.getTechMetadataElements();

        
        for (FitsMetadataElement element : elements) {
            System.err.println(element.getName() + " " + element.getReportingToolName() + " " + element.getStatus()
                    + " " + element.getValue());
        }
        
        System.err.println(fop.getTechMetadataType());
        System.err.println(fop.getErrorMessages());
        List<Exception> exs = fop.getCaughtExceptions();
        for (Exception ex : exs) {
            System.err.println(ex.toString());
        }
    }
    

}
