package nl.knaw.dans.farm.fits;

import java.util.HashSet;
import java.util.List;
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

import edu.harvard.hul.ois.fits.FitsOutput;
import edu.harvard.hul.ois.fits.identity.FitsIdentity;
import edu.harvard.hul.ois.fits.tools.ToolInfo;

@Entity
@Table(name = "fitsprofile", indexes = {@Index(columnList="fedora_identifier")})
public class FitsProfile extends DBEntity
{

    private static final long serialVersionUID = -4307015295735418345L;
    
    @Id
    @GeneratedValue
    @Column(name = "fitsprofile_id")
    private Long id;
    
    @NaturalId
    @Column(name = "fedora_identifier", nullable = false, unique = true)
    private String identifier;
    
    @Column(name = "mediatype_conflict")
    private boolean mediaTypeConflict;
    
    @OneToMany(mappedBy = "fitsProfile", fetch = FetchType.EAGER)
    @Cascade({CascadeType.ALL})
    private Set<FitsMediatype> mediatypes;
    
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
    
    public void update(FitsOutput fop) {
        getMediatypes().clear();
        List<FitsIdentity> identities = fop.getIdentities();
        setMediaTypeConflict(identities.size() != 1);
        if (hasMediaTypeConflict()) {
            for (FitsIdentity identity : identities) {
                String mediatype = identity.getMimetype();
                for (ToolInfo tool : identity.getReportingTools()) {
                    addMediatype(tool.getName(), tool.getVersion(), mediatype);
                }
            }
        } else {
            FitsIdentity identity = identities.get(0);
            addMediatype(identity.getToolName(), identity.getToolVersion(), identity.getMimetype());
        }
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
    
    public void addMediatype(String toolName, String toolVersion, String mediatype) {
        addMediatype(new FitsMediatype(toolName, toolVersion, mediatype));
    }
    
    public void addMediatype(FitsMediatype mediatype) {
        mediatype.setIdentifier(getIdentifier());
        mediatype.setParent(this);
        getMediatypes().add(mediatype);
    }

    public boolean hasMediaTypeConflict()
    {
        return mediaTypeConflict;
    }

    public void setMediaTypeConflict(boolean mediaTypeConflict)
    {
        this.mediaTypeConflict = mediaTypeConflict;
    }

    
}
