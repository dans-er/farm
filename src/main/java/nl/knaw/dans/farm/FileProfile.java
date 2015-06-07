package nl.knaw.dans.farm;

import java.util.Date;

import org.joda.time.DateTime;

import com.yourmediashelf.fedora.generated.management.DatastreamProfile;

public class FileProfile
{
    
    private final DatastreamProfile dsProfile;
    
    private DateTime lastModified;
    
    public FileProfile(DatastreamProfile dsProfile) {
        this.dsProfile = dsProfile;
    }
    
    public DatastreamProfile getDatastreamProfile() {
        return dsProfile;
    }
    
    public String getIdentifier() {
        return dsProfile.getPid();
    }
    
    public DateTime getCreationDate() {
        return new DateTime(dsProfile.getDsCreateDate().toGregorianCalendar().getTime());
    }

    public DateTime getLastModified()
    {
        return lastModified;
    }

    public void setLastModified(Date lastModified)
    {
        this.lastModified = new DateTime(lastModified);
    }
    
    public String getChecksum() {
        return dsProfile.getDsChecksum();
    }
    
    public String getChecksumType() {
        return dsProfile.getDsChecksumType();
    }
    
    public String getControlGroup() {
        return dsProfile.getDsControlGroup();
    }
    
    public String getFormatURI() {
        return dsProfile.getDsFormatURI();
    }
    
    public String getLabel() {
        return dsProfile.getDsLabel();
    }
    
    public String getMediaType() {
        return dsProfile.getDsMIME();
    }
    
    public long getSize() {
        return dsProfile.getDsSize().longValue();
    }
    
    public String getState() {
        return dsProfile.getDsState();
    }
    
    public boolean isVersionable() {
        return "true".equalsIgnoreCase(dsProfile.getDsVersionable());
    }
    
    public String getVersionId() {
        return dsProfile.getDsVersionID();
    }

}
