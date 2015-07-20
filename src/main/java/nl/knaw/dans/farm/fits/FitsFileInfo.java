package nl.knaw.dans.farm.fits;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "fitsfileinfo", indexes = {
        @Index(name = "fitsfileinfo_fedora_identifier_index", columnList = "fedora_identifier", unique = false)
})
public class FitsFileInfo  extends AbstractFitsElement
{
    
    public enum Element
    {
        copyrightBasis, 
        copyrightNote,
        created,
        creatingApplicationName,
        creatingApplicationVersion,
        creatingos,
        //filepath,         // omitted. not relevant.
        //filename,         // omitted. we evaluate inputStreams.
        //fslastmodified,   // omitted. we evaluate inputStreams. 
        inhibitorType,
        inhibitorTarget,
        lastmodified,
        md5checksum,
        rightsBasis,
        size

    }

    private static final long serialVersionUID = 5262896024578566642L;
    
    @Id
    @GeneratedValue
    @Column(name = "fileinfo_id")
    private Long id;

    
    public Long getId()
    {
        return id;
    }
    
    public static boolean excludeElement(String name) {
        return "filepath".equalsIgnoreCase(name) 
                | "filename".equalsIgnoreCase(name)
                | "fslastmodified".equalsIgnoreCase(name);
    }

}
