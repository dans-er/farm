package nl.knaw.dans.farm.fed;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import nl.knaw.dans.farm.FileInformationPackage;
import nl.knaw.dans.farm.FileMetadata;
import nl.knaw.dans.farm.FileProfile;
import nl.knaw.dans.farm.ResourceProfile;
import nl.knaw.dans.farm.rdb.DBEntity;

@Entity
@Table(name = "profile", indexes = {
        @Index(name = "profile_fedora_identifier_index", columnList="fedora_identifier", unique = true)})
public class Profile extends DBEntity
{
    
    private static final long serialVersionUID = 7710778080642553612L;
    
    @Id
    @GeneratedValue
    @Column(name = "profile_id")
    private Long id;
    
    @NaturalId
    @Column(name = "fedora_identifier", nullable = false, unique = true)
    private String identifier;
    
    @Column(name = "obj_label")
    private String objLabel;
    @Column(name = "obj_creation_date")
    private Date objCreationDate;
    @Column(name = "obj_last_modified")
    private Date oblLastModified;
    @Column(name = "obj_owner_id")
    private String objOwnerId;
    @Column(name = "obj_state")
    private String objState;
    
    @Column(name = "ds_version_id")
    private String dsVersionId;
    @Column(name = "ds_creation_date")
    private Date dsCreationDate;
    @Column(name = "ds_last_modified")
    private Date dsLastModified;
    @Column(name = "ds_label")
    private String dsLabel;
    @Column(name = "ds_versionable")
    private boolean dsVersionable;
    @Column(name = "ds_state")
    private String dsState;
    @Column(name = "ds_control_group")
    private String dsControlGroup;
    @Column(name = "ds_checksum_type")
    private String dsChecksumType;
    @Column(name = "ds_checksum")
    private String dsChecksum;
    @Column(name = "ds_format_uri")
    private String dsFormatUri;
    @Column(name = "ds_mediatype")
    private String dsMediaType;
    @Column(name = "ds_size")
    private long dsSize;
    
    @Column(name = "fmd_identifier")
    private String fmdIdentifier;
    @Column(name = "fmd_parent_id")
    private String fmdParentId;
    @Column(name = "fmd_dataset_id")
    private String fmdDatasetId;
    @Column(name = "fmd_filename", length = 512)
    private String fmdFileName;
    @Column(name = "fmd_path", length = 1024)
    private String fmdPath;
    @Column(name = "fmd_mediatype")
    private String fmdMediatype;
    @Column(name = "fmd_size")
    private long fmdSize;
    @Column(name = "fmd_creator_role")
    private String fmdCreatorRole;
    @Column(name = "fmd_visible_to")
    private String fmdVisibleTo;
    @Column(name = "fmd_accessible_to")
    private String fmdAccessibleTo;
    
    protected Profile() {
        
    }
    
    public Profile(FileInformationPackage fip) {
        update(fip);
    }

    public void update(FileInformationPackage fip)
    {
        setIdentifier(fip.getIdentifier());
        
        ResourceProfile rp = fip.getResourceProfile();
        setObjLabel(rp.getLabel());
        setObjCreationDate(convert(rp.getCreationDate()));
        setOblLastModified(convert(rp.getLastModifiecd()));
        setObjOwnerId(rp.getOwnerId());
        setObjState(rp.getState());
        
        FileProfile fp = fip.getFileProfile();
        setDsVersionId(fp.getVersionId());
        setDsCreationDate(convert(fp.getCreationDate()));
        setDsLastModified(convert(fp.getLastModified()));
        setDsLabel(fp.getLabel());
        setDsVersionable(fp.isVersionable());
        setDsState(fp.getState());
        setDsControlGroup(fp.getControlGroup());
        setDsChecksumType(fp.getChecksumType());
        setDsChecksum(fp.getChecksum());
        setDsFormatUri(fp.getFormatURI());
        setDsMediaType(fp.getMediaType());
        setDsSize(fp.getSize());
        
        FileMetadata fmd = fip.getFileMetadata();
        setFmdIdentifier(fmd.getIdentifier());
        setFmdParentId(fmd.getParentId());
        setFmdDatasetId(fmd.getDatasetId());
        setFmdFileName(fmd.getFilename());
        setFmdPath(fmd.getPath());
        setFmdMediatype(fmd.getMediaType());
        setFmdSize(fmd.getSize());
        setFmdCreatorRole(fmd.getCreatorRole());
        setFmdVisibleTo(fmd.getVisibleTo());
        setFmdAccessibleTo(fmd.getAccessibleTo());
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

    public String getObjLabel()
    {
        return objLabel;
    }

    public void setObjLabel(String objLabel)
    {
        this.objLabel = objLabel;
    }

    public Date getObjCreationDate()
    {
        return objCreationDate;
    }

    public void setObjCreationDate(Date objCreationDate)
    {
        this.objCreationDate = objCreationDate;
    }

    public Date getOblLastModified()
    {
        return oblLastModified;
    }

    public void setOblLastModified(Date oblLastModified)
    {
        this.oblLastModified = oblLastModified;
    }

    public String getObjOwnerId()
    {
        return objOwnerId;
    }

    public void setObjOwnerId(String objOwnerId)
    {
        this.objOwnerId = objOwnerId;
    }

    public String getObjState()
    {
        return objState;
    }

    public void setObjState(String objState)
    {
        this.objState = objState;
    }

    public String getDsVersionId()
    {
        return dsVersionId;
    }

    public void setDsVersionId(String dsVersionId)
    {
        this.dsVersionId = dsVersionId;
    }

    public Date getDsCreationDate()
    {
        return dsCreationDate;
    }

    public void setDsCreationDate(Date dsCreationDate)
    {
        this.dsCreationDate = dsCreationDate;
    }

    public Date getDsLastModified()
    {
        return dsLastModified;
    }

    public void setDsLastModified(Date dsLastModified)
    {
        this.dsLastModified = dsLastModified;
    }

    public String getDsLabel()
    {
        return dsLabel;
    }

    public void setDsLabel(String dsLabel)
    {
        this.dsLabel = dsLabel;
    }

    public boolean isDsVersionable()
    {
        return dsVersionable;
    }

    public void setDsVersionable(boolean dsVersionable)
    {
        this.dsVersionable = dsVersionable;
    }

    public String getDsState()
    {
        return dsState;
    }

    public void setDsState(String dsState)
    {
        this.dsState = dsState;
    }

    public String getDsControlGroup()
    {
        return dsControlGroup;
    }

    public void setDsControlGroup(String dsControlGroup)
    {
        this.dsControlGroup = dsControlGroup;
    }

    public String getDsChecksumType()
    {
        return dsChecksumType;
    }

    public void setDsChecksumType(String dsChecksumType)
    {
        this.dsChecksumType = dsChecksumType;
    }

    public String getDsChecksum()
    {
        return dsChecksum;
    }

    public void setDsChecksum(String dsChecksum)
    {
        this.dsChecksum = dsChecksum;
    }

    public String getDsFormatUri()
    {
        return dsFormatUri;
    }

    public void setDsFormatUri(String dsFormatUri)
    {
        this.dsFormatUri = dsFormatUri;
    }

    public String getDsMediaType()
    {
        return dsMediaType;
    }

    public void setDsMediaType(String dsMediaType)
    {
        this.dsMediaType = dsMediaType;
    }

    public long getDsSize()
    {
        return dsSize;
    }

    public void setDsSize(long dsSize)
    {
        this.dsSize = dsSize;
    }

    public String getFmdDatasetId()
    {
        return fmdDatasetId;
    }

    public void setFmdDatasetId(String fmdDatasetId)
    {
        this.fmdDatasetId = fmdDatasetId;
    }

    public String getFmdIdentifier()
    {
        return fmdIdentifier;
    }

    public void setFmdIdentifier(String fmdIdentifier)
    {
        this.fmdIdentifier = fmdIdentifier;
    }

    public String getFmdParentId()
    {
        return fmdParentId;
    }

    public void setFmdParentId(String fmdParentId)
    {
        this.fmdParentId = fmdParentId;
    }

    public String getFmdFileName()
    {
        return fmdFileName;
    }

    public void setFmdFileName(String fmdFileName)
    {
        this.fmdFileName = fmdFileName;
    }

    public String getFmdPath()
    {
        return fmdPath;
    }

    public void setFmdPath(String fmdPath)
    {
        this.fmdPath = fmdPath;
    }

    public String getFmdMediatype()
    {
        return fmdMediatype;
    }

    public void setFmdMediatype(String fmdMediatype)
    {
        this.fmdMediatype = fmdMediatype;
    }

    public long getFmdSize()
    {
        return fmdSize;
    }

    public void setFmdSize(long fmdSize)
    {
        this.fmdSize = fmdSize;
    }

    public String getFmdCreatorRole()
    {
        return fmdCreatorRole;
    }

    public void setFmdCreatorRole(String fmdCreatorRole)
    {
        this.fmdCreatorRole = fmdCreatorRole;
    }

    public String getFmdVisibleTo()
    {
        return fmdVisibleTo;
    }

    public void setFmdVisibleTo(String fmdVisibleTo)
    {
        this.fmdVisibleTo = fmdVisibleTo;
    }

    public String getFmdAccessibleTo()
    {
        return fmdAccessibleTo;
    }

    public void setFmdAccessibleTo(String fmdAccessibleTo)
    {
        this.fmdAccessibleTo = fmdAccessibleTo;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Profile) {
            Profile other = (Profile) obj;
            if (other.identifier == null || this.identifier == null) {
                return false;
            } else {
                return identifier.equals(other.identifier);
            }
        }
        return false;
    }
    
    @Override
    public int hashCode()
    {
        if (identifier == null) {
            return 0;
        } else {
            return identifier.hashCode();
        }
    }

}
