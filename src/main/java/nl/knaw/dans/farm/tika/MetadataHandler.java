package nl.knaw.dans.farm.tika;

import javax.persistence.EntityTransaction;

import nl.knaw.dans.farm.FileInformationPackage;
import nl.knaw.dans.farm.rdb.JPAUtil;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class MetadataHandler extends FarmHandler
{
    
    // the field length in the database
    public static final int MAX_CONTENT_LENGTH = 255;
    
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
        if (isMeta(uri, localName)) {
            String content = StringUtils.abbreviate(atts.getValue("content"), MAX_CONTENT_LENGTH);
            currentProfile.addMeta(new TikaMeta(atts.getValue("name"), content));
        }
    }
    
    @Override
    public void endDocument() throws SAXException
    {
        store.saveOrUpdate(currentProfile);
        currentTx.commit();
        currentProfile = null;
        currentTx = null;
    }

}
