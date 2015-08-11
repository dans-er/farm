package nl.knaw.dans.farm.barn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.knaw.dans.farm.Discriminator;
import nl.knaw.dans.farm.FileInformationPackage;

public class ExtensionDiscriminator implements Discriminator
{
    
    private static Logger logger = LoggerFactory.getLogger(ExtensionDiscriminator.class);

    @Override
    public boolean accept(FileInformationPackage fip)
    {
        boolean isGml = fip.getFileMetadata().getFilename().endsWith(".gml");
        if (isGml)
        {
            logger.info("Fip not accepted. Discriminating gml-files.");
        }
        return !isGml;
    }

}
