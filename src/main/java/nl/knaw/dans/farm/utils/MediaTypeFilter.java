package nl.knaw.dans.farm.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tika.mime.MediaType;

public class MediaTypeFilter
{
    
    public enum Type {
        MESSAGE, MULTIPART, APPLICATION, VIDEO, AUDIO, IMAGE, TEXT
    }
    
    private List<Type> includes = new ArrayList<Type>();
    
    public MediaTypeFilter(Type... types) {
        includes = Arrays.asList(types);
    }
    
    public boolean isIncluded(String mediaType) {
        MediaType mt = MediaType.parse(mediaType);
        return mt == null ? false : isIncluded(MediaType.parse(mediaType));
    }
    
    public boolean isIncluded(MediaType mediaType) {
        String type = mediaType.getType();
        Type t;
        try
        {
            t = Type.valueOf(type.toUpperCase());
        }
        catch (IllegalArgumentException e)
        {
            return false;
        }
        return includes.contains(t);
    }

}
