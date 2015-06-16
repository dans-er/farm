package nl.knaw.dans.farm.barn;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Reporter
{
    
    public static final String SEP = ";";
    public static final String NL = "\n";
    public static final String MISSING_FILE = "missing.csv";
    
    private String missingFile;

    public String getMissingFile()
    {
        if (missingFile == null) {
            missingFile = MISSING_FILE;
        }
        return missingFile;
    }

    public void setMissingFile(String missingFile)
    {
        this.missingFile = missingFile;
    }
    
    public void reportMissing(String identifier, String reason) {
        String line = identifier + SEP + reason + NL;
        report(getMissingFile(), line);
    }
    
    protected void report(String file, String line) {
        try
        {
            FileUtils.writeStringToFile(new File(file), line, "UTF-8", true);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

}
