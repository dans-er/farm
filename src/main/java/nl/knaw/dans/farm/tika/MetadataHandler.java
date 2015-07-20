package nl.knaw.dans.farm.tika;

import javax.persistence.EntityTransaction;

import nl.knaw.dans.farm.FileInformationPackage;
import nl.knaw.dans.farm.rdb.JPAUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class MetadataHandler extends FarmHandler
{
    
    // the field length in the database
    public static final int MAX_CONTENT_LENGTH = 255;
    
    private static Logger logger = LoggerFactory.getLogger(MetadataHandler.class);
    
    private TikaProfileStore store;
    private EntityTransaction currentTx;
    private TikaProfile currentProfile;
    
    private static boolean isMeta(String uri, String localName) {
        return "http://www.w3.org/1999/xhtml".equals(uri)
                && "meta".equals(localName);
    }
    
    public MetadataHandler() {
        store = new TikaProfileStore(JPAUtil.getEntityManager());
    }
    
    @Override
    public void setFileInformationPackage(FileInformationPackage fip)
    {
        if (currentTx != null) {
           logger.warn("===============> Previous transaction not closed.");
           if (currentTx.isActive()) {
               logger.warn("=================> Rolling back previous transaction.");
               currentTx.rollback();
           }
           store.clear();
        }
        currentTx = store.newTransAction();
        currentTx.begin();
        currentProfile = store.findByNaturalId(fip.getIdentifier());
        if (currentProfile == null) {
            currentProfile = new TikaProfile(fip);
        } else {
            currentProfile.update(fip);
            currentProfile.getMetadata().clear();
        }
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException
    {
        try
        {
            if (isMeta(uri, localName)) {
                String name = atts.getValue("name");
                String content = StringUtils.abbreviate(atts.getValue("content"), MAX_CONTENT_LENGTH);
                currentProfile.addMeta(new TikaMeta(name, content));
                if ("Content-Type".equals(name)) {
                    currentProfile.setContentType(content);
                }
            }
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    
    @Override
    public void endDocument() throws SAXException
    {
        try
        {
            store.saveOrUpdate(currentProfile);
            currentTx.commit();
        } catch (Exception e) {
            currentTx.rollback();
            throw e;
        }
        finally
        {
            store.clear();
            currentProfile = null;
            currentTx = null;
        }
        
    }

}
