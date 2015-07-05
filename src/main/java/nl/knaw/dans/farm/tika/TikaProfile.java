package nl.knaw.dans.farm.tika;

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
@Table(name = "tikaprofile", indexes = {
        @Index(name = "tika_meta_fedora_identifier_index", columnList="fedora_identifier", unique = true)})
public class TikaProfile extends DBEntity
{

    private static final long serialVersionUID = -5025773153112962980L;
    
    @Id
    @GeneratedValue
    @Column(name = "tikaprofile_id")
    private Long id;
    
    @NaturalId
    @Column(name = "fedora_identifier", nullable = false, unique = true)
    private String identifier;
    
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, orphanRemoval = true)
    @Cascade({CascadeType.ALL})
    private Set<TikaMeta> metadata;
    
    @Column(name = "type")
    private String type;
    
    protected TikaProfile() {}
    
    public TikaProfile(FileInformationPackage fip) {
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

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
    
    public Set<TikaMeta> getMetadata() {
        if (metadata == null) {
            metadata = new HashSet<TikaMeta>();
        }
        return metadata;
    }
    
    public TikaMeta addMeta(TikaMeta meta) {
        meta.setIdentifier(getIdentifier(), this);
        getMetadata().add(meta);
        return meta;
    }
    
}
