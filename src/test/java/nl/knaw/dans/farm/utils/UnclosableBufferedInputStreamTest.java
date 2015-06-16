package nl.knaw.dans.farm.utils;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class UnclosableBufferedInputStreamTest
{
    
    @Test
    public void testOpenInputStreams() throws Exception {
        InputStream ins = FileUtils.openInputStream(new File("non-pub/test.properties"));
        InputStream bins = new UnclosableBufferedInputStream(ins);
        
        Properties p1 = new Properties();
        Properties p2 = new Properties();
        p1.load(bins);
        bins.reset();
        p2.load(bins);
        assertEquals(p1.getProperty("fedora.host"), p2.getProperty("fedora.host"));
        ins.close();
    }

}
