package nl.knaw.dans.farm.fits;

import javax.persistence.EntityManager;

import nl.knaw.dans.farm.rdb.AbstractGenericStore;

public class FitsProfileStore extends AbstractGenericStore<FitsProfile, Long>
{

    public FitsProfileStore()
    {
        super(FitsProfile.class);
    }
    
    public FitsProfileStore(EntityManager em) {
        super(FitsProfile.class);
        setEntityManager(em);
    }
    
    public FitsProfile findByNaturalId(String identifier) {
        FitsProfile fprofile =  (FitsProfile) ((org.hibernate.jpa.HibernateEntityManager) getEntityManager()).getSession()
                .byNaturalId(getEntityBeanType()).using("identifier", identifier).load();
        return fprofile;
    }

}
