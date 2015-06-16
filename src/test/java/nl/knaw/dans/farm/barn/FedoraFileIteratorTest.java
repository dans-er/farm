package nl.knaw.dans.farm.barn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import nl.knaw.dans.farm.FileInformationPackage;
import nl.knaw.dans.farm.FileMetadata;
import nl.knaw.dans.farm.FileProfile;
import nl.knaw.dans.farm.IdentifierFilter;

import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class FedoraFileIteratorTest extends AbstractFedoraTest
{
    
    private static final String TEST_ID = "easy-file:7382";
    
    
    @Test
    public void testGetFileMetadata() throws Exception {        
        FedoraFileIterator iter = new FedoraFileIterator();
        FileMetadata fmd = iter.getFileMetadata(TEST_ID);
        assertEquals("easy-file:7382", fmd.getIdentifier());
        assertEquals("KNOWN", fmd.getAccessibleTo());
        assertEquals("DEPOSITOR", fmd.getCreatorRole());
        assertEquals("easy-dataset:43", fmd.getDatasetId());
        assertEquals("p1001.pdf", fmd.getFilename());
        assertEquals("application/pdf", fmd.getMediaType());
        assertEquals("easy-folder:635", fmd.getParentId());
        assertEquals("original/dat/p1001.pdf", fmd.getPath());
        assertEquals(1561372L, fmd.getSize());
        assertEquals("ANONYMOUS", fmd.getVisibleTo());
        
    }
    
    @Test
    public void testGetFileProfile() throws Exception {
        FedoraFileIterator iter = new FedoraFileIterator();
        FileProfile fp = iter.getFileProfile(TEST_ID);
        assertEquals("easy-file:7382", fp.getIdentifier());
        assertEquals(new DateTime("2011-03-30T09:19:44.217Z"), fp.getCreationDate());
        // unclear what lastModified date means for datastreams. same as creation date?
        assertEquals(new DateTime("2011-03-30T09:19:44.217Z"), fp.getLastModified());
        assertEquals("DISABLED", fp.getChecksumType());
        assertEquals("M", fp.getControlGroup());
        assertEquals("", fp.getFormatURI());
        assertEquals("p1001.pdf", fp.getLabel());
        assertEquals("application/pdf", fp.getMediaType());
        // size is not set on datastream metadata
        assertEquals(-1, fp.getSize());
        assertEquals("A", fp.getState());
        assertFalse(fp.isVersionable());
        assertEquals("EASY_FILE.0", fp.getVersionId());
    }
    
    @Test
    public void testGetFileInformationPackage() throws Exception {
        FedoraFileIterator iter = new FedoraFileIterator();
        FileInformationPackage fip = iter.getFileInformationPackage(TEST_ID);
        
        FileMetadata fmd = fip.getFileMetadata();
        assertEquals(TEST_ID, fmd.getIdentifier());
        
        FileProfile fp = fip.getFileProfile();
        assertEquals(TEST_ID, fp.getIdentifier());
        
        assertTrue(fip.getInputStream().markSupported());
        File file = fip.getFile();
        assertNotNull(file);
        assertTrue(file.exists());
        assertEquals("p1001.pdf", file.getName());
        assertEquals(FileInformationPackage.FARM_FILES, file.getParentFile().getName());
        
        fip.close();
        assertFalse(file.exists());
        
    }
    
    @Test
    public void testNext() {
        int maxCount = 30;
        int count = 0;
        FedoraFileIterator iter = new FedoraFileIterator();
        iter.setIdentifierFilter(new IdentifierFilter()
        {
            
            
            @Override
            public boolean accept(String identifier)
            {
                if (identifier.startsWith("easy-file:2")) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        while (iter.hasNext() && count < maxCount) {
            FileInformationPackage fip = iter.next();
            System.err.println(fip.getIdentifier());
            fip.close();
            count += 1;
        }
        
    }
    

}
