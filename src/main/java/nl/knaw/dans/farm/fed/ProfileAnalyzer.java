package nl.knaw.dans.farm.fed;

import javax.persistence.EntityTransaction;

import nl.knaw.dans.farm.Analyzer;
import nl.knaw.dans.farm.FileInformationPackage;
import nl.knaw.dans.farm.ProcessingException;
import nl.knaw.dans.farm.rdb.JPAUtil;

public class ProfileAnalyzer implements Analyzer
{
    
    private ProfileStore store;
    
    public ProfileAnalyzer()
    {
        store = new ProfileStore(JPAUtil.getEntityManager());
    }

    @Override
    public void process(FileInformationPackage fip) throws ProcessingException
    {
        EntityTransaction tx = store.newTransAction();
        tx.begin();
        Profile profile = store.findByNaturalId(fip.getIdentifier());
        if (profile == null) {
            profile = new Profile(fip);
        } else {
            profile.update(fip);
        }
        store.saveOrUpdate(profile);
        tx.commit();
    }
    

}
