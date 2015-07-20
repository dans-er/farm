package nl.knaw.dans.farm.fits;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "fitsmediatype", indexes = {
        @Index(name = "fitsmediatype_fedora_identifier_index", columnList = "fedora_identifier", unique = false)
})
public class FitsMediatype extends AbstractFitsElement
{

    private static final long serialVersionUID = -8226690906593112415L;

    @Id
    @GeneratedValue
    @Column(name = "mediatype_id")
    private Long id;
    
    @Column(name = "format")
    private String format;
    
    protected FitsMediatype()
    {
        
    }
    
    public FitsMediatype(String elementName, String toolName, String toolVersion, String mediatype, String format) {
        super(elementName, toolName, toolVersion);
        setValue(mediatype);
        this.format = format;
    }
    
    public Long getId()
    {
        return id;
    }

    
    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }


}
