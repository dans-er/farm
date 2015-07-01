package nl.knaw.dans.farm.rdb;

import org.junit.BeforeClass;

public abstract class AbstractStoreTest
{
    
    //private final EntityManager em;
    
    
    @BeforeClass
    public static void beforeClass()
    {
        JPAUtil.setTestState(true);
    }
    
//    public AbstractStoreTest()
//    {
//        em = JPAUtil.getEntityManager();
//    }
//    
//    public EntityManager getEm()
//    {
//        return em;
//    }
    

}
