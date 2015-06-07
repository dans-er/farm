package nl.knaw.dans.farm.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class MediaTypeFilterTest
{
    
    @Test
    public void testIncludes() throws Exception {
        MediaTypeFilter mtf = new MediaTypeFilter();
        
        assertFalse(mtf.isIncluded("text/plain"));
        assertFalse(mtf.isIncluded("image/jpg"));
        
        mtf = new MediaTypeFilter(MediaTypeFilter.Type.TEXT, MediaTypeFilter.Type.IMAGE);
        assertTrue(mtf.isIncluded("text/plain"));
        assertTrue(mtf.isIncluded("image/jpg"));
        assertFalse(mtf.isIncluded("application/pdf"));
        
        assertFalse(mtf.isIncluded("foo/bar"));
        assertFalse(mtf.isIncluded("text"));
        assertFalse(mtf.isIncluded(""));
        String nullmt = null;
        assertFalse(mtf.isIncluded(nullmt));
    }

}
