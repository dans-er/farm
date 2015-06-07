package nl.knaw.dans.farm;

public class FileMetadata
{
    
    public static final String DS_ID_EASY_FILE_METADATA = "EASY_FILE_METADATA";
    
    private String identifier;
    private String parentId;
    private String datasetId;
    private String filename;
    private String path;
    private String mediaType;
    private long size;
    private String creatorRole;
    private String visibleTo;
    private String accessibleTo;
    
    public String getIdentifier()
    {
        return identifier;
    }
    
    public FileMetadata setIdentifier(String identifier)
    {
        this.identifier = identifier;
        return this;
    }
    
    public String getParentId()
    {
        return parentId;
    }
    
    public FileMetadata setParentId(String parentId)
    {
        this.parentId = parentId;
        return this;
    }
    
    public String getDatasetId()
    {
        return datasetId;
    }
    
    public FileMetadata setDatasetId(String datasetId)
    {
        this.datasetId = datasetId;
        return this;
    }
    
    public String getFilename()
    {
        return filename;
    }
    
    public FileMetadata setFilename(String filename)
    {
        this.filename = filename;
        return this;
    }
    
    public String getPath()
    {
        return path;
    }
    
    public FileMetadata setPath(String path)
    {
        this.path = path;
        return this;
    }
    
    public String getMediaType()
    {
        return mediaType;
    }
    public FileMetadata setMediaType(String mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }
    
    public long getSize()
    {
        return size;
    }
    
    public FileMetadata setSize(long size)
    {
        this.size = size;
        return this;
    }
    
    public FileMetadata setSize(String size) {
        long s = Long.parseLong(size);
        return setSize(s);
    }
    
    public String getCreatorRole()
    {
        return creatorRole;
    }
    
    public FileMetadata setCreatorRole(String creatorRole)
    {
        this.creatorRole = creatorRole;
        return this;
    }
    
    public String getVisibleTo()
    {
        return visibleTo;
    }
    
    public FileMetadata setVisibleTo(String visibleTo)
    {
        this.visibleTo = visibleTo;
        return this;
    }
    
    public String getAccessibleTo()
    {
        return accessibleTo;
    }
    
    public FileMetadata setAccessibleTo(String accessibleTo)
    {
        this.accessibleTo = accessibleTo;
        return this;
    }

}
