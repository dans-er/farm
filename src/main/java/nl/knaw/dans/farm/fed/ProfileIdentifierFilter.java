package nl.knaw.dans.farm.fed;

import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.knaw.dans.farm.IdentifierFilter;
import nl.knaw.dans.farm.rdb.JPAUtil;

public class ProfileIdentifierFilter implements IdentifierFilter
{
    
    private Logger logger = LoggerFactory.getLogger(ProfileIdentifierFilter.class);
    private ProfileStore store;
    
    public ProfileIdentifierFilter()
    {
        store = new ProfileStore(JPAUtil.getEntityManager());
    }

    @Override
    public boolean accept(String identifier)
    {
        boolean accept = false;
        EntityTransaction tx = store.newTransAction();
        tx.begin();
        try
        {
            Profile profile = store.findByNaturalId(identifier);
            if (profile == null) {
                accept = true;
            } else {
                accept = false;
                logger.info("Not accepting, profile stored: " + identifier);
            }
            tx.commit();
        } catch (Exception e)   {
            tx.rollback();
            throw e;
        } finally {
            store.clear();
        }
        return accept;
    }

}
