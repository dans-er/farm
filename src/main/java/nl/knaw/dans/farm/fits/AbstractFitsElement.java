package nl.knaw.dans.farm.fits;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.NaturalId;

@MappedSuperclass
public abstract class AbstractFitsElement implements Serializable
{

    private static final long serialVersionUID = 5063810653122695253L;

    @NaturalId
    @Column(name = "fedora_identifier", nullable = false)
    private String identifier;
    
    @NaturalId
    @Column(name = "element_name", nullable = false)
    private String elementName;
    
    @NaturalId
    @Column(name = "tool_name", nullable = false)
    private String toolName;
    
    @NaturalId
    @Column(name = "tool_version", nullable = false)
    private String toolVersion;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "value")
    private String value;

    @ManyToOne(optional = false)
    private FitsProfile parent;
    
    protected AbstractFitsElement() {}
    
    public AbstractFitsElement(String elementName, String toolName, String toolVersion) {
        this.elementName = elementName;
        this.toolName = toolName;
        this.toolVersion = toolVersion;
    }
    
    public abstract Long getId();

    public String getIdentifier()
    {
        return identifier;
    }


    protected void setIdentifier(String identifier, FitsProfile parent)
    {
        this.identifier = identifier;
        this.parent = parent;
    }

    public String getElementName()
    {
        return elementName;
    }

    public void setElementName(String elementName)
    {
        this.elementName = elementName;
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

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj == this) return true;
        if(obj == null) return false;
        if (obj instanceof AbstractFitsElement) {
            AbstractFitsElement other = (AbstractFitsElement) obj;
            return new EqualsBuilder()
                .append(identifier, other.identifier)
                .append(elementName, other.elementName)
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
            .append(elementName)
            .append(toolName)
            .append(toolVersion)
            .toHashCode();
    }
    
    

}
