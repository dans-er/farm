package nl.knaw.dans.farm.rdb;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import nl.knaw.dans.farm.FileInformationPackage;

@Entity
@Table(name = "profile", indexes = {@Index(columnList="fedora_identifier")})
public class Profile extends DBEntity
{
    
    private static final long serialVersionUID = 7710778080642553612L;
    
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
    
    protected Profile() {
        
    }
    
    public Profile(FileInformationPackage fip) {
        setIdentifier(fip.getIdentifier());
        identifier = fip.getIdentifier();
        
        setObjLabel(fip.getResourceProfile().getLabel());
        setObjCreationDate(convert(fip.getResourceProfile().getCreationDate()));
        setOblLastModified(convert(fip.getResourceProfile().getLastModifiecd()));
        setObjOwnerId(fip.getResourceProfile().getOwnerId());
        setObjState(fip.getResourceProfile().getState());
        
        setDsVersionId(fip.getFileProfile().getVersionId());
        setDsCreationDate(convert(fip.getFileProfile().getCreationDate()));
        setDsLastModified(convert(fip.getFileProfile().getLastModified()));
        setDsLabel(fip.getFileProfile().getLabel());
        setDsVersionable(fip.getFileProfile().isVersionable());
        setDsState(fip.getFileProfile().getState());
        setDsControlGroup(fip.getFileProfile().getControlGroup());
        setDsChecksumType(fip.getFileProfile().getChecksumType());
        setDsChecksum(fip.getFileProfile().getChecksum());
        setDsFormatUri(fip.getFileProfile().getFormatURI());
        setDsMediaType(fip.getFileProfile().getMediaType());
        setDsSize(fip.getFileProfile().getSize());
        
        fip.getFileProfile().getSize();
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

}
