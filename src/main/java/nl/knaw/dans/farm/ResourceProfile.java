package nl.knaw.dans.farm;

import org.joda.time.DateTime;

import com.yourmediashelf.fedora.generated.access.ObjectProfile;

public class ResourceProfile
{
    
    private final ObjectProfile objectProfile;
    
    public ResourceProfile(ObjectProfile objectProfile) {
        this.objectProfile = objectProfile;
    }
    
    public ObjectProfile getObjectProfile() {
        return objectProfile;
    }
    
    public String getIdentifier() {
        return objectProfile.getPid();
    }
    
    public String getOwnerId() {
        return objectProfile.getObjOwnerId();
    }
    
    public String getLabel() {
        return objectProfile.getObjLabel();
    }
    
    public String getState() {
        return objectProfile.getObjState();
    }
    
    public DateTime getCreationDate() {
        return new DateTime(objectProfile.getObjCreateDate().toGregorianCalendar().getTime());
    }
    
    public DateTime getLastModifiecd() {
        return new DateTime(objectProfile.getObjLastModDate().toGregorianCalendar().getTime());
    }

}
