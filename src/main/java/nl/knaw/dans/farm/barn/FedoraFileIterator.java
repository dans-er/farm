package nl.knaw.dans.farm.barn;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.help.UnsupportedOperationException;

import nl.knaw.dans.farm.FarmException;
import nl.knaw.dans.farm.FileInformationPackage;
import nl.knaw.dans.farm.FileIterator;
import nl.knaw.dans.farm.FileMetadata;
import nl.knaw.dans.farm.FileProfile;
import nl.knaw.dans.farm.IdentifierFilter;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yourmediashelf.fedora.client.FedoraClientException;
import com.yourmediashelf.fedora.client.request.FindObjects;
import com.yourmediashelf.fedora.client.request.GetDatastream;
import com.yourmediashelf.fedora.client.request.GetDatastreamDissemination;
import com.yourmediashelf.fedora.client.response.FedoraResponse;
import com.yourmediashelf.fedora.client.response.FindObjectsResponse;
import com.yourmediashelf.fedora.client.response.GetDatastreamResponse;

public class FedoraFileIterator implements FileIterator
{
    
    public static final int MAX_RESULTS = 25;
    
    private static Logger logger = LoggerFactory.getLogger(FedoraFileIterator.class);
    
    private List<String> pids;
    private Iterator<String> pidIter;
    private String token = "start";
    private IdentifierFilter identifierFilter;
    private String nextIdentifier = "start";
    
    public FedoraFileIterator() {
        
    }
    

    public IdentifierFilter getIdentifierFilter()
    {
        if (identifierFilter == null) {
            identifierFilter = new IdentifierFilter()
            {
                
                @Override
                public boolean accept(String identifier)
                {
                    return true;
                }
            };
        }
        return identifierFilter;
    }

    public void setIdentifierFilter(IdentifierFilter identifierFilter)
    {
        this.identifierFilter = identifierFilter;
    }

    @Override
    public boolean hasNext()
    {
        if ("start".equals(nextIdentifier)) {
            findNextIdentifier();
        }
        return nextIdentifier != null;
    }

    @Override
    public FileInformationPackage next()
    {
        if ("start".equals(nextIdentifier)) {
            findNextIdentifier();
        }
        if (nextIdentifier == null){
            throw new NoSuchElementException();
        }
        try
        {
            FileInformationPackage fip = getFileInformationPackage(nextIdentifier);
            findNextIdentifier();
            return fip;
        }
        catch (FedoraClientException e)
        {
            throw new RuntimeException(e);
        }
        catch (JDOMException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        catch (FarmException e)
        {
            throw new RuntimeException(e);
        }
    }

    protected String findNextIdentifier()
    {
        
        boolean found = false;
        try
        {
            while (hasNextPid() && !found) {
                nextIdentifier = nextPid();
                found = getIdentifierFilter().accept(nextIdentifier);
            }
        }
        catch (FedoraClientException e)
        {
            throw new RuntimeException(e);
        }
        if (!found) {
            nextIdentifier = null;
            pids = null;
        }
        return nextIdentifier;
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException();
    }
    
    protected boolean hasNextPid() throws FedoraClientException {
        if (pids == null) {
            startFindFiles();
        }
        if (!pidIter.hasNext() && token != null) {
            continueFindFiles();
        }
        return pidIter.hasNext();
    }
    
    protected String nextPid() throws FedoraClientException {
        if (pids == null) {
            startFindFiles();
        }
        if (!pidIter.hasNext() && token != null) {
            continueFindFiles();
        }
        return pidIter.next();
    }
    
    protected void startFindFiles() throws FedoraClientException {
        FindObjectsResponse response = new FindObjects() //
            .query(getQuery()) //
            .pid() //
            .maxResults(MAX_RESULTS) //
            .execute();
        pids = response.getPids();
        token = response.getToken();
        pidIter = pids.iterator();
        logger.debug("Start find files found {} identifiers.", pids.size());
    }


    protected String getQuery()
    {
        return "pid%7Eeasy-file:*";
    }
    
    protected void continueFindFiles() throws FedoraClientException {
        FindObjectsResponse response = new FindObjects() //
            .sessionToken(token) //
            .pid() //
            .maxResults(MAX_RESULTS) //
            .execute();
        pids = response.getPids();
        token = response.getToken();
        pidIter = pids.iterator();
        logger.debug("Continue find files found {} identifiers.", pids.size());
    }
    
    protected FileInformationPackage getFileInformationPackage(String identifier) throws FedoraClientException, JDOMException, IOException, FarmException {
        FileInformationPackage fip = new FileInformationPackage(identifier);
        fip.setFileMetadata(getFileMetadata(identifier));
        fip.setFileProfile(getFileProfile(identifier));
        
        FedoraResponse response = new GetDatastreamDissemination(identifier, FileInformationPackage.DS_ID_EASY_FILE).execute();
        fip.setInutStream(response.getEntityInputStream());
        
        logger.debug("Retrieved file information package for {}", identifier);
        return fip;
    }
    
    protected FileProfile getFileProfile(String identifier) throws FedoraClientException, FarmException {
        GetDatastreamResponse response = new GetDatastream(identifier, FileInformationPackage.DS_ID_EASY_FILE).execute();
        FileProfile fp = new FileProfile(response.getDatastreamProfile());
        fp.setLastModified(response.getLastModifiedDate());
        if (!identifier.equals(fp.getIdentifier())) {
            throw new FarmException("Unequal identifiers: objId=" + identifier + ", " 
                    + FileInformationPackage.DS_ID_EASY_FILE + "=" + fp.getIdentifier());
        }
        return fp;
    }
    
    protected FileMetadata getFileMetadata(String identifier) throws FedoraClientException, JDOMException, IOException, FarmException {
        FileMetadata fmd = new FileMetadata();
        FedoraResponse response = new GetDatastreamDissemination(identifier, FileMetadata.DS_ID_EASY_FILE_METADATA).execute();
        Document doc = getDoc(response.getEntityInputStream());
        Element root = doc.getRootElement();
        fmd.setIdentifier(root.getChildText("sid"))
            .setFilename(root.getChildText("name"))
            .setParentId(root.getChildText("parentSid"))
            .setDatasetId(root.getChildText("datasetSid"))
            .setPath(root.getChildText("path"))
            .setMediaType(root.getChildText("mimeType"))
            .setSize(root.getChildText("size"))
            .setCreatorRole(root.getChildText("creatorRole"))
            .setVisibleTo(root.getChildText("visibleTo"))
            .setAccessibleTo(root.getChildText("accessibleTo"));
        if (!identifier.equals(fmd.getIdentifier())) {
            throw new FarmException("Unequal identifiers: objId=" + identifier + ", " 
                    + FileMetadata.DS_ID_EASY_FILE_METADATA + "=" + fmd.getIdentifier());
        }
        return fmd;
    }
    
    protected Document getDoc(InputStream ins) throws IOException, JDOMException {
        try
        {
            Document doc = new SAXBuilder().build(ins);
            return doc;
        }
        finally {
            ins.close();
        }
    }
    

}
