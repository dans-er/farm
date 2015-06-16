package nl.knaw.dans.farm.rdb;

import javax.persistence.EntityTransaction;

import nl.knaw.dans.farm.Analyzer;
import nl.knaw.dans.farm.FileInformationPackage;
import nl.knaw.dans.farm.ProcessingException;

public class ProfileAnalyzer implements Analyzer
{
    
    private ProfileStore profileStore;

    @Override
    public void process(FileInformationPackage fip) throws ProcessingException
    {
        EntityTransaction tx = getProfileStore().newTransAction();
        tx.begin();
        Profile profile = new Profile(fip);
        profileStore.saveOrUpdate(profile);
        tx.commit();
    }
    
    private ProfileStore getProfileStore() {
        if (profileStore == null) {
            profileStore = new ProfileStore(JPAUtil.getEntityManager());
        }
        return profileStore;
    }

}
