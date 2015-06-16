package nl.knaw.dans.farm.rdb;

import java.util.Date;

import javax.persistence.EntityTransaction;

import org.junit.Test;


public class ProfileStoreTest extends AbstractStoreTest
{
    
    @Test
    public void crudTest() throws Exception {
        
        ProfileStore store = getProjectStore();
        EntityTransaction tx = store.newTransAction();
        tx.begin();
        Profile pro = new Profile();
        pro.setIdentifier("easy-file:123");
        pro.setObjCreationDate(new Date(123));
        pro.setOblLastModified(new Date());
        pro.setObjLabel("Een Label");
        pro.setObjOwnerId("me");
        
        store.saveOrUpdate(pro);
        tx.commit();
        
        
    }

}
