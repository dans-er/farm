package nl.knaw.dans.farm.barn;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import nl.knaw.dans.farm.Discriminator;
import nl.knaw.dans.farm.FileInformationPackage;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.schlichtherle.io.FileInputStream;

public class DatasetDiscriminator implements Discriminator
{
    
    private static Logger logger = LoggerFactory.getLogger(DatasetDiscriminator.class);
    
    private List<String> datasetList;
    

    @Override
    public boolean accept(FileInformationPackage fip)
    {
        String datasetId = fip.getFileMetadata().getDatasetId();
        boolean contains = getDatasetList().contains(datasetId);
        if (contains) {
            logger.info("Not accepting fip with datasetId {}", datasetId);
        }
        return !contains;
    }
    
    private List<String> getDatasetList() {
        if (datasetList == null) {
            File file = new File("dataset_list.txt");
            if (file.exists()) {
                FileInputStream fis = null;
                try
                {
                    fis = new FileInputStream(file);
                    datasetList = IOUtils.readLines(fis);
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                } finally {
                    IOUtils.closeQuietly(fis);
                }
                
            } else {
                datasetList = Collections.emptyList();
            }
        }
        return datasetList;
    }

}
