package nl.knaw.dans.farm;

/**
 * Bean for carrying info on an EASY file-item. The 'F-*' properties are things found in
 * the datastream EASY_FILE_METADATA.
 *
 */
public class FarmData
{
        
    private String mediaType;
    private String language;
    private boolean languageCertain;
    
    public FarmData() {
        
    }

    public String getMediaType() {
        return mediaType;
    }
    
    public FarmData setMediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public String getLanguage()
    {
        return language;
    }

    public FarmData setLanguage(String language)
    {
        this.language = language;
        return this;
    }

    public boolean isLanguageCertain()
    {
        return languageCertain;
    }

    public FarmData setLanguageCertain(boolean languageCertain)
    {
        this.languageCertain = languageCertain;
        return this;
    }
    
    

}
