package nl.knaw.dans.farm.fits;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fitstechmd")
public class FitsTechMetadata extends AbstractFitsElement
{

    private static final long serialVersionUID = 1447238779969726570L;

    @Id
    @GeneratedValue
    @Column(name = "techmd_id")
    private Long id;

    @Override
    public Long getId()
    {
        return id;
    }

}
