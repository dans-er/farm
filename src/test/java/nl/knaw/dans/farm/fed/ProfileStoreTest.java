package nl.knaw.dans.farm.fed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import javax.persistence.EntityTransaction;

import nl.knaw.dans.farm.rdb.JPAUtil;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ProfileStoreTest
{
    @BeforeClass
    public static void before() throws Exception {
        JPAUtil.setTestState(true);
    }
    
    @Test
    public void crudTest() throws Exception {
        
        ProfileStore store = new ProfileStore(JPAUtil.getEntityManager());
        EntityTransaction tx = store.newTransAction();
        tx.begin();
        Profile profile = store.findByNaturalId("easy-file:123");
        if (profile != null) {
            store.remove(profile);
        }
        tx.commit();
        
        tx = store.newTransAction();
        tx.begin();
        profile = store.findByNaturalId("easy-file:123");
        assertNull(profile);
        tx.commit();
        
        tx = store.newTransAction();
        tx.begin();
        Profile pro = new Profile();
        pro.setIdentifier("easy-file:123");
        pro.setObjCreationDate(new Date(123));
        pro.setOblLastModified(new Date());
        pro.setObjLabel("Een Label");
        pro.setObjOwnerId("me");
        store.saveOrUpdate(pro);
        tx.commit();   
        
        tx = store.newTransAction();
        tx.begin();
        profile = store.findByNaturalId("easy-file:123");
        assertNotNull(profile);
        assertEquals("easy-file:123", profile.getIdentifier());
        assertEquals("Een Label", profile.getObjLabel());
        tx.commit();
        
    }
    


}
