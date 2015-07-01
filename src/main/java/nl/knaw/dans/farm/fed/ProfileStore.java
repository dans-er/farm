package nl.knaw.dans.farm.fed;

import javax.persistence.EntityManager;

import nl.knaw.dans.farm.rdb.AbstractGenericStore;

public class ProfileStore extends AbstractGenericStore<Profile, Long>
{

    public ProfileStore()
    {
        super(Profile.class);
    }
    
    public ProfileStore(EntityManager em) {
        super(Profile.class);
        setEntityManager(em);
    }
    
    public Profile findByNaturalId(String identifier) {
        Profile profile =  (Profile) ((org.hibernate.jpa.HibernateEntityManager) getEntityManager()).getSession()
                .byNaturalId(getEntityBeanType()).using("identifier", identifier).load();
        return profile;
    }
    

}
