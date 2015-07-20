package nl.knaw.dans.farm.barn;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class Reporter
{
    
    public static final String SEP = ";";
    public static final String NL = "\n";
    public static final String MISSING_FILE = "missing.csv";
    public static final String ERROR_FILE = "errors.csv";
    
    private String missingFile;
    private String errorFile;

    public String getMissingFile()
    {
        if (missingFile == null) {
            missingFile = MISSING_FILE;
        }
        return missingFile;
    }
    
    public String getErrorFile() {
        if (errorFile == null) {
            errorFile = ERROR_FILE;
        }
        return errorFile;
    }

    public void setMissingFile(String missingFile)
    {
        this.missingFile = missingFile;
    }
    
    public void reportMissing(String identifier, String reason) {
        String line = new Date() + SEP + "missing" + SEP + identifier + SEP + reason + NL;
        report(getMissingFile(), line);
    }
    
    public void reportError(String identifier, String filename, Throwable e) {
        String line = new Date() + SEP + "error" + SEP + identifier + SEP + filename + SEP + e.getMessage() + NL;
        report(getErrorFile(), line);
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
