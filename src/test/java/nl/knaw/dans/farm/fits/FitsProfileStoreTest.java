package nl.knaw.dans.farm.fits;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityTransaction;

import nl.knaw.dans.farm.rdb.JPAUtil;

import org.junit.BeforeClass;
import org.junit.Test;

public class FitsProfileStoreTest
{
    
    private static final String EF_ID = "easy-file:123";
    
    @BeforeClass
    public static void before() throws Exception {
        JPAUtil.setTestState(true);
    }

    @Test
    public void crud() throws Exception {
        FitsProfileStore store = new FitsProfileStore(JPAUtil.getEntityManager());
        
        EntityTransaction tx = store.newTransAction();
        tx.begin();
        FitsProfile fp = store.findByNaturalId(EF_ID);
        if (fp != null) {
            store.remove(fp);
        }
        tx.commit();
        
        tx = store.newTransAction();
        tx.begin();
        fp = store.findByNaturalId(EF_ID);
        assertNull(fp);
        tx.commit();
        
        tx = store.newTransAction();
        tx.begin();
        fp = new FitsProfile();
        fp.setIdentifier(EF_ID);
        fp.addMediatype(new FitsMediatype(FitsProfile.ELEMENT_NAME_MEDIA_TYPE, "test-tool", "1.0", "text/test", "bla"));
        fp.addMediatype(new FitsMediatype(FitsProfile.ELEMENT_NAME_MEDIA_TYPE, "tester", "1.0", "test/fun", "format"));
        store.saveOrUpdate(fp);
        tx.commit();
        
        tx = store.newTransAction();
        tx.begin();
        fp = store.findByNaturalId(EF_ID);
        assertNotNull(fp);
        assertEquals(2, fp.getMediatypes().size());
        fp.addMediatype(new FitsMediatype(FitsProfile.ELEMENT_NAME_MEDIA_TYPE, "test-tool", "1.1", "text/test", "format bla"));
        store.saveOrUpdate(fp);
        tx.commit();
        
        tx = store.newTransAction();
        tx.begin();
        fp = store.findByNaturalId(EF_ID);
        assertNotNull(fp);
        assertEquals(3, fp.getMediatypes().size());
        
        tx.commit();
    }

}
