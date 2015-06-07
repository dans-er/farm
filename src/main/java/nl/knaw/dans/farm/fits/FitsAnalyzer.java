package nl.knaw.dans.farm.fits;

import java.io.IOException;

import nl.knaw.dans.farm.Analyzer;
import nl.knaw.dans.farm.FileInformationPackage;
import nl.knaw.dans.farm.ProcessingException;
import nl.knaw.dans.fits.FitsWrap;

import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import edu.harvard.hul.ois.fits.exceptions.FitsConfigurationException;
import edu.harvard.hul.ois.fits.exceptions.FitsException;

public class FitsAnalyzer implements Analyzer
{

    @Override
    public void process(FileInformationPackage fip) throws ProcessingException
    {
        try
        {
            Document doc = FitsWrap.instance().extract(fip.getFile());
            System.err.println(new XMLOutputter(Format.getPrettyFormat()).outputString(doc));
        }
        catch (FitsConfigurationException e)
        {
            throw new ProcessingException(e);
        }
        catch (FitsException e)
        {
            throw new ProcessingException(e);
        }
        catch (IOException e)
        {
            throw new ProcessingException(e);
        }

    }
}
