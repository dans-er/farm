package nl.knaw.dans.farm.fits;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fitsfilestatus")
public class FitsFileStatus extends AbstractFitsElement
{
    
    public enum Element {
        message,
        valid,
        well_formed,
    }

    private static final long serialVersionUID = 1618616217734380630L;
    
    @Id
    @GeneratedValue
    @Column(name = "filestatus_id")
    private Long id;

    @Override
    public Long getId()
    {
        return id;
    }

}