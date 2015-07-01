package nl.knaw.dans.farm.fits;

import static org.junit.Assert.*;

import javax.persistence.EntityTransaction;

import nl.knaw.dans.farm.rdb.AbstractStoreTest;
import nl.knaw.dans.farm.rdb.JPAUtil;

import org.junit.Test;

public class FitsProfileStoreTest extends AbstractStoreTest
{
    
    private static final String EF_ID = "easy-file:123";

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
        fp.addMediatype(new FitsMediatype("test-tool", "1.0", "text/test"));
        fp.addMediatype(new FitsMediatype("tester", "1.0", "test/fun"));
        store.saveOrUpdate(fp);
        tx.commit();
        
        tx = store.newTransAction();
        tx.begin();
        fp = store.findByNaturalId(EF_ID);
        assertNotNull(fp);
        assertEquals(2, fp.getMediatypes().size());
        fp.addMediatype(new FitsMediatype("test-tool", "1.1", "text/test"));
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
