package nl.knaw.dans.farm.rdb;

import javax.persistence.EntityManager;

import org.junit.BeforeClass;

public abstract class AbstractStoreTest
{
    
    private final EntityManager em;
    
    private ProfileStore profileStore;
    
    @BeforeClass
    public static void beforeClass()
    {
        JPAUtil.setTestState(true);
    }
    
    public AbstractStoreTest()
    {
        em = JPAUtil.getEntityManager();
        profileStore = new ProfileStore(em);
    }
    
    public EntityManager getEm()
    {
        return em;
    }
    
    public ProfileStore getProjectStore()
    {
        return profileStore;
    }

}
