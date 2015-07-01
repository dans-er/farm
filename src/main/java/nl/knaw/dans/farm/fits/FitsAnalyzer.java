package nl.knaw.dans.farm.fits;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityTransaction;

import nl.knaw.dans.farm.Analyzer;
import nl.knaw.dans.farm.FileInformationPackage;
import nl.knaw.dans.farm.ProcessingException;
import nl.knaw.dans.farm.rdb.JPAUtil;
import nl.knaw.dans.fits.FitsWrap;
import edu.harvard.hul.ois.fits.FitsOutput;
import edu.harvard.hul.ois.fits.exceptions.FitsException;
import edu.harvard.hul.ois.fits.identity.FitsIdentity;
import edu.harvard.hul.ois.fits.tools.ToolInfo;

public class FitsAnalyzer implements Analyzer
{
    
    private FitsProfileStore store;
    
    public FitsAnalyzer()
    {
        store = new FitsProfileStore(JPAUtil.getEntityManager());
    }

    @Override
    public void process(FileInformationPackage fip) throws ProcessingException
    {
        FitsOutput fop;
        try
        {
            fop = FitsWrap.instance().process(fip.getFile());
        }
        catch (FitsException | IOException e)
        {
            throw new ProcessingException(e);
        }
        
        persistProfile(fip, fop);
        
        for (FitsIdentity fid : fop.getIdentities()) {
            String mediaType = fid.getMimetype();
            List<ToolInfo> rtools = fid.getReportingTools();
            for (ToolInfo ti : rtools) {
                System.err.println(ti.getDate());
                System.err.println(ti.getName());
                System.err.println(ti.getNote());
                System.err.println(ti.getVersion());
                System.err.println();
            }
            System.err.println(mediaType);
        }
        try
        {
            fop.output(System.err);
        }
        catch (IOException e)
        {
            throw new ProcessingException(e);
        }

    }

    private void persistProfile(FileInformationPackage fip, FitsOutput fop)
    {
        EntityTransaction tx = store.newTransAction();
        tx.begin();
        FitsProfile profile = store.findByNaturalId(fip.getIdentifier());
        if (profile == null) {
            profile = new FitsProfile(fip);
        } else {
            profile.update(fip);
        }
        profile.update(fop);
        
        store.saveOrUpdate(profile);
        tx.commit();
    }
}
