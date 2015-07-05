package nl.knaw.dans.farm.tika;

import javax.persistence.EntityManager;

import nl.knaw.dans.farm.rdb.AbstractGenericStore;

public class TikaProfileStore extends AbstractGenericStore<TikaProfile, Long>
{

    public TikaProfileStore()
    {
        super(TikaProfile.class);
    }
    
    public TikaProfileStore(EntityManager em) {
        super(TikaProfile.class);
        setEntityManager(em);
    }
    
    public TikaProfile findByNaturalId(String identifier) {
        TikaProfile tprofile =  (TikaProfile) ((org.hibernate.jpa.HibernateEntityManager) getEntityManager()).getSession()
                .byNaturalId(getEntityBeanType()).using("identifier", identifier).load();
        return tprofile;
    }

}
