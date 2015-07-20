package nl.knaw.dans.farm;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import de.schlichtherle.io.FileInputStream;

public class FileInformationPackage
{
    
    public static final String FARM_FILES = "FARM_FILES";

    public static final String DS_ID_EASY_FILE = "EASY_FILE";
    
    private static File WORK_DIR;
    
    private final String identifier;
    
    private FileMetadata fileMetadata;
    private ResourceProfile resourceProfile;
    private FileProfile fileProfile;
    //private InputStream ins;
    //private UnclosableBufferedInputStream bins;
    private FarmData farmData;
    private File file;
    private List<InputStream> streams = new ArrayList<InputStream>();
    
    public FileInformationPackage(String identifier) {
        this.identifier = identifier;
        farmData = new FarmData();
    }
    
    public String getIdentifier() {
        return identifier;
    }
    
    public FileMetadata getFileMetadata() {
        return fileMetadata;
    }
    
    public void setFileMetadata(FileMetadata fileMetadata)
    {
        this.fileMetadata = fileMetadata;
    }

    public ResourceProfile getResourceProfile()
    {
        return resourceProfile;
    }

    public void setResourceProfile(ResourceProfile resourceProfile)
    {
        this.resourceProfile = resourceProfile;
    }

    public FileProfile getFileProfile() {
        return fileProfile;
    }
    
    public void setFileProfile(FileProfile fileProfile)
    {
        this.fileProfile = fileProfile;
    }

    public File getFile() throws IOException
    {
//        if (file == null) {
//            String filename = getFileMetadata().getFilename();
//            if (filename == null | "".equals(filename)) {
//                filename = "tmp.file";
//            }
//            file = new File(getWorkDir(), filename);
//            FileUtils.copyInputStreamToFile(getInputStream(), file);
//        }
        return file;
    }

    public FarmData getFarmData()
    {
        return farmData;
    }
    
    public void setInutStream(InputStream ins) throws IOException {
//        this.ins = ins;
//        bins = new UnclosableBufferedInputStream(ins);
        String filename = getFileMetadata().getFilename();
        if (filename == null | "".equals(filename)) {
            filename = "tmp.file";
        }
        file = new File(getWorkDir(), filename);
        FileUtils.copyInputStreamToFile(ins, file);
    }
    
    public InputStream getInputStream() throws IOException {
//        bins.reset();
//        return bins;
        InputStream fis = new FileInputStream(file);
        streams.add(fis);
        return fis;
    }
    
    public void close() throws IOException {
        //IOUtils.closeQuietly(ins);
        for (InputStream is : streams) {
            is.close();
        }
        streams.clear();
        if (file != null) {
            file.delete();
        }
    }
    
    private File getWorkDir() {
        if (WORK_DIR == null) {
            WORK_DIR = new File(FARM_FILES);
            WORK_DIR.mkdirs();
        }
        return WORK_DIR;
    }

}
