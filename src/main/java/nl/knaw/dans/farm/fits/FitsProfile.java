package nl.knaw.dans.farm.fits;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import nl.knaw.dans.farm.FileInformationPackage;
import nl.knaw.dans.farm.rdb.DBEntity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "fitsprofile", indexes = {@Index(columnList="fedora_identifier")})
public class FitsProfile extends DBEntity
{
    
    public static final String ELEMENT_NAME_MEDIA_TYPE = "mediatype";
    public static final String ELEMENT_NAME_EXTERNAL_ID = "exid";

    private static final long serialVersionUID = -4307015295735418345L;
    
    @Id
    @GeneratedValue
    @Column(name = "fitsprofile_id")
    private Long id;
    
    @NaturalId
    @Column(name = "fedora_identifier", nullable = false, unique = true)
    private String identifier;
    
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, orphanRemoval = true)
    @Cascade({CascadeType.ALL})
    private Set<FitsMediatype> mediatypes;
    
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, orphanRemoval = true)
    @Cascade({CascadeType.ALL})
    private Set<FitsFileInfo> fileInformation;
    
    
    protected FitsProfile()
    {
        
    }
    
    public FitsProfile(FileInformationPackage fip) {
        update(fip);
    }

    public void update(FileInformationPackage fip)
    {
        setIdentifier(fip.getIdentifier());       
    }
    

    @Override
    public Long getId()
    {
        return id;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }

    public Set<FitsMediatype> getMediatypes()
    {
        if (mediatypes == null) {
            mediatypes = new HashSet<FitsMediatype>();
        }
        return mediatypes;
    }
    
    public FitsMediatype addMediatype(String elementName, String toolName, String toolVersion, String mediatype, String format) {
        return addMediatype(new FitsMediatype(elementName, toolName, toolVersion, mediatype, format));
    }
    
    public FitsMediatype addMediatype(FitsMediatype mediatype) {
        mediatype.setIdentifier(getIdentifier(), this);
        getMediatypes().add(mediatype);
        return mediatype;
    }
    
    public Set<FitsFileInfo> getFileInformation() {
        if (fileInformation == null) {
            fileInformation = new HashSet<FitsFileInfo>();
        }
        return fileInformation;
    }
    
    public FitsFileInfo addFileInfo(FitsFileInfo fileInfo) {
        fileInfo.setIdentifier(identifier, this);
        getFileInformation().add(fileInfo);
        return fileInfo;
    }
    
}
