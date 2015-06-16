package nl.knaw.dans.farm.rdb;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.criterion.Restrictions;

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
    
    @SuppressWarnings("unchecked")
    public Profile findByIdentifier(String identifier)
    {
        List<Profile> list = ((org.hibernate.jpa.HibernateEntityManager) getEntityManager()).getSession()
                .createCriteria(getEntityBeanType())
                .add(Restrictions.eq("fedora_identifier", identifier))
                .list();
        if (list.isEmpty())
        {
            throw new RuntimeException("Profile with fedora_identifier " + identifier + " does not exist");
        }
        if (list.size() > 1)
        {
            throw new RuntimeException("More than one profile with fedora_identifier " + identifier + " found");
        }
        return list.get(0);
    }

}
