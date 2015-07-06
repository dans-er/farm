package nl.knaw.dans.farm.fits;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityTransaction;

import nl.knaw.dans.farm.Analyzer;
import nl.knaw.dans.farm.FileInformationPackage;
import nl.knaw.dans.farm.ProcessingException;
import nl.knaw.dans.farm.rdb.JPAUtil;
import nl.knaw.dans.fits.FitsWrap;
import edu.harvard.hul.ois.fits.FitsMetadataElement;
import edu.harvard.hul.ois.fits.FitsOutput;
import edu.harvard.hul.ois.fits.exceptions.FitsConfigurationException;
import edu.harvard.hul.ois.fits.exceptions.FitsException;
import edu.harvard.hul.ois.fits.identity.ExternalIdentifier;
import edu.harvard.hul.ois.fits.identity.FitsIdentity;
import edu.harvard.hul.ois.fits.tools.ToolInfo;

public class FitsAnalyzer implements Analyzer
{
    
    private FitsProfileStore store;
    
    public FitsAnalyzer() throws FitsConfigurationException
    {
        store = new FitsProfileStore(JPAUtil.getEntityManager());
        FitsWrap.instance().setStatisticsEnabled(false);
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
//        try
//        {
//            fop.output(System.err);
//        }
//        catch (IOException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

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
        updateProfile(fip, fop, profile);
        store.saveOrUpdate(profile);
        tx.commit();
    }

    private void updateProfile(FileInformationPackage fip, FitsOutput fop, FitsProfile profile)
    {
        updateIdentification(fip, fop, profile);
        updateFileInfo(fip, fop, profile);
        updateFileStatus(fip, fop, profile);
        updateTechMetadata(fip, fop, profile);
    }
    
    private void updateIdentification(FileInformationPackage fip, FitsOutput fop, FitsProfile profile)
    {
        // add mediaTypes
        profile.getMediatypes().clear();
        List<FitsIdentity> identities = fop.getIdentities();
        if (identities.size() != 1) {
            for (FitsIdentity identity : identities) {
                String mediatype = identity.getMimetype();
                String format = identity.getFormat();
                for (ToolInfo tool : identity.getReportingTools()) {
                    FitsMediatype fmt = profile.addMediatype(FitsProfile.ELEMENT_NAME_MEDIA_TYPE, 
                            tool.getName(), tool.getVersion(), mediatype, format);
                    fmt.setStatus("CONFLICT");
                }
                updateExternalIdentifiers(identity, profile);
            }
        } else {
            
            FitsIdentity identity = identities.get(0);
            FitsMediatype fmt = profile.addMediatype(FitsProfile.ELEMENT_NAME_MEDIA_TYPE, 
                    identity.getToolName(), identity.getToolVersion(), identity.getMimetype(), identity.getFormat());
            fmt.setStatus("SINGLE_RESULT");
            updateExternalIdentifiers(identity, profile);
        }
    }
    
    private void updateExternalIdentifiers(FitsIdentity identity, FitsProfile profile) {
        for (ExternalIdentifier exid : identity.getExternalIdentifiers()) {
            ToolInfo ti = exid.getToolInfo();
            profile.addMediatype(FitsProfile.ELEMENT_NAME_EXTERNAL_ID, ti.getName(), ti.getVersion(), exid.getValue(), null);
        }
    }
    
    private void updateFileInfo(FileInformationPackage fip, FitsOutput fop, FitsProfile profile)
    {
        profile.getFileInformation().clear();
        for (FitsMetadataElement el : fop.getFileInfoElements()) {
            if (!FitsFileInfo.excludeElement(el.getName())) {
                FitsFileInfo ffi = new FitsFileInfo();
                ffi.setElementName(el.getName());
                ffi.setToolName(el.getReportingToolName());
                ffi.setToolVersion(el.getReportingToolVersion());
                ffi.setStatus(el.getStatus());
                ffi.setValue(el.getValue());
                profile.addFileInfo(ffi);
            }
        }
    }

    private void updateFileStatus(FileInformationPackage fip, FitsOutput fop, FitsProfile profile)
    {
        profile.getFileStatus().clear();
        for (FitsMetadataElement el : fop.getFileStatusElements()) {
            FitsFileStatus ffs = new FitsFileStatus();
            ffs.setElementName(el.getName());
            ffs.setToolName(el.getReportingToolName());
            ffs.setToolVersion(el.getReportingToolVersion());
            ffs.setStatus(el.getStatus());
            ffs.setValue(el.getValue());
            profile.addFileStat(ffs);
        }   
    }
    
    private void updateTechMetadata(FileInformationPackage fip, FitsOutput fop, FitsProfile profile)
    {
        profile.setType(fop.getTechMetadataType());
        profile.getTechMetadata().clear();
        for (FitsMetadataElement el : fop.getTechMetadataElements()) {
            FitsTechMetadata ftmd = new FitsTechMetadata();
            ftmd.setElementName(el.getName());
            ftmd.setToolName(el.getReportingToolName());
            ftmd.setToolVersion(el.getReportingToolVersion());
            ftmd.setStatus(el.getStatus());
            ftmd.setValue(el.getValue());
            profile.addTechMd(ftmd);
        }
    }

}
