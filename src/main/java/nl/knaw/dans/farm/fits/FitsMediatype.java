package nl.knaw.dans.farm.fits;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nl.knaw.dans.farm.rdb.DBEntity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "fitsmediatype")
public class FitsMediatype extends DBEntity
{

    private static final long serialVersionUID = -8810255315391155551L;
    
    @Id
    @GeneratedValue
    @Column(name = "mediatype_id")
    private Long id;
    
    @NaturalId
    @Column(name = "fedora_identifier", nullable = false)
    private String identifier;
    
    @NaturalId
    @Column(name = "tool_name", nullable = false)
    private String toolName;
    
    @NaturalId
    @Column(name = "tool_version", nullable = false)
    private String toolVersion;
    
    @Column(name = "mediatype")
    private String mediatype;
    
    @ManyToOne(optional = false)
    private FitsProfile fitsProfile;
    
    protected FitsMediatype()
    {
        
    }
    
    public FitsMediatype(String toolName, String toolVersion, String mediatype) {
        this.toolName = toolName;
        this.toolVersion = toolVersion;
        this.mediatype = mediatype;
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


    protected void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }
    
    protected void setParent(FitsProfile fitsProfile) {
        this.fitsProfile = fitsProfile;
    }


    public String getToolName()
    {
        return toolName;
    }


    public void setToolName(String toolName)
    {
        this.toolName = toolName;
    }


    public String getToolVersion()
    {
        return toolVersion;
    }


    public void setToolVersion(String toolVersion)
    {
        this.toolVersion = toolVersion;
    }


    public String getMediatype()
    {
        return mediatype;
    }


    public void setMediatype(String mediatype)
    {
        this.mediatype = mediatype;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if(obj == this) return true;
        if(obj == null) return false;
        if (obj instanceof FitsMediatype) {
            FitsMediatype other = (FitsMediatype) obj;
            return new EqualsBuilder()
                .append(identifier, other.identifier)
                .append(toolName, other.toolName)
                .append(toolVersion, other.toolVersion)
                .isEquals();
        }
        return false;
    }
    
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder()
            .append(identifier)
            .append(toolName)
            .append(toolVersion)
            .toHashCode();
    }

}
